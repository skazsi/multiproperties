package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;

/**
 * The <code>PositionPage</code> wizard page gives options to insert a new record at a desired place.
 * @author sallai
 */
public class PositionPage extends WizardPage
{

	/**
	 * The <code>Position</code> enumeration represents the possible positions where the new record can be inserted.
	 */
	enum Position {
		INSERT_BEFORE_SELECTION, INSERT_AFTER_SELECTION, APPEND_AT_END
	};

	private Position position = Position.APPEND_AT_END;
	// Form fields
	private Button insertBeforeSelection;
	private Button insertAfterSelection;
	private Button appendAtEnd;

	/**
	 * Default constructor.
	 */
	public PositionPage()
	{
		super("property.page"); //$NON-NLS-1$
		setTitle(Messages.getString("wizard.position.title")); //$NON-NLS-1$
		setDescription(Messages.getString("wizard.position.description")); //$NON-NLS-1$

		// Checks whether the selection is empty, if not the page will have the "insert before" as selected by default 
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		if (!selection.isEmpty() && selection instanceof IStructuredSelection)
			position = Position.INSERT_BEFORE_SELECTION;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(final Composite parent)
	{
		// Container
		final Composite composite = new Composite(parent, SWT.NULL);
		setControl(composite);
		composite.setLayout(LayoutFactory.createGridLayout(1, 10, 10));

		// Position group
		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new RowLayout(SWT.VERTICAL));
		group.setText(Messages.getString("wizard.position.title")); //$NON-NLS-1$
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		insertBeforeSelection = new Button(group, SWT.RADIO);
		insertBeforeSelection.setSelection(position.equals(Position.INSERT_BEFORE_SELECTION));
		insertBeforeSelection.setEnabled(!position.equals(Position.APPEND_AT_END));
		insertBeforeSelection.setText(Messages.getString("wizard.position.insertbefore")); //$NON-NLS-1$
		insertAfterSelection = new Button(group, SWT.RADIO);
		insertAfterSelection.setSelection(position.equals(Position.INSERT_AFTER_SELECTION));
		insertAfterSelection.setEnabled(!position.equals(Position.APPEND_AT_END));
		insertAfterSelection.setText(Messages.getString("wizard.position.insertafter")); //$NON-NLS-1$
		appendAtEnd = new Button(group, SWT.RADIO);
		appendAtEnd.setSelection(position.equals(Position.APPEND_AT_END));
		appendAtEnd.setText(Messages.getString("wizard.position.appendatend")); //$NON-NLS-1$
	}

	/**
	 * Returns the selected position. 
	 * @return the selected position
	 */
	Position getPosition()
	{
		if (insertBeforeSelection.getSelection())
			return Position.INSERT_BEFORE_SELECTION;
		else if (insertAfterSelection.getSelection())
			return Position.INSERT_AFTER_SELECTION;
		else
			return Position.APPEND_AT_END;
	}
}
