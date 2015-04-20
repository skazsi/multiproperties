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

	public ApplicationWorkbenchWindowAdvisor(final IWorkbenchWindowConfigurer configurer)
	{
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(final IActionBarConfigurer configurer)
	{
		final ICoolBarManager coolBarManager = configurer.getCoolBarManager();
		coolBarManager.setLockLayout(false);
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen()
	{
		final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(600, 400));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(false);
	}

	@Override
	public void postWindowOpen()
	{
		super.postWindowOpen();

		// Suppressing the unwanted menu items
		final IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (int i = 0; i < windows.length; ++i)
		{
			final IWorkbenchPage page = windows[i].getActivePage();
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
					final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

					final String title = Messages.getString("general.alphaversion.title"); //$NON-NLS-1$
					final String toogle = Messages.getString("general.alphaversion.toogle"); //$NON-NLS-1$
					final String message = Messages.getString("general.alphaversion.notification"); //$NON-NLS-1$

					MessageDialogWithToggle.openInformation(shell, title, message, toogle, false, store,
							PreferenceConstants.NOTIFICATION_VER_ALPHA);
				}
			});
		}
	}
}
