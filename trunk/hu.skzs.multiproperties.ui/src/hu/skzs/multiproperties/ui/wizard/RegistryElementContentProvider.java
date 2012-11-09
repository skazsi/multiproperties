package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.registry.element.AbstractRegistryElement;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The {@link RegistryElementContentProvider} is the content provider for registry element selection.
 * @author skzs
 */
public class RegistryElementContentProvider<T extends AbstractRegistryElement> implements IStructuredContentProvider
{
	private List<T> elements;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void inputChanged(final Viewer v, final Object oldInput, final Object newInput)
	{
		elements = (List<T>) newInput;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(final Object parent)
	{
		return elements.toArray();
	}
}
