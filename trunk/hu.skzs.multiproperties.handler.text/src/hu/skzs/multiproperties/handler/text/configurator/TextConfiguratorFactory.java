package hu.skzs.multiproperties.handler.text.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.support.handler.configurator.AbstractConfiguratorFactory;
import hu.skzs.multiproperties.support.handler.configurator.IConfigurator;

/**
 * Factory implementation for providing {@link IConfigurator} implementation.
 * @author skzs
 */
public class TextConfiguratorFactory extends AbstractConfiguratorFactory
{
	private static TextConfiguratorFactory instance;

	/**
	 * Private constructor
	 */
	private TextConfiguratorFactory()
	{
	}

	/**
	 * Returns the singleton instance of this factory.
	 * @return the singleton instance of this factory
	 */
	public static TextConfiguratorFactory getInstance()
	{
		if (instance != null)
			return instance;
		synchronized (TextConfiguratorFactory.class)
		{
			if (instance == null)
				instance = new TextConfiguratorFactory();
			return instance;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TextHandlerConfigurator getConfigurator(final String configuration) throws HandlerException
	{
		TextHandlerConfigurator configurator = null;
		if (presenceOfEclipse())
			configurator = new WorkspaceConfigurator();
		else
			configurator = new FileSystemConfigurator();
		configurator.setConfiguration(configuration);
		return configurator;
	}
}
