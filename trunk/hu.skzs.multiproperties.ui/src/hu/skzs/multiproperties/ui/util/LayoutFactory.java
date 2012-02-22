package hu.skzs.multiproperties.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * The <code>LayoutFactory</code> is a helper class for constructing different layout and layout data instances. 
 * @author sallai
 */
public class LayoutFactory
{

	/**
	 * Returns a newly created <code>TableWrapLayout</code> instance which has <code>1</code> column only.
	 * While the margin on left and right sides will be <code>5</code>, until on the top and bottom sides it will be <code>10</code>.
	 * @return a newly created TableWrapLayout object
	 */
	public static synchronized TableWrapLayout createTableWrapLayout()
	{
		return createTableWrapLayout(1, 5, 10);
	}

	/**
	 * Returns a newly created <code>TableWrapLayout</code> instance which has the given <code>numColumns</code> columns.
	 * While the margin on left and right sides will be <code>5</code>, until on the top and bottom sides it will be <code>10</code>.
	 * @param numColumns number of columns which should be used
	 * @return a newly created TableWrapLayout object
	 */
	public static synchronized TableWrapLayout createTableWrapLayout(final int numColumns)
	{
		return createTableWrapLayout(numColumns, 5, 10);
	}

	/**
	 * Returns a newly created <code>TableWrapLayout</code> instance which has <code>numColumns</code> columns,
	 * <code>marginWidth</code> margin on left and right sides, and <code>marginHeight</code> margin on top and bottom sides.
	 * @param numColumns number of columns which should be used
	 * @param marginWidth margin on left and right sides 
	 * @param marginHeight margin on top and bottom sides
	 * @return a newly created TableWrapLayout object
	 */
	public static synchronized TableWrapLayout createTableWrapLayout(final int numColumns, final int marginWidth,
			final int marginHeight)
	{
		final TableWrapLayout tableWrapLayout = new TableWrapLayout();
		tableWrapLayout.numColumns = numColumns;
		tableWrapLayout.topMargin = marginHeight;
		tableWrapLayout.bottomMargin = marginHeight;
		tableWrapLayout.leftMargin = marginWidth;
		tableWrapLayout.rightMargin = marginWidth;
		return tableWrapLayout;
	}

	/**
	 * Returns a newly created <code>GridLayout</code> instance which has <code>1</code> column only.
	 * While the margin on left and right sides will be <code>5</code>, until on the top and bottom sides it will be <code>10</code>.
	 * @return a newly created GridLayout object
	 */
	public static synchronized GridLayout createGridLayout()
	{
		return createGridLayout(1, 5, 10);
	}

	/**
	 * Returns a newly created <code>GridLayout</code> instance which has the given <code>numColumns</code> columns.
	 * While the margin on left and right sides will be <code>5</code>, until on the top and bottom sides it will be <code>10</code>.
	 * @param numColumns number of columns which should be used
	 * @return a newly created GridLayout object
	 */
	public static synchronized GridLayout createGridLayout(final int numColumns)
	{
		return createGridLayout(numColumns, 5, 10);
	}

	/**
	 * Returns a newly created <code>GridLayout</code> instance which has <code>numColumns</code> columns,
	 * <code>marginWidth</code> margin on left and right sides, and <code>marginHeight</code> margin on top and bottom sides.
	 * @param numColumns number of columns which should be used
	 * @param marginWidth margin on left and right sides 
	 * @param marginHeight margin on top and bottom sides
	 * @return a newly created GridLayout object
	 */
	public static synchronized GridLayout createGridLayout(final int numColumns, final int marginWidth,
			final int marginHeight)
	{
		final GridLayout gridLayout = new GridLayout(numColumns, false);
		gridLayout.marginHeight = marginHeight;
		gridLayout.marginWidth = marginWidth;
		return gridLayout;
	}

	/**
	 * Returns a newly created <code>Label</code> instance which represents a horizontal separator line.
	 * <p>It can be used only in {@link GridLayout}.</p>
	 * @param parent the parent composite
	 * @param horizontalSpan specifies the number of column cells that the control will take up
	 * @return a newly created <code>Label</code> instance which represents a horizontal separator line
	 */
	public static synchronized Label createSeparatorInGrid(final Composite parent, final int horizontalSpan)
	{
		final Label label = new Label(parent, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		gridData.horizontalSpan = 3;
		gridData.heightHint = 20;
		gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
		label.setLayoutData(gridData);
		return label;
	}
}
