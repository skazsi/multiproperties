package hu.skzs.multiproperties.handler.java;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.api.IHandlerConfigurator;
import hu.skzs.multiproperties.handler.java.configurator.ConfiguratorFactory;
import hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator;
import hu.skzs.multiproperties.handler.java.preference.OutputFilePreferencePage;

import java.util.Properties;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * The {@link JavaHandlerConfigurator} is the default implementation of {@link IHandlerConfigurator} and responsible
 * for configuring the Java Handler.
 * @author sallai
 * @see Properties
 */
public class JavaHandlerConfigurator implements IHandlerConfigurator
{

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.api.IHandler#configure(org.eclipse.swt.widgets.Shell, java.lang.String)
	 */
	public String configure(final Shell shell, final String configuration) throws HandlerException
	{
		try
		{
			final WorkspaceConfigurator workspaceConfigurator = (WorkspaceConfigurator) ConfiguratorFactory
					.getConfigurator(configuration); // it must be WorkspaceConfigurator in this case

			final OutputFilePreferencePage outputPage = new OutputFilePreferencePage(workspaceConfigurator);
			final PreferenceManager mgr = new PreferenceManager();
			final IPreferenceNode outputNode = new PreferenceNode("outputPage", outputPage); //$NON-NLS-1$
			mgr.addToRoot(outputNode);
			final PreferenceDialog dialog = new PreferenceDialog(shell, mgr);
			dialog.create();
			if (dialog.open() == Window.OK)
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
