package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.ui.Activator;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

class ColumnsPageLabelProvider extends LabelProvider implements ITableLabelProvider
{

	public String getColumnText(final Object object, final int index)
	{
		final Column column = (Column) object;
		return column.getName();
	}

	public Image getColumnImage(final Object object, final int index)
	{
		return Activator.getDefault().getImageRegistry().get("column"); //$NON-NLS-1$
	}
}
