package hu.skzs.multiproperties.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * The <code>LayoutFactory</code> is a helper class for constructing different layout and layout data instances. 
 * @author skzs
 */
public class LayoutFactory
{

	/**
	 * Returns a newly created <code>TableWrapLayout</code> instance which has <code>1</code> column only.
	 * While the margin on left and right sides will be <code>5</code>, until on the top and bottom sides it will be <code>10</code>.
	 * @return a newly created TableWrapLayout object
	 */
	public static TableWrapLayout createTableWrapLayout()
	{
		return createTableWrapLayout(1, 5, 10);
	}

	/**
	 * Returns a newly created <code>TableWrapLayout</code> instance which has the given <code>numColumns</code> columns.
	 * While the margin on left and right sides will be <code>5</code>, until on the top and bottom sides it will be <code>10</code>.
	 * @param numColumns number of columns which should be used
	 * @return a newly created TableWrapLayout object
	 */
	public static TableWrapLayout createTableWrapLayout(final int numColumns)
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
	public static TableWrapLayout createTableWrapLayout(final int numColumns, final int marginWidth,
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
	public static GridLayout createGridLayout()
	{
		return createGridLayout(1, 5, 10);
	}

	/**
	 * Returns a newly created <code>GridLayout</code> instance which has the given <code>numColumns</code> columns.
	 * While the margin on left and right sides will be <code>5</code>, until on the top and bottom sides it will be <code>10</code>.
	 * @param numColumns number of columns which should be used
	 * @return a newly created GridLayout object
	 */
	public static GridLayout createGridLayout(final int numColumns)
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
	public static GridLayout createGridLayout(final int numColumns, final int marginWidth, final int marginHeight)
	{
		final GridLayout gridLayout = new GridLayout(numColumns, false);
		gridLayout.marginHeight = marginHeight;
		gridLayout.marginWidth = marginWidth;
		return gridLayout;
	}

	/**
	 * Returns a newly created <code>GridData</code> instance which fills and grabs spaces according to the
	 * <code>horizontalGrab</code> and <code>verticalGrab</code> parameters. It also sets the 
	 * horizontal span property according to the relevant parameter.
	 * @param horizontalGrab grab horizontal flag
	 * @param verticalGrab grab vertical flag 
	 * @param horizontalSpan the number of grid cell to span
	 * @return a newly created GridData object
	 */
	public static GridData createGridData(final boolean horizontalGrab, final boolean verticalGrab,
			final int horizontalSpan)
	{
		int style = 0;
		if (horizontalGrab)
			style = style | GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL;
		if (verticalGrab)
			style = style | GridData.FILL_VERTICAL | GridData.GRAB_VERTICAL;
		final GridData gridData = new GridData(style);
		gridData.horizontalSpan = horizontalSpan;
		return gridData;
	}

	/**
	 * Returns a newly created <code>Label</code> instance which represents a horizontal separator line.
	 * <p>It can be used only in {@link GridLayout}.</p>
	 * @param parent the parent composite
	 * @param horizontalSpan specifies the number of column cells that the control will take up
	 * @return a newly created <code>Label</code> instance which represents a horizontal separator line
	 */
	public static Label createSeparatorInGrid(final Composite parent, final int horizontalSpan)
	{
		return createLabelInGrid(parent, horizontalSpan, 20, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
	}

	/**
	 * Returns a newly created <code>Label</code> instance which represents a horizontal separator line.
	 * <p>It can be used only in {@link GridLayout}.</p>
	 * @param parent the parent composite
	 * @param horizontalSpan specifies the number of column cells that the control will take up
	 * @param height the height to be used
	 * @return a newly created <code>Label</code> instance which represents a horizontal separator line
	 */
	public static Label createSeparatorInGrid(final Composite parent, final int height, final int horizontalSpan)
	{
		return createLabelInGrid(parent, horizontalSpan, height, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
	}

	/**
	 * Returns a newly created <code>Label</code> instance which represents a horizontal space line.
	 * <p>It can be used only in {@link GridLayout}.</p>
	 * @param parent the parent composite
	 * @param horizontalSpan specifies the number of column cells that the control will take up
	 * @return a newly created <code>Label</code> instance which represents a horizontal separator line
	 */
	public static Label createSpaceInGrid(final Composite parent, final int horizontalSpan)
	{
		return createLabelInGrid(parent, horizontalSpan, 20, SWT.NONE);
	}

	/**
	 * Returns a newly created <code>Label</code> instance which represents a horizontal space line.
	 * <p>It can be used only in {@link GridLayout}.</p>
	 * @param parent the parent composite
	 * @param horizontalSpan specifies the number of column cells that the control will take up
	 * @param height the height to be used
	 * @return a newly created <code>Label</code> instance which represents a horizontal separator line
	 */
	public static Label createSpaceInGrid(final Composite parent, final int height, final int horizontalSpan)
	{
		return createLabelInGrid(parent, horizontalSpan, height, SWT.NONE);
	}

	private static Label createLabelInGrid(final Composite parent, final int horizontalSpan, final int height,
			final int style)
	{
		final Label label = new Label(parent, style);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		gridData.horizontalSpan = horizontalSpan;
		gridData.heightHint = height;
		gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
		label.setLayoutData(gridData);
		return label;
	}
}
