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

	@Override
	public void parsePath(final String path)
	{
		if (path.length() > 0)
		{
			containerName = path.substring(0, path.lastIndexOf("/"));
			fileName = path.substring(path.lastIndexOf("/") + 1);
		}
		else
		{
			containerName = null;
			fileName = null;
		}
	}

	@Override
	public String formatPath()
	{
		if (containerName == null && fileName == null)
			return "";
		return containerName + "/" + fileName;
	}

	public void setContainerName(final String containerName)
	{
		this.containerName = containerName;
	}

	public String getContainerName()
	{
		return containerName;
	}

	public void setFileName(final String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileName()
	{
		return fileName;
	}

}
