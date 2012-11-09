package hu.skzs.multiproperties.base.registry;

import hu.skzs.multiproperties.base.api.IImporter;
import hu.skzs.multiproperties.base.registry.element.ImporterRegistryElement;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The {@link ImporterRegistry} loads and stores all of the {@link IImporter} implementations currently
 * available for the plugin.
 * @author skzs
 *
 */
public class ImporterRegistry extends AbstractRegistry<ImporterRegistryElement>
{

	private static ImporterRegistry instance;

	/**
	 * Private constructor to avoid instantiation.
	 */
	private ImporterRegistry()
	{
		initialize();
	}

	/**
	 * Returns the singleton
	 * @return the singleton
	 */
	public static synchronized ImporterRegistry getInstance()
	{
		if (instance == null)
			instance = new ImporterRegistry();
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.registry.AbstractRegistry#getExtensionPointId()
	 */
	@Override
	public String getExtensionPointId()
	{
		return IImporter.IMPORTER_EXTENSION_ID;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.registry.AbstractRegistry#getRegistryElement(org.eclipse.core.runtime.IConfigurationElement)
	 */
	@Override
	public ImporterRegistryElement getRegistryElement(final IConfigurationElement configurationElement)
			throws CoreException
	{
		final ImporterRegistryElement element = new ImporterRegistryElement();

		// Element
		element.setConfigurationElement(configurationElement);
		// Name
		element.setName(configurationElement.getAttribute(IImporter.NAME));
		// Image descriptor
		final String extendingPluginId = configurationElement.getContributor().getName();
		final String iconPath = configurationElement.getAttribute(IImporter.ICON);
		final ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(extendingPluginId, iconPath);
		element.setImageDescriptor(imageDescriptor);
		return element;
	}
}
