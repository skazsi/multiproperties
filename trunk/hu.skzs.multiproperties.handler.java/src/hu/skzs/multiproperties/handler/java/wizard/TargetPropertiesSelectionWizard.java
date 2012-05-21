package hu.skzs.multiproperties.handler.java.wizard;

import hu.skzs.multiproperties.handler.java.Messages;
import hu.skzs.multiproperties.handler.java.writer.WorkspaceWriter;

import org.eclipse.jface.wizard.Wizard;

/**
 * The <code>TargetPropertiesSelectionWizard</code> wizard is able to configure a column's target file.
 * When the user saves the MultiProperties file, the related column's content will be saved in this
 * target file. 
 * @author sallai
 */
public class TargetPropertiesSelectionWizard extends Wizard
{
	private final WorkspaceWriter writer;
	private TargetPropertiesSelectionPage targetPropertiesSelectionPage;

	public TargetPropertiesSelectionWizard(final WorkspaceWriter configurationConverter)
	{
		this.writer = configurationConverter;
	}

	@Override
	public void addPages()
	{
		setWindowTitle(Messages.getString("wizard.configuration.title")); //$NON-NLS-1$
		// TODO: wizard picture is missing
		//setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz"));
		targetPropertiesSelectionPage = new TargetPropertiesSelectionPage(writer);
		addPage(targetPropertiesSelectionPage);
	}

	@Override
	public boolean performFinish()
	{
		writer.setContainerName(targetPropertiesSelectionPage.getLocation());
		writer.setFileName(targetPropertiesSelectionPage.getFileName());
		writer.setIncludeDescription(targetPropertiesSelectionPage.isDescriptionIncluded());
		writer.setIncludeColumnDescription(targetPropertiesSelectionPage.isColumnDescriptionIncluded());
		writer.setIncludeDisabled(targetPropertiesSelectionPage.isDisabledPropertiesIncluded());
		writer.setDisableDefaultValues(targetPropertiesSelectionPage.isDisableDefault());
		return true;
	}
}
