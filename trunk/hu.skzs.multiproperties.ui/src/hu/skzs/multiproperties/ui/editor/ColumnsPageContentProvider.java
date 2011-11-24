package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Columns;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

class ColumnsPageContentProvider implements IStructuredContentProvider
{
	private Columns columns;

	public Object[] getElements(final Object inputElement)
	{
		return columns.toArray();
	}

	public void dispose()
	{
	}

	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput)
	{
		columns = (Columns) newInput;
	}
}