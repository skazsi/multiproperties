package hu.skzs.multiproperties.base.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class InputStreamContentReaderTest
{

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.io.InputStreamContentReader()}.
	 */
	@Test
	public void testConstructor() throws IOException
	{
		byte[] content = "foo bar many times".getBytes();
		InputStream inputStream = new ByteArrayInputStream(content);

		InputStreamContentReader reader = new InputStreamContentReader(inputStream);
		byte[] got = reader.getContent();
		Assert.assertEquals(content.length, got.length);
		for (int i = 0; i < content.length; i++)
		{
			Assert.assertEquals(content[i], got[i]);
		}
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.io.InputStreamContentReader()}.
	 */
	@Test
	public void testConstructorEmpty() throws IOException
	{
		InputStream inputStream = new ByteArrayInputStream(new byte[0]);

		InputStreamContentReader reader = new InputStreamContentReader(inputStream);
		byte[] got = reader.getContent();
		Assert.assertNotNull(got);
		Assert.assertEquals(0, got.length);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.io.InputStreamContentReader()}.
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructorNull() throws IOException
	{
		new InputStreamContentReader(null);
	}
}
