package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.editor.Editor;
import hu.skzs.multiproperties.ui.wizard.CommentEditWizard;
import hu.skzs.multiproperties.ui.wizard.PropertyEditWizard;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class EditHandler extends AbstractHandler
{

	/**
	 * The <code>COMMAND_ID</code> represents the <strong>edit</strong> command identifier. It is the same
	 * value than how the command is specified in the <code>plugin.xml</code>.
	 */
	public static final String COMMAND_ID = "hu.skzs.multiproperties.ui.command.Edit"; //$NON-NLS-1$

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart instanceof Editor)
		{
			ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
			if (selection == null || selection.isEmpty())
				return null;

			if (selection instanceof IStructuredSelection)
			{
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;

				AbstractRecord record = (AbstractRecord) structuredSelection.getFirstElement();
				Editor editor = (Editor) editorPart;

				IWizard wizard = null;
				if (record instanceof PropertyRecord)
				{
					wizard = new PropertyEditWizard((PropertyRecord) record, editor.getTable());
				}
				else
				{
					wizard = new CommentEditWizard((CommentRecord) record);
				}
				final WizardDialog wizarddialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
				wizarddialog.open();
			}
		}
		return null;
	}

}
