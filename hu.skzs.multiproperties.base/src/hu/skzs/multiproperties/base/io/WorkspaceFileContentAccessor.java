package hu.skzs.multiproperties.base.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

/**
 * The {@link WorkspaceFileContentAccessor} is an Eclipse workspace based
 * implementation of {@link FileContentAccessor}.
 * @author skzs
 */
public class WorkspaceFileContentAccessor extends FileContentAccessor<IFile>
{

	public WorkspaceFileContentAccessor(final IFile file)
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
			inputStream = file.getContents(true);
			return getBytes(inputStream);
		}
		catch (final CoreException e)
		{
			throw new IOException("Unable to get the content", e); //$NON-NLS-1$
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
		try
		{
			final InputStream inputStream = new ByteArrayInputStream(bytes);
			if (file.exists())
			{
				file.setCharset(SCHEMA_CHARSET, null);
				file.setContents(inputStream, true, true, null);
			}
			else
			{
				file.create(inputStream, true, null);
				file.setCharset(SCHEMA_CHARSET, null);
			}
		}
		catch (final CoreException e)
		{
			throw new IOException("Unable to set the content", e); //$NON-NLS-1$
		}
	}

}