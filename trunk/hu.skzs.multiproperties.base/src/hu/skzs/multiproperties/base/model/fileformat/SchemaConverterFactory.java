package hu.skzs.multiproperties.base.model.fileformat;

import hu.skzs.multiproperties.base.Activator;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.model.fileformat_1_0.SchemaConverter;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

/**
 * The <code>SchemaConverterFactory</code> constructs {@link ISchemaConverter} instances.
 * 
 * @author Krisztian_Zsolt_Sall
 * 
 */
public class SchemaConverterFactory {

    private static final String SCHEMA_CONVERTER_PACKAGE_PREFIX = "hu.skzs.multiproperties.base.model.fileformat_";
    private static final String SCHEMA_CONVERTER_CLASS_NAME = "SchemaConverter";

    /**
     * Returns a newly constructed {@link SchemaConverter} instance based on the given <code>file</code>.
     * 
     * @param file
     *            the given file
     * @return a newly constructed {@link SchemaConverter} instance
     * @throws SchemaConverterException
     *             if an unexpected error occurred
     */
    public static ISchemaConverter getSchemaConverter(IFile file) throws SchemaConverterException
    {
        InputStream inputStream = null;
        String version = null;
        try
        {
            // Identifying the schema version
            inputStream = file.getContents();
            version = getVersion(inputStream).replaceAll("\\.", "_");
        } catch (CoreException e) {
            throw new SchemaConverterException("Unexpected error occurred during constructing schema converter", e);
        } finally
        {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Activator.logWarn("Unable to close properly the input stream", e);
                }
        }

        // Creating the schema converter instance
        try
        {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName(SCHEMA_CONVERTER_PACKAGE_PREFIX + version + "." + SCHEMA_CONVERTER_CLASS_NAME);
            ISchemaConverter schemaConverter = (ISchemaConverter) c.newInstance();
            return schemaConverter;
        } catch (Exception e)
        {
            throw new SchemaConverterException("Unexpected exception when constructing schema converter", e);
        }
    }

    /**
     * Returns a newly constructed {@link SchemaConverter} instance based on the given {@link Table}.
     * 
     * @param table
     *            the given table
     * @return a newly constructed {@link SchemaConverter} instance
     * @throws SchemaConverterException
     *             if an unexpected error occurred
     */
    public static ISchemaConverter getSchemaConverter(Table table) throws SchemaConverterException
    {
        String version = table.getVersion().replaceAll("\\.", "_");

        // Creating the schema converter instance
        try
        {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName(SCHEMA_CONVERTER_PACKAGE_PREFIX + version + "." + SCHEMA_CONVERTER_CLASS_NAME);
            ISchemaConverter schemaConverter = (ISchemaConverter) c.newInstance();
            return schemaConverter;
        } catch (Exception e)
        {
            throw new SchemaConverterException("Unexpected exception when constructing schema converter", e);
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
    private static String getVersion(InputStream inputStream) throws SchemaConverterException
    {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            VersionInfoParser versionInfoParser = new VersionInfoParser();
            saxParser.parse(inputStream, versionInfoParser);
            return versionInfoParser.getVersion();
        } catch (Exception e) {
            throw new SchemaConverterException("Unable to identify the schema version", e);
        }
    }
}
