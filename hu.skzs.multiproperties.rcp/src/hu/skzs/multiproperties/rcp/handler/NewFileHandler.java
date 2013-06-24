package hu.skzs.multiproperties.rcp.handler;

import hu.skzs.multiproperties.rcp.wizard.NewMultiPropertiesWizard;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

public class NewFileHandler extends AbstractHandler
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
			final IWizard wizard = new NewMultiPropertiesWizard();
			final WizardDialog wizarddialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getShell(), wizard);
			wizarddialog.open();
		}
		catch (Exception e)
		{
			throw new ExecutionException("Unable to handle new file command", //$NON-NLS-1$
					e);
		}
		return null;
	}
}
