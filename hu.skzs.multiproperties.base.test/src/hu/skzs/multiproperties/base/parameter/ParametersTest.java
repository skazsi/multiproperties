package hu.skzs.multiproperties.base.parameter;

import hu.skzs.multiproperties.base.model.fileformat.AbstractSchemaConverterTest;
import junit.framework.Assert;

import org.junit.Test;

/**
 * @author skzs
 * 
 */
public class ParametersTest extends AbstractSchemaConverterTest
{

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.parameter.Parameters#Parameters(String[])}.
	 */
	@Test
	public void testConstructorEmpty()
	{
		// when
		Parameters parameters = new Parameters(new String[] {});

		// then
		Assert.assertEquals(0, parameters.size());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.parameter.Parameters#Parameters(String[])}.
	 */
	@Test
	public void testConstructorHelp()
	{
		// when
		Parameters parameters = new Parameters(new String[] { "-help" });

		// then
		Assert.assertEquals(1, parameters.size());
		Assert.assertNotNull(parameters.getHelpParameter());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.parameter.Parameters#Parameters(String[])}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorMissingDash()
	{
		// when
		new Parameters(new String[] { "param" });
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.parameter.Parameters#Parameters(String[])}.
	 */
	@Test
	public void testConstructorSomeParameter()
	{
		// when
		Parameters parameters = new Parameters(new String[] { "-columnConfig", "columnconfiguration", "-columnName",
				"columnname" });

		// then
		Assert.assertEquals(2, parameters.size());
		Assert.assertEquals("columnconfiguration", parameters.getColumnConfigParameter().getValue());
		Assert.assertEquals("columnname", parameters.getColumnNameParameter().getValue());
		Assert.assertNull(parameters.getFileParameter());
		Assert.assertNull(parameters.getHandlerClassParameter());

	}
}
