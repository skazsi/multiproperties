package hu.skzs.multiproperties.handler.java.writer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * File system based {@link Writer} implementation.
 * <p>It is able to write the output files into a specific file system path specified by a filename.</p>
 * @author sallai
 *
 */
public class FileSystemWriter extends Writer
{

	private String fileName;

	/**
	 * Default constructor, which parses the given <code>configuration</code>.
	 * @param configuration the given configuration
	 * @throws WriterConfigurationException when the format is invalid
	 */
	public FileSystemWriter(final String configuration) throws WriterConfigurationException
	{
		super(configuration);
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.handler.java.writer.Writer#write(byte[])
	 */
	@Override
	public void write(final byte[] bytes) throws WriterException
	{
		final File file = new File(fileName);
		FileOutputStream outputStream = null;
		try
		{
			outputStream = new FileOutputStream(file);
			outputStream.write(bytes);
			outputStream.flush();
			outputStream.close();
		}
		catch (final Exception e)
		{
			throw new WriterException("Unable to write the content", e); //$NON-NLS-1$
		}
		finally
		{
			try
			{
				if (outputStream != null)
					outputStream.close();
			}
			catch (final Exception e)
			{
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.handler.java.writer.Writer#parsePath(java.lang.String)
	 */
	@Override
	public void parsePath(final String path)
	{
		fileName = path;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.handler.java.writer.Writer#formatPath()
	 */
	@Override
	public String formatPath()
	{
		return fileName;
	}

	/**
	 * Sets the file name
	 * @param fileName the given file name
	 */
	public void setFileName(final String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * Returns the file name
	 * @return the file name
	 */
	public String getFileName()
	{
		return fileName;
	}

}
