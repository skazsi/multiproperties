package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The <code>EditorContentProvider</code> is a standard content provider for the table page.
 * @author sallai
 */
public class EditorContentProvider implements IStructuredContentProvider
{

	/**
	 * The <code>table</code> member hold a reference to the {@link Table} instance.
	 */
	private Table table;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer v, Object oldInput, Object newInput)
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
	public Object[] getElements(Object parent)
	{
		return table.toArray();
	}
}
