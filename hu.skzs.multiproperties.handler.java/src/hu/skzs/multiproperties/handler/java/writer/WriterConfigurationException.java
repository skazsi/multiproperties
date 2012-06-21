package hu.skzs.multiproperties.handler.java.writer;

/**
 * The <code>WriterConfigurationException</code> is thrown when the configuration cannot be parsed or formatted.
 * @author sallai
 *
 */
public class WriterConfigurationException extends WriterException
{

	private static final long serialVersionUID = 1L;

	public WriterConfigurationException()
	{
		super();
	}

	public WriterConfigurationException(final String message)
	{
		super(message);
	}

	public WriterConfigurationException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public WriterConfigurationException(final Throwable cause)
	{
		super(cause);
	}
}
