package hu.skzs.multiproperties.handler.text.configurator;

import hu.skzs.multiproperties.support.handler.configurator.IFileSystemConfigurator;
import hu.skzs.multiproperties.support.handler.writer.FileSystemWriter;

/**
 * A {@link FileSystemConfigurator} implementation represents and describes a file system based handler
 * configuration for the <strong>Text File Handler</strong>.
 * @author skzs
 * @see FileSystemWriter
 * @see WorkspaceConfigurator
 */
public class FileSystemConfigurator extends TextHandlerConfigurator implements IFileSystemConfigurator
{

	private String fileName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.skzs.multiproperties.handler.java.writer.Writer#parsePath(java.lang.String)
	 */
	@Override
	public void parsePath(final String path)
	{
		fileName = path;
	}

	/*
	 * (non-Javadoc)
	 * 
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
