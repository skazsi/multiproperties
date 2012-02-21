package hu.skzs.multiproperties.base.model.fileformat;

/**
 * The <code>UnsupportedSchemaVersionException</code> exception is thrown when the schema version is not known.
 *  <p>The most often case is when an older plugin tries to load a newer schema (MultiProperties file).</p>
 * @author sallai
 */
public class UnsupportedSchemaVersionException extends SchemaConverterException
{

	private static final long serialVersionUID = 1L;

	public UnsupportedSchemaVersionException(String alma)
	{
		super();
	}
}
