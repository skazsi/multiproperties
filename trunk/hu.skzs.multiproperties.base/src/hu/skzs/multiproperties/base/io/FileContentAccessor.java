package hu.skzs.multiproperties.base.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * The {@link FileContentAccessor} and its implementations are providing common way for writing a file content
 * independently of the type of file.
 * @author skzs
 * @param <T> the type of the file
 */
public abstract class FileContentAccessor<T>
{
	/**
	 * The <code>SCHEMA_CHARSET</code> specifies the used encoding for the MultiProperties file.
	 */
	public static final String SCHEMA_CHARSET = "UTF-8"; //$NON-NLS-1$

	protected T file;

	/**
	 * Constructor
	 * @param file the given file
	 */
	FileContentAccessor(final T file)
	{
		this.file = file;
	}

	/**
	 * Returns the file
	 * @return the file
	 */
	public T getFile()
	{
		return file;
	}

	/**
	 * Returns the content as byte array.
	 * @return the content as byte array.
	 * @throws IOException 
	 */
	public abstract byte[] getContent() throws IOException;

	/**
	 * Sets the content with the given byte array content
	 * @param bytes the given byte array
	 * @throws IOException 
	 */
	public abstract void setContent(byte[] bytes) throws IOException;

	protected byte[] getBytes(final InputStream inputStream) throws IOException
	{
		final byte[] content = new byte[inputStream.available()];
		int position = 0;
		final byte[] buffer = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(buffer)) > -1)
		{
			System.arraycopy(buffer, 0, content, position, read);
			position = position + read;
		}
		return content;
	}
}
