package hu.skzs.multiproperties.base.parameter;

import hu.skzs.multiproperties.base.api.IHandler;

/**
 * The {@link HandlerClassParameter} represents the handler class to be used
 * @author skzs
 *
 */
public class HandlerClassParameter extends ValuedParameter<Class<IHandler>>
{

	/**
	 * Default constructor
	 * @param value the given parameter value as String
	 */
	public HandlerClassParameter(final String value)
	{
		super(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Class<IHandler> convertValue(final String value)
	{
		try
		{
			@SuppressWarnings("unchecked")
			final Class<IHandler> clazz = (Class<IHandler>) Class.forName(value);
			return clazz;
		}
		catch (final ClassNotFoundException e)
		{
			throw new IllegalArgumentException("Unable to load handler class", e); //$NON-NLS-1$
		}
	}
}
