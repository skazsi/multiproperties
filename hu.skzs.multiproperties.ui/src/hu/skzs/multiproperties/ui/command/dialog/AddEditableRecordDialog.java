package hu.skzs.multiproperties.ui.command.dialog;

import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * Custom dialog implementation for selecting position for the new record together with a checkbox for opening the edit dialog
 * after pressing the ok button.
 * @author skzs
 * @see AddRecordDialog
 */
public class AddEditableRecordDialog extends AddRecordDialog
{
	private static final String OPENEDIT_STORE_KEY = "dialog.addrecord.openedit"; //$NON-NLS-1$

	private boolean openEdit;
	// Form fields
	private Button openEditCheck;

	public AddEditableRecordDialog(final Shell parentShell)
	{
		super(parentShell);
		openEdit = store.getBoolean(OPENEDIT_STORE_KEY);
	}

	@Override
	protected void createInnerArea(final Composite parent)
	{
		super.createInnerArea(parent);

		openEditCheck = new Button(parent, SWT.CHECK);
		openEditCheck.setText(Messages.getString("dialog.addrecord.openedit")); //$NON-NLS-1$
		openEditCheck.setSelection(openEdit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void processFormContent()
	{
		openEdit = openEditCheck.getSelection();
		store.setValue(OPENEDIT_STORE_KEY, openEdit);
		super.processFormContent();
	}

	/**
	 * Returns a boolean value whether to open the edit dialog after pressing the ok button
	 * @return a boolean value
	 */
	public boolean openEdit()
	{
		return openEdit;
	}
}
