package hu.skzs.multiproperties.handler.java.writer;

/**
 * The <code>WriterException</code> is a generic exception for the {@link Writer} implementations.
 * @author sallai
 *
 */
public class WriterException extends Exception
{

	private static final long serialVersionUID = 1L;

	public WriterException()
	{
		super();
	}

	public WriterException(final String message)
	{
		super(message);
	}

	public WriterException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public WriterException(final Throwable cause)
	{
		super(cause);
	}
}
