package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.ui.editor.Editor;
import hu.skzs.multiproperties.ui.wizard.EmptyAddWizard;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class AddEmptyHandler extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart instanceof Editor)
		{
			Editor editor = (Editor) editorPart;
			final IWizard wizard = new EmptyAddWizard(editor.getTable());
			final WizardDialog wizarddialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
			wizarddialog.open();
		}
		return null;
	}

}
