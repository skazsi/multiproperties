package hu.skzs.multiproperties.base.parameter;

import hu.skzs.multiproperties.base.api.IHandler;

/**
 * The {@link HandlerClassParameter} represents the handler class to be used
 * @author sallai
 *
 */
public class HandlerClassParameter extends ValuedParameter<Class<IHandler>>
{

	private final Class<IHandler> clazz;

	/**
	 * Default constructor
	 * @param value the given parameter value as String
	 */
	@SuppressWarnings("unchecked")
	public HandlerClassParameter(final String value)
	{
		super(value);
		try
		{
			clazz = (Class<IHandler>) Class.forName(value);
		}
		catch (final ClassNotFoundException e)
		{
			throw new IllegalArgumentException("Unable to load handler class", e); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.parameter.ValuedParameter#getValue()
	 */
	@Override
	public Class<IHandler> getValue()
	{
		return clazz;
	}

}
