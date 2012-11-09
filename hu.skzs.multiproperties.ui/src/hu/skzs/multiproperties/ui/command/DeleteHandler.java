package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.editor.Editor;

import java.util.Arrays;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * The <code>DeleteCommand</code> deletes the selected {@link AbstractRecord} instances.
 * @author skzs
 */
public class DeleteHandler extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		Editor editor = (Editor) HandlerUtil.getActiveEditor(event);
		Table table = editor.getTable();

		// Checking whether the selection is instance of IStructuredSelection
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (!(selection instanceof IStructuredSelection))
			return null;

		// Checking whether the selection is empty
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		if (structuredSelection.isEmpty())
			return null;

		// Confirming
		if (!MessageDialog.openConfirm(null, Messages.getString("command.delete"), Messages.getString("command.delete.confirm"))) //$NON-NLS-1$//$NON-NLS-2$
			return null;

		// Deleting the records
		table.remove(Arrays.asList(structuredSelection.toArray()).toArray(new AbstractRecord[structuredSelection.size()]));
		return null;
	}

}
