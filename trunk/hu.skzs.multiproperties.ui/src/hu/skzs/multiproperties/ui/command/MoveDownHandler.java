package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.editor.Editor;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * The <code>MoveDownHandler</code> moves down the selected {@link AbstractRecord} instances.
 * @author skzs
 */
public class MoveDownHandler extends AbstractHandler
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

		final Object[] recordObjects = structuredSelection.toArray();
		for (int i = recordObjects.length - 1; i >= 0; i--)
		{
			final AbstractRecord record = (AbstractRecord) recordObjects[i];
			table.moveDown(record);
		}
		return null;
	}

}
