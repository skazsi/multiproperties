package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.command.dialog.AddPropertyRecordDialog;
import hu.skzs.multiproperties.ui.command.dialog.AddRecordDialog;

import org.eclipse.swt.widgets.Shell;

/**
 * The <code>AddPropertyHandler</code> adds a new {@link PropertyRecord} instance.
 * @author skzs
 */
public class AddPropertyHandler extends AddRecordHandler
{

	@Override
	protected AddRecordDialog createDialog(final Shell shell)
	{
		return new AddPropertyRecordDialog(shell);
	}

	@Override
	protected AbstractRecord createRecord(final AddRecordDialog dialog)
	{
		final AddPropertyRecordDialog addPropertyRecordDialog = (AddPropertyRecordDialog) dialog;
		final PropertyRecord record = new PropertyRecord(""); //$NON-NLS-1$
		if (addPropertyRecordDialog.getType() == AddPropertyRecordDialog.Type.MULTILINE)
			record.setMultiLine(true);
		return record;
	}

}
