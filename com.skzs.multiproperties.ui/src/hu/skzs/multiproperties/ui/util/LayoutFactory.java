package hu.skzs.multiproperties.ui.util;

import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class LayoutFactory
{

	/**
	 * Returns a newly created TableWrapLayout object which has <code>1</code> column only. While the margin on left and right
	 * edges will be <code>5</code>, until the on top and bottom edges it will be <code>10</code>.
	 * @return a newly created TableWrapLayout object
	 */
	public static TableWrapLayout createTableWrapLayout()
	{
		return createTableWrapLayout(1, 5, 10);
	}

	/**
	 * Returns a newly created TableWrapLayout object which has <code>numColumns</code> columns. While the margin on left and right
	 * edges will be <code>5</code>, until the on top and bottom edges it will be <code>10</code>.
	 * @param numColumns number of columns which should be used
	 * @return a newly created TableWrapLayout object
	 */
	public static TableWrapLayout createTableWrapLayout(final int numColumns)
	{
		return createTableWrapLayout(numColumns, 5, 10);
	}

	/**
	 * Returns a newly created TableWrapLayout object which has <code>numColumns</code> columns, <code>marginWidth</code> margin on left and right
	 * edges, and <code>marginHeight</code> margin on top and bottom edges.
	 * @param numColumns number of columns which should be used
	 * @param marginWidth margin on left and right edges 
	 * @param marginHeight margin on top and bottom edges
	 * @return a newly created TableWrapLayout object
	 */
	public static TableWrapLayout createTableWrapLayout(final int numColumns, final int marginWidth, final int marginHeight)
	{

		final TableWrapLayout tableWrapLayout = new TableWrapLayout();
		tableWrapLayout.numColumns = numColumns;
		tableWrapLayout.topMargin = marginHeight;
		tableWrapLayout.bottomMargin = marginHeight;
		tableWrapLayout.leftMargin = marginWidth;
		tableWrapLayout.rightMargin = marginWidth;
		return tableWrapLayout;
	}
}
