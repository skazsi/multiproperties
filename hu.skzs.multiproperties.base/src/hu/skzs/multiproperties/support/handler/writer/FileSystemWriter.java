package hu.skzs.multiproperties.support.handler.writer;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.support.handler.configurator.IFileSystemConfigurator;

import java.io.File;
import java.io.FileOutputStream;

/**
 * File system based {@link AbstractWriter} implementation.
 * @author skzs
 */
public class FileSystemWriter extends AbstractWriter<IFileSystemConfigurator>
{

	/**
	 * Package protected constructor.
	 * @param configurator the given {@link IFileSystemConfigurator} instance
	 * @see WriterFactory
	 */
	FileSystemWriter(final IFileSystemConfigurator configurator)
	{
		super(configurator);
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.api.writer.IWriter#write(byte[])
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
