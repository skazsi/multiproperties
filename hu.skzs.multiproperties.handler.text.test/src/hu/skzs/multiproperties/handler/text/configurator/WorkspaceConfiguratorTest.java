package hu.skzs.multiproperties.handler.text.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

/**
 * @author sallai
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
	private static final String PATTERNS = AbstractConfigurator.DELIM + HEADER + AbstractConfigurator.DELIM + PROPERTY
			+ AbstractConfigurator.DELIM + COMMENT + AbstractConfigurator.DELIM + EMPTY + AbstractConfigurator.DELIM
			+ FOOTER;
	private static final String PATTERNS_WITH_ENCODING = AbstractConfigurator.DELIM + ENCODING + PATTERNS;
	private static final String CONF = CONTAINER_AND_FILENAME + PATTERNS_WITH_ENCODING + AbstractConfigurator.DELIM
			+ ETX;

	private WorkspaceConfigurator configurator;

	@After
	public void tearDown()
	{
		configurator = null;
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator()}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorEmpty() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator(""); //$NON-NLS-1$

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
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator()}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructor() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator(CONF);

		// then
		Assert.assertEquals(CONTAINER, configurator.getContainerName());
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertEquals(ENCODING, configurator.getEncodingPattern());
		Assert.assertEquals(HEADER, configurator.getHeaderPattern());
		Assert.assertEquals(FOOTER, configurator.getFooterPattern());
		Assert.assertEquals(PROPERTY, configurator.getPropertyPattern());
		Assert.assertEquals(COMMENT, configurator.getCommentPattern());
		Assert.assertEquals(EMPTY, configurator.getEmptyPattern());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator()}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorWithDefaultEncoding() throws HandlerException
	{
		// when
		final String config = CONTAINER_AND_FILENAME + AbstractConfigurator.DELIM + PATTERNS
				+ AbstractConfigurator.DELIM + ETX;
		configurator = new WorkspaceConfigurator(config);

		// then
		Assert.assertEquals(CONTAINER, configurator.getContainerName());
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertNull(configurator.getEncodingPattern());
		Assert.assertEquals(HEADER, configurator.getHeaderPattern());
		Assert.assertEquals(FOOTER, configurator.getFooterPattern());
		Assert.assertEquals(PROPERTY, configurator.getPropertyPattern());
		Assert.assertEquals(COMMENT, configurator.getCommentPattern());
		Assert.assertEquals(EMPTY, configurator.getEmptyPattern());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator()}.
	 * @throws HandlerException 
	 */
	@Test(expected = HandlerException.class)
	public void testConstructorNoSlash() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator("blabla" + FILENAME + PATTERNS_WITH_ENCODING); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator()}.
	 * @throws HandlerException 
	 */
	@Test(expected = HandlerException.class)
	public void testConstructorTooLong() throws HandlerException
	{
		// when
		configurator = new WorkspaceConfigurator(CONF + AbstractConfigurator.DELIM + "blabla"); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator#toString()()}.
	 */
	@Test
	public void testToString() throws HandlerException
	{
		// given
		configurator = new WorkspaceConfigurator(CONF);

		// when
		final String conf = configurator.toString();

		// then
		Assert.assertEquals(CONF, conf);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator#toString()()}.
	 */
	@Test
	public void testToStringWithDefaultEncoding() throws HandlerException
	{
		// given
		final String config = CONTAINER_AND_FILENAME + AbstractConfigurator.DELIM + PATTERNS
				+ AbstractConfigurator.DELIM + ETX;
		configurator = new WorkspaceConfigurator(config);

		// when
		final String conf = configurator.toString();

		// then
		Assert.assertEquals(config, conf);
	}

}
