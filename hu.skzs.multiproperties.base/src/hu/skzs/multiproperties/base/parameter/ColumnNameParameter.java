package hu.skzs.multiproperties.base.parameter;

/**
 * The {@link ColumnNameParameter} represents the name of the desired column in the MultiProperties file
 * @author skzs
 *
 */
public class ColumnNameParameter extends ValuedParameter<String>
{

	private final String columnName;

	/**
	 * Default constructor
	 * @param value the given parameter value as String
	 */
	public ColumnNameParameter(final String value)
	{
		super(value);
		columnName = value;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.parameter.ValuedParameter#getValue()
	 */
	@Override
	public String getValue()
	{
		return columnName;
	}

}
