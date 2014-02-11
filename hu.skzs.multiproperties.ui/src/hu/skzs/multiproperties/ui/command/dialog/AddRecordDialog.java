package hu.skzs.multiproperties.ui.command.dialog;

import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Custom dialog implementation for selecting position for the new record.
 * <p>Three possibilities are there, <i>insert before the selection</i>, <i>insert after the selection</i> or <i>append in the end</i>.</p>
 * @author skzs
 *
 */
public class AddRecordDialog extends AbstractAddRecordDialog
{

	/**
	 * The <code>Position</code> enumeration represents the possible positions where the new record can be inserted.
	 */
	public enum Position
	{
		INSERT_BEFORE_SELECTION, INSERT_AFTER_SELECTION, APPEND_AT_END
	};

	private static final String POSITION_STORE_KEY = "dialog.addrecord.position"; //$NON-NLS-1$

	private Position position = Position.INSERT_AFTER_SELECTION;
	private boolean hasSelection = true;

	// Form fields
	private Button insertBeforeSelection;
	private Button insertAfterSelection;
	private Button appendAtEnd;

	public AddRecordDialog(final Shell parentShell)
	{
		super(parentShell);

		if (store.getString(POSITION_STORE_KEY) != null)
		{
			try
			{
				position = Position.valueOf(store.getString(POSITION_STORE_KEY));
			}
			catch (final IllegalArgumentException e)
			{
			}
		}

		// Checks whether the selection is empty, if not the page will have the "insert before" as selected by default 
		final ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
				.getSelection();
		if (selection.isEmpty() || !(selection instanceof IStructuredSelection))
		{
			position = Position.APPEND_AT_END;
			hasSelection = false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createInnerArea(final Composite parent)
	{
		// Position group
		final Group group = new Group(parent, SWT.NONE);
		group.setLayout(new RowLayout(SWT.VERTICAL));
		group.setText(Messages.getString("dialog.addrecord.position")); //$NON-NLS-1$
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		insertBeforeSelection = new Button(group, SWT.RADIO);
		insertBeforeSelection.setSelection(position.equals(Position.INSERT_BEFORE_SELECTION));
		insertBeforeSelection.setEnabled(hasSelection);
		insertBeforeSelection.setText(Messages.getString("dialog.addrecord.insertbefore")); //$NON-NLS-1$
		insertAfterSelection = new Button(group, SWT.RADIO);
		insertAfterSelection.setSelection(position.equals(Position.INSERT_AFTER_SELECTION));
		insertAfterSelection.setEnabled(hasSelection);
		insertAfterSelection.setText(Messages.getString("dialog.addrecord.insertafter")); //$NON-NLS-1$
		appendAtEnd = new Button(group, SWT.RADIO);
		appendAtEnd.setSelection(position.equals(Position.APPEND_AT_END));
		appendAtEnd.setText(Messages.getString("dialog.addrecord.appendatend")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void processFormContent()
	{
		if (insertBeforeSelection.getSelection())
		{
			position = Position.INSERT_BEFORE_SELECTION;
		}
		else if (insertAfterSelection.getSelection())
			position = Position.INSERT_AFTER_SELECTION;
		else
			position = Position.APPEND_AT_END;
		store.setValue(POSITION_STORE_KEY, position.name());
	}

	/**
	 * Returns the selected position
	 * @return the selected position
	 */
	public Position getPosition()
	{
		return position;
	}
}
