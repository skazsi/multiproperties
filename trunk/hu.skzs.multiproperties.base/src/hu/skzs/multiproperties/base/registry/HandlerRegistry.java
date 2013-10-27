package hu.skzs.multiproperties.base.registry;

import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.api.IImporter;
import hu.skzs.multiproperties.base.registry.element.HandlerRegistryElement;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * The {@link HandlerRegistry} loads and stores all of the {@link IHandler} implementations currently
 * available for the plugin.
 * @author skzs
 *
 */
public class HandlerRegistry extends AbstractRegistry<HandlerRegistryElement>
{

	private static HandlerRegistry instance;

	/**
	 * Private constructor to avoid instantiation.
	 */
	private HandlerRegistry()
	{
		initialize();
	}

	/**
	 * Returns the singleton
	 * @return the singleton
	 */
	public static synchronized HandlerRegistry getInstance()
	{
		if (instance == null)
			instance = new HandlerRegistry();
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.registry.AbstractRegistry#getExtensionPointId()
	 */
	@Override
	public String getExtensionPointId()
	{
		return IHandler.HANDLER_EXTENSION_ID;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.registry.AbstractRegistry#getRegistryElement(org.eclipse.core.runtime.IConfigurationElement)
	 */
	@Override
	public HandlerRegistryElement getRegistryElement(final IConfigurationElement configurationElement)
			throws CoreException
	{
		final HandlerRegistryElement element = new HandlerRegistryElement();

		// Element
		element.setConfigurationElement(configurationElement);
		// Name
		element.setName(configurationElement.getAttribute(IImporter.NAME));
		return element;
	}
}
