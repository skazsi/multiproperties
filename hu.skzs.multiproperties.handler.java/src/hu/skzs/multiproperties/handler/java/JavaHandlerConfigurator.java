package hu.skzs.multiproperties.handler.java;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.api.IHandlerConfigurator;
import hu.skzs.multiproperties.handler.java.configurator.JavaConfiguratorFactory;
import hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator;
import hu.skzs.multiproperties.handler.java.preference.OutputFilePreferencePage;

public class JavaHandlerConfigurator implements IHandlerConfigurator
{

	public String configure(final Shell shell, final String configuration) throws HandlerException
	{
		try
		{
			final WorkspaceConfigurator workspaceConfigurator = (WorkspaceConfigurator) JavaConfiguratorFactory
					.getInstance().getConfigurator(configuration);

			final OutputFilePreferencePage outputPage = new OutputFilePreferencePage(workspaceConfigurator);
			final PreferenceManager mgr = new PreferenceManager();
			final IPreferenceNode outputNode = new PreferenceNode("outputPage", outputPage);
			mgr.addToRoot(outputNode);
			final PreferenceDialog dialog = new PreferenceDialog(shell, mgr);
			dialog.create();
			if (dialog.open() == Window.OK)
				return workspaceConfigurator.getConfiguration();
			else
				return null;
		}
		catch (final Exception e)
		{
			throw new HandlerException("Unexpected error occurred during configuring the column by handler", e);
		}
	}
}
