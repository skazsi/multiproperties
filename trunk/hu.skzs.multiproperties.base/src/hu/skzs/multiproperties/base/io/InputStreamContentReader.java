package hu.skzs.multiproperties.base.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * The {@link InputStreamContentReader} is a thread safe and singleton content reader.
 * <p>Reads up the whole content of the input stream and stores it in an internal byte array.</p> 
 * @author sallai
 *
 */
public class InputStreamContentReader
{
	private final byte[] content;

	/**
	 * Returns the content of the given input stream as byte array.
	 * @param inputStream
	 * @return the content of the given input stream as byte array
	 * @throws IOException
	 */
	public InputStreamContentReader(final InputStream inputStream) throws IOException
	{
		content = new byte[inputStream.available()];
		int position = 0;
		final byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(buffer)) > -1)
		{
			System.arraycopy(buffer, 0, content, position, read);
			position = position + read;
		}
	}

	/**
	 * Returns a defensive copy of the internal byte array.
	 * @return a defensive copy of the internal byte array
	 */
	public byte[] getContent()
	{
		final byte[] copy = new byte[content.length];
		System.arraycopy(content, 0, copy, 0, content.length);
		return copy;
	}

}
