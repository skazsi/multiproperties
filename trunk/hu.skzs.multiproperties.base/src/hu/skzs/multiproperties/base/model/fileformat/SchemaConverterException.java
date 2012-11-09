package hu.skzs.multiproperties.base.model.fileformat;

/**
 * The <code>SchemaConverterException</code> exception type represents
 * schema converting problems.
 * @author skzs
 */
public class SchemaConverterException extends Exception
{

	private static final long serialVersionUID = 1L;

	public SchemaConverterException()
	{
		super();
	}

	public SchemaConverterException(String message)
	{
		super(message);
	}

	public SchemaConverterException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public SchemaConverterException(Throwable cause)
	{
		super(cause);
	}
}
