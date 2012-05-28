package hu.skzs.multiproperties.base.model.fileformat;

import hu.skzs.multiproperties.base.model.Table;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * The <code>SchemaConverterFactory</code> constructs {@link ISchemaConverter} instances.
 * 
 * @author sallai
 * 
 */
public class SchemaConverterFactory
{

	/**
	 * The <code>NEWEST_VERSION</code> represents the newest schema version
	 */
	public static final String NEWEST_VERSION = hu.skzs.multiproperties.base.model.fileformat_1_1.SchemaConverter.VERSION;

	static final String SCHEMA_CONVERTER_PACKAGE_PREFIX = "hu.skzs.multiproperties.base.model.fileformat_"; //$NON-NLS-1$
	static final String SCHEMA_CONVERTER_CLASS_NAME = "SchemaConverter"; //$NON-NLS-1$

	/**
	 * Returns a newly constructed {@link SchemaConverter} instance based on the given <code>content</code>.
	 * 
	 * @param content
	 *            the given contents byte array
	 * @return a newly constructed {@link SchemaConverter} instance
	 * @throws UnsupportedSchemaVersionException
	 *             if an unsupported schema is tried to be used
	 * @throws SchemaConverterException
	 *             if an unexpected error occurred
	 */
	public static ISchemaConverter getSchemaConverter(final byte[] content) throws SchemaConverterException
	{
		try
		{
			// Identifying the schema version
			final String version = getVersion(new ByteArrayInputStream(content));
			return getSchemaConverter(version);
		}
		catch (final Exception e)
		{
			throw new SchemaConverterException("Unexpected error occurred during constructing schema converter", e); //$NON-NLS-1$
		}
	}

	/**
	 * Returns a newly constructed {@link SchemaConverter} instance based on the given {@link Table}.
	 * 
	 * @param table
	 *            the given table
	 * @return a newly constructed {@link SchemaConverter} instance
	 * @throws UnsupportedSchemaVersionException
	 *             if an unsupported schema is tried to be used
	 * @throws SchemaConverterException
	 *             if an unexpected error occurred
	 */
	public static ISchemaConverter getSchemaConverter(final Table table) throws SchemaConverterException
	{
		return getSchemaConverter(table.getVersion());
	}

	/**
	 * Returns a newly constructed newest {@link SchemaConverter} instance.
	 * <p>This method should be called for creating the latest schema format.</p>
	 * 
	 * @return a newly constructed newest {@link SchemaConverter} instance
	 * @throws SchemaConverterException if an unexpected error occurred
	 */
	public static ISchemaConverter getSchemaConverter() throws SchemaConverterException
	{
		return getSchemaConverter(NEWEST_VERSION);
	}

	/**
	 * Returns a newly constructed {@link SchemaConverter} instance based on the given version.
	 * 
	 * @param version
	 *            the given version
	 * @return a newly constructed {@link SchemaConverter} instance
	 * @throws UnsupportedSchemaVersionException
	 *             if an unsupported schema is tried to be used
	 * @throws SchemaConverterException
	 *             if an unexpected error occurred
	 */
	private static ISchemaConverter getSchemaConverter(final String version) throws SchemaConverterException
	{
		final String _version = version.replaceAll("\\.", "_"); //$NON-NLS-1$//$NON-NLS-2$

		// Creating the schema converter instance
		try
		{
			@SuppressWarnings("rawtypes")
			final Class c = Class.forName(SCHEMA_CONVERTER_PACKAGE_PREFIX + _version
					+ "." + SCHEMA_CONVERTER_CLASS_NAME); //$NON-NLS-1$
			final ISchemaConverter schemaConverter = (ISchemaConverter) c.newInstance();
			return schemaConverter;
		}
		catch (final ClassNotFoundException e)
		{
			throw new UnsupportedSchemaVersionException(version);
		}
		catch (final Exception e)
		{
			throw new SchemaConverterException("Unexpected exception when constructing schema converter", e); //$NON-NLS-1$
		}
	}

	/**
	 * Returns the schema version, or in other word the file format version of the given input stream.
	 * <p>
	 * The method always returns a value or it throws {@link SchemaConverterException}.
	 * </p>
	 * 
	 * @param inputStream
	 *            the given input stream
	 * @return the schema version
	 * @throws SchemaConverterException
	 *             if an unexpected error occurred
	 */
	private static String getVersion(final InputStream inputStream) throws SchemaConverterException
	{
		try
		{
			final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			final SAXParser saxParser = saxParserFactory.newSAXParser();

			final VersionInfoParser versionInfoParser = new VersionInfoParser();
			saxParser.parse(inputStream, versionInfoParser);
			return versionInfoParser.getVersion();
		}
		catch (final Exception e)
		{
			throw new SchemaConverterException("Unable to identify the schema version", e); //$NON-NLS-1$
		}
	}
}
