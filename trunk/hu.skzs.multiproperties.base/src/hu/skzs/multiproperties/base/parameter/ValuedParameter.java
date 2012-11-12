package hu.skzs.multiproperties.base.parameter;

/**
 * The {@link ValuedParameter} is the base class of each valued parameter.
 * <p>The standard use case is to parsing the parameter value in the constructor, storing in a member, and simply
 * giving back in the {@link #getValue()} method.</p>
 * @author skzs
 *
 * @param <T> is the type of the parameter
 */
public abstract class ValuedParameter<T> extends Parameter
{

	private final T value;

	/**
	 * Key constant for representing that VM argument which disables the <code>\n</code>
	 * line break resolving. If the this <code>disableLineBreak</code> exists and
	 * it's value equals to <code>true</code>, then the <code>\n</code> substring in the
	 * parameters will not be resolved as line break. 
	 */
	public static final String DISABLE_LINE_BREAK = "disableLineBreak"; //$NON-NLS-1$

	/**
	 * Default constructor
	 * <p><strong>Note:</strong> must be overridden and parse the given <code>valueString</code> properly.
	 * @param valueString the given parameter value as String
	 */
	public ValuedParameter(final String valueString)
	{
		this.value = convertValue(resolveNewLine(valueString));
	}

	/**
	 * Returns the converted value of the given String.
	 * @param value the given String
	 * @return the converted value of the given String
	 */
	protected abstract T convertValue(String value);

	/**
	 * Returns a new line resolved String if the {@link #DISABLE_LINE_BREAK} VM argument exists and it's
	 * value equals to <code>true</code>, otherwise it return the given String value.
	 * @return a new line resolved String
	 */
	protected String resolveNewLine(final String value)
	{
		if (System.getProperty(DISABLE_LINE_BREAK) != null
				&& System.getProperty(DISABLE_LINE_BREAK).equalsIgnoreCase(Boolean.TRUE.toString()))
		{
			return value;
		}
		return value.replaceAll("\\\\n", "\n"); //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * Returns the value of the parameter.
	 * @return the value of the parameter
	 */
	public T getValue()
	{
		return value;
	}

}
