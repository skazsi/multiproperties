package hu.skzs.multiproperties.rcp.handler;

import hu.skzs.multiproperties.rcp.Messages;
import hu.skzs.multiproperties.ui.editor.Editor;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.FileStoreEditorInput;

public class OpenFileHandler extends AbstractHandler
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands
	 * .ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		try
		{
			// Selecting an input file
			FileDialog fileDialog = new FileDialog(HandlerUtil.getActiveShell(event));
			fileDialog.setText(Messages.getString("command.openfile")); //$NON-NLS-1$
			fileDialog.setFilterExtensions(new String[] { "*.multiproperties" }); //$NON-NLS-1$
			fileDialog.setFilterNames(new String[] { "*.multiproperties" }); //$NON-NLS-1$
			String selected = fileDialog.open();

			if (selected != null)
			{
				// Creating the editor input
				IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(selected));
				FileStoreEditorInput fileStoreEditorInput = new FileStoreEditorInput(fileStore);

				// Opening the editor
				IWorkbenchPage activePage = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
				activePage.openEditor(fileStoreEditorInput, Editor.ID);
			}
		}
		catch (Exception e)
		{
			throw new ExecutionException("Unable to handle open file command", //$NON-NLS-1$
					e);
		}
		return null;
	}
}
