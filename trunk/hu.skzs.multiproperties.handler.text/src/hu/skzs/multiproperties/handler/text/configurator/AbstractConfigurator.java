package hu.skzs.multiproperties.handler.text.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;

/**
 * A {@link AbstractConfigurator} implementation is responsible for parsing and formatting the handler configuration.
 * @author sallai
 */
public abstract class AbstractConfigurator
{

	public static final String DELIM = "/#/"; //$NON-NLS-1$

	protected String encodingPattern;
	protected String headerPattern;
	protected String footerPattern;
	protected String propertyPattern;
	protected String commentPattern;
	protected String emptyPattern;

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
			final String[] tokens = configuration.split(DELIM);
			if (tokens.length != 8)
				throw new IllegalArgumentException("Invalid configuration: " + configuration); //$NON-NLS-1$

			parsePath(tokens[0]);
			if (tokens[1].length() == 0)
				encodingPattern = null;
			else
				encodingPattern = tokens[1];
			headerPattern = tokens[2];
			propertyPattern = tokens[3];
			commentPattern = tokens[4];
			emptyPattern = tokens[5];
			footerPattern = tokens[6];
			// The last pattern is the ETX. It is used only when the last token is empty String,
			// in that case the split does not result enough tokens.
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
		if (encodingPattern != null)
			stringBuilder.append(encodingPattern);
		stringBuilder.append(DELIM);
		stringBuilder.append(headerPattern);
		stringBuilder.append(DELIM);
		stringBuilder.append(propertyPattern);
		stringBuilder.append(DELIM);
		stringBuilder.append(commentPattern);
		stringBuilder.append(DELIM);
		stringBuilder.append(emptyPattern);
		stringBuilder.append(DELIM);
		stringBuilder.append(footerPattern);
		stringBuilder.append(DELIM);
		stringBuilder.append("ETX"); //$NON-NLS-1$
		return stringBuilder.toString();
	}

	/**
	 * Parses the path part of the configuration.
	 * 
	 * @param path the given part part of the configuration
	 */
	public abstract void parsePath(String path);

	/**
	 * Returns the formatted path part of the configuration.
	 */
	public abstract String formatPath();

	/**
	 * Returns the encoding pattern or <code>null</code> if default encoding
	 * is used.
	 * @return the encoding pattern or <code>null</code> if default encoding
	 * is used
	 */
	public String getEncodingPattern()
	{
		return encodingPattern;
	}

	/**
	 * Sets the encoding. Can be <code>null</code> for using default encoding.
	 * @param encodingPattern the given encoding pattern
	 */
	public void setEncodingPattern(final String encodingPattern)
	{
		this.encodingPattern = encodingPattern;
	}

	public String getHeaderPattern()
	{
		return headerPattern;
	}

	public void setHeaderPattern(final String headerPattern)
	{
		this.headerPattern = headerPattern;
	}

	public String getFooterPattern()
	{
		return footerPattern;
	}

	public void setFooterPattern(final String footerPattern)
	{
		this.footerPattern = footerPattern;
	}

	public String getPropertyPattern()
	{
		return propertyPattern;
	}

	public void setPropertyPattern(final String propertyPattern)
	{
		this.propertyPattern = propertyPattern;
	}

	public String getCommentPattern()
	{
		return commentPattern;
	}

	public void setCommentPattern(final String commentPattern)
	{
		this.commentPattern = commentPattern;
	}

	public String getEmptyPattern()
	{
		return emptyPattern;
	}

	public void setEmptyPattern(final String emptyPattern)
	{
		this.emptyPattern = emptyPattern;
	}

}
