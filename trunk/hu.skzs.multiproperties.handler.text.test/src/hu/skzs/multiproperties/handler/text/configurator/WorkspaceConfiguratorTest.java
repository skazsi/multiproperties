package hu.skzs.multiproperties.handler.text.configurator;

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

	private static final String ETX = "ETX"; //$NON-NLS-1$
	private static final String CONTAINER = "/project/folder/subfolder/"; //$NON-NLS-1$
	private static final String FILENAME = "filename.properties"; //$NON-NLS-1$
	private static final String CONTAINER_AND_FILENAME = CONTAINER + "/" + FILENAME; //$NON-NLS-1$
	private static final String ENCODING = "UTF-8"; //$NON-NLS-1$
	private static final String HEADER = "header"; //$NON-NLS-1$
	private static final String FOOTER = "footer"; //$NON-NLS-1$
	private static final String PROPERTY = "property"; //$NON-NLS-1$
	private static final String COMMENT = "comment"; //$NON-NLS-1$
	private static final String EMPTY = "empty"; //$NON-NLS-1$
	private static final String PATTERNS = TextHandlerConfigurator.DELIM + HEADER + TextHandlerConfigurator.DELIM
			+ PROPERTY + TextHandlerConfigurator.DELIM + COMMENT + TextHandlerConfigurator.DELIM + EMPTY
			+ TextHandlerConfigurator.DELIM + FOOTER;
	private static final String PATTERNS_WITH_ENCODING = TextHandlerConfigurator.DELIM + ENCODING + PATTERNS;
	private static final String CONF = CONTAINER_AND_FILENAME + PATTERNS_WITH_ENCODING + TextHandlerConfigurator.DELIM
			+ ETX;

	private WorkspaceConfigurator configurator;

	@After
	public void tearDown()
	{
		configurator = null;
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorEmpty() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(""); //$NON-NLS-1$

		// then
		Assert.assertNull(configurator.getContainerName());
		Assert.assertNull(configurator.getFileName());
		Assert.assertNull(configurator.getHeaderPattern());
		Assert.assertNull(configurator.getFooterPattern());
		Assert.assertNull(configurator.getPropertyPattern());
		Assert.assertNull(configurator.getCommentPattern());
		Assert.assertNull(configurator.getEmptyPattern());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructor() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(CONF);

		// then
		Assert.assertEquals(CONTAINER, configurator.getContainerName());
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertEquals(ENCODING, configurator.getEncoding());
		Assert.assertEquals(HEADER, configurator.getHeaderPattern());
		Assert.assertEquals(FOOTER, configurator.getFooterPattern());
		Assert.assertEquals(PROPERTY, configurator.getPropertyPattern());
		Assert.assertEquals(COMMENT, configurator.getCommentPattern());
		Assert.assertEquals(EMPTY, configurator.getEmptyPattern());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorWithDefaultEncoding() throws HandlerException
	{
		// when
		final String config = CONTAINER_AND_FILENAME + TextHandlerConfigurator.DELIM + PATTERNS
				+ TextHandlerConfigurator.DELIM + ETX;
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(config);

		// then
		Assert.assertEquals(CONTAINER, configurator.getContainerName());
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertNull(configurator.getEncoding());
		Assert.assertEquals(HEADER, configurator.getHeaderPattern());
		Assert.assertEquals(FOOTER, configurator.getFooterPattern());
		Assert.assertEquals(PROPERTY, configurator.getPropertyPattern());
		Assert.assertEquals(COMMENT, configurator.getCommentPattern());
		Assert.assertEquals(EMPTY, configurator.getEmptyPattern());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test(expected = HandlerException.class)
	public void testConstructorNoSlash() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration("blabla" + FILENAME + PATTERNS_WITH_ENCODING); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator#setConfiguration(String)}.
	 * @throws HandlerException 
	 */
	@Test(expected = HandlerException.class)
	public void testConstructorTooLong() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(CONF + TextHandlerConfigurator.DELIM + "blabla"); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator#getConfiguration()}.
	 * @throws HandlerException
	 */
	@Test
	public void testGetConfiguration() throws HandlerException
	{
		// given
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(CONF);

		// when
		final String conf = configurator.getConfiguration();

		// then
		Assert.assertEquals(CONF, conf);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator#getConfiguration()}.
	 * @throws HandlerException
	 */
	@Test
	public void testGetConfigurationWithDefaultEncoding() throws HandlerException
	{
		// given
		final String config = CONTAINER_AND_FILENAME + TextHandlerConfigurator.DELIM + PATTERNS
				+ TextHandlerConfigurator.DELIM + ETX;
		configurator = new WorkspaceConfigurator();
		configurator.setConfiguration(config);

		// when
		final String conf = configurator.getConfiguration();

		// then
		Assert.assertEquals(config, conf);
	}

}
