package hu.skzs.multiproperties.ui.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.IHandlerService;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.command.dialog.AddEditableRecordDialog;
import hu.skzs.multiproperties.ui.command.dialog.AddRecordDialog;
import hu.skzs.multiproperties.ui.editor.Editor;
import hu.skzs.multiproperties.ui.editor.TablePage;

/**
 * Abstract handler implementation for adding new records.
 * @author skzs
 */
public abstract class AddRecordHandler extends AbstractHandler
{

	public final Object execute(final ExecutionEvent event) throws ExecutionException
	{
		final IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		if (!(editorPart instanceof Editor))
			return null;

		final Editor editor = (Editor) editorPart;
		final Table table = editor.getTable();
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		final AddRecordDialog dialog = createDialog(shell);
		dialog.create();
		if (dialog.open() == Window.OK)
		{
			// Create and add record
			final AbstractRecord record = createRecord(dialog);
			addRecord(record, table, dialog.getPosition());

			// Set selected the new record
			final TablePage tablepage = (TablePage) editor.getSelectedPage();
			final TableViewer tableviewer = tablepage.getTableViewer();
			tableviewer.setSelection(new StructuredSelection(record));

			// Open edit dialog
			if (dialog instanceof AddEditableRecordDialog)
			{
				final AddEditableRecordDialog editableDialog = (AddEditableRecordDialog) dialog;
				if (editableDialog.openEdit())
				{
					openEditDialog(event);
				}
			}
		}
		return null;
	}

	/**
	 * Returns a new instance of {@link AddRecordDialog}
	 * @param shell the given {@link Shell} instance
	 * @return a new instance of {@link AddRecordDialog}
	 */
	protected abstract AddRecordDialog createDialog(Shell shell);

	/**
	 * Returns a new instance of {@link AbstractRecord} to be added
	 * @param dialog the created dialog by {@link #createDialog(Shell)} method
	 * @return a new instance of {@link AbstractRecord}
	 */
	protected abstract AbstractRecord createRecord(AddRecordDialog dialog);

	private void addRecord(final AbstractRecord record, final Table table, final AddRecordDialog.Position position)
	{
		if (position == AddRecordDialog.Position.APPEND_AT_END)
			table.add(record);
		else
		{
			final IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getSelectionService().getSelection();
			if (position == AddRecordDialog.Position.INSERT_BEFORE_SELECTION)
			{
				final AbstractRecord firstSelectedRecord = (AbstractRecord) structuredSelection.getFirstElement();
				table.insert(record, table.indexOf(firstSelectedRecord));
			}
			else
			{
				final Object[] selectedRecordObjects = structuredSelection.toArray();
				final AbstractRecord lastSelectedRecord = (AbstractRecord) selectedRecordObjects[selectedRecordObjects.length
						- 1];
				table.insert(record, table.indexOf(lastSelectedRecord) + 1);
			}
		}
	}

	private void openEditDialog(final ExecutionEvent event)
	{
		try
		{
			final IWorkbenchSite site = HandlerUtil.getActiveSiteChecked(event);
			final IHandlerService handlerService = site.getService(IHandlerService.class);
			handlerService.executeCommand(EditHandler.COMMAND_ID, null);
		}
		catch (final Exception e)
		{
			Activator.logError(e);
		}
	}
}
