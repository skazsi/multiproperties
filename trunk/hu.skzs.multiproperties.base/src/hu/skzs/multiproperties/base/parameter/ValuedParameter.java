package hu.skzs.multiproperties.base.parameter;

/**
 * The {@link ValuedParameter} is the base class of each valued parameter.
 * <p>The standard use case is to parsing the parameter value in the constructor, storing in a member, and simply
 * giving back in the {@link #getValue()} method.</p>
 * @author sallai
 *
 * @param <T> is the type of the parameter
 */
public abstract class ValuedParameter<T> extends Parameter
{

	/**
	 * Default constructor
	 * <p><strong>Note:</strong> must be overridden and parse the given <code>valueString</code> properly.
	 * @param valueString the given parameter value as String
	 */
	public ValuedParameter(final String valueString)
	{
	}

	/**
	 * Returns the value of the parameter.
	 * @return the value of the parameter
	 */
	public abstract T getValue();
}
