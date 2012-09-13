package hu.skzs.multiproperties.handler.java.importer;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class LineReaderTest
{

	private static final String PER_N = "key1=value1\nkey2=value2"; //$NON-NLS-1$
	private static final String PER_R_PER_N = "key1=value1\r\nkey2=value2"; //$NON-NLS-1$
	private static final String EMPTY = ""; //$NON-NLS-1$
	private static final String NORMAL = "normal.properties"; //$NON-NLS-1$

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.LineReader()}.
	 * @throws FileNotFoundException
	 */
	@Test
	public void testConstructorInputStream() throws FileNotFoundException
	{
		// when
		new LineReader(new ByteArrayInputStream(EMPTY.getBytes()));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.LineReader()}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testConstructorReader() throws FileNotFoundException
	{
		// when
		new LineReader(new ByteArrayInputStream(EMPTY.getBytes()));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.LineReader#ReadLine()}.
	 * @throws IOException 
	 */
	@Test
	public void testrReadLineEmptyInputStream() throws IOException
	{
		// when
		final LineReader lineReader = new LineReader(new ByteArrayInputStream(EMPTY.getBytes()));

		// then
		Assert.assertEquals(-1, lineReader.readLine());

	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.LineReader#ReadLine()}.
	 * @throws IOException 
	 */
	@Test
	public void testReadLineEmptyReader() throws IOException
	{
		// when
		final LineReader lineReader = new LineReader(new ByteArrayInputStream(EMPTY.getBytes()));

		// then
		Assert.assertEquals(-1, lineReader.readLine());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.LineReader#ReadLine()}.
	 * @throws IOException 
	 */
	@Test
	public void testReadLinePerN() throws IOException
	{
		// when
		final LineReader lineReader = new LineReader(new ByteArrayInputStream(PER_N.getBytes()));
		int i = -1;

		// then and when
		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("key1=value1", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("key2=value2", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		Assert.assertEquals(-1, lineReader.readLine());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.LineReader#ReadLine()}.
	 * @throws IOException 
	 */
	@Test
	public void testReadLinePerRPerN() throws IOException
	{
		// when
		final LineReader lineReader = new LineReader(new ByteArrayInputStream(PER_R_PER_N.getBytes()));
		int i = -1;

		// then and when
		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("key1=value1", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("key2=value2", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		Assert.assertEquals(-1, lineReader.readLine());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.LineReader#ReadLine()}.
	 * @throws IOException 
	 */
	@Test
	public void testrReadLineInputStream() throws IOException
	{
		// when
		final LineReader lineReader = new LineReader(getInputStream(NORMAL));
		int i = -1;

		// then and when
		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("#comment", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("#comment", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertEquals(0, i);

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("#comment 1", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("key.1=value 1", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("key.2=value 2", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("key.3=value 3", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertEquals(0, i);

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("#comment 2", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("key.4=value 4 new liner", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("key\\u0073.5=value\\u0073 5", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$

		i = lineReader.readLine();
		Assert.assertTrue(i > -1);
		Assert.assertEquals("key.6=value 6", new String(lineReader.getLineBuffer(), 0, i)); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.importer.LineReader#ReadLine()}.
	 * @throws IOException 
	 */
	@Test
	public void testReadLineReader() throws IOException
	{
		// when
		final LineReader lineReader = new LineReader(getReader(NORMAL));

		// then
		Assert.assertTrue(lineReader.readLine() > -1);
	}

	/**
	 * Returns an {@link InputStream} instance specified by the given <code>fileName</code> parameter
	 * and located next to this class.
	 * @param fileName the given file name
	 * @return an {@link InputStream} instance 
	 */
	private InputStream getInputStream(final String fileName)
	{
		return LineReaderTest.class.getResourceAsStream(fileName);
	}

	/**
	 * Returns an {@link } instance specified by the given <code>fileName</code> parameter
	 * and located next to this class.
	 * @param fileName the given file name
	 * @return an {@link InputStream} instance 
	 */
	private Reader getReader(final String fileName)
	{
		return new InputStreamReader(LineReaderTest.class.getResourceAsStream(fileName));
	}
}
