package hu.skzs.multiproperties.base.parameter;

/**
 * The {@link ColumnConfigParameter} represents the configuration to be used for the desired column.
 * It is used when the handler saves the content of the column.
 * @author skzs
 *
 */
public class ColumnConfigParameter extends ValuedParameter<String>
{

	/**
	 * Default constructor
	 * @param value the given parameter value as String
	 */
	public ColumnConfigParameter(final String value)
	{
		super(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String convertValue(final String value)
	{
		return value;
	}
}
