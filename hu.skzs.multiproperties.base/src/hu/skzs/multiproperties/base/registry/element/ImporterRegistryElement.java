package hu.skzs.multiproperties.base.registry.element;

import hu.skzs.multiproperties.base.api.IImporter;
import hu.skzs.multiproperties.base.api.AbstractImporterWizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * The {@link ImporterRegistryElement} is an implementation of {@link AbstractRegistryElement} and
 * represent an importer extension element.
 * @author skzs
 *
 */
public class ImporterRegistryElement extends AbstractRegistryElement
{

	private ImageDescriptor imageDescriptor;

	/**
	 * Returns a newly created {@link IImporter} instance 
	 * @return a newly created {@link IImporter} instance
	 * @throws CoreException
	 */
	public IImporter getImporter() throws CoreException
	{
		final IImporter importer = (IImporter) configurationElement.createExecutableExtension(IImporter.CLASS);
		return importer;
	}

	/**
	 * Returns a newly created {@link AbstractImporterWizard} instance 
	 * @return a newly created {@link AbstractImporterWizard} instance
	 * @throws CoreException
	 */
	public AbstractImporterWizard getImporterWizard() throws CoreException
	{
		final AbstractImporterWizard importerWizard = (AbstractImporterWizard) configurationElement
				.createExecutableExtension(IImporter.WIZARD_CLASS);
		return importerWizard;
	}

	/**
	 * Sets the image descriptor
	 * @param imageDescriptor the given image descriptor
	 */
	public void setImageDescriptor(final ImageDescriptor imageDescriptor)
	{
		this.imageDescriptor = imageDescriptor;
	}

	/**
	 * Returns the image descriptor
	 * @return the image descriptor
	 */
	public ImageDescriptor getImageDescriptor()
	{
		return imageDescriptor;
	}
}
