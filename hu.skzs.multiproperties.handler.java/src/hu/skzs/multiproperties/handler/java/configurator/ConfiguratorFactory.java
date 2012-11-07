package hu.skzs.multiproperties.handler.java.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;

/**
 * Factory implementation for providing {@link AbstractConfigurator} implementation.
 * @author sallai
 */
public class ConfiguratorFactory
{
	private static Boolean presenseOfEclipse;

	/**
	 * Returns a {@link AbstractConfigurator} implementation.
	 * <p>In case of Eclipse plug-in it returns a {@link WorkspaceConfigurator} implementation, otherwise it returns a
	 * {@link FileSystemConfigurator} implementation.</p>
	 * <p>Because the {@link AbstractConfigurator} instantiation also includes the configuration parsing, thus the this
	 * {@link #getConfigurator(String)} method can throw {@link HandlerException} when the given
	 * <code>configuration</code> cannot be parsed. See {@link AbstractConfigurator} constructor.</p>
	 * @param configuration the given configuration
	 * @return a {@link AbstractConfigurator} implementation
	 * @throws HandlerException when the configuration format is invalid
	 */
	public static AbstractConfigurator getConfigurator(final String configuration) throws HandlerException
	{
		if (presenceOfEclipse())
			return new WorkspaceConfigurator(configuration);
		else
			return new FileSystemConfigurator(configuration);
	}

	/**
	 * Returns a boolean value about the presence of Eclipse.
	 * <p>The check is based on the presence of AbstractUIPlugin class.</p>
	 * @return a boolean value about the presence of Eclipse
	 */
	protected static boolean presenceOfEclipse()
	{
		if (presenseOfEclipse == null)
		{
			synchronized (ConfiguratorFactory.class)
			{
				if (presenseOfEclipse == null)
				{
					try
					{
						Class.forName("org.eclipse.ui.plugin.AbstractUIPlugin"); //$NON-NLS-1$
						presenseOfEclipse = Boolean.TRUE;
						return true;
					}
					catch (final ClassNotFoundException e)
					{
						presenseOfEclipse = Boolean.FALSE;
						return false;
					}
				}
			}
		}
		return presenseOfEclipse.booleanValue();
	}
}
