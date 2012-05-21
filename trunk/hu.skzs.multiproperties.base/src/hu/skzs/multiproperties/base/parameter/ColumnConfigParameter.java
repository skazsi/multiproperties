package hu.skzs.multiproperties.base.parameter;

/**
 * The {@link ColumnConfigParameter} represents the configuration to be used for the desired column.
 * It is used when the handler saves the content of the column.
 * @author sallai
 *
 */
public class ColumnConfigParameter extends ValuedParameter<String>
{

	private final String columnConfig;

	/**
	 * Default constructor
	 * @param value the given parameter value as String
	 */
	public ColumnConfigParameter(final String value)
	{
		super(value);
		columnConfig = value;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.parameter.ValuedParameter#getValue()
	 */
	@Override
	public String getValue()
	{
		return columnConfig;
	}

}
