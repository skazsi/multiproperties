package hu.skzs.multiproperties.handler.text.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;

/**
 * A {@link FileSystemConfigurator} implementation is responsible for parsing and formatting the file system based handler configuration.
 * @author sallai
 */
public class FileSystemConfigurator extends AbstractConfigurator
{

	private String fileName;

	/**
	 * Default constructor, which parses the given <code>configuration</code>.
	 * @param configuration the given configuration
	 * @throws HandlerException when the format is invalid
	 */
	public FileSystemConfigurator(final String configuration) throws HandlerException
	{
		super(configuration);
	}

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
