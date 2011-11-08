package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.wizard.PositionPage.Position;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.PlatformUI;

/**
 * The <code>PropertyAddWizard</code> wizard is able to add new property record. 
 * @author sallai
 */
public class PropertyAddWizard extends Wizard
{
	private final Table table;
	private PropertyPage propertyPage;
	private PositionPage positionPage;

	public PropertyAddWizard(Table table)
	{
		this.table = table;
	}

	@Override
	public void addPages()
	{
		setWindowTitle(Messages.getString("wizard.property.add.title")); //$NON-NLS-1$
		setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz")); //$NON-NLS-1$
		propertyPage = new PropertyPage(null, table);
		addPage(propertyPage);
		positionPage = new PositionPage();
		addPage(positionPage);
	}

	@Override
	public boolean performFinish()
	{
		final PropertyRecord record = propertyPage.getPropertyRecord();
		if (positionPage.getPosition() == Position.APPEND_AT_END)
			table.add(record);
		else
		{
			IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
			if (positionPage.getPosition() == Position.INSERT_BEFORE_SELECTION)
			{
				AbstractRecord firstSelectedRecord = (AbstractRecord) structuredSelection.getFirstElement();
				table.insert(record, table.indexOf(firstSelectedRecord));
			}
			else
			{
				Object[] selectedRecordObjects = structuredSelection.toArray();
				AbstractRecord lastSelectedRecord = (AbstractRecord) selectedRecordObjects[selectedRecordObjects.length - 1];
				table.insert(record, table.indexOf(lastSelectedRecord) + 1);
			}
		}
		return true;
	}
}
