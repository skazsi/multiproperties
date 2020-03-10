package hu.skzs.multiproperties.ui.editor;

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

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.preferences.PreferenceConstants;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

/**
 * The <code>OutlinePage</code> visualizes the currently selected
 * {@link PropertyRecord} if there is exactly one this typed selection.
 *
 * @author skzs
 */
public class OutlinePage extends Page implements IContentOutlinePage, ISelectionListener {

	private Composite control;
	private Label keylabel;
	private TableViewer tableViewer;

	private final Table table;

	public OutlinePage(final Table table) {
		this.table = table;
	}

	@Override
	public void init(final IPageSite pageSite) {
		super.init(pageSite);

		// Registering the selection listener
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().addSelectionListener(this);
	}

	@Override
	public void dispose() {
		// Unregistering the selection listener
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().removeSelectionListener(this);

		super.dispose();
	}

	public void setTable(final Table table) {
		tableViewer.setContentProvider(new OutlineContentProvider(table));
	}

	@Override
	public void createControl(final Composite parent) {
		// Creating the control
		control = new Composite(parent, SWT.NONE);
		control.setLayout(LayoutFactory.createGridLayout(1, 0, 0));

		// Key label
		keylabel = new Label(control, SWT.NONE);
		keylabel.setText("");
		keylabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		// Table viewer
		tableViewer = new TableViewer(control, SWT.FULL_SELECTION);
		tableViewer.getTable()
				.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.setContentProvider(new OutlineContentProvider(table));

		// Columns
		createTableViewerColumn(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				final OutlineInputElement outlineInputElement = (OutlineInputElement) element;
				return outlineInputElement.getColumn().getName();
			}
		}, Messages.getString("outline.column"), PreferenceConstants.OUTLINE_COLUMN_WIDTH);
		createTableViewerColumn(new OutlineValueColumnLabelProvider(), Messages.getString("outline.value"),
				PreferenceConstants.OUTLINE_VALUE_WIDTH);
	}

	private void createTableViewerColumn(final CellLabelProvider labelProvider, final String columnText,
			final String columnWidthConst) {
		final IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		final TableViewerColumn valueTableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		valueTableViewerColumn.setLabelProvider(labelProvider);
		valueTableViewerColumn.getColumn().setText(columnText);
		valueTableViewerColumn.getColumn().setWidth(preferenceStore.getInt(columnWidthConst));
		valueTableViewerColumn.getColumn().addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				final TableColumn tablecolumn = (TableColumn) e.getSource();
				preferenceStore.setValue(columnWidthConst, tablecolumn.getWidth());
			}
		});
	}

	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		String key = "";
		PropertyRecord propertyRecord = null;
		try {
			if (!(part instanceof Editor) || !(selection instanceof IStructuredSelection) || selection.isEmpty())
				return;

			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() > 1)
				return;

			if (!(structuredSelection.getFirstElement() instanceof PropertyRecord))
				return;

			propertyRecord = (PropertyRecord) structuredSelection.getFirstElement();
			key = propertyRecord.getValue();
		} finally {
			keylabel.setText(key);
			tableViewer.setInput(propertyRecord);
			tableViewer.refresh();
		}
	}

	@Override
	public Control getControl() {
		return control;
	}

	@Override
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		// No selection can be made in outline page
	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		// No selection can be made in outline page
	}

	public void setSelection(final ISelection selection) {
		// No selection can be made in outline page
	}

	public ISelection getSelection() {
		// No selection can be made in outline page
		return null;
	}

	@Override
	public void setActionBars(final IActionBars actionBars) {
	}
}
