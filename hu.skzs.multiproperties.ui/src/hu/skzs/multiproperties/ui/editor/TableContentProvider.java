package hu.skzs.multiproperties.ui.editor;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import hu.skzs.multiproperties.base.model.Table;

/**
 * The <code>TableContentProvider</code> is the content provider for the table
 * page.
 * 
 * @author skzs
 */
public class TableContentProvider implements IStructuredContentProvider {

	/**
	 * The <code>table</code> member hold a reference to the {@link Table} instance.
	 */
	private Table table;

	public void inputChanged(final Viewer v, final Object oldInput, final Object newInput) {
		table = (Table) newInput;
	}

	public void dispose() {
	}

	public Object[] getElements(final Object parent) {
		return table.toArray();
	}
}
