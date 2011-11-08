package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.command.EditHandler;
import hu.skzs.multiproperties.ui.command.TooltipHandler;

import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.handlers.RegistryToggleState;

public class TablePage extends MPEditorPage
{

	private TableViewer tableviewer;
	IPropertyChangeListener preferenceListener = new IPropertyChangeListener() {

		public void propertyChange(final PropertyChangeEvent event)
		{
			if (event.getProperty().equals("color_property_foreground") || (event.getProperty().equals("color_property_disengaged_foreground")) || (event.getProperty().equals("color_property_background")) || (event.getProperty().equals("color_comment_foreground"))
					|| (event.getProperty().equals("color_comment_background")) || (event.getProperty().equals("color_empty_background")))
			{
				tableviewer.refresh();
			}
		}
	};

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
	public void createPartControl(Composite parent)
	{
		parent.setLayout(new FillLayout());

		tableviewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		tableviewer.getTable().setLinesVisible(true);
		tableviewer.getTable().setHeaderVisible(true);
		final TableColumn tc = new TableColumn(tableviewer.getTable(), SWT.NONE, 0);
		tc.setText(Messages.getString("tables.key")); //$NON-NLS-1$
		tc.setWidth(editor.getTable().getKeyColumnWidth());
		tc.addControlListener(new ControlListener() {

			public void controlMoved(final ControlEvent e)
			{
			}

			public void controlResized(final ControlEvent e)
			{
				final TableColumn tablecolumn = (TableColumn) e.getSource();
				editor.getTable().setKeyColumnWidth(tablecolumn.getWidth());
			}
		});
		refreshColumn();
		tableviewer.setContentProvider(new EditorContentProvider(editor));
		tableviewer.setLabelProvider(new EditorLabelProvider(editor));
		tableviewer.setInput(editor.getEditorInput());
		tableviewer.setCellModifier(new EditorCellModifier(editor, tableviewer));
		// Disable native tooltip
		tableviewer.getTable().setToolTipText(""); //$NON-NLS-1$

		// Makes the selection available to the workbench
		getSite().setSelectionProvider(tableviewer);

		// Implement a "fake" tooltip
		final Listener labelListener = new Listener() {

			public void handleEvent(final Event event)
			{
				final Label label = (Label) event.widget;
				final Shell shell = label.getShell();
				switch (event.type)
				{
				case SWT.MouseDown:
					final Event e = new Event();
					e.item = (TableItem) label.getData("_TABLEITEM"); //$NON-NLS-1$
					// Assuming table is single select, set the selection as if
					// the mouse down event went through to the table
					tableviewer.getTable().setSelection(new TableItem[] { (TableItem) e.item });
					tableviewer.getTable().notifyListeners(SWT.Selection, e);
					// fall through
				case SWT.MouseExit:
					shell.dispose();
					break;
				}
			}
		};
		final Listener tableListener = new Listener() {

			Shell tip = null;
			Label title = null;
			Label description = null;

			public void handleEvent(final Event event)
			{
				switch (event.type)
				{
				case SWT.Dispose:
				case SWT.KeyDown:
				case SWT.MouseMove:
				{
					if (tip == null)
						break;
					tip.dispose();
					tip = null;
					title = null;
					description = null;
					break;
				}
				case SWT.MouseHover:
				{
					ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
					if (commandService == null)
						return;

					Boolean showTooltip = (Boolean) commandService.getCommand(TooltipHandler.COMMAND_ID).getState(RegistryToggleState.STATE_ID).getValue();
					if (!showTooltip)
						return;

					if (tableviewer.getTable().getItem(new Point(event.x, event.y)) != null)
					{
						final AbstractRecord record = (AbstractRecord) tableviewer.getElementAt(tableviewer.getTable().indexOf(tableviewer.getTable().getItem(new Point(event.x, event.y))));
						if (!(record instanceof PropertyRecord))
							return;
						final PropertyRecord propertyrecord = (PropertyRecord) record;
						final TableItem item = tableviewer.getTable().getItem(new Point(event.x, event.y));
						if (item != null)
						{
							if (tip != null && !tip.isDisposed())
								tip.dispose();
							tip = new Shell(tableviewer.getTable().getShell(), SWT.ON_TOP | SWT.TOOL);
							final RowLayout rowlayout = new RowLayout(SWT.VERTICAL);
							rowlayout.fill = true;
							rowlayout.spacing = 1;
							rowlayout.marginTop = 0;
							rowlayout.marginBottom = 0;
							rowlayout.marginLeft = 0;
							rowlayout.marginRight = 0;
							tip.setLayout(rowlayout);
							title = new Label(tip, SWT.NONE);
							title.setForeground(tableviewer.getTable().getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND));
							title.setBackground(tableviewer.getTable().getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
							title.setFont(EditorFontRegistry.get(EditorFontRegistry.TOOLTIP_TITLE));
							title.setData("_TABLEITEM", item); //$NON-NLS-1$
							title.setText(propertyrecord.getValue());
							title.addListener(SWT.MouseExit, labelListener);
							title.addListener(SWT.MouseDown, labelListener);
							if (propertyrecord.getDescription() != null)
							{
								description = new Label(tip, SWT.NONE);
								description.setForeground(tableviewer.getTable().getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND));
								description.setBackground(tableviewer.getTable().getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
								description.setData("_TABLEITEM", item); //$NON-NLS-1$
								description.setText(propertyrecord.getDescription());
								description.addListener(SWT.MouseExit, labelListener);
								description.addListener(SWT.MouseDown, labelListener);
							}
							final Point size = tip.computeSize(SWT.DEFAULT, SWT.DEFAULT);
							final Rectangle rect = item.getBounds(0);
							final Point pt = tableviewer.getTable().toDisplay(rect.x + tableviewer.getTable().getHorizontalBar().getSelection(), rect.y);
							tip.setBounds(pt.x, pt.y, size.x, size.y);
							tip.setVisible(true);
						}
					}
				}
				}
			}
		};
		tableviewer.getTable().addListener(SWT.Dispose, tableListener);
		tableviewer.getTable().addListener(SWT.KeyDown, tableListener);
		tableviewer.getTable().addListener(SWT.MouseMove, tableListener);
		tableviewer.getTable().addListener(SWT.MouseHover, tableListener);

		final MenuManager menuManager = new MenuManager();
		tableviewer.getTable().setMenu(menuManager.createContextMenu(tableviewer.getTable()));
		getSite().registerContextMenu(menuManager, tableviewer);

		tableviewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(final DoubleClickEvent event)
			{
				IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);
				try
				{
					handlerService.executeCommand(EditHandler.COMMAND_ID, null);
				}
				catch (NotEnabledException e)
				{
					// do nothing
					// TODO: it should be investigated how the enabled state could be checked
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void setActive()
	{
		if (editor.getTable().getColumns().isStale())
		{
			refreshColumn();
			tableviewer.refresh();
			editor.getTable().getColumns().setStale(false);
		}
		// TODO: not active flag is specified
		//		if (!active && editor.getOutlinePage() != null)
		//			editor.getOutlinePage().update(null);
	}

	private void refreshColumn()
	{
		tableviewer.getTable().setRedraw(false);
		// removes the olds
		int col = tableviewer.getTable().getColumnCount();
		while (col > 1)
		{
			final TableColumn tc = tableviewer.getTable().getColumn(--col);
			tc.dispose();
		}
		// add the news
		final String[] columnproperties = new String[editor.getTable().getColumns().size() + 1];
		columnproperties[0] = "0"; //$NON-NLS-1$
		final TextCellEditor keyscelleditor = new TextCellEditor(tableviewer.getTable());
		final TextCellEditor valuescelleditor = new TextCellEditor(tableviewer.getTable());
		final CellEditor[] celleditors = new CellEditor[editor.getTable().getColumns().size() + 1];
		celleditors[0] = keyscelleditor;
		for (int i = 0; i < editor.getTable().getColumns().size(); i++)
		{
			final TableColumn tc = new TableColumn(tableviewer.getTable(), SWT.NONE, i + 1);
			tc.setText(editor.getTable().getColumns().get(i).getName());
			tc.setWidth(editor.getTable().getColumns().get(i).getWidth());
			tc.setData(new Integer(i));
			tc.addControlListener(new ControlListener() {

				public void controlMoved(final ControlEvent e)
				{
				}

				public void controlResized(final ControlEvent e)
				{
					final TableColumn tablecolumn = (TableColumn) e.getSource();
					final Integer index = (Integer) tablecolumn.getData();
					editor.getTable().getColumns().get(index.intValue()).setWidth(tablecolumn.getWidth());
				}
			});
			columnproperties[i + 1] = "" + (i + 1); //$NON-NLS-1$
			celleditors[i + 1] = valuescelleditor;
		}
		tableviewer.setColumnProperties(columnproperties);
		tableviewer.setCellEditors(celleditors);
		tableviewer.getTable().setRedraw(true);
	}
}
