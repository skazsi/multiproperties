package hu.skzs.multiproperties.base.parameter;

/**
 * The {@link ColumnConfigPatternParameter} represents the configuration to be used for all of columns.
 * <p>The Ant style <code>${columnName}</code> can be used to result different value for every column.</p>
 * It is used when the handler saves the content of the column.
 * @author skzs
 *
 */
public class ColumnConfigPatternParameter extends ValuedParameter<String>
{

	private final String columnConfigPattern;

	/**
	 * Default constructor
	 * @param value the given parameter value as String
	 */
	public ColumnConfigPatternParameter(final String value)
	{
		super(value);
		columnConfigPattern = value;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.parameter.ValuedParameter#getValue()
	 */
	@Override
	public String getValue()
	{
		return columnConfigPattern;
	}

}
