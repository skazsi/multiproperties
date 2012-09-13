package hu.skzs.multiproperties.base.api;

/**
 * The {@link ImporterException} type represents importer problems.
 * @author sallai
 */
public class ImporterException extends Exception
{

	private static final long serialVersionUID = 1L;

	public ImporterException()
	{
		super();
	}

	public ImporterException(final String message)
	{
		super(message);
	}

	public ImporterException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public ImporterException(final Throwable cause)
	{
		super(cause);
	}
}
