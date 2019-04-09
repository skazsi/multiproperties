package hu.skzs.multiproperties.handler.java.wizard;

import hu.skzs.multiproperties.base.api.AbstractImporterWizard;
import hu.skzs.multiproperties.handler.java.Messages;
import hu.skzs.multiproperties.handler.java.importer.JavaImporterInput;

public final class JavaImporterWizard extends AbstractImporterWizard
{
	private JavaImporterPage javaImporterPage;

	@Override
	public void addPages()
	{
		setWindowTitle(Messages.getString("wizard.importer.title"));
		// TODO: wizard picture is missing
		//setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz"));
		javaImporterPage = new JavaImporterPage();
		addPage(javaImporterPage);
	}

	@Override
	public Object getInput()
	{
		return new JavaImporterInput(javaImporterPage.getFileName(), javaImporterPage.getEncoding());
	}

}
