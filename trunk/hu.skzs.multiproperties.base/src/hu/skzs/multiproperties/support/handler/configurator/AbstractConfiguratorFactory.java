package hu.skzs.multiproperties.support.handler.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;

/**
 * Abstract factory implementation for providing {@link IConfigurator} implementation.
 * 
 * <p>The usage of the {@link IConfigurator} interface and consequently this factory are <strong>not</strong>
 * mandatory for the handlers. It is just a simplification for those handlers which produce a kind of file output,
 * such as the <strong>Java Properties Handler</strong> or the <strong>Test File Handler</strong>.</p>
 * 
 * <p>The logic behind this factory is the fact, that the handlers can be also used from command line.
 * In that case file system paths should be used for the output files instead of Eclipse workspace paths.
 * This factory depending on the presence of Eclipse can instantiate {@link IFileSystemConfigurator} or
 * {@link IWorkspaceConfigurator} instances.</p>
 * @author skzs
 */
public abstract class AbstractConfiguratorFactory
{
	protected static Boolean presenseOfEclipse;

	/**
	 * Returns a {@link IConfigurator} implementation.
	 * 
	 * <p>The method should return an {@link IWorkspaceConfigurator} or {@link IFileSystemConfigurator}
	 * instance depending on the presence of Eclipse. See the {@link #presenceOfEclipse()}.</p>
	 * 
	 * <p>Implementation of this abstract method must guarantee that the
	 * {@link IConfigurator#setConfiguration(String)} is also called on the newly created 
	 * {@link IConfigurator} instance before returning it back to the caller.</p>
	 * 
	 * @param configuration the given configuration
	 * @return a {@link IConfigurator} implementation
	 * @throws HandlerException in case of any problem
	 * @see #presenceOfEclipse()
	 */
	public abstract IConfigurator getConfigurator(final String configuration) throws HandlerException;

	/**
	 * Returns a boolean value about the presence of Eclipse.
	 * <p>The check is based on the presence of <code>org.eclipse.ui.plugin.AbstractUIPlugin</code>
	 * class.</p>
	 * @return a boolean value about the presence of Eclipse
	 */
	public static boolean presenceOfEclipse()
	{
		if (presenseOfEclipse == null)
		{
			synchronized (AbstractConfiguratorFactory.class)
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
