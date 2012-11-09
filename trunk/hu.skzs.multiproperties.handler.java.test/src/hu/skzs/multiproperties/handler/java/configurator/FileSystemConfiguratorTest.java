package hu.skzs.multiproperties.handler.java.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

/**
 * @author skzs
 * 
 */
public class FileSystemConfiguratorTest
{

	private static final String FILENAME = "c:\folder\filename.properties"; //$NON-NLS-1$
	private static final String CONF_1_0 = FILENAME + "|true|true|true"; //$NON-NLS-1$

	private FileSystemConfigurator configurator;

	@After
	public void tearDown()
	{
		configurator = null;
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.FileSystemConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructor() throws HandlerException
	{
		// when
		configurator = new FileSystemConfigurator();
		configurator.setConfiguration(CONF_1_0);

		// then
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertTrue(configurator.isDescriptionIncluded());
		Assert.assertTrue(configurator.isColumnDescriptionIncluded());
		Assert.assertTrue(configurator.isDisabledPropertiesIncluded());
		Assert.assertFalse(configurator.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.FileSystemConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorFalseDesc() throws HandlerException
	{
		// when
		configurator = new FileSystemConfigurator();
		configurator.setConfiguration(FILENAME + "|false|true|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertFalse(configurator.isDescriptionIncluded());
		Assert.assertTrue(configurator.isColumnDescriptionIncluded());
		Assert.assertTrue(configurator.isDisabledPropertiesIncluded());
		Assert.assertFalse(configurator.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.FileSystemConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorFalseColDesc() throws HandlerException
	{
		// when
		configurator = new FileSystemConfigurator();
		configurator.setConfiguration(FILENAME + "|true|false|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertTrue(configurator.isDescriptionIncluded());
		Assert.assertFalse(configurator.isColumnDescriptionIncluded());
		Assert.assertTrue(configurator.isDisabledPropertiesIncluded());
		Assert.assertFalse(configurator.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.FileSystemConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorFalseDisabled() throws HandlerException
	{
		// when
		configurator = new FileSystemConfigurator();
		configurator.setConfiguration(FILENAME + "|true|true|false"); //$NON-NLS-1$

		// then
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertTrue(configurator.isDescriptionIncluded());
		Assert.assertTrue(configurator.isColumnDescriptionIncluded());
		Assert.assertFalse(configurator.isDisabledPropertiesIncluded());
		Assert.assertFalse(configurator.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.FileSystemConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test(expected = HandlerException.class)
	public void testConstructorWrongBoolean() throws HandlerException
	{
		// when
		configurator = new FileSystemConfigurator();
		configurator.setConfiguration(FILENAME + "|true|ttrue|true"); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.FileSystemConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructor_1_1() throws HandlerException
	{
		// when
		configurator = new FileSystemConfigurator();
		configurator.setConfiguration(FILENAME + "|true|true|true|true"); //$NON-NLS-1$

		// then
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertTrue(configurator.isDescriptionIncluded());
		Assert.assertTrue(configurator.isColumnDescriptionIncluded());
		Assert.assertTrue(configurator.isDisabledPropertiesIncluded());
		Assert.assertTrue(configurator.isDisableDefaultValues());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.configurator.FileSystemConfigurator#getConfiguration()}.
	 * @throws HandlerException
	 */
	@Test
	public void testGetConfiguration() throws HandlerException
	{
		// given
		configurator = new FileSystemConfigurator();
		configurator.setConfiguration(FILENAME + "|true|true|true|true"); //$NON-NLS-1$

		// when
		final String conf = configurator.getConfiguration();

		// then
		Assert.assertEquals(FILENAME + "|true|true|true|true", conf); //$NON-NLS-1$
	}
}
