package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.editor.Editor;
import hu.skzs.multiproperties.ui.editor.TablePage;

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

	public Object execute(final ExecutionEvent event) throws ExecutionException
	{
		final Editor editor = (Editor) HandlerUtil.getActiveEditor(event);
		final Table table = editor.getTable();

		// Checking whether the selection is instance of IStructuredSelection
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (!(selection instanceof IStructuredSelection))
			return null;

		// Checking whether the selection is empty
		final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		if (structuredSelection.isEmpty())
			return null;

		final Object[] recordObjects = structuredSelection.toArray();
		for (int i = recordObjects.length - 1; i >= 0; i--)
		{
			final AbstractRecord record = (AbstractRecord) recordObjects[i];
			table.moveDown(record);
		}

		// Revealing the last one
		final TablePage tablepage = (TablePage) editor.getSelectedPage();
		tablepage.getTableViewer().reveal(recordObjects[recordObjects.length - 1]);

		return null;
	}

}
