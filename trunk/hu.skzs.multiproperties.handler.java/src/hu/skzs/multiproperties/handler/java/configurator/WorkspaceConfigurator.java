package hu.skzs.multiproperties.handler.java.configurator;

import hu.skzs.multiproperties.support.handler.configurator.IWorkspaceConfigurator;
import hu.skzs.multiproperties.support.handler.writer.WorkspaceWriter;

/**
 * A {@link WorkspaceConfigurator} implementation represents and describes a workspace based handler
 * configuration for the <strong>Java Properties Handler</strong>.
 * @author skzs
 * @see WorkspaceWriter
 * @see FileSystemConfigurator
 */
public class WorkspaceConfigurator extends JavaHandlerConfigurator implements IWorkspaceConfigurator
{

	private String containerName;
	private String fileName;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parsePath(final String path)
	{
		if (path.length() > 0)
		{
			containerName = path.substring(0, path.lastIndexOf("/")); //$NON-NLS-1$
			fileName = path.substring(path.lastIndexOf("/") + 1); //$NON-NLS-1$
		}
		else
		{
			containerName = null;
			fileName = null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String formatPath()
	{
		if (containerName == null && fileName == null)
			return ""; //$NON-NLS-1$
		return containerName + "/" + fileName; //$NON-NLS-1$
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
	 * {@inheritDoc}
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
	 * {@inheritDoc}
	 */
	public String getFileName()
	{
		return fileName;
	}

}
