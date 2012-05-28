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

			// Identifying the column
			final Column column = getColumn(parameters.getColumnNameParameter().getValue(), table);

			// Identifying the column configuration
			String columnConfiguration;
			if (parameters.getColumnConfigParameter() != null)
				columnConfiguration = parameters.getColumnConfigParameter().getValue();
			else
				columnConfiguration = column.getHandlerConfiguration();

			// Instantiating the handler
			final IHandler handler = getHandler(parameters);

			// Calling the handler
			handler.save(columnConfiguration, table, column);
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
			throw new IllegalArgumentException("Missing 'file' parameter"); //$NON-NLS-1$
		if (parameters.getColumnNameParameter() == null)
			throw new IllegalArgumentException("Missing 'columnName' parameter"); //$NON-NLS-1$
		if (parameters.getHandlerClassParameter() == null)
			throw new IllegalArgumentException("Missing 'handlerClass' parameter"); //$NON-NLS-1$
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
		stringBuilder
				.append("\t<-file MultiPropertiesFilePath> <-columnName ColumnName> [-columnConfig ColumnConfig] <-handlerClass HandlerClass>\n"); //$NON-NLS-1$
		stringBuilder.append("or\n"); //$NON-NLS-1$
		stringBuilder.append("\t-help\n"); //$NON-NLS-1$
		stringBuilder.append("Where:\n"); //$NON-NLS-1$
		stringBuilder.append("\tfile         : Path to the MultiProperties file\n"); //$NON-NLS-1$
		stringBuilder.append("\tcolumnName   : Name of the column to be used by the handler\n"); //$NON-NLS-1$
		stringBuilder.append("\tcolumnConfig : The handler specific configuration to be used\n"); //$NON-NLS-1$
		stringBuilder.append("\thandlerClass : Fully qualified class name of the handler\n"); //$NON-NLS-1$
		stringBuilder.append("\thelp         : Prints this help out\n"); //$NON-NLS-1$
		stringBuilder.append("Example:\n"); //$NON-NLS-1$
		stringBuilder
				.append("\t-file c:\\file.multiproperties -columnName EN -columnConfig c:\\EN.properties|true|true|true -handlerClass hu.skzs.multiproperties.handler.java.JavaHandler\n"); //$NON-NLS-1$
		stringBuilder.append("Note:\n"); //$NON-NLS-1$
		stringBuilder
				.append("If you don't specify the columnConfig paramater, then the column configuration stored in the MultiProperties will be used.\n"); //$NON-NLS-1$
		return stringBuilder.toString();
	}
}
