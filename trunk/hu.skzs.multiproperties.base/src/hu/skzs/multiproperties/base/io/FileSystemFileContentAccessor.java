package hu.skzs.multiproperties.base.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;

/**
 * The {@link FileSystemFileContentAccessor} is a file system based
 * implementation of {@link FileContentAccessor}.
 * @author skzs
 */
public class FileSystemFileContentAccessor extends FileContentAccessor<File>
{

	public FileSystemFileContentAccessor(final File file)
	{
		super(file);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getContent() throws IOException
	{
		InputStream inputStream = null;
		try
		{
			inputStream = file.toURI().toURL().openStream();
			return getBytes(inputStream);
		}
		finally
		{
			if (inputStream != null)
			{
				try
				{
					inputStream.close();
				}
				catch (final IOException e)
				{
					// Do nothing
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setContent(final byte[] bytes) throws IOException
	{
		OutputStream outputStream = null;
		try
		{
			final URI targetURI = file.toURI();
			final IFileStore store = EFS.getStore(targetURI);
			outputStream = store.openOutputStream(EFS.NONE, null);
			outputStream.write(bytes);
			outputStream.flush();
		}
		catch (final CoreException e)
		{
			throw new IOException("Unable to set the content", e); //$NON-NLS-1$
		}
		finally
		{
			if (outputStream != null)
			{
				try
				{
					outputStream.close();
				}
				catch (final IOException e)
				{
					// Do nothing
				}
			}
		}
	}

}