package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.command.EditHandler;
import hu.skzs.multiproperties.ui.preferences.PreferenceConstants;

import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.IHandlerService;

public class TablePage extends MPEditorPage
{

	/**
	 * The <code>PAGE_ID</code> represents the page identifier.
	 * It is used for changing the pages.
	 */
	public static final String PAGE_ID = "table"; //$NON-NLS-1$

	private TableViewer tableviewer;

	/**
	 * The <code>preferenceListener</code> listens for any color related preference changes. In case of change,
	 * it initiates a refresh on the table.
	 */
	IPropertyChangeListener preferenceListener = new IPropertyChangeListener()
	{

		public void propertyChange(final PropertyChangeEvent event)
		{
			if (event.getProperty().startsWith(PreferenceConstants.COLOR_PREFIX))
			{
				tableviewer.refresh();
			}
		}
	};

	/**
	 * Default constructor.
	 */
	public TablePage()
	{
		super();
		setId(PAGE_ID);
	}

	public TableViewer getTableViewer()
	{
		return tableviewer;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.ui.editors.MPEditorPage#getPageText()
	 */
	@Override
	public String getPageText()
	{
		return Messages.getString("general.table"); //$NON-NLS-1$
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException
	{
		super.init(site, input);
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(preferenceListener);
	}

	@Override
	public void dispose()
	{
		Activator.getDefault().getPreferenceStore().removePropertyChangeListener(preferenceListener);
		super.dispose();
	}

	@Override
	public void createPartControl(final Composite parent)
	{
		parent.setLayout(new FillLayout());

		// Table viewer
		tableviewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		tableviewer.getTable().setLinesVisible(true);
		tableviewer.getTable().setHeaderVisible(true);
		tableviewer.setContentProvider(new TableContentProvider());
		tableviewer.setInput(editor.getTable());

		createKeyColumn();
		updateValueColumns();

		// Enabling tooltip
		ColumnViewerToolTipSupport.enableFor(tableviewer);

		// Makes the selection available to the workbench
		getSite().setSelectionProvider(tableviewer);

		// Creating the context menu
		final MenuManager menuManager = new MenuManager();
		tableviewer.getTable().setMenu(menuManager.createContextMenu(tableviewer.getTable()));
		getSite().registerContextMenu(menuManager, tableviewer);

		// Double clicking
		tableviewer.addDoubleClickListener(new IDoubleClickListener()
		{

			public void doubleClick(final DoubleClickEvent event)
			{
				final IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);
				try
				{
					handlerService.executeCommand(EditHandler.COMMAND_ID, null);
				}
				catch (final NotEnabledException e)
				{
					// do nothing
					// TODO: it should be investigated how the enabled state could be checked
				}
				catch (final Exception e)
				{
					Activator.logError(e);
				}
			}
		});

		final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(tableviewer,
				new FocusCellOwnerDrawHighlighter(tableviewer));
		final ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(tableviewer)
		{
			@Override
			protected boolean isEditorActivationEvent(final ColumnViewerEditorActivationEvent event)
			{
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};
		TableViewerEditor.create(tableviewer, focusCellManager, actSupport, ColumnViewerEditor.TABBING_HORIZONTAL
				| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR | ColumnViewerEditor.TABBING_VERTICAL
				| ColumnViewerEditor.KEYBOARD_ACTIVATION);
	}

	/**
	 * Create the key column.
	 * @param tableViewer
	 */
	private void createKeyColumn()
	{
		final TableViewerColumn keyTableViewerColumn = new TableViewerColumn(tableviewer, SWT.NONE);
		keyTableViewerColumn.setLabelProvider(new TableColumnLabelProvider());
		keyTableViewerColumn.setEditingSupport(new TableEditingSupport(tableviewer));
		keyTableViewerColumn.getColumn().setText(Messages.getString("tables.key")); //$NON-NLS-1$
		keyTableViewerColumn.getColumn().setWidth(editor.getTable().getKeyColumnWidth());
		keyTableViewerColumn.getColumn().addControlListener(new ControlAdapter()
		{
			@Override
			public void controlResized(final ControlEvent e)
			{
				final TableColumn tablecolumn = (TableColumn) e.getSource();
				editor.getTable().setKeyColumnWidth(tablecolumn.getWidth());
			}
		});
	}

	@Override
	public void setActive()
	{
		if (editor.getTable().getColumns().isStale())
		{
			updateValueColumns();
			tableviewer.refresh();
			editor.getTable().getColumns().setStale(false);
		}
		// TODO: not active flag is specified
		//		if (!active && editor.getOutlinePage() != null)
		//			editor.getOutlinePage().update(null);
	}

	/**
	 * Updates the value columns
	 */
	private void updateValueColumns()
	{
		// Turning off the redraw flag on the table viewer
		tableviewer.getTable().setRedraw(false);

		// Removing the value columns
		int col = tableviewer.getTable().getColumnCount();
		while (col > 1)
		{
			final TableColumn tc = tableviewer.getTable().getColumn(--col);
			tc.dispose();
		}

		// Adding the new value columns
		for (int i = 0; i < editor.getTable().getColumns().size(); i++)
		{
			final Column column = editor.getTable().getColumns().get(i);

			final TableViewerColumn tableViewerColumn = new TableViewerColumn(tableviewer, SWT.NONE, i + 1);
			tableViewerColumn.setLabelProvider(new TableColumnLabelProvider(editor.getTable().getColumns().get(i)));
			tableViewerColumn.setEditingSupport(new TableEditingSupport(tableviewer, column));

			final TableColumn tableColumn = tableViewerColumn.getColumn();
			tableColumn.setText(editor.getTable().getColumns().get(i).getName());
			tableColumn.setWidth(editor.getTable().getColumns().get(i).getWidth());
			tableColumn.addControlListener(new ControlAdapter()
			{
				@Override
				public void controlResized(final ControlEvent e)
				{
					final TableColumn tablecolumn = (TableColumn) e.getSource();
					column.setWidth(tablecolumn.getWidth());
				}
			});
		}

		// Turning on the redraw flag on the table viewer
		tableviewer.getTable().setRedraw(true);
	}
}
