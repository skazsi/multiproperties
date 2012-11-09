package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.jface.wizard.Wizard;

/**
 * The <code>PropertyEditWizard</code> wizard is able to edit a property record. 
 * @author skzs
 */
public class PropertyEditWizard extends Wizard
{

	private final PropertyRecord propertyRecord;
	private final Table table;
	private PropertyPage propertyPage;

	public PropertyEditWizard(final PropertyRecord propertyRecord, final Table table)
	{
		this.propertyRecord = propertyRecord;
		this.table = table;
	}

	@Override
	public void addPages()
	{
		setWindowTitle(Messages.getString("wizard.property.edit.title")); //$NON-NLS-1$
		setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz")); //$NON-NLS-1$
		propertyPage = new PropertyPage(propertyRecord, table);
		addPage(propertyPage);
	}

	@Override
	public boolean performFinish()
	{
		final PropertyRecord modifiedRecord = propertyPage.getPropertyRecord();
		propertyRecord.setValue(modifiedRecord.getValue());
		propertyRecord.setDefaultColumnValue(modifiedRecord.getDefaultColumnValue());
		propertyRecord.setDisabled(modifiedRecord.isDisabled());
		for (int i = 0; i < table.getColumns().size(); i++)
		{
			final Column column = table.getColumns().get(i);
			if (modifiedRecord.getColumnValue(column) != null)
				propertyRecord.putColumnValue(column, modifiedRecord.getColumnValue(column));
			else
				propertyRecord.removeColumnValue(column);
		}
		propertyRecord.setDescription(modifiedRecord.getDescription());
		return true;
	}
}
