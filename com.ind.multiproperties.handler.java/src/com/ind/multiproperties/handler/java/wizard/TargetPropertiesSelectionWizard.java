/*
 * Created on 2006.03.28.
 */
package com.ind.multiproperties.handler.java.wizard;

import org.eclipse.jface.wizard.Wizard;

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
		setWindowTitle("Edit comment");
		// TODO: wizard picture is missing
		//setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz"));
		targetPropertiesSelectionPage = new TargetPropertiesSelectionPage(target);
		addPage(targetPropertiesSelectionPage);
	}

	@Override
	public boolean performFinish()
	{

		target = targetPropertiesSelectionPage.getLocation() + "/" + targetPropertiesSelectionPage.getFileName();
		return true;
	}

	public String getConfiguration()
	{
		return target;
	}
}
