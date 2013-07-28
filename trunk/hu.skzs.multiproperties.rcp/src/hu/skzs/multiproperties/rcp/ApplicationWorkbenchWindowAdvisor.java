package hu.skzs.multiproperties.rcp;

import hu.skzs.multiproperties.rcp.preference.PreferenceConstants;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
	{
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer)
	{
		ICoolBarManager coolBarManager = configurer.getCoolBarManager();
		coolBarManager.setLockLayout(false);
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen()
	{
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(600, 400));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(false);
	}

	@Override
	public void postWindowOpen()
	{
		super.postWindowOpen();

		// Suppressing the unwanted menu items
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (int i = 0; i < windows.length; ++i)
		{
			IWorkbenchPage page = windows[i].getActivePage();
			if (page != null)
				page.hideActionSet("org.eclipse.ui.actionSet.openFiles"); //$NON-NLS-1$
		}

		// Displaying the notification dialog about the alpha version
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.getString(PreferenceConstants.NOTIFICATION_VER_ALPHA) == MessageDialogWithToggle.PROMPT)
		{
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable()
			{
				public void run()
				{
					Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

					String title = Messages.getString("general.alphaversion.title"); //$NON-NLS-1$
					String toogle = Messages.getString("general.alphaversion.toogle"); //$NON-NLS-1$
					StringBuilder strb = new StringBuilder(Messages.getString("general.alphaversion.notification1")); //$NON-NLS-1$
					strb.append("\n\n"); //$NON-NLS-1$
					strb.append(Messages.getString("general.alphaversion.notification2")); //$NON-NLS-1$

					MessageDialogWithToggle.openInformation(shell, title, strb.toString(), toogle, false, store,
							PreferenceConstants.NOTIFICATION_VER_ALPHA);
				}
			});
		}
	}
}
