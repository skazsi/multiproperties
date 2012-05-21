package hu.skzs.multiproperties.handler.java.writer;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class WorkspaceWriterTest
{

	private static final String CONTAINER = "/project/folder/subfolder/"; //$NON-NLS-1$
	private static final String FILENAME = "filename.properties"; //$NON-NLS-1$
	private static final String CONTAINER_AND_FILENAME = CONTAINER + "/" + FILENAME; //$NON-NLS-1$
	private static final String CONF_1_0 = CONTAINER_AND_FILENAME + "|true|true|true"; //$NON-NLS-1$

	private WorkspaceWriter writer;

	@After
	public void tearDown()
	{
		writer = null;
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WorkspaceWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test
	public void testConstructor() throws WriterConfigurationException
	{
		// when
		writer = new WorkspaceWriter(CONF_1_0);

		// then
		Assert.assertEquals(CONTAINER, writer.getContainerName());
		Assert.assertEquals(FILENAME, writer.getFileName());
		Assert.assertTrue(writer.isDescriptionIncluded());
		Assert.assertTrue(writer.isColumnDescriptionIncluded());
		Assert.assertTrue(writer.isDisabledPropertiesIncluded());
		Assert.assertFalse(writer.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WorkspaceWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test(expected = WriterConfigurationException.class)
	public void testConstructorNoSlash() throws WriterConfigurationException
	{
		// when
		writer = new WorkspaceWriter("blabla" + FILENAME + "|true|true|true"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WorkspaceWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test
	public void testConstructorFalseDesc() throws WriterConfigurationException
	{
		// when
		writer = new WorkspaceWriter(CONTAINER_AND_FILENAME + "|false|true|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(CONTAINER, writer.getContainerName());
		Assert.assertEquals(FILENAME, writer.getFileName());
		Assert.assertFalse(writer.isDescriptionIncluded());
		Assert.assertTrue(writer.isColumnDescriptionIncluded());
		Assert.assertTrue(writer.isDisabledPropertiesIncluded());
		Assert.assertFalse(writer.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WorkspaceWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test
	public void testConstructorFalseColDesc() throws WriterConfigurationException
	{
		// when
		writer = new WorkspaceWriter(CONTAINER_AND_FILENAME + "|true|false|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(CONTAINER, writer.getContainerName());
		Assert.assertEquals(FILENAME, writer.getFileName());
		Assert.assertTrue(writer.isDescriptionIncluded());
		Assert.assertFalse(writer.isColumnDescriptionIncluded());
		Assert.assertTrue(writer.isDisabledPropertiesIncluded());
		Assert.assertFalse(writer.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WorkspaceWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test
	public void testConstructorFalseDisabled() throws WriterConfigurationException
	{
		// when
		writer = new WorkspaceWriter(CONTAINER_AND_FILENAME + "|true|true|false"); //$NON-NLS-1$

		// then
		Assert.assertEquals(CONTAINER, writer.getContainerName());
		Assert.assertEquals(FILENAME, writer.getFileName());
		Assert.assertTrue(writer.isDescriptionIncluded());
		Assert.assertTrue(writer.isColumnDescriptionIncluded());
		Assert.assertFalse(writer.isDisabledPropertiesIncluded());
		Assert.assertFalse(writer.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WorkspaceWriter()}.
	 * @throws WriterConfigurationException 
	 */
	@Test(expected = WriterConfigurationException.class)
	public void testConstructorWrongBoolean() throws WriterConfigurationException
	{
		// when
		writer = new WorkspaceWriter(CONTAINER_AND_FILENAME + "|true|ttrue|true"); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WorkspaceWriter()}.
	 */
	@Test
	public void testConstructor_1_1() throws WriterConfigurationException
	{
		// when
		writer = new WorkspaceWriter(CONTAINER_AND_FILENAME + "|true|true|true|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(CONTAINER, writer.getContainerName());
		Assert.assertEquals(FILENAME, writer.getFileName());
		Assert.assertTrue(writer.isDescriptionIncluded());
		Assert.assertTrue(writer.isColumnDescriptionIncluded());
		Assert.assertTrue(writer.isDisabledPropertiesIncluded());
		Assert.assertTrue(writer.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WorkspaceWriter#toString()()}.
	 */
	@Test
	public void testToString() throws WriterConfigurationException
	{
		// given
		writer = new WorkspaceWriter(CONTAINER_AND_FILENAME + "|true|true|true|true"); //$NON-NLS-1$

		// when
		final String conf = writer.toString();

		// then
		Assert.assertEquals(CONTAINER_AND_FILENAME + "|true|true|true|true", conf); //$NON-NLS-1$
	}

}
