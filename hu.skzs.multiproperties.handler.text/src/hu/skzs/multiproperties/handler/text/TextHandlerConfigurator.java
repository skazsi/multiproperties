package hu.skzs.multiproperties.handler.text;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.api.IHandlerConfigurator;
import hu.skzs.multiproperties.handler.text.configurator.ConfiguratorFactory;
import hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator;
import hu.skzs.multiproperties.handler.text.wizard.TextHandlerConfigurationWizard;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * The {@link TextHandlerConfigurator} is the text file based implementation of {@link IHandlerConfigurator} and responsible
 * for configuring the Text Handler.
 * @author sallai
 */
public class TextHandlerConfigurator implements IHandlerConfigurator
{

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.api.IHandlerConfigurator#configure(org.eclipse.swt.widgets.Shell, java.lang.String)
	 */
	public String configure(final Shell shell, final String configuration) throws HandlerException
	{
		try
		{
			final ColorsPreferencesPage page = new ColorsPreferencesPage();
			final PreferenceManager mgr = new PreferenceManager();
			final IPreferenceNode node = new PreferenceNode("1", page);
			mgr.addToRoot(node);
			final PreferenceDialog dialog = new PreferenceDialog(shell, mgr);
			dialog.create();
			dialog.setMessage("page.getTitle()");
			dialog.open();

			final WorkspaceConfigurator workspaceConfigurator = (WorkspaceConfigurator) ConfiguratorFactory
					.getConfigurator(configuration); // it must be WorkspaceConfigurator in this case

			final TextHandlerConfigurationWizard wizard = new TextHandlerConfigurationWizard(workspaceConfigurator);
			final WizardDialog wizarddialog = new WizardDialog(shell, wizard);
			if (wizarddialog.open() == Window.OK)
				return workspaceConfigurator.toString();
			else
				return null;
		}
		catch (final Exception e)
		{
			throw new HandlerException("Unexpected error occurred during configuring the column by handler", e); //$NON-NLS-1$
		}
	}

}
