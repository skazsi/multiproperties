package hu.skzs.multiproperties.handler.java.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

public class LineReaderTest
{

	private static final String PER_N = "key1=value1\nkey2=value2";
	private static final String PER_R_PER_N = "key1=value1\r\nkey2=value2";

	@Test
	public void readLine_Empty() throws IOException
	{
		// Fixture
		final LineReader underTest = new LineReader(new InputStreamReader(new ByteArrayInputStream(new byte[] {})));

		// When and Then
		assertEquals(-1, underTest.readLine());
	}

	@Test
	public void readLine_PerN() throws IOException
	{
		// Fixture
		final LineReader underTest = new LineReader(new InputStreamReader(new ByteArrayInputStream(PER_N.getBytes())));
		int i = -1;

		// When and Then
		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("key1=value1", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("key2=value2", new String(underTest.getLineBuffer(), 0, i));

		assertEquals(-1, underTest.readLine());
	}

	@Test
	public void readLine_PerRPerN() throws IOException
	{
		// Fixture
		final LineReader underTest = new LineReader(
				new InputStreamReader(new ByteArrayInputStream(PER_R_PER_N.getBytes())));
		int i = -1;

		// When and Then
		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("key1=value1", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("key2=value2", new String(underTest.getLineBuffer(), 0, i));

		assertEquals(-1, underTest.readLine());
	}

	@Test
	public void readLine_Complex() throws IOException
	{
		// Fixture
		final Reader reader = new InputStreamReader(
				LineReaderTest.class.getResourceAsStream("complex_ISO-8859-1.properties"));

		// When
		final LineReader underTest = new LineReader(reader);
		int i = -1;

		// Then
		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("#comment", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("#comment", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertEquals(0, i);

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("#comment 1", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("key.1=value 1", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("key.2=value 2", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("key.3=value 3", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertEquals(0, i);

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("#comment 2", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("key.4=value 4 new liner", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("key\\u0073.5=value \\u0151\\u0171 5", new String(underTest.getLineBuffer(), 0, i));

		i = underTest.readLine();
		assertTrue(i > -1);
		assertEquals("key.6=value 6", new String(underTest.getLineBuffer(), 0, i));
	}
}
