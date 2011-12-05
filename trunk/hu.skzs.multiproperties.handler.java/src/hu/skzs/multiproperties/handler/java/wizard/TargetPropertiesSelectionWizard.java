package hu.skzs.multiproperties.handler.java.wizard;

import hu.skzs.multiproperties.handler.java.Messages;

import org.eclipse.jface.wizard.Wizard;

/**
 * The <code>TargetPropertiesSelectionWizard</code> wizard is able to configure a column's target file.
 * When the user saves the MultiProperties file, the related column's content will be saved in this
 * target file. 
 * @author sallai
 */
public class TargetPropertiesSelectionWizard extends Wizard
{
	private String target;
	private TargetPropertiesSelectionPage targetPropertiesSelectionPage;

	public TargetPropertiesSelectionWizard(final String target)
	{
		this.target = target;
	}

	@Override
	public void addPages()
	{
		setWindowTitle(Messages.getString("wizard.configuration.title")); //$NON-NLS-1$
		// TODO: wizard picture is missing
		//setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz"));
		targetPropertiesSelectionPage = new TargetPropertiesSelectionPage(target);
		addPage(targetPropertiesSelectionPage);
	}

	@Override
	public boolean performFinish()
	{
		target = targetPropertiesSelectionPage.getLocation() + "/" + targetPropertiesSelectionPage.getFileName(); //$NON-NLS-1$
		return true;
	}

	public String getConfiguration()
	{
		return target;
	}
}
