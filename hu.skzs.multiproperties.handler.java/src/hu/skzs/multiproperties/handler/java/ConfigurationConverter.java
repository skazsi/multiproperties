package hu.skzs.multiproperties.handler.java;

import java.util.StringTokenizer;

/**
 * The <code>ConfigurationConverter</code> is able to convert the handler's configuration between the Java properties and
 * persisted formats.
 * @author sallai
 *
 */
public class ConfigurationConverter
{

	private static final String DELIM = "|"; //$NON-NLS-1$
	private String containerName;
	private String fileName;
	private boolean includeDescription;
	private boolean includeColumnDescription;
	private boolean includeDisabled;
	private boolean disableDefault;

	/**
	 * Default constructor, which parses the given <code>configuration</code>.
	 * @param configuration the given configuration
	 */
	public ConfigurationConverter(final String configuration)
	{
		if (configuration.equals("")) //$NON-NLS-1$
			return;
		try
		{
			if (configuration.indexOf(DELIM) > -1)
			{
				final StringTokenizer tokenizer = new StringTokenizer(configuration, DELIM);
				final String path = tokenizer.nextToken();
				containerName = path.substring(0, path.lastIndexOf("/")); //$NON-NLS-1$
				fileName = path.substring(path.lastIndexOf("/") + 1); //$NON-NLS-1$
				includeDescription = Boolean.parseBoolean(tokenizer.nextToken());
				includeColumnDescription = Boolean.parseBoolean(tokenizer.nextToken());
				includeDisabled = Boolean.parseBoolean(tokenizer.nextToken());
				if (tokenizer.hasMoreTokens())
				{
					disableDefault = Boolean.parseBoolean(tokenizer.nextToken());
				}
			}
			else
			{
				// The previous version did not supported the three boolean flags
				containerName = configuration.substring(0, configuration.lastIndexOf("/")); //$NON-NLS-1$
				fileName = configuration.substring(configuration.lastIndexOf("/") + 1); //$NON-NLS-1$
			}
		}
		catch (final RuntimeException e)
		{
			Activator.logError("Unexpected error occurred during parsing the handler configuration", e); //$NON-NLS-1$
			throw e;
		}
	}

	/**
	 * Returns the persisted format of the handler configuration 
	 * @return the persisted format of the handler configuration
	 */
	@Override
	public String toString()
	{
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(containerName);
		stringBuilder.append("/"); //$NON-NLS-1$
		stringBuilder.append(fileName);
		stringBuilder.append(DELIM);
		stringBuilder.append(Boolean.toString(includeDescription));
		stringBuilder.append(DELIM);
		stringBuilder.append(Boolean.toString(includeColumnDescription));
		stringBuilder.append(DELIM);
		stringBuilder.append(Boolean.toString(includeDisabled));
		stringBuilder.append(DELIM);
		stringBuilder.append(Boolean.toString(disableDefault));
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

	/**
	 * Sets whether the description should be included as comment or not
	 * @param include specifies whether the description should be included as comment or not
	 */
	public void setIncludeDescription(final boolean include)
	{
		this.includeDescription = include;
	}

	/**
	 * Returns whether the description should be included as comment or not
	 * @return whether the description should be included as comment or not
	 */
	public boolean isDescriptionIncluded()
	{
		return includeDescription;
	}

	/**
	 * Sets whether the column description should be included as comment or not
	 * @param include specifies whether the column description should be included as comment or not
	 */
	public void setIncludeColumnDescription(final boolean include)
	{
		this.includeColumnDescription = include;
	}

	/**
	 * Returns whether the column description should be included as comment or not
	 * @return whether the column description should be included as comment or not
	 */
	public boolean isColumnDescriptionIncluded()
	{
		return includeColumnDescription;
	}

	/**
	 * Sets whether the disabled properties should be included as comment or not
	 * @param include specifies whether the disabled properties should be included as comment or not
	 */
	public void setIncludeDisabled(final boolean include)
	{
		this.includeDisabled = include;
	}

	/**
	 * Returns whether the disabled properties should be included as comment or not
	 * @return whether the disabled properties should be included as comment or not
	 */
	public boolean isDisabledPropertiesIncluded()
	{
		return includeDisabled;
	}

	/**
	 * Sets whether the default values should NOT be written
	 * @param disableDefault specifies whether the default values should NOT be written
	 */
	public void setDisableDefaultValues(final boolean disableDefault)
	{
		this.disableDefault = disableDefault;
	}

	/**
	 * Returns whether the default values should NOT be written
	 * @return whether the default values should NOT be written
	 */
	public boolean isDisableDefaultValues()
	{
		return disableDefault;
	}
}
