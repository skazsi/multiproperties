package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.preferences.PreferenceConstants;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * The <code>OutlinePage</code> visualizes the currently selected {@link PropertyRecord} if there is
 * exactly one this typed selection.
 * @author sallai
 */
public class OutlinePage extends Page implements IContentOutlinePage, ISelectionListener
{

	private Composite control;
	private Label keylabel;
	private TableViewer tableViewer;

	/**
	 * The <code>table</code> member hold a reference to the {@link Table} instance.
	 */
	private final Table table;

	/**
	 * Default constructor
	 * @param table the given {@link Table} instance
	 */
	public OutlinePage(final Table table)
	{
		this.table = table;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.Page#init(org.eclipse.ui.part.IPageSite)
	 */
	@Override
	public void init(final IPageSite pageSite)
	{
		super.init(pageSite);

		// Registering the selection listener
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().addSelectionListener(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.Page#dispose()
	 */
	@Override
	public void dispose()
	{
		// Unregistering the selection listener
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().removeSelectionListener(this);

		super.dispose();
	}

	/**
	 * Sets the Table
	 * @param table
	 */
	public void setTable(final Table table)
	{

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.Page#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent)
	{
		// Creating the control
		control = new Composite(parent, SWT.NONE);
		control.setLayout(LayoutFactory.createGridLayout(1, 0, 0));

		// Key label
		keylabel = new Label(control, SWT.NONE);
		keylabel.setText(""); //$NON-NLS-1$
		keylabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		// Table viewer
		tableViewer = new TableViewer(control, SWT.FULL_SELECTION);
		tableViewer.getTable().setLayoutData(
				new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.setContentProvider(new OutlineContentProvider(table));

		// Columns
		createTableViewerColumn(new ColumnLabelProvider()
		{
			@Override
			public String getText(final Object element)
			{
				final OutlineInputElement outlineInputElement = (OutlineInputElement) element;
				return outlineInputElement.getColumn().getName();
			}
		}, Messages.getString("outline.column"), PreferenceConstants.OUTLINE_COLUMN_WIDTH); //$NON-NLS-1$
		createTableViewerColumn(new OutlineValueColumnLabelProvider(),
				Messages.getString("outline.value"), PreferenceConstants.OUTLINE_VALUE_WIDTH); //$NON-NLS-1$
	}

	/**
	 * Creates a new {@link TableViewerColumn}
	 * @param labelProvider the desired column {@link CellLabelProvider}
	 * @param columnText the desired column text
	 * @param columnWidthConst the constant which can be used to store the width of the column in the preference store
	 */
	private void createTableViewerColumn(final CellLabelProvider labelProvider, final String columnText,
			final String columnWidthConst)
	{
		final IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		final TableViewerColumn valueTableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		valueTableViewerColumn.setLabelProvider(labelProvider);
		valueTableViewerColumn.getColumn().setText(columnText);
		valueTableViewerColumn.getColumn().setWidth(preferenceStore.getInt(columnWidthConst));
		valueTableViewerColumn.getColumn().addControlListener(new ControlAdapter()
		{
			@Override
			public void controlResized(final ControlEvent e)
			{
				final TableColumn tablecolumn = (TableColumn) e.getSource();
				preferenceStore.setValue(columnWidthConst, tablecolumn.getWidth());
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection)
	{
		String key = ""; //$NON-NLS-1$
		PropertyRecord propertyRecord = null;
		try
		{
			if (!(part instanceof Editor) || !(selection instanceof IStructuredSelection) || selection.isEmpty())
				return;

			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() > 1)
				return;

			if (!(structuredSelection.getFirstElement() instanceof PropertyRecord))
				return;

			propertyRecord = (PropertyRecord) structuredSelection.getFirstElement();
			key = propertyRecord.getValue();
		}
		finally
		{
			keylabel.setText(key);
			tableViewer.setInput(propertyRecord);
			tableViewer.refresh();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.Page#getControl()
	 */
	@Override
	public Control getControl()
	{
		return control;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.IPage#setFocus()
	 */
	@Override
	public void setFocus()
	{
		tableViewer.getTable().setFocus();
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
	 * @see org.eclipse.ui.part.Page#setActionBars(org.eclipse.ui.IActionBars)
	 */
	@Override
	public void setActionBars(final IActionBars actionBars)
	{
	}
}
