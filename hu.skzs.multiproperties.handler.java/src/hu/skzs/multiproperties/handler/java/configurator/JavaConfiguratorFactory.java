package hu.skzs.multiproperties.handler.java.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.support.handler.configurator.AbstractConfiguratorFactory;

public class JavaConfiguratorFactory extends AbstractConfiguratorFactory
{

	private static JavaConfiguratorFactory instance = new JavaConfiguratorFactory();

	private JavaConfiguratorFactory()
	{
		// Intentionally empty
	}

	public static JavaConfiguratorFactory getInstance()
	{
		return instance;
	}

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
