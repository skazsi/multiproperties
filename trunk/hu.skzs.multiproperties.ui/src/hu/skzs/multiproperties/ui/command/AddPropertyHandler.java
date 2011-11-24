package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.editor.Editor;
import hu.skzs.multiproperties.ui.wizard.PropertyAddWizard;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

/**
 * The <code>AddPropertyHandler</code> adds a new {@link PropertyRecord} instance.
 * @author sallai
 */
public class AddPropertyHandler extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart instanceof Editor)
		{
			Editor editor = (Editor) editorPart;
			final IWizard wizard = new PropertyAddWizard(editor.getTable());
			final WizardDialog wizarddialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
			wizarddialog.open();
		}
		return null;
	}

}
