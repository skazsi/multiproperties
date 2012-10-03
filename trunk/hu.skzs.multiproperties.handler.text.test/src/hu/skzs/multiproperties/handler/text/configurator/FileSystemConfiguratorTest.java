package hu.skzs.multiproperties.handler.text.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class FileSystemConfiguratorTest
{

	private static final String ETX = "ETX"; //$NON-NLS-1$
	private static final String FILENAME = "c:\folder\filename.properties"; //$NON-NLS-1$
	private static final String ENCODING = "UTF-8"; //$NON-NLS-1$
	private static final String HEADER = "header"; //$NON-NLS-1$
	private static final String FOOTER = "footer"; //$NON-NLS-1$
	private static final String PROPERTY = "property"; //$NON-NLS-1$
	private static final String COMMENT = "comment"; //$NON-NLS-1$
	private static final String EMPTY = "empty"; //$NON-NLS-1$
	private static final String PATTERNS = AbstractConfigurator.DELIM + HEADER + AbstractConfigurator.DELIM + PROPERTY
			+ AbstractConfigurator.DELIM + COMMENT + AbstractConfigurator.DELIM + EMPTY + AbstractConfigurator.DELIM
			+ FOOTER;
	private static final String PATTERNS_WITH_ENCODING = AbstractConfigurator.DELIM + ENCODING + PATTERNS;;
	private static final String CONF = FILENAME + PATTERNS_WITH_ENCODING + AbstractConfigurator.DELIM + ETX;

	private FileSystemConfigurator configurator;

	@After
	public void tearDown()
	{
		configurator = null;
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.FileSystemConfigurator()}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorEmpty() throws HandlerException
	{
		// when
		configurator = new FileSystemConfigurator(""); //$NON-NLS-1$

		// then
		Assert.assertNull(configurator.getFileName());
		Assert.assertNull(configurator.getEncodingPattern());
		Assert.assertNull(configurator.getHeaderPattern());
		Assert.assertNull(configurator.getFooterPattern());
		Assert.assertNull(configurator.getPropertyPattern());
		Assert.assertNull(configurator.getCommentPattern());
		Assert.assertNull(configurator.getEmptyPattern());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.FileSystemConfigurator()}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructor() throws HandlerException
	{
		// when
		configurator = new FileSystemConfigurator(CONF);

		// then
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertEquals(ENCODING, configurator.getEncodingPattern());
		Assert.assertEquals(HEADER, configurator.getHeaderPattern());
		Assert.assertEquals(FOOTER, configurator.getFooterPattern());
		Assert.assertEquals(PROPERTY, configurator.getPropertyPattern());
		Assert.assertEquals(COMMENT, configurator.getCommentPattern());
		Assert.assertEquals(EMPTY, configurator.getEmptyPattern());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.FileSystemConfigurator()}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConstructorWithDefaultEncoding() throws HandlerException
	{
		// when
		final String config = FILENAME + AbstractConfigurator.DELIM + PATTERNS + AbstractConfigurator.DELIM + ETX;
		configurator = new FileSystemConfigurator(config);

		// then
		Assert.assertEquals(FILENAME, configurator.getFileName());
		Assert.assertNull(configurator.getEncodingPattern());
		Assert.assertEquals(HEADER, configurator.getHeaderPattern());
		Assert.assertEquals(FOOTER, configurator.getFooterPattern());
		Assert.assertEquals(PROPERTY, configurator.getPropertyPattern());
		Assert.assertEquals(COMMENT, configurator.getCommentPattern());
		Assert.assertEquals(EMPTY, configurator.getEmptyPattern());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.FileSystemConfigurator()}.
	 * @throws HandlerException 
	 */
	@Test(expected = HandlerException.class)
	public void testConstructorTooLong() throws HandlerException
	{
		// when
		configurator = new FileSystemConfigurator(CONF + AbstractConfigurator.DELIM + "blabla"); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.FileSystemConfigurator#toString()()}.
	 */
	@Test
	public void testToString() throws HandlerException
	{
		// given
		configurator = new FileSystemConfigurator(CONF);

		// when
		final String conf = configurator.toString();

		// then
		Assert.assertEquals(CONF, conf);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.configurator.FileSystemConfigurator#toString()()}.
	 */
	@Test
	public void testToStringWithDefaultEncoding() throws HandlerException
	{
		// given
		final String config = FILENAME + AbstractConfigurator.DELIM + PATTERNS + AbstractConfigurator.DELIM + ETX;
		configurator = new FileSystemConfigurator(config);

		// when
		final String conf = configurator.toString();

		// then
		Assert.assertEquals(config, conf);
	}
}
