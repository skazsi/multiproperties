package hu.skzs.multiproperties.base.api;

/**
 * The <code>HandlerException</code> exception type represents
 * handler problems.
 * @author sallai
 */
public class HandlerException extends Exception
{

	private static final long serialVersionUID = 1L;

	public HandlerException()
	{
		super();
	}

	public HandlerException(final String message)
	{
		super(message);
	}

	public HandlerException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public HandlerException(final Throwable cause)
	{
		super(cause);
	}
}
