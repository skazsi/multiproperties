package hu.skzs.multiproperties.base.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class ImporterServiceTest
{
	private static final Column COLUMN = new Column();

	private static final String VALUE = "VALUE";
	private static final EmptyRecord EMPTY_1 = new EmptyRecord();
	private static final EmptyRecord EMPTY_2 = new EmptyRecord();
	private static final CommentRecord COMMENT_1 = new CommentRecord(VALUE + "1");
	private static final CommentRecord COMMENT_2 = new CommentRecord(VALUE + "2");

	private ImporterService underTest;

	/**
	 * Returns a newly created and empty table with a test column, does not containing any records.
	 * @return a newly created and empty table
	 */
	private Table getTable()
	{
		final Table table = new Table();
		table.getColumns().add(COLUMN);
		return table;
	}

	/**
	 * Returns a newly created {@link PropertyRecord}.
	 * @param value the value to be used for the new {@link PropertyRecord}
	 * @param columnValue the test column value for testing the normalization (during the import
	 * the column values are removed). It can be <code>null</code>
	 * @return
	 */
	private PropertyRecord getPropertyRecord(final String value, final String columnValue)
	{
		final PropertyRecord propertyRecord = new PropertyRecord(value);
		if (columnValue != null)
		{
			propertyRecord.putColumnValue(COLUMN, columnValue);
		}
		return propertyRecord;
	}

	@Test(expected = IllegalArgumentException.class)
	public void construction_NullTable()
	{
		// When
		new ImporterService(null, COLUMN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void performImport_RecordsNull()
	{
		// Given
		underTest = new ImporterService(getTable(), null);

		// When
		underTest.performImport(null, ImportMode.STRUCTURAL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void performImport_MethodInvalidStructutral()
	{
		// Given
		underTest = new ImporterService(getTable(), COLUMN);
		final List<AbstractRecord> records = new LinkedList<AbstractRecord>();

		// When
		underTest.performImport(records, ImportMode.STRUCTURAL);
	}

	@Test
	public void performImport_MethodStructuralIntoEmpty()
	{
		// Given
		underTest = new ImporterService(getTable(), null);

		final List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		final PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		final PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// When
		underTest.performImport(records, ImportMode.STRUCTURAL);

		// Then
		assertEquals(6, underTest.getTable().size());
		assertTrue(underTest.getTable().get(0) == COMMENT_1);
		assertTrue(underTest.getTable().get(1) == EMPTY_1);
		assertTrue(underTest.getTable().get(2) == propertyRecord1);
		assertNull(((PropertyRecord) underTest.getTable().get(2)).getColumnValue(COLUMN));
		assertTrue(underTest.getTable().get(3) == COMMENT_2);
		assertTrue(underTest.getTable().get(4) == EMPTY_2);
		assertTrue(underTest.getTable().get(5) == propertyRecord2);
		assertNull(((PropertyRecord) underTest.getTable().get(5)).getColumnValue(COLUMN));
	}

	@Test
	public void performImport_MethodStructuralIntoNonEmpty()
	{
		// Given
		final Table table = new Table();
		table.getColumns().add(COLUMN);
		final EmptyRecord emptyRecord = new EmptyRecord();
		final CommentRecord commentRecord = new CommentRecord();
		final PropertyRecord propertyRecord = getPropertyRecord(VALUE, VALUE + "column");
		table.add(emptyRecord);
		table.add(commentRecord);
		table.add(propertyRecord);
		underTest = new ImporterService(table, null);

		final List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		final PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		final PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// When
		underTest.performImport(records, ImportMode.STRUCTURAL);

		// Then
		assertEquals(9, underTest.getTable().size());
		assertTrue(underTest.getTable().get(0) == emptyRecord);
		assertTrue(underTest.getTable().get(1) == commentRecord);
		assertTrue(underTest.getTable().get(2) == propertyRecord);
		assertTrue(underTest.getTable().get(3) == COMMENT_1);
		assertTrue(underTest.getTable().get(4) == EMPTY_1);
		assertTrue(underTest.getTable().get(5) == propertyRecord1);
		assertNull(((PropertyRecord) underTest.getTable().get(5)).getColumnValue(COLUMN));
		assertTrue(underTest.getTable().get(6) == COMMENT_2);
		assertTrue(underTest.getTable().get(7) == EMPTY_2);
		assertTrue(underTest.getTable().get(8) == propertyRecord2);
		assertNull(((PropertyRecord) underTest.getTable().get(8)).getColumnValue(COLUMN));
	}

	@Test
	public void performImport_MethodKeyValueIntoEmpty()
	{
		// Given
		underTest = new ImporterService(getTable(), COLUMN);

		final List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		final PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		final PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// When
		underTest.performImport(records, ImportMode.KEY_VALUE);

		// Then
		assertEquals(1, underTest.getTable().size());
		assertTrue(underTest.getTable().get(0) == propertyRecord1);
		assertTrue(((PropertyRecord) underTest.getTable().get(0)).getColumnValue(COLUMN).equals(VALUE));
	}

	@Test
	public void performImport_MethodKeyValueIntoNonEmpty()
	{
		// Given
		final Table table = new Table();
		table.getColumns().add(COLUMN);
		final EmptyRecord emptyRecord = new EmptyRecord();
		final CommentRecord commentRecord = new CommentRecord();
		final PropertyRecord propertyRecord = getPropertyRecord(VALUE, VALUE + "column");
		table.add(emptyRecord);
		table.add(commentRecord);
		table.add(propertyRecord);
		underTest = new ImporterService(table, COLUMN);

		final List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		final PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		final PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// When
		underTest.performImport(records, ImportMode.KEY_VALUE);

		// Then
		assertEquals(3, underTest.getTable().size());
		assertTrue(underTest.getTable().get(0) == emptyRecord);
		assertTrue(underTest.getTable().get(1) == commentRecord);
		assertTrue(underTest.getTable().get(2) == propertyRecord);
		assertTrue(((PropertyRecord) underTest.getTable().get(2)).getColumnValue(COLUMN).equals(VALUE));
	}

	@Test
	public void performImport_MethodValueIntoEmpty()
	{
		// Given
		underTest = new ImporterService(getTable(), COLUMN);

		final List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		final PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		final PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// When
		underTest.performImport(records, ImportMode.VALUE);

		// Then
		assertEquals(0, underTest.getTable().size());
	}

	@Test
	public void performImport_MethodValueIntoNonEmpty()
	{
		// Given
		final Table table = new Table();
		table.getColumns().add(COLUMN);
		final EmptyRecord emptyRecord = new EmptyRecord();
		final CommentRecord commentRecord = new CommentRecord();
		final PropertyRecord propertyRecord = getPropertyRecord(VALUE, VALUE + "column");
		table.add(emptyRecord);
		table.add(commentRecord);
		table.add(propertyRecord);
		underTest = new ImporterService(table, COLUMN);

		final List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		final PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		final PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// When
		underTest.performImport(records, ImportMode.VALUE);

		// Then
		assertEquals(3, underTest.getTable().size());
		assertTrue(underTest.getTable().get(0) == emptyRecord);
		assertTrue(underTest.getTable().get(1) == commentRecord);
		assertTrue(underTest.getTable().get(2) == propertyRecord);
		assertTrue(((PropertyRecord) underTest.getTable().get(2)).getColumnValue(COLUMN).equals(VALUE));
	}
}
