package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.preferences.PreferenceConstants;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * The <code>OutlinePage</code> vizualizes the currently selected {@link PropertyRecord} if there is
 * exactly one this typed selection.
 * @author sallai
 */
public class OutlinePage implements IContentOutlinePage, ISelectionListener
{

	private Composite composite;
	private Label keylabel;
	private Table table;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(final Composite parent)
	{
		composite = new Composite(parent, SWT.NONE);
		final GridLayout gridlayout = new GridLayout(1, false);
		gridlayout.marginHeight = 0;
		gridlayout.marginWidth = 0;
		composite.setLayout(gridlayout);
		keylabel = new Label(composite, SWT.NONE);
		keylabel.setText(""); //$NON-NLS-1$
		GridData griddata = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		keylabel.setLayoutData(griddata);
		table = new Table(composite, SWT.FULL_SELECTION);
		griddata = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		table.setLayoutData(griddata);
		TableColumn tc = new TableColumn(table, SWT.NONE);
		tc.setText(Messages.getString("outline.column")); //$NON-NLS-1$
		tc.setWidth(Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.OUTLINE_COLUMN_WIDTH));
		tc.addControlListener(new ControlListener() {

			public void controlMoved(final ControlEvent e)
			{
			}

			public void controlResized(final ControlEvent e)
			{
				final TableColumn tablecolumn = (TableColumn) e.getSource();
				Activator.getDefault().getPreferenceStore().setValue(PreferenceConstants.OUTLINE_COLUMN_WIDTH, tablecolumn.getWidth());
			}
		});
		tc = new TableColumn(table, SWT.NONE);
		tc.setText(Messages.getString("outline.value")); //$NON-NLS-1$
		tc.setWidth(Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.OUTLINE_VALUE_WIDTH));
		tc.addControlListener(new ControlListener() {

			public void controlMoved(final ControlEvent e)
			{
			}

			public void controlResized(final ControlEvent e)
			{
				final TableColumn tablecolumn = (TableColumn) e.getSource();
				Activator.getDefault().getPreferenceStore().setValue(PreferenceConstants.OUTLINE_VALUE_WIDTH, tablecolumn.getWidth());
			}
		});
		table.setHeaderVisible(true);

		// Registering the selection listener
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().addSelectionListener(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#dispose()
	 */
	public void dispose()
	{
		// Unregistering the selection listener
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().removeSelectionListener(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#getControl()
	 */
	public Control getControl()
	{
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#setFocus()
	 */
	public void setFocus()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(final ISelectionChangedListener listener)
	{
		// No selection can be made in outline page
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(final ISelectionChangedListener listener)
	{
		// No selection can be made in outline page
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	public void setSelection(final ISelection selection)
	{
		// No selection can be made in outline page
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	public ISelection getSelection()
	{
		// No selection can be made in outline page
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#setActionBars(org.eclipse.ui.IActionBars)
	 */
	public void setActionBars(final IActionBars actionBars)
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection)
	{
		keylabel.setText(""); //$NON-NLS-1$
		table.removeAll();

		if (!(part instanceof Editor) || !(selection instanceof IStructuredSelection) || selection.isEmpty())
			return;

		Editor editor = (Editor) part;
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		if (structuredSelection.size() > 1)
			return;

		if (!(structuredSelection.getFirstElement() instanceof PropertyRecord))
			return;

		final PropertyRecord propertyRecord = (PropertyRecord) structuredSelection.getFirstElement();
		keylabel.setText(propertyRecord.getValue());
		for (int i = 0; i < editor.getTable().getColumns().size(); i++)
		{
			if (propertyRecord.getColumnValue(editor.getTable().getColumns().get(i)) != null)
			{
				final TableItem tableitem = new TableItem(table, SWT.NONE);
				tableitem.setText(0, editor.getTable().getColumns().get(i).getName());
				tableitem.setText(1, propertyRecord.getColumnValue(editor.getTable().getColumns().get(i)));
			}
		}
	}
}
