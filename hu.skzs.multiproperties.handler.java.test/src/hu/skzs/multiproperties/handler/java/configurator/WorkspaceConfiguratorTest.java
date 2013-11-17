package hu.skzs.multiproperties.handler.java.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author skzs
 * 
 */
public class WorkspaceConfiguratorTest
{

	private static final String CONTAINER = "/project/folder/subfolder/"; //$NON-NLS-1$
	private static final String FILENAME = "filename.properties"; //$NON-NLS-1$
	private static final String CONTAINER_AND_FILENAME = CONTAINER + "/" + FILENAME; //$NON-NLS-1$
	private static final String CONF_1_0 = CONTAINER_AND_FILENAME + "|true|true|true"; //$NON-NLS-1$

	private WorkspaceConfigurator configurator;

	@After
	public void tearDown()
	{
		configurator = null;
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructor() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(CONF_1_0);

		// then
		Assert.assertEquals(CONTAINER, configurator.getContainerName());
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertTrue(configurator.isDescriptionIncluded());
		Assert.assertTrue(configurator.isColumnDescriptionIncluded());
		Assert.assertTrue(configurator.isDisabledPropertiesIncluded());
		Assert.assertFalse(configurator.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test(expected = HandlerException.class)
	public void testConstructorNoSlash() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration("blabla" + FILENAME + "|true|true|true"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorFalseDesc() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(CONTAINER_AND_FILENAME + "|false|true|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(CONTAINER, configurator.getContainerName());
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertFalse(configurator.isDescriptionIncluded());
		Assert.assertTrue(configurator.isColumnDescriptionIncluded());
		Assert.assertTrue(configurator.isDisabledPropertiesIncluded());
		Assert.assertFalse(configurator.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorFalseColDesc() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(CONTAINER_AND_FILENAME + "|true|false|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(CONTAINER, configurator.getContainerName());
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertTrue(configurator.isDescriptionIncluded());
		Assert.assertFalse(configurator.isColumnDescriptionIncluded());
		Assert.assertTrue(configurator.isDisabledPropertiesIncluded());
		Assert.assertFalse(configurator.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorFalseDisabled() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(CONTAINER_AND_FILENAME + "|true|true|false"); //$NON-NLS-1$

		// then
		Assert.assertEquals(CONTAINER, configurator.getContainerName());
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertTrue(configurator.isDescriptionIncluded());
		Assert.assertTrue(configurator.isColumnDescriptionIncluded());
		Assert.assertFalse(configurator.isDisabledPropertiesIncluded());
		Assert.assertFalse(configurator.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test(expected = HandlerException.class)
	public void testConstructorWrongBoolean() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(CONTAINER_AND_FILENAME + "|true|ttrue|true"); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException
	 */
	@Test
	public void testConstructor_1_1() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(CONTAINER_AND_FILENAME + "|true|true|true|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(CONTAINER, configurator.getContainerName());
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertTrue(configurator.isDescriptionIncluded());
		Assert.assertTrue(configurator.isColumnDescriptionIncluded());
		Assert.assertTrue(configurator.isDisabledPropertiesIncluded());
		Assert.assertTrue(configurator.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator#getConfiguration()}.
	 * @throws HandlerException
	 */
	@Test
	public void testGetConfiguration() throws HandlerException
	{
		// given
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(CONTAINER_AND_FILENAME + "|true|true|true|true"); //$NON-NLS-1$

		// when
		final String conf = configurator.getConfiguration();

		// then
		Assert.assertEquals(CONTAINER_AND_FILENAME + "|true|true|true|true", conf); //$NON-NLS-1$
	}
}
