package hu.skzs.multiproperties.handler.java.writer;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class FileSystemWriterTest
{

	private static final String FILENAME = "c:\folder\filename.properties"; //$NON-NLS-1$
	private static final String CONF_1_0 = FILENAME + "|true|true|true"; //$NON-NLS-1$

	private FileSystemWriter writer;

	@After
	public void tearDown()
	{
		writer = null;
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.FileSystemWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test
	public void testConstructor() throws WriterConfigurationException
	{
		// when
		writer = new FileSystemWriter(CONF_1_0);

		// then
		Assert.assertEquals(FILENAME, writer.getFileName());
		Assert.assertTrue(writer.isDescriptionIncluded());
		Assert.assertTrue(writer.isColumnDescriptionIncluded());
		Assert.assertTrue(writer.isDisabledPropertiesIncluded());
		Assert.assertFalse(writer.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.FileSystemWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test
	public void testConstructorFalseDesc() throws WriterConfigurationException
	{
		// when
		writer = new FileSystemWriter(FILENAME + "|false|true|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(FILENAME, writer.getFileName());
		Assert.assertFalse(writer.isDescriptionIncluded());
		Assert.assertTrue(writer.isColumnDescriptionIncluded());
		Assert.assertTrue(writer.isDisabledPropertiesIncluded());
		Assert.assertFalse(writer.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.FileSystemWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test
	public void testConstructorFalseColDesc() throws WriterConfigurationException
	{
		// when
		writer = new FileSystemWriter(FILENAME + "|true|false|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(FILENAME, writer.getFileName());
		Assert.assertTrue(writer.isDescriptionIncluded());
		Assert.assertFalse(writer.isColumnDescriptionIncluded());
		Assert.assertTrue(writer.isDisabledPropertiesIncluded());
		Assert.assertFalse(writer.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.FileSystemWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test
	public void testConstructorFalseDisabled() throws WriterConfigurationException
	{
		// when
		writer = new FileSystemWriter(FILENAME + "|true|true|false"); //$NON-NLS-1$

		// then
		Assert.assertEquals(FILENAME, writer.getFileName());
		Assert.assertTrue(writer.isDescriptionIncluded());
		Assert.assertTrue(writer.isColumnDescriptionIncluded());
		Assert.assertFalse(writer.isDisabledPropertiesIncluded());
		Assert.assertFalse(writer.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.FileSystemWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test(expected = WriterConfigurationException.class)
	public void testConstructorWrongBoolean() throws WriterConfigurationException
	{
		// when
		writer = new FileSystemWriter(FILENAME + "|true|ttrue|true"); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.FileSystemWriter()}.
	 */
	@Test
	public void testConstructor_1_1() throws WriterConfigurationException
	{
		// when
		writer = new FileSystemWriter(FILENAME + "|true|true|true|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(FILENAME, writer.getFileName());
		Assert.assertTrue(writer.isDescriptionIncluded());
		Assert.assertTrue(writer.isColumnDescriptionIncluded());
		Assert.assertTrue(writer.isDisabledPropertiesIncluded());
		Assert.assertTrue(writer.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.FileSystemWriter#toString()()}.
	 */
	@Test
	public void testToString() throws WriterConfigurationException
	{
		// given
		writer = new FileSystemWriter(FILENAME + "|true|true|true|true"); //$NON-NLS-1$

		// when
		final String conf = writer.toString();

		// then
		Assert.assertEquals(FILENAME + "|true|true|true|true", conf); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.FileSystemWriter#write()()}.
	 */
	@Test(expected = WriterException.class)
	public void testWrite() throws WriterException
	{
		// given
		writer = new FileSystemWriter("wrong_drive:folder/file|true|true|true|true"); //$NON-NLS-1$

		// when
		writer.write("content".getBytes()); //$NON-NLS-1$
	}

}
