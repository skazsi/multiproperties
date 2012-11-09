package hu.skzs.multiproperties.handler.text;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.api.IHandlerConfigurator;
import hu.skzs.multiproperties.handler.text.configurator.TextConfiguratorFactory;
import hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator;
import hu.skzs.multiproperties.handler.text.preference.OutputFilePreferencePage;
import hu.skzs.multiproperties.handler.text.preference.PatternsPreferencePage;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
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
			final WorkspaceConfigurator workspaceConfigurator = (WorkspaceConfigurator) TextConfiguratorFactory
					.getInstance().getConfigurator(configuration); // it must be WorkspaceConfigurator in this case

			final OutputFilePreferencePage outputPage = new OutputFilePreferencePage(workspaceConfigurator);
			final PatternsPreferencePage patternPage = new PatternsPreferencePage(workspaceConfigurator);
			final PreferenceManager mgr = new PreferenceManager();
			final IPreferenceNode outputNode = new PreferenceNode("outputPage", outputPage); //$NON-NLS-1$
			final IPreferenceNode patternNode = new PreferenceNode("patternPage", patternPage); //$NON-NLS-1$
			mgr.addToRoot(outputNode);
			mgr.addToRoot(patternNode);
			final PreferenceDialog dialog = new PreferenceDialog(shell, mgr);
			dialog.create();
			if (dialog.open() == Window.OK)
				return workspaceConfigurator.getConfiguration();
			else
				return null;
		}
		catch (final Exception e)
		{
			throw new HandlerException("Unexpected error occurred during configuring the column by handler", e); //$NON-NLS-1$
		}
	}

}
