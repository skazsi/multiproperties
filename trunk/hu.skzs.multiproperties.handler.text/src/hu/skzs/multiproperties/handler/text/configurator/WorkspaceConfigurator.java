package hu.skzs.multiproperties.handler.text.configurator;

import hu.skzs.multiproperties.support.handler.configurator.IWorkspaceConfigurator;
import hu.skzs.multiproperties.support.handler.writer.WorkspaceWriter;

/**
 * A {@link WorkspaceConfigurator} implementation represents and describes a workspace based handler
 * configuration for the <strong>Text File Handler</strong>.
 * @author skzs
 * @see WorkspaceWriter
 * @see FileSystemConfigurator
 */
public class WorkspaceConfigurator extends TextHandlerConfigurator implements IWorkspaceConfigurator
{

	private String containerName;
	private String fileName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.skzs.multiproperties.handler.java.writer.Writer#parsePath(java.lang.String)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.skzs.multiproperties.handler.java.writer.Writer#formatPath()
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
	 * 
	 * @param containerName the given container name
	 */
	public void setContainerName(final String containerName)
	{
		this.containerName = containerName;
	}

	/**
	 * Returns the container name
	 * 
	 * @return the container name
	 */
	public String getContainerName()
	{
		return containerName;
	}

	/**
	 * Sets the file name
	 * 
	 * @param fileName the given file name
	 */
	public void setFileName(final String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * Returns the file name
	 * 
	 * @return the file name
	 */
	public String getFileName()
	{
		return fileName;
	}

}
