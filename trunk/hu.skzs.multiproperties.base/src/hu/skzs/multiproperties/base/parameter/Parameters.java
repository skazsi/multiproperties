package hu.skzs.multiproperties.base.parameter;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

/**
 * The  <code>Parameters</code> class is able to parse the program arguments into instances of {@link Parameter}.
 * @author skzs
 *
 */
public class Parameters
{

	/**
	 * The package name of the parameters. All of the parameters must be in this package.
	 */
	private static final String PARAMETER_PACKAGE = "hu.skzs.multiproperties.base.parameter"; //$NON-NLS-1$

	private final List<Parameter> parameters = new LinkedList<Parameter>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Parameters(final String[] args)
	{
		int index = 0;
		while (index < args.length)
		{
			final Class clazz = getParameterClass(args[index]);
			try
			{
				Constructor constructor = null;
				Parameter parameter = null;
				try
				{
					constructor = clazz.getConstructor(String.class);
					parameter = (Parameter) constructor.newInstance(args[index + 1]);
					index = index + 2;
				}
				catch (final NoSuchMethodException e)
				{
					constructor = clazz.getConstructor();
					parameter = (Parameter) clazz.newInstance();
					index = index + 1;
				}
				parameters.add(parameter);
			}
			catch (final Exception e)
			{
				throw new IllegalArgumentException("Unexcepted error while parsing parameter: " //$NON-NLS-1$
						+ args[index].substring(1), e);
			}
		}
	}

	/**
	 * Return a {@link Class} object based on the given <code>className</code>. The class must be in the
	 * {@link Parameters#PARAMETER_PACKAGE} package.
	 * @param className
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Class getParameterClass(final String className)
	{
		if (!className.startsWith("-")) //$NON-NLS-1$
			throw new IllegalArgumentException("Invalid parameter: " + className); //$NON-NLS-1$
		try
		{
			final String resolvedClassName = className.substring(1, 2).toUpperCase() + className.substring(2);
			return Class.forName(PARAMETER_PACKAGE + "." + resolvedClassName + "Parameter"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		catch (final ClassNotFoundException e)
		{
			throw new IllegalArgumentException("Unknown parameter: " + className.substring(1)); //$NON-NLS-1$
		}
	}

	/**
	 * Returns the {@link HelpParameter} or <code>null</code> if it does not exists.
	 * @return the {@link HelpParameter}
	 */
	public HelpParameter getHelpParameter()
	{
		for (final Parameter parameter : parameters)
		{
			if (parameter instanceof HelpParameter)
				return (HelpParameter) parameter;
		}
		return null;
	}

	/**
	 * Returns the {@link FileParameter} or <code>null</code> if it does not exists.
	 * @return the {@link FileParameter}
	 */
	public FileParameter getFileParameter()
	{
		for (final Parameter parameter : parameters)
		{
			if (parameter instanceof FileParameter)
				return (FileParameter) parameter;
		}
		return null;
	}

	/**
	 * Returns the {@link ColumnNameParameter} or <code>null</code> if it does not exists.
	 * @return the {@link ColumnNameParameter}
	 */
	public ColumnNameParameter getColumnNameParameter()
	{
		for (final Parameter parameter : parameters)
		{
			if (parameter instanceof ColumnNameParameter)
				return (ColumnNameParameter) parameter;
		}
		return null;
	}

	/**
	 * Returns the {@link ColumnConfigParameter} or <code>null</code> if it does not exists.
	 * @return the {@link ColumnConfigParameter}
	 */
	public ColumnConfigParameter getColumnConfigParameter()
	{
		for (final Parameter parameter : parameters)
		{
			if (parameter instanceof ColumnConfigParameter)
				return (ColumnConfigParameter) parameter;
		}
		return null;
	}

	/**
	 * Returns the {@link HandlerClassParameter} or <code>null</code> if it does not exists.
	 * @return the {@link HandlerClassParameter}
	 */
	public HandlerClassParameter getHandlerClassParameter()
	{
		for (final Parameter parameter : parameters)
		{
			if (parameter instanceof HandlerClassParameter)
				return (HandlerClassParameter) parameter;
		}
		return null;
	}

	/**
	 * Returns the size of the parameters.
	 * @return the size of the parameters
	 */
	public int size()
	{
		return parameters.size();
	}

	/**
	 * Returns a specific parameter which was passed in position of <code>index</code>.
	 * @param index position of the parameter
	 * @return a specific parameter which was passed in position of <code>index</code>
	 */
	public Parameter get(final int index)
	{
		return parameters.get(index);
	}

	/**
	 * Returns the first parameter which is instance of the given <code>parameterClass</code> or <code>null</code>
	 * if there is no such parameter.
	 * @param parameterClass the given parameter class
	 * @return  the first parameter which is instance of the given <code>parameterClass</code>
	 */
	public Parameter get(final Class<? extends Parameter> parameterClass)
	{
		for (final Parameter parameter : parameters)
		{
			if (parameter.getClass().isAssignableFrom(parameterClass))
				return parameter;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("parameters:{"); //$NON-NLS-1$
		for (int i = 0; i < parameters.size(); i++)
		{
			if (i > 0)
				sb.append(","); //$NON-NLS-1$
			sb.append("{" + parameters.get(i).getClass().getName()); //$NON-NLS-1$
			if (parameters.get(i) instanceof ValuedParameter)
				sb.append(":" + ((ValuedParameter) parameters.get(i)).getValue()); //$NON-NLS-1$
			sb.append("}"); //$NON-NLS-1$
		}
		sb.append("}"); //$NON-NLS-1$
		return sb.toString();
	}
}
