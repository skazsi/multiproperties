package hu.skzs.multiproperties.handler.java.configurator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hu.skzs.multiproperties.base.api.HandlerException;

public class FileSystemConfiguratorTest
{

	private static final String FILENAME = "c:\folder\filename.properties";
	private static final String CONF_ORIGINAL = FILENAME + "|true|true|true";
	private static final String CONF_WITH_DISABLED_DEFAULT = CONF_ORIGINAL + "|true";
	private static final String CONF_WITH_ENCODING = CONF_WITH_DISABLED_DEFAULT + "|UTF-8";

	private final FileSystemConfigurator underTest = new FileSystemConfigurator();

	@Test
	public void setConfiguration_Original_Format() throws HandlerException
	{
		// When
		underTest.setConfiguration(CONF_ORIGINAL);

		// Then
		assertEquals(FILENAME, underTest.getFileName());
		assertTrue(underTest.isDescriptionIncluded());
		assertTrue(underTest.isColumnDescriptionIncluded());
		assertTrue(underTest.isDisabledPropertiesIncluded());
		assertFalse(underTest.isDisableDefaultValues());
		assertEquals("ISO-8859-1", underTest.getEncoding());
	}

	@Test
	public void setConfiguration_WithDisabledDefault() throws HandlerException
	{
		// When
		underTest.setConfiguration(CONF_WITH_DISABLED_DEFAULT);

		// Then
		assertEquals(FILENAME, underTest.getFileName());
		assertTrue(underTest.isDescriptionIncluded());
		assertTrue(underTest.isColumnDescriptionIncluded());
		assertTrue(underTest.isDisabledPropertiesIncluded());
		assertTrue(underTest.isDisableDefaultValues());
		assertEquals("ISO-8859-1", underTest.getEncoding());
	}

	@Test
	public void setConfiguration_WithEncoding() throws HandlerException
	{
		// When
		underTest.setConfiguration(CONF_WITH_ENCODING);

		// Then
		assertEquals(FILENAME, underTest.getFileName());
		assertTrue(underTest.isDescriptionIncluded());
		assertTrue(underTest.isColumnDescriptionIncluded());
		assertTrue(underTest.isDisabledPropertiesIncluded());
		assertTrue(underTest.isDisableDefaultValues());
		assertEquals("UTF-8", underTest.getEncoding());
	}

	@Test
	public void setConfiguration_FalseDesc() throws HandlerException
	{
		// When
		underTest.setConfiguration(FILENAME + "|false|true|true|true|UTF-8");

		// Then
		assertEquals(FILENAME, underTest.getFileName());
		assertFalse(underTest.isDescriptionIncluded());
		assertTrue(underTest.isColumnDescriptionIncluded());
		assertTrue(underTest.isDisabledPropertiesIncluded());
		assertTrue(underTest.isDisableDefaultValues());
		assertEquals("UTF-8", underTest.getEncoding());
	}

	@Test
	public void setConfiguration_FalseColDesc() throws HandlerException
	{
		// When
		underTest.setConfiguration(FILENAME + "|true|false|true|true|UTF-8");

		// Then
		assertEquals(FILENAME, underTest.getFileName());
		assertTrue(underTest.isDescriptionIncluded());
		assertFalse(underTest.isColumnDescriptionIncluded());
		assertTrue(underTest.isDisabledPropertiesIncluded());
		assertTrue(underTest.isDisableDefaultValues());
		assertEquals("UTF-8", underTest.getEncoding());
	}

	@Test
	public void setConfiguration_FalseDisabled() throws HandlerException
	{
		// When
		underTest.setConfiguration(FILENAME + "|true|true|false|true|UTF-8");

		// Then
		assertEquals(FILENAME, underTest.getFileName());
		assertTrue(underTest.isDescriptionIncluded());
		assertTrue(underTest.isColumnDescriptionIncluded());
		assertFalse(underTest.isDisabledPropertiesIncluded());
		assertTrue(underTest.isDisableDefaultValues());
		assertEquals("UTF-8", underTest.getEncoding());
	}

	@Test
	public void setConfigurationFalseDisabledDefault() throws HandlerException
	{
		// When
		underTest.setConfiguration(FILENAME + "|true|true|true|false|UTF-8");

		// Then
		assertEquals(FILENAME, underTest.getFileName());
		assertTrue(underTest.isDescriptionIncluded());
		assertTrue(underTest.isColumnDescriptionIncluded());
		assertTrue(underTest.isDisabledPropertiesIncluded());
		assertFalse(underTest.isDisableDefaultValues());
		assertEquals("UTF-8", underTest.getEncoding());
	}

	@Test(expected = HandlerException.class)
	public void setConfiguration_WrongBoolean() throws HandlerException
	{
		// When
		underTest.setConfiguration(FILENAME + "|true|ttrue|true");
	}

	@Test
	public void getConfiguration() throws HandlerException
	{
		// Given
		underTest.setConfiguration(FILENAME + "|true|true|true|true|UTF-8");

		// When
		final String conf = underTest.getConfiguration();

		// Then
		assertEquals(FILENAME + "|true|true|true|true|UTF-8", conf);
	}
}
