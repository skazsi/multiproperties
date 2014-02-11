package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.editor.Editor;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * The <code>DuplicateHandler</code> deletes the selected {@link AbstractRecord} instances.
 * @author skzs
 */
public class DuplicateHandler extends AbstractHandler
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

		final Object[] records = structuredSelection.toArray();
		for (int i = 0; i < records.length; i++)
		{
			final AbstractRecord oldrecord = (AbstractRecord) records[i];
			AbstractRecord newrecord = null;

			if (oldrecord instanceof PropertyRecord)
				newrecord = new PropertyRecord((PropertyRecord) oldrecord);
			else if (oldrecord instanceof CommentRecord)
				newrecord = new CommentRecord((CommentRecord) oldrecord);
			else
				newrecord = new EmptyRecord();

			final int index = editor.getTable().indexOf(oldrecord) + structuredSelection.size();
			table.insert(newrecord, index);
		}
		return null;
	}

}
