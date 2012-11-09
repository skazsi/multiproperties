package hu.skzs.multiproperties.handler.java.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.support.handler.configurator.AbstractConfiguratorFactory;
import hu.skzs.multiproperties.support.handler.configurator.IConfigurator;

/**
 * Factory implementation for providing {@link IConfigurator} implementation.
 * @author skzs
 */
public class JavaConfiguratorFactory extends AbstractConfiguratorFactory
{

	private static JavaConfiguratorFactory instance;

	/**
	 * Private constructor
	 */
	private JavaConfiguratorFactory()
	{
	}

	/**
	 * Returns the singleton instance of this factory.
	 * @return the singleton instance of this factory
	 */
	public static JavaConfiguratorFactory getInstance()
	{
		if (instance != null)
			return instance;
		synchronized (JavaConfiguratorFactory.class)
		{
			if (instance == null)
				instance = new JavaConfiguratorFactory();
			return instance;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JavaHandlerConfigurator getConfigurator(final String configuration) throws HandlerException
	{
		JavaHandlerConfigurator configurator = null;
		if (presenceOfEclipse())
			configurator = new WorkspaceConfigurator();
		else
			configurator = new FileSystemConfigurator();
		configurator.setConfiguration(configuration);
		return configurator;
	}
}
