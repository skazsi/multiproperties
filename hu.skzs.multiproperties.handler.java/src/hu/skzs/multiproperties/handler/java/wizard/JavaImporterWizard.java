package hu.skzs.multiproperties.handler.java.wizard;

import hu.skzs.multiproperties.base.api.AbstractImporterWizard;
import hu.skzs.multiproperties.handler.java.Messages;

/**
 * The {@link JavaImporterWizard} is the default <code>java.util.Properties</code> based
 * importer wizard implementation.
 * @author skzs
 */
public final class JavaImporterWizard extends AbstractImporterWizard
{

	private JavaImporterPage javaImporterPage;

	@Override
	public void addPages()
	{
		setWindowTitle(Messages.getString("wizard.importer.title")); //$NON-NLS-1$
		// TODO: wizard picture is missing
		//setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz"));
		javaImporterPage = new JavaImporterPage();
		addPage(javaImporterPage);
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.api.AbstractImporterWizard#getInput()
	 */
	@Override
	public Object getInput()
	{
		return javaImporterPage.getFileName();
	}

}
