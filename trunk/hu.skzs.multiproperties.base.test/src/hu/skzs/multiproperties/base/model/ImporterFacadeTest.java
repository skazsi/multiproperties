package hu.skzs.multiproperties.base.model;

import hu.skzs.multiproperties.base.api.IImporter;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class ImporterFacadeTest
{

	private static final Column COLUMN = new Column();

	private static final String VALUE = "VALUE";
	private static final EmptyRecord EMPTY_1 = new EmptyRecord();
	private static final EmptyRecord EMPTY_2 = new EmptyRecord();
	private static final CommentRecord COMMENT_1 = new CommentRecord(VALUE + "1");
	private static final CommentRecord COMMENT_2 = new CommentRecord(VALUE + "2");

	private ImporterFacade facade;

	@Before
	public void setUp()
	{
		facade = new ImporterFacade();
		facade.init(getTable(), COLUMN);
	}

	public void tearDown()
	{
		facade = null;
	}

	/**
	 * Returns a newly created and empty table with a test column, does not containing any records.
	 * @return a newly created and empty table
	 */
	private Table getTable()
	{
		Table table = new Table();
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
	private PropertyRecord getPropertyRecord(String value, String columnValue)
	{
		PropertyRecord propertyRecord = new PropertyRecord(value);
		if (columnValue != null)
			propertyRecord.putColumnValue(COLUMN, columnValue);
		return propertyRecord;
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.ImporterFacade()}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructionNull()
	{
		// when
		facade.init(null, COLUMN);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.ImporterFacade#performImport(Table,int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPerformImportTableNull()
	{
		// when
		facade.performImport(null, IImporter.METHOD_STRUCTURAL);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.ImporterFacade#performImport(Table,int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPerformImportMethodInvalid()
	{
		// fixture
		List<AbstractRecord> records = new LinkedList<AbstractRecord>();

		// when
		facade.performImport(records, 3);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.ImporterFacade#performImport(Table,int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPerformImportMethodInvalidStructutral()
	{
		// fixture
		List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		// Column is set

		// when
		facade.performImport(records, IImporter.METHOD_STRUCTURAL);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.ImporterFacade#performImport(Table,int)}.
	 */
	@Test
	public void testPerformImportMethodStructuralIntoEmpty()
	{
		// fixture
		List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		Assert.assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		Assert.assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		facade.init(getTable(), null);

		// when
		facade.performImport(records, IImporter.METHOD_STRUCTURAL);

		// then
		Assert.assertEquals(6, facade.getTable().size());
		Assert.assertTrue(facade.getTable().get(0) == COMMENT_1);
		Assert.assertTrue(facade.getTable().get(1) == EMPTY_1);
		Assert.assertTrue(facade.getTable().get(2) == propertyRecord1);
		Assert.assertNull(((PropertyRecord) facade.getTable().get(2)).getColumnValue(COLUMN));
		Assert.assertTrue(facade.getTable().get(3) == COMMENT_2);
		Assert.assertTrue(facade.getTable().get(4) == EMPTY_2);
		Assert.assertTrue(facade.getTable().get(5) == propertyRecord2);
		Assert.assertNull(((PropertyRecord) facade.getTable().get(5)).getColumnValue(COLUMN));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.ImporterFacade#performImport(Table,int)}.
	 */
	@Test
	public void testPerformImportMethodStructuralIntoNonEmpty()
	{
		// fixture
		Table table = new Table();
		table.getColumns().add(COLUMN);
		EmptyRecord emptyRecord = new EmptyRecord();
		CommentRecord commentRecord = new CommentRecord();
		PropertyRecord propertyRecord = getPropertyRecord(VALUE, VALUE + "column");
		table.add(emptyRecord);
		table.add(commentRecord);
		table.add(propertyRecord);
		facade.init(table, null);

		List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		Assert.assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		Assert.assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// when
		facade.performImport(records, IImporter.METHOD_STRUCTURAL);

		// then
		Assert.assertEquals(9, facade.getTable().size());
		Assert.assertTrue(facade.getTable().get(0) == emptyRecord);
		Assert.assertTrue(facade.getTable().get(1) == commentRecord);
		Assert.assertTrue(facade.getTable().get(2) == propertyRecord);
		Assert.assertTrue(facade.getTable().get(3) == COMMENT_1);
		Assert.assertTrue(facade.getTable().get(4) == EMPTY_1);
		Assert.assertTrue(facade.getTable().get(5) == propertyRecord1);
		Assert.assertNull(((PropertyRecord) facade.getTable().get(5)).getColumnValue(COLUMN));
		Assert.assertTrue(facade.getTable().get(6) == COMMENT_2);
		Assert.assertTrue(facade.getTable().get(7) == EMPTY_2);
		Assert.assertTrue(facade.getTable().get(8) == propertyRecord2);
		Assert.assertNull(((PropertyRecord) facade.getTable().get(8)).getColumnValue(COLUMN));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.ImporterFacade#performImport(Table,int)}.
	 */
	@Test
	public void testPerformImportMethodKeyValueIntoEmpty()
	{
		// fixture
		List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		Assert.assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		Assert.assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// when
		facade.performImport(records, IImporter.METHOD_KEY_VALUE);

		// then
		Assert.assertEquals(1, facade.getTable().size());
		Assert.assertTrue(facade.getTable().get(0) == propertyRecord1);
		Assert.assertTrue(((PropertyRecord) facade.getTable().get(0)).getColumnValue(COLUMN).equals(VALUE));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.ImporterFacade#performImport(Table,int)}.
	 */
	@Test
	public void testPerformImportMethodKeyValueIntoNonEmpty()
	{
		// fixture
		Table table = new Table();
		table.getColumns().add(COLUMN);
		EmptyRecord emptyRecord = new EmptyRecord();
		CommentRecord commentRecord = new CommentRecord();
		PropertyRecord propertyRecord = getPropertyRecord(VALUE, VALUE + "column");
		table.add(emptyRecord);
		table.add(commentRecord);
		table.add(propertyRecord);
		facade.init(table, COLUMN);

		List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		Assert.assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		Assert.assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// when
		facade.performImport(records, IImporter.METHOD_KEY_VALUE);

		// then
		Assert.assertEquals(3, facade.getTable().size());
		Assert.assertTrue(facade.getTable().get(0) == emptyRecord);
		Assert.assertTrue(facade.getTable().get(1) == commentRecord);
		Assert.assertTrue(facade.getTable().get(2) == propertyRecord);
		Assert.assertTrue(((PropertyRecord) facade.getTable().get(2)).getColumnValue(COLUMN).equals(VALUE));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.ImporterFacade#performImport(Table,int)}.
	 */
	@Test
	public void testPerformImportMethodValueIntoEmpty()
	{
		// fixture
		List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		Assert.assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		Assert.assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// when
		facade.performImport(records, IImporter.METHOD_VALUE);

		// then
		Assert.assertEquals(0, facade.getTable().size());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.ImporterFacade#performImport(Table,int)}.
	 */
	@Test
	public void testPerformImportMethodValueIntoNonEmpty()
	{
		// fixture
		Table table = new Table();
		table.getColumns().add(COLUMN);
		EmptyRecord emptyRecord = new EmptyRecord();
		CommentRecord commentRecord = new CommentRecord();
		PropertyRecord propertyRecord = getPropertyRecord(VALUE, VALUE + "column");
		table.add(emptyRecord);
		table.add(commentRecord);
		table.add(propertyRecord);
		facade.init(table, COLUMN);

		List<AbstractRecord> records = new LinkedList<AbstractRecord>();
		records.add(COMMENT_1);
		records.add(EMPTY_1);
		PropertyRecord propertyRecord1 = getPropertyRecord(VALUE, null);
		Assert.assertNull(propertyRecord1.getColumnValue(COLUMN));
		records.add(propertyRecord1);
		records.add(COMMENT_2);
		records.add(EMPTY_2);
		PropertyRecord propertyRecord2 = getPropertyRecord(VALUE, VALUE);
		Assert.assertTrue(propertyRecord2.getColumnValue(COLUMN).equals(VALUE));
		records.add(propertyRecord2);

		// when
		facade.performImport(records, IImporter.METHOD_VALUE);

		// then
		Assert.assertEquals(3, facade.getTable().size());
		Assert.assertTrue(facade.getTable().get(0) == emptyRecord);
		Assert.assertTrue(facade.getTable().get(1) == commentRecord);
		Assert.assertTrue(facade.getTable().get(2) == propertyRecord);
		Assert.assertTrue(((PropertyRecord) facade.getTable().get(2)).getColumnValue(COLUMN).equals(VALUE));
	}
}
