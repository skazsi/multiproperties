package hu.skzs.multiproperties.ui.wizards;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.ContentAssistant;

import org.eclipse.jface.wizard.Wizard;


public class EditPropertyWizard extends Wizard
{
	private final PropertyRecord record;
	private PropertyEditPage propertyEditPage;

	public EditPropertyWizard(final PropertyRecord record)
	{
		this.record = record;
	}

	@Override
	public void addPages()
	{
		setWindowTitle("Edit property");
		setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz"));
		propertyEditPage = new PropertyEditPage(record);
		addPage(propertyEditPage);
	}

	@Override
	public boolean performFinish()
	{
		final PropertyRecord newrecord = propertyEditPage.getPropertyRecord();
		record.setValue(newrecord.getValue());
		record.setDisabled(newrecord.isDisabled());
		for (int i = 0; i < ContentAssistant.getTable().getColumns().size(); i++)
		{
			final Column column = ContentAssistant.getTable().getColumns().get(i);
			if (newrecord.getColumnValue(column) != null)
				record.putColumnValue(column, newrecord.getColumnValue(column));
			else
				record.removeColumnValue(column);
		}
		record.setDescription(newrecord.getDescription());
		return true;
	}
}
