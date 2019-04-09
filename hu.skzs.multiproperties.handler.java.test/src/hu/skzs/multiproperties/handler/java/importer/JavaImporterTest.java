package hu.skzs.multiproperties.handler.java.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import hu.skzs.multiproperties.base.api.ImporterException;
import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;

public class JavaImporterTest
{

	private static final String DIRECTORY = "bin/" + JavaImporter.class.getPackage().getName().replaceAll("\\.", "/");
	private static final String EMPTY_FILE = DIRECTORY + "/empty.properties";
	private static final String COMPLEX_FILE_ISO_8859_1 = DIRECTORY + "/complex_ISO-8859-1.properties";
	private static final String COMPLEX_FILE_UTF_8 = DIRECTORY + "/complex_UTF-8.properties";

	private final JavaImporter underTest = new JavaImporter();

	@Test(expected = ImporterException.class)
	public void getRecords_Null() throws ImporterException
	{
		// When
		underTest.getRecords(null, null);
	}

	@Test
	public void getRecords_Empty() throws ImporterException
	{
		// Given
		final JavaImporterInput input = new JavaImporterInput(EMPTY_FILE, "ISO-8859-1");

		// When
		final List<AbstractRecord> records = underTest.getRecords(input, null);

		// Then
		assertEquals(0, records.size());
	}

	@Test
	public void getRecords_ComplexWithNullColumn() throws ImporterException
	{
		// Given
		final JavaImporterInput input = new JavaImporterInput(COMPLEX_FILE_ISO_8859_1, "ISO-8859-1");

		// When
		final List<AbstractRecord> records = underTest.getRecords(input, null);

		// Then
		assertEquals(12, records.size());

		assertTrue(records.get(0) instanceof CommentRecord);
		final CommentRecord record0 = (CommentRecord) records.get(0);
		assertEquals("comment", record0.getValue());

		assertTrue(records.get(1) instanceof CommentRecord);
		final CommentRecord record1 = (CommentRecord) records.get(1);
		assertEquals("comment", record1.getValue());

		assertTrue(records.get(2) instanceof EmptyRecord);

		assertTrue(records.get(3) instanceof CommentRecord);
		final CommentRecord record3 = (CommentRecord) records.get(3);
		assertEquals("comment 1", record3.getValue());

		assertTrue(records.get(4) instanceof PropertyRecord);
		final PropertyRecord record4 = (PropertyRecord) records.get(4);
		assertEquals("key.1", record4.getValue());

		assertTrue(records.get(5) instanceof PropertyRecord);
		final PropertyRecord record5 = (PropertyRecord) records.get(5);
		assertEquals("key.2", record5.getValue());

		assertTrue(records.get(6) instanceof PropertyRecord);
		final PropertyRecord record6 = (PropertyRecord) records.get(6);
		assertEquals("key.3", record6.getValue());

		assertTrue(records.get(7) instanceof EmptyRecord);

		assertTrue(records.get(8) instanceof CommentRecord);
		final CommentRecord record8 = (CommentRecord) records.get(8);
		assertEquals("comment 2", record8.getValue());

		assertTrue(records.get(9) instanceof PropertyRecord);
		final PropertyRecord record9 = (PropertyRecord) records.get(9);
		assertEquals("key.4", record9.getValue());

		assertTrue(records.get(10) instanceof PropertyRecord);
		final PropertyRecord record10 = (PropertyRecord) records.get(10);
		assertEquals("keys.5", record10.getValue());

		assertTrue(records.get(11) instanceof PropertyRecord);
		final PropertyRecord record11 = (PropertyRecord) records.get(11);
		assertEquals("key.6", record11.getValue());
	}

