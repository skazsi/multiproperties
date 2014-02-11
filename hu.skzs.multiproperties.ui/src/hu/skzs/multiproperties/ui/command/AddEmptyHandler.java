package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.ui.command.dialog.AddRecordDialog;

import org.eclipse.swt.widgets.Shell;

/**
 * The <code>AddEmptyHandler</code> adds a new {@link EmptyRecord} instance.
 * @author skzs
 */
public class AddEmptyHandler extends AddRecordHandler
{

	@Override
	protected AddRecordDialog createDialog(final Shell shell)
	{
		return new AddRecordDialog(shell);
	}

	@Override
	protected AbstractRecord createRecord(final AddRecordDialog dialog)
	{
		return new EmptyRecord();
	}

}
