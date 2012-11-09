package hu.skzs.multiproperties.handler.java.importer;

import hu.skzs.multiproperties.base.api.ImporterException;
import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * @author skzs
 * 
 */
public class JavaImporterTest
{

	private static final String DIRECTORY = "bin/" + JavaImporter.class.getPackage().getName().replaceAll("\\.", "/"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
	private static final String EMPTY_FILE = DIRECTORY + "/empty.properties"; //$NON-NLS-1$
	private static final String NORMAL_FILE = DIRECTORY + "/normal.properties"; //$NON-NLS-1$
	private JavaImporter importer;

	@Before
	public void before()
	{
		importer = new JavaImporter();
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.JavaImporter#getRecords(Object, hu.skzs.multiproperties.base.model.Column)}.
	 * @throws ImporterException 
	 */
	@Test(expected = ImporterException.class)
	public void testGetRecordsNull() throws ImporterException
	{
		// when
		importer.getRecords(null, null);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.JavaImporter#getRecords(Object, hu.skzs.multiproperties.base.model.Column)}.
	 * @throws ImporterException 
	 */
	@Test
	public void testGetRecordsEmpty() throws ImporterException
	{
		// when
		final List<AbstractRecord> records = importer.getRecords(EMPTY_FILE, null);

		// then
		Assert.assertEquals(0, records.size());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.JavaImporter#getRecords(Object, hu.skzs.multiproperties.base.model.Column)}.
	 * @throws ImporterException 
	 */
	@Test
	public void testGetRecordsNormalWithNullColumn() throws ImporterException
	{
		// when
		final List<AbstractRecord> records = importer.getRecords(NORMAL_FILE, null);

		// then
		Assert.assertEquals(12, records.size());

		Assert.assertTrue(records.get(0) instanceof CommentRecord);
		final CommentRecord record0 = (CommentRecord) records.get(0);
		Assert.assertEquals("comment", record0.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(1) instanceof CommentRecord);
		final CommentRecord record1 = (CommentRecord) records.get(1);
		Assert.assertEquals("comment", record1.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(2) instanceof EmptyRecord);

		Assert.assertTrue(records.get(3) instanceof CommentRecord);
		final CommentRecord record3 = (CommentRecord) records.get(3);
		Assert.assertEquals("comment 1", record3.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(4) instanceof PropertyRecord);
		final PropertyRecord record4 = (PropertyRecord) records.get(4);
		Assert.assertEquals("key.1", record4.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(5) instanceof PropertyRecord);
		final PropertyRecord record5 = (PropertyRecord) records.get(5);
		Assert.assertEquals("key.2", record5.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(6) instanceof PropertyRecord);
		final PropertyRecord record6 = (PropertyRecord) records.get(6);
		Assert.assertEquals("key.3", record6.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(7) instanceof EmptyRecord);

		Assert.assertTrue(records.get(8) instanceof CommentRecord);
		final CommentRecord record8 = (CommentRecord) records.get(8);
		Assert.assertEquals("comment 2", record8.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(9) instanceof PropertyRecord);
		final PropertyRecord record9 = (PropertyRecord) records.get(9);
		Assert.assertEquals("key.4", record9.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(10) instanceof PropertyRecord);
		final PropertyRecord record10 = (PropertyRecord) records.get(10);
		Assert.assertEquals("key\u0073.5", record10.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(11) instanceof PropertyRecord);
		final PropertyRecord record11 = (PropertyRecord) records.get(11);
		Assert.assertEquals("key.6", record11.getValue()); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.JavaImporter#getRecords(Object, hu.skzs.multiproperties.base.model.Column)}.
	 * @throws ImporterException 
	 */
	@Test
	public void testGetRecordsNormalWithColumn() throws ImporterException
	{
		// fixture
		final Column column = new Column();

		// when
		final List<AbstractRecord> records = importer.getRecords(NORMAL_FILE, column);

		// then
		Assert.assertEquals(12, records.size());

		Assert.assertTrue(records.get(0) instanceof CommentRecord);
		final CommentRecord record0 = (CommentRecord) records.get(0);
		Assert.assertEquals("comment", record0.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(1) instanceof CommentRecord);
		final CommentRecord record1 = (CommentRecord) records.get(1);
		Assert.assertEquals("comment", record1.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(2) instanceof EmptyRecord);

		Assert.assertTrue(records.get(3) instanceof CommentRecord);
		final CommentRecord record3 = (CommentRecord) records.get(3);
		Assert.assertEquals("comment 1", record3.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(4) instanceof PropertyRecord);
		final PropertyRecord record4 = (PropertyRecord) records.get(4);
		Assert.assertEquals("key.1", record4.getValue()); //$NON-NLS-1$
		Assert.assertEquals("value 1", record4.getColumnValue(column)); //$NON-NLS-1$

		Assert.assertTrue(records.get(5) instanceof PropertyRecord);
		final PropertyRecord record5 = (PropertyRecord) records.get(5);
		Assert.assertEquals("key.2", record5.getValue()); //$NON-NLS-1$
		Assert.assertEquals("value 2", record5.getColumnValue(column)); //$NON-NLS-1$

		Assert.assertTrue(records.get(6) instanceof PropertyRecord);
		final PropertyRecord record6 = (PropertyRecord) records.get(6);
		Assert.assertEquals("key.3", record6.getValue()); //$NON-NLS-1$
		Assert.assertEquals("value 3", record6.getColumnValue(column)); //$NON-NLS-1$

		Assert.assertTrue(records.get(7) instanceof EmptyRecord);

		Assert.assertTrue(records.get(8) instanceof CommentRecord);
		final CommentRecord record8 = (CommentRecord) records.get(8);
		Assert.assertEquals("comment 2", record8.getValue()); //$NON-NLS-1$

		Assert.assertTrue(records.get(9) instanceof PropertyRecord);
		final PropertyRecord record9 = (PropertyRecord) records.get(9);
		Assert.assertEquals("key.4", record9.getValue()); //$NON-NLS-1$
		Assert.assertEquals("value 4 new liner", record9.getColumnValue(column)); //$NON-NLS-1$

		Assert.assertTrue(records.get(10) instanceof PropertyRecord);
		final PropertyRecord record10 = (PropertyRecord) records.get(10);
		Assert.assertEquals("keys.5", record10.getValue()); //$NON-NLS-1$
		Assert.assertEquals("values 5", record10.getColumnValue(column)); //$NON-NLS-1$

		Assert.assertTrue(records.get(11) instanceof PropertyRecord);
		final PropertyRecord record11 = (PropertyRecord) records.get(11);
		Assert.assertEquals("key.6", record11.getValue()); //$NON-NLS-1$
		Assert.assertEquals("value 6", record11.getColumnValue(column)); //$NON-NLS-1$
	}
}
