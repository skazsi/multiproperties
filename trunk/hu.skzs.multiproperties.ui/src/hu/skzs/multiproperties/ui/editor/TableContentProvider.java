package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The <code>TableContentProvider</code> is the content provider for the table page.
 * @author skzs
 */
public class TableContentProvider implements IStructuredContentProvider
{

	/**
	 * The <code>table</code> member hold a reference to the {@link Table} instance.
	 */
	private Table table;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(final Viewer v, final Object oldInput, final Object newInput)
	{
		table = (Table) newInput;
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
		return table.toArray();
	}
}