	@Test
	public void getRecords_ComplexWithColumn() throws ImporterException
	{
		// Given
		final JavaImporterInput input = new JavaImporterInput(COMPLEX_FILE_ISO_8859_1, "ISO-8859-1");
		final Column column = new Column();

		// When
		final List<AbstractRecord> records = underTest.getRecords(input, column);

		// Then
		assertEquals(12, records.size());

		assertTrue(records.get(0) instanceof CommentRecord);
		final CommentRecord record0 = (CommentRecord) records.get(0);
		assertEquals("comment", record0.getValue());

		assertTrue(records.get(1) instanceof CommentRecord);
		final CommentRecord record1 = (CommentRecord) records.get(1);
		assertEquals("comment", record1.getValue());

		assertTrue(records.get(2) instanceof EmptyRecord);

		assertTrue(records.get(3) instanceof CommentRecord);
		final CommentRecord record3 = (CommentRecord) records.get(3);
		assertEquals("comment 1", record3.getValue());

		assertTrue(records.get(4) instanceof PropertyRecord);
		final PropertyRecord record4 = (PropertyRecord) records.get(4);
		assertEquals("key.1", record4.getValue());
		assertEquals("value 1", record4.getColumnValue(column));

		assertTrue(records.get(5) instanceof PropertyRecord);
		final PropertyRecord record5 = (PropertyRecord) records.get(5);
		assertEquals("key.2", record5.getValue());
		assertEquals("value 2", record5.getColumnValue(column));

		assertTrue(records.get(6) instanceof PropertyRecord);
		final PropertyRecord record6 = (PropertyRecord) records.get(6);
		assertEquals("key.3", record6.getValue());
		assertEquals("value 3", record6.getColumnValue(column));

		assertTrue(records.get(7) instanceof EmptyRecord);

		assertTrue(records.get(8) instanceof CommentRecord);
		final CommentRecord record8 = (CommentRecord) records.get(8);
		assertEquals("comment 2", record8.getValue());

		assertTrue(records.get(9) instanceof PropertyRecord);
		final PropertyRecord record9 = (PropertyRecord) records.get(9);
		assertEquals("key.4", record9.getValue());
		assertEquals("value 4 new liner", record9.getColumnValue(column));

		assertTrue(records.get(10) instanceof PropertyRecord);
		final PropertyRecord record10 = (PropertyRecord) records.get(10);
		assertEquals("keys.5", record10.getValue());
		assertEquals("value \u0151\u0171 5", record10.getColumnValue(column));

		assertTrue(records.get(11) instanceof PropertyRecord);
		final PropertyRecord record11 = (PropertyRecord) records.get(11);
		assertEquals("key.6", record11.getValue());
		assertEquals("value 6", record11.getColumnValue(column));
	}

	@Test
	public void getRecords_ComplexWithColumnWithUTF8() throws ImporterException
	{
		// Given
		final JavaImporterInput input = new JavaImporterInput(COMPLEX_FILE_UTF_8, "UTF-8");
		final Column column = new Column();

		// When
		final List<AbstractRecord> records = underTest.getRecords(input, column);

		// Then
		assertEquals(12, records.size());

		assertTrue(records.get(0) instanceof CommentRecord);
		final CommentRecord record0 = (CommentRecord) records.get(0);
		assertEquals("comment", record0.getValue());

		assertTrue(records.get(1) instanceof CommentRecord);
		final CommentRecord record1 = (CommentRecord) records.get(1);
		assertEquals("comment", record1.getValue());

		assertTrue(records.get(2) instanceof EmptyRecord);

		assertTrue(records.get(3) instanceof CommentRecord);
		final CommentRecord record3 = (CommentRecord) records.get(3);
		assertEquals("comment 1", record3.getValue());

		assertTrue(records.get(4) instanceof PropertyRecord);
		final PropertyRecord record4 = (PropertyRecord) records.get(4);
		assertEquals("key.1", record4.getValue());
		assertEquals("value 1", record4.getColumnValue(column));

		assertTrue(records.get(5) instanceof PropertyRecord);
		final PropertyRecord record5 = (PropertyRecord) records.get(5);
		assertEquals("key.2", record5.getValue());
		assertEquals("value 2", record5.getColumnValue(column));

		assertTrue(records.get(6) instanceof PropertyRecord);
		final PropertyRecord record6 = (PropertyRecord) records.get(6);
		assertEquals("key.3", record6.getValue());
		assertEquals("value 3", record6.getColumnValue(column));

		assertTrue(records.get(7) instanceof EmptyRecord);

		assertTrue(records.get(8) instanceof CommentRecord);
		final CommentRecord record8 = (CommentRecord) records.get(8);
		assertEquals("comment 2", record8.getValue());

		assertTrue(records.get(9) instanceof PropertyRecord);
		final PropertyRecord record9 = (PropertyRecord) records.get(9);
		assertEquals("key.4", record9.getValue());
		assertEquals("value 4 new liner", record9.getColumnValue(column));

		assertTrue(records.get(10) instanceof PropertyRecord);
		final PropertyRecord record10 = (PropertyRecord) records.get(10);
		assertEquals("keys.5", record10.getValue());
		assertEquals("value \u0151\u0171 5", record10.getColumnValue(column));

		assertTrue(records.get(11) instanceof PropertyRecord);
		final PropertyRecord record11 = (PropertyRecord) records.get(11);
		assertEquals("key.6", record11.getValue());
		assertEquals("value 6", record11.getColumnValue(column));
	}
}
