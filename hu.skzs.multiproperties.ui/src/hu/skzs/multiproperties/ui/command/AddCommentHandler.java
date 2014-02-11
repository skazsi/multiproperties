package hu.skzs.multiproperties.ui.command;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.ui.command.dialog.AddEditableRecordDialog;
import hu.skzs.multiproperties.ui.command.dialog.AddRecordDialog;

import org.eclipse.swt.widgets.Shell;

/**
 * The <code>AddCommentHandler</code> adds a new {@link CommentRecord} instance.
 * @author skzs
 */
public class AddCommentHandler extends AddRecordHandler
{

	@Override
	protected AddRecordDialog createDialog(final Shell shell)
	{
		return new AddEditableRecordDialog(shell);
	}

	@Override
	protected AbstractRecord createRecord(final AddRecordDialog dialog)
	{
		return new CommentRecord(""); //$NON-NLS-1$
	}

}
