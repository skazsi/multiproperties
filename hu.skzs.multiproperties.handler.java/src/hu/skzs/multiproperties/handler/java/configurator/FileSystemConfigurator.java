package hu.skzs.multiproperties.handler.java.configurator;

import hu.skzs.multiproperties.support.handler.configurator.IFileSystemConfigurator;
import hu.skzs.multiproperties.support.handler.writer.FileSystemWriter;

/**
 * A {@link FileSystemConfigurator} implementation represents and describes a file system based handler
 * configuration for the <strong>Java Properties Handler</strong>.
 * @author skzs
 * @see FileSystemWriter
 * @see WorkspaceConfigurator
 */
public class FileSystemConfigurator extends JavaHandlerConfigurator implements IFileSystemConfigurator
{

	private String fileName;

	@Override
	public void parsePath(final String path)
	{
		fileName = path;
	}

	@Override
	public String formatPath()
	{
		return fileName;
	}

	public String getFileName()
	{
		return fileName;
	}

}
