package hu.skzs.multiproperties.handler.java.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;

/**
 * A {@link AbstractConfigurator} implementation is responsible for parsing and formatting the handler configuration.
 * @author sallai
 */
public abstract class AbstractConfigurator
{

	public static final String DELIM = "|"; //$NON-NLS-1$

	protected boolean includeDescription;
	protected boolean includeColumnDescription;
	protected boolean includeDisabled;
	/**
	 * @since 1.1 file format version
	 */
	protected boolean disableDefault;

	/**
	 * Default constructor, which parses the given <code>configuration</code>.
	 * 
	 * @param configuration the given configuration
	 * @throws HandlerException when the format is invalid
	 */
	public AbstractConfigurator(final String configuration) throws HandlerException
	{
		if (configuration == null || configuration.equals("")) //$NON-NLS-1$
			return;
		try
		{
			final String[] tokens = configuration.split("\\" + DELIM); //$NON-NLS-1$
			if (tokens.length != 4 && tokens.length != 5)
				throw new IllegalArgumentException("Invalid configuration: " + configuration); //$NON-NLS-1$

			parsePath(tokens[0]);
			includeDescription = parseBoolean(tokens[1]);
			includeColumnDescription = parseBoolean(tokens[2]);
			includeDisabled = parseBoolean(tokens[3]);
			if (tokens.length == 5)
			{
				disableDefault = Boolean.parseBoolean(tokens[4]);
			}
		}
		catch (final Exception e)
		{
			throw new HandlerException("Unexpected error occurred during parsing the handler configuration", e); //$NON-NLS-1$
		}
	}

	/**
	 * Returns the persisted format of the handler configuration
	 * 
	 * @return the persisted format of the handler configuration
	 */
	@Override
	public String toString()
	{
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(formatPath());
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
	 * Returns the boolean value of the given value string. Return only <code>true</code>, if the given
	 * <code>value</code> parameter equals to "true" by ignoring the case. Return only <code>false</code>,
	 * if the given <code>value</code> parameter equals to "false" by ignoring the case.
	 * @param value the given boolean string
	 * @return the boolean value of the given value string
	 * @throws HandlerException if the value cannot be parsed as boolean
	 */
	protected boolean parseBoolean(final String value) throws HandlerException
	{
		if (value == null)
			throw new HandlerException("The boolean text cannot be null"); //$NON-NLS-1$
		if (value.equalsIgnoreCase(Boolean.TRUE.toString()))
			return true;
		else if (value.equalsIgnoreCase(Boolean.FALSE.toString()))
			return false;
		else
			throw new HandlerException("Not a boolean value text: " + value); //$NON-NLS-1$
	}

	/**
	 * Parses the path part of the configuration.
	 * 
	 * @param path the given part part of the configuration
	 */
	public abstract void parsePath(String path);

	/**
	 * Returns the formatted path part of the configuration.
	 * @return the formatted String
	 */
	public abstract String formatPath();

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
	 * @since 1.1 file format version
	 */
	public void setDisableDefaultValues(final boolean disableDefault)
	{
		this.disableDefault = disableDefault;
	}

	/**
	 * Returns whether the default values should NOT be written
	 * @return whether the default values should NOT be written
	 * @since 1.1 file format version
	 */
	public boolean isDisableDefaultValues()
	{
		return disableDefault;
	}
}
