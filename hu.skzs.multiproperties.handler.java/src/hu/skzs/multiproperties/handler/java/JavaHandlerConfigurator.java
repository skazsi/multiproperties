package hu.skzs.multiproperties.handler.java;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.api.IHandlerConfigurator;
import hu.skzs.multiproperties.handler.java.wizard.TargetPropertiesSelectionWizard;
import hu.skzs.multiproperties.handler.java.writer.WorkspaceWriter;
import hu.skzs.multiproperties.handler.java.writer.Writer;
import hu.skzs.multiproperties.handler.java.writer.WriterFactory;

import java.util.Properties;

import org.eclipse.jface.wizard.WizardDialog;
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
			final Writer writer = WriterFactory.getWriter(configuration); // it must be WorkspaceWriter in this case
			final TargetPropertiesSelectionWizard wizard = new TargetPropertiesSelectionWizard((WorkspaceWriter) writer);
			final WizardDialog wizarddialog = new WizardDialog(shell, wizard);
			wizarddialog.open();
			return writer.toString();
		}
		catch (final Exception e)
		{
			throw new HandlerException("Unexpected error occured during configuring the column by handler"); //$NON-NLS-1$
		}
	}
}
