package hu.skzs.multiproperties.ui.wizard.importer;

import hu.skzs.multiproperties.base.registry.element.ImporterRegistryElement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The {@link ImporterSelectionLabelProvider} is the label provider for importer selection.
 * @author skzs
 */
public class ImporterSelectionLabelProvider extends ColumnLabelProvider
{

	private final Map<ImporterRegistryElement, Image> images = new HashMap<ImporterRegistryElement, Image>();

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object object)
	{
		final ImporterRegistryElement element = (ImporterRegistryElement) object;
		return element.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(final Object object)
	{
		final ImporterRegistryElement element = (ImporterRegistryElement) object;

		// Checking whether is already loaded
		if (images.containsKey(element))
			return images.get(element);

		// Creating the image
		if (element.getImageDescriptor() != null)
		{
			final Image image = element.getImageDescriptor().createImage();
			images.put(element, image);
			return image;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
	 */
	@Override
	public void dispose()
	{
		final Iterator<ImporterRegistryElement> iterator = images.keySet().iterator();
		while (iterator.hasNext())
		{
			final ImporterRegistryElement element = iterator.next();
			Image image = images.get(element);
			image.dispose();
			image = null;
		}
	}
}
