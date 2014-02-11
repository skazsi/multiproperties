package hu.skzs.multiproperties.ui.command.dialog;

import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * Custom dialog implementation for selecting position for the new property record in addition of selecting the multiline type.
 * @author skzs
 * @see AddEditableRecordDialog
 * @see AddRecordDialog
 */
public class AddPropertyRecordDialog extends AddEditableRecordDialog
{
	/**
	 * The <code>Position</code> enumeration represents the possible positions where the new record can be inserted.
	 */
	public enum Type
	{
		NORMAL, MULTILINE
	};

	private static final String TYPE_STORE_KEY = "dialog.addrecord.type"; //$NON-NLS-1$

	private Type type = Type.NORMAL;

	// Form fields
	private Button normal;
	private Button multiline;

	public AddPropertyRecordDialog(final Shell parentShell)
	{
		super(parentShell);

		if (store.getString(TYPE_STORE_KEY) != null)
		{
			try
			{
				type = Type.valueOf(store.getString(TYPE_STORE_KEY));
			}
			catch (final IllegalArgumentException e)
			{
			}
		}
	}

	@Override
	public void create()
	{
		super.create();
		setMessage(Messages.getString("dialog.addpropertyrecord.description"), IMessageProvider.INFORMATION); //$NON-NLS-1$
	}

	@Override
	protected void createInnerArea(final Composite parent)
	{
		// Position group
		final Group group = new Group(parent, SWT.NONE);
		group.setLayout(new RowLayout(SWT.VERTICAL));
		group.setText(Messages.getString("dialog.addrecord.type")); //$NON-NLS-1$
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		normal = new Button(group, SWT.RADIO);
		normal.setSelection(type.equals(Type.NORMAL));
		normal.setText(Messages.getString("dialog.addrecord.normal")); //$NON-NLS-1$
		multiline = new Button(group, SWT.RADIO);
		multiline.setSelection(type.equals(Type.MULTILINE));
		multiline.setText(Messages.getString("dialog.addrecord.multiline")); //$NON-NLS-1$

		super.createInnerArea(parent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void processFormContent()
	{
		if (normal.getSelection())
			type = Type.NORMAL;
		else
			type = Type.MULTILINE;
		store.setValue(TYPE_STORE_KEY, type.name());
		super.processFormContent();
	}

	/**
	 * Returns the type position
	 * @return the type position
	 */
	public Type getType()
	{
		return type;
	}
}
