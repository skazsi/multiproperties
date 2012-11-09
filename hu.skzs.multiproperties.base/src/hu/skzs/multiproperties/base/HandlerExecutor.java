package hu.skzs.multiproperties.base;

import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.io.InputStreamContentReader;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.model.fileformat.ISchemaConverter;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory;
import hu.skzs.multiproperties.base.parameter.Parameters;

import java.io.File;
import java.io.FileInputStream;

public class HandlerExecutor
{
	private static final String MARKER_COLUMNNAME = "${columnName}"; //$NON-NLS-1$

	public static void main(final String[] args)
	{
		try
		{
			// Checking the arguments
			final Parameters parameters = new Parameters(args);
			if (parameters.size() == 0 || parameters.getHelpParameter() != null)
			{
				System.out.println(getUsage());
				System.exit(-1);
			}
			validateParameters(parameters);

			final File multiPropertiesFile = parameters.getFileParameter().getValue();
			final byte[] content = new InputStreamContentReader(new FileInputStream(multiPropertiesFile)).getContent();

			// Loading the MultiProperties file
			final ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter(content);
			final Table table = schemaConverter.convert(content);

			// Instantiating the handler
			final IHandler handler = getHandler(parameters);

			if (parameters.getColumnConfigPatternParameter() != null)
			{ // Transforming all of the columns
				for (final Column column : table.getColumns().toArray())
				{
					final String columnConfiguration = replaceConfiguration(parameters
							.getColumnConfigPatternParameter().getValue(), column);

					// Calling the handler
					handler.save(columnConfiguration, table, column);
				}
			}
			else
			{ // Transforming only one column

				// Identifying the column
				final Column column = getColumn(parameters.getColumnNameParameter().getValue(), table);

				// Identifying the column configuration
				String columnConfiguration;
				if (parameters.getColumnConfigParameter() != null)
					columnConfiguration = parameters.getColumnConfigParameter().getValue();
				else
					columnConfiguration = column.getHandlerConfiguration();

				// Calling the handler
				handler.save(columnConfiguration, table, column);
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Specifies whether the given {@link Parameters} object is fully constructed or not.
	 * @param parameters
	 * @throws IllegalArgumentException if one of the required parameters is missing
	 */
	private static void validateParameters(final Parameters parameters)
	{
		if (parameters.getFileParameter() == null)
			throw new IllegalArgumentException("The 'file' parameter is required"); //$NON-NLS-1$

		if (parameters.getColumnConfigParameter() != null && parameters.getColumnConfigPatternParameter() != null)
			throw new IllegalArgumentException(
					"Only one of 'columnConfig' or 'columnConfigPattern' parameters are allowed at same time"); //$NON-NLS-1$

		if (parameters.getColumnNameParameter() == null && parameters.getColumnConfigPatternParameter() == null)
			throw new IllegalArgumentException("The 'columnName' parameter is required"); //$NON-NLS-1$

		if (parameters.getColumnNameParameter() != null && parameters.getColumnConfigPatternParameter() != null)
			throw new IllegalArgumentException(
					"The 'columnName' parameter is not allowed to be used, if 'columnConfigPattern' is set"); //$NON-NLS-1$

		if (parameters.getHandlerClassParameter() == null)
			throw new IllegalArgumentException("The 'handlerClass' parameter is required"); //$NON-NLS-1$
	}

	/**
	 * Return the the first {@link Column} in the given {@link Table} which name equals to the
	 * given <code>columnName</code>.
	 * @return
	 * @throws IllegalArgumentException if no column can be found with such a name
	 */
	private static Column getColumn(final String columnName, final Table table)
	{
		for (final Column currentColumn : table.getColumns().toArray())
		{
			if (currentColumn.getName().equals(columnName))
			{
				return currentColumn;
			}
		}
		throw new IllegalArgumentException("No '" + columnName + "' named column can be found"); //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * Returns the replaced column configuration.
	 * @param pattern the original value
	 * @param column the given {@link Column} instance
	 * @return the replaced pattern
	 */
	private static String replaceConfiguration(final String pattern, final Column column)
	{
		final String replaced = pattern.replace(MARKER_COLUMNNAME, column.getName());
		return replaced;
	}

	/**
	 * Returns a newly constructed {@link IHandler} based on the given <code>parameters</code>'
	 * handlerClassParameter.
	 * @param parameters
	 * @see Parameters#getHandlerClassParameter()
	 */
	private static IHandler getHandler(final Parameters parameters)
	{
		final Class<IHandler> handlerClass = parameters.getHandlerClassParameter().getValue();
		try
		{
			return handlerClass.newInstance();
		}
		catch (final Exception e)
		{
			throw new IllegalArgumentException("Unable to instantiating '" + handlerClass + "' handler"); //$NON-NLS-1$//$NON-NLS-2$
		}
	}

	/**
	 * Returns the usage information as String.
	 * @return the usage information as String
	 */
	private static final String getUsage()
	{
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("MultiProperties Handler Executor\n"); //$NON-NLS-1$
		stringBuilder.append("Usage:\n"); //$NON-NLS-1$
		stringBuilder.append("\tfor transforming only one column:\n"); //$NON-NLS-1$
		stringBuilder
				.append("\t  <-file MultiPropertiesFilePath> <-columnName ColumnName> [-columnConfig ColumnConfig] <-handlerClass HandlerClass>\n"); //$NON-NLS-1$
		stringBuilder.append("\tfor transforming all columns:\n"); //$NON-NLS-1$
		stringBuilder
				.append("\t  <-file MultiPropertiesFilePath> <-columnConfigPattern ColumnConfigPattern> <-handlerClass HandlerClass>\n"); //$NON-NLS-1$
		stringBuilder.append("\thelp\n"); //$NON-NLS-1$
		stringBuilder.append("\t  -help\n"); //$NON-NLS-1$
		stringBuilder.append("Where:\n"); //$NON-NLS-1$
		stringBuilder.append("\tfile                : Path to the MultiProperties file.\n"); //$NON-NLS-1$
		stringBuilder.append("\tcolumnName          : Name of the column to be used by the handler.\n"); //$NON-NLS-1$
		stringBuilder.append("\tcolumnConfig        : The handler specific configuration to be used.\n"); //$NON-NLS-1$
		stringBuilder.append("\t                      When neither of this and 'columnConfigPattern' is set,\n"); //$NON-NLS-1$
		stringBuilder.append("\t                      then the stored value by MultiProperties file is used.\n"); //$NON-NLS-1$
		stringBuilder.append("\t                      Cannot be used together with 'columnConfigPattern'.\n"); //$NON-NLS-1$
		stringBuilder.append("\tcolumnConfigPattern : The handler specific configuration pattern to be used.\n"); //$NON-NLS-1$
		stringBuilder
				.append("\t                      When used, all of the columns will be transformed. It's value can\n"); //$NON-NLS-1$
		stringBuilder
				.append("\t                      contain ${columnName} marker which will be replaced to the current column.\n"); //$NON-NLS-1$
		stringBuilder.append("\t                      Cannot be used together with 'columnConfig'.\n"); //$NON-NLS-1$
		stringBuilder.append("\thandlerClass        : Fully qualified class name of the handler.\n"); //$NON-NLS-1$
		stringBuilder.append("\thelp                : Prints this help out.\n"); //$NON-NLS-1$
		stringBuilder.append("Example:\n"); //$NON-NLS-1$
		stringBuilder
				.append("\t-file c:\\file.multiproperties -columnName EN -columnConfig c:\\EN.properties|true|true|true -handlerClass hu.skzs.multiproperties.handler.java.JavaHandler\n"); //$NON-NLS-1$
		stringBuilder.append("or\n"); //$NON-NLS-1$
		stringBuilder
				.append("\t-file c:\\file.multiproperties -columnConfigPattern c:\\${columnName}.properties|true|true|true -handlerClass hu.skzs.multiproperties.handler.java.JavaHandler\n"); //$NON-NLS-1$
		return stringBuilder.toString();
	}
}
