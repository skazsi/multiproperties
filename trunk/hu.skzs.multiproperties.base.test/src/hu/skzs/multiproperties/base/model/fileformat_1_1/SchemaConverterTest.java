package hu.skzs.multiproperties.base.model.fileformat_1_1;

import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.model.fileformat.AbstractSchemaConverterTest;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author skzs
 * 
 */
public class SchemaConverterTest extends AbstractSchemaConverterTest
{

	private static final String VERSION = "1.0";
	private static final String NORMAL_FILE = "normal.multiproperties";
	private static final String EMPTY_FILE = "empty.multiproperties";
	private final SchemaConverter schemaConverter = new SchemaConverter();

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat_1_1.SchemaConverter#convert(byte[])}.
	 * 
	 * @throws SchemaConverterException
	 * @throws IOException 
	 */
	@Test
	public void testConvertIFile() throws SchemaConverterException, IOException
	{
		// when
		Table table = schemaConverter.convert(readBytes(SchemaConverterTest.class.getResourceAsStream(NORMAL_FILE)));

		// then
		Assert.assertNotNull(table);
		Assert.assertEquals(VERSION, table.getVersion());
		Assert.assertEquals(NAME, table.getName());
		Assert.assertEquals(DESCRIPTION, table.getDescription());
		Assert.assertEquals(HANDLER, table.getHandler());
		// columns
		Assert.assertEquals(100, table.getKeyColumnWidth());
		Assert.assertEquals(2, table.getColumns().size());
		assertEquals("EN", "EN " + DESCRIPTION, 200, "EN " + HANDLER, table.getColumns().get(0));
		assertEquals("HU", "HU " + DESCRIPTION, 200, "HU " + HANDLER, table.getColumns().get(1));
		// records
		Assert.assertEquals(5, table.size());
		Assert.assertTrue(table.get(0) instanceof CommentRecord);
		assertEquals(COMMENT, (CommentRecord) table.get(0));
		Assert.assertTrue(table.get(1) instanceof PropertyRecord);
		assertEquals(NAME, DESCRIPTION, false, null, new String[] { "EN value", "HU value" }, table.getColumns(),
				(PropertyRecord) table.get(1));
		Assert.assertTrue(table.get(2) instanceof PropertyRecord);
		assertEquals(NAME, DESCRIPTION, false, DEFAULT_VALUE, new String[] { "EN value", "HU value" },
				table.getColumns(), (PropertyRecord) table.get(1));
		Assert.assertTrue(table.get(3) instanceof PropertyRecord);
		assertEquals(NAME, DESCRIPTION, true, null, new String[] { "EN value", "HU value" }, table.getColumns(),
				(PropertyRecord) table.get(2));
		Assert.assertTrue(table.get(4) instanceof EmptyRecord);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat_1_1.SchemaConverter#convert(byte[])}.
	 * 
	 * @throws SchemaConverterException
	 * @throws IOException 
	 */
	@Test
	public void testConvertIFileEmpty() throws SchemaConverterException, IOException
	{
		// when
		Table table = schemaConverter.convert(readBytes(SchemaConverterTest.class.getResourceAsStream(EMPTY_FILE)));

		// then
		Assert.assertNotNull(table);
		Assert.assertEquals(VERSION, table.getVersion());
		Assert.assertEquals(NAME, table.getName());
		Assert.assertEquals(DESCRIPTION, table.getDescription());
		Assert.assertEquals(HANDLER, table.getHandler());
		// columns
		Assert.assertEquals(100, table.getKeyColumnWidth());
		Assert.assertEquals(0, table.getColumns().size());
		// records
		Assert.assertEquals(0, table.size());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat_1_1.SchemaConverter#convert(byte[])}.
	 * 
	 * @throws SchemaConverterException
	 */
	@Test(expected = SchemaConverterException.class)
	public void testConvertIFileNull() throws SchemaConverterException
	{
		// when
		schemaConverter.convert((byte[]) null);
	}

	/**
	 * Test method for
	 * {@link hu.skzs.multiproperties.base.model.fileformat_1_1.SchemaConverter#convert(hu.skzs.multiproperties.base.model.Table)}
	 * .
	 * 
	 * @throws SchemaConverterException
	 * @throws IOException
	 */
	@Test
	public void testConvertIFileTable() throws SchemaConverterException, IOException
	{
		// fixture
		Table table = new Table();
		table.setVersion(VERSION);
		table.setName(NAME);
		table.setDescription(DESCRIPTION);
		table.setHandler(HANDLER);
		table.setKeyColumnWidth(100);

		// when
		byte[] content = schemaConverter.convert(table);

		// then
		assertEquals(SchemaConverterTest.class.getResourceAsStream(EMPTY_FILE), new ByteArrayInputStream(content));
	}
}
