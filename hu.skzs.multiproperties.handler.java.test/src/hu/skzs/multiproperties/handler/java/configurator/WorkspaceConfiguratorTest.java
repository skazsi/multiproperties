package hu.skzs.multiproperties.handler.java.configurator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hu.skzs.multiproperties.base.api.HandlerException;

public class WorkspaceConfiguratorTest
{

	private static final String CONTAINER = "/project/folder/subfolder";
	private static final String FILENAME = "filename.properties";
	private static final String CONTAINER_AND_FILENAME = CONTAINER + "/" + FILENAME;
	private static final String CONF_ORIGINAL = CONTAINER_AND_FILENAME + "|true|true|true";
	private static final String CONF_WITH_DISABLED_DEFAULT = CONF_ORIGINAL + "|true";
	private static final String CONF_WITH_ENCODING = CONF_WITH_DISABLED_DEFAULT + "|UTF-8";

	private final WorkspaceConfigurator underTest = new WorkspaceConfigurator();

	@Test
	public void setConfiguration_Original_Format() throws HandlerException
	{
		// When
		underTest.setConfiguration(CONF_ORIGINAL);

		// Then
		assertEquals(CONTAINER, underTest.getContainerName());
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
		assertEquals(CONTAINER, underTest.getContainerName());
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
		assertEquals(CONTAINER, underTest.getContainerName());
		assertEquals(FILENAME, underTest.getFileName());
		assertTrue(underTest.isDescriptionIncluded());
		assertTrue(underTest.isColumnDescriptionIncluded());
		assertTrue(underTest.isDisabledPropertiesIncluded());
		assertTrue(underTest.isDisableDefaultValues());
		assertEquals("UTF-8", underTest.getEncoding());
	}

	@Test(expected = HandlerException.class)
	public void setConfigurationNoSlashInPath() throws HandlerException
	{
		// When
		underTest.setConfiguration("blabla" + FILENAME + "|true|true|true");
	}

	@Test
	public void setConfigurationFalseDesc() throws HandlerException
	{
		// When
		underTest.setConfiguration(CONTAINER_AND_FILENAME + "|false|true|true|true|UTF-8");

		// Then
		assertEquals(CONTAINER, underTest.getContainerName());
		assertEquals(FILENAME, underTest.getFileName());
		assertFalse(underTest.isDescriptionIncluded());
		assertTrue(underTest.isColumnDescriptionIncluded());
		assertTrue(underTest.isDisabledPropertiesIncluded());
		assertTrue(underTest.isDisableDefaultValues());
		assertEquals("UTF-8", underTest.getEncoding());
	}

	@Test
	public void setConfigurationFalseColDesc() throws HandlerException
	{
		// When
		underTest.setConfiguration(CONTAINER_AND_FILENAME + "|true|false|true|true|UTF-8");

		// Then
		assertEquals(CONTAINER, underTest.getContainerName());
		assertEquals(FILENAME, underTest.getFileName());
		assertTrue(underTest.isDescriptionIncluded());
		assertFalse(underTest.isColumnDescriptionIncluded());
		assertTrue(underTest.isDisabledPropertiesIncluded());
		assertTrue(underTest.isDisableDefaultValues());
		assertEquals("UTF-8", underTest.getEncoding());
	}

	@Test
	public void setConfigurationFalseDisabled() throws HandlerException
	{
		// When
		underTest.setConfiguration(CONTAINER_AND_FILENAME + "|true|true|false|true|UTF-8");

		// Then
		assertEquals(CONTAINER, underTest.getContainerName());
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
		underTest.setConfiguration(CONTAINER_AND_FILENAME + "|true|true|true|false|UTF-8");

		// Then
		assertEquals(CONTAINER, underTest.getContainerName());
		assertEquals(FILENAME, underTest.getFileName());
		assertTrue(underTest.isDescriptionIncluded());
		assertTrue(underTest.isColumnDescriptionIncluded());
		assertTrue(underTest.isDisabledPropertiesIncluded());
		assertFalse(underTest.isDisableDefaultValues());
		assertEquals("UTF-8", underTest.getEncoding());
	}

	@Test(expected = HandlerException.class)
	public void setConfigurationWrongBoolean() throws HandlerException
	{
		// When
		underTest.setConfiguration(CONTAINER_AND_FILENAME + "|true|ttrue|true|true|UTF-8");
	}

	@Test
	public void getConfiguration() throws HandlerException
	{
		// Given
		underTest.setConfiguration(CONTAINER_AND_FILENAME + "|true|true|true|true|UTF-8");

		// when
		final String conf = underTest.getConfiguration();

		// Then
		assertEquals(CONTAINER_AND_FILENAME + "|true|true|true|true|UTF-8", conf);
	}
}
