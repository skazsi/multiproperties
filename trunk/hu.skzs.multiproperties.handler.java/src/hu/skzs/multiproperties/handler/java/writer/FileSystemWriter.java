package hu.skzs.multiproperties.handler.java.writer;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.handler.java.configurator.FileSystemConfigurator;

import java.io.File;
import java.io.FileOutputStream;

/**
 * File system based {@link IWriter} implementation.
 * @author sallai
 */
public class FileSystemWriter implements IWriter
{

	private final FileSystemConfigurator configurator;

	/**
	 * Package level constructor.
	 * @param configurator the given instance of {@link FileSystemConfigurator} to be used.
	 */
	FileSystemWriter(final FileSystemConfigurator configurator)
	{
		this.configurator = configurator;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.handler.text.writer.AbstractWriter#write(byte[])
	 */
	public void write(final byte[] bytes) throws HandlerException
	{
		final File file = new File(configurator.getFileName());
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
			throw new HandlerException("Unable to write the content", e); //$NON-NLS-1$
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
}
