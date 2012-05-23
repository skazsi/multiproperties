package hu.skzs.multiproperties.handler.java.writer;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

/**
 * Eclipse workspace based {@link Writer} implementation.
 * <p>It is able to write the output files into a specific workspace path specified by a container and a filename.</p>
 * @author sallai
 *
 */
public class WorkspaceWriter extends Writer
{

	private String containerName;
	private String fileName;

	/**
	 * Default constructor, which parses the given <code>configuration</code>.
	 * @param configuration the given configuration
	 * @throws WriterConfigurationException when the format is invalid
	 */
	public WorkspaceWriter(final String configuration) throws WriterConfigurationException
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
		try
		{
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			final IResource resource = root.findMember(new Path(containerName));
			if (resource == null)
				throw new WriterException("Non existing workspace location: " + containerName); //$NON-NLS-1$
			final IContainer container = (IContainer) resource;

			final IFile file = container.getFile(new Path(fileName));

			// Writing the content
			final ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			if (file.exists())
			{
				file.setContents(stream, false, true, null);
			}
			else
			{
				file.create(stream, false, null);
			}
		}
		catch (final CoreException e)
		{
			throw new WriterException("Unable to write the content", e); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.handler.java.writer.Writer#parsePath(java.lang.String)
	 */
	@Override
	public void parsePath(final String path)
	{
		containerName = path.substring(0, path.lastIndexOf("/")); //$NON-NLS-1$
		fileName = path.substring(path.lastIndexOf("/") + 1); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.handler.java.writer.Writer#formatPath()
	 */
	@Override
	public String formatPath()
	{
		final StringBuilder stringBuilder = new StringBuilder(containerName);
		stringBuilder.append("/"); //$NON-NLS-1$
		stringBuilder.append(fileName);
		return stringBuilder.toString();
	}

	/**
	 * Sets the container name
	 * @param containerName the given container name
	 */
	public void setContainerName(final String containerName)
	{
		this.containerName = containerName;
	}

	/**
	 * Returns the container name
	 * @return the container name
	 */
	public String getContainerName()
	{
		return containerName;
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
