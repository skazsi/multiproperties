package hu.skzs.multiproperties.base.model.fileformat;

/**
 * The <code>UnsupportedSchemaVersionException</code> exception is thrown when the schema version is not known.
 *  <p>The most often case is when an older plugin tries to load a newer schema (MultiProperties file).</p>
 * @author skzs
 */
public class UnsupportedSchemaVersionException extends SchemaConverterException
{

	private static final long serialVersionUID = 1L;
	private final String version;

	/**
	 * Default constructor
	 * @param version the schema version which is not supported
	 */
	public UnsupportedSchemaVersionException(String version)
	{
		super();
		this.version = version;
	}

	@Override
	public String getLocalizedMessage()
	{
		return "Unsupported schema version: " + version; //$NON-NLS-1$
	}
}
