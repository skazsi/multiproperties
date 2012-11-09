package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The <code>OutlineContentProvider</code> is the content provider for the outline page.
 * @author skzs
 */
public class OutlineContentProvider implements IStructuredContentProvider
{

	/**
	 * The <code>table</code> member hold a reference to the {@link Table} instance.
	 */
	private final Table table;

	/**
	 * The <code>propertyRecord</code> member hold a reference to the {@link PropertyRecord} instance, which acts as the input.
	 */
	private PropertyRecord propertyRecord;

	/**
	 * Default constructor
	 * @param table the given {@link Table} instance to be used
	 */
	public OutlineContentProvider(final Table table)
	{
		this.table = table;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(final Viewer v, final Object oldInput, final Object newInput)
	{
		if (newInput == null || !(newInput instanceof PropertyRecord))
		{
			propertyRecord = null;
			return;
		}

		propertyRecord = (PropertyRecord) newInput;
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
		if (propertyRecord == null)
			return Collections.EMPTY_LIST.toArray();

		// Building the element array to be returned
		final List<OutlineInputElement> columns = new LinkedList<OutlineInputElement>();
		for (int i = 0; i < table.getColumns().size(); i++)
		{
			final Column column = table.getColumns().get(i);

			if (propertyRecord.getColumnValue(column) != null || propertyRecord.getDefaultColumnValue() != null)
				columns.add(new OutlineInputElement(column, propertyRecord));
		}
		return columns.toArray();
	}
}
