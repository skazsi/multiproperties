package hu.skzs.multiproperties.ui.command.dialog;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;

/**
 * Abstract implementation of custom dialog for selecting position for the new record.
 * @author skzs
 *
 */
public abstract class AbstractAddRecordDialog extends TitleAreaDialog
{

	/**
	 * Preference store instance for saving and restoring the form states.
	 */
	protected final IPreferenceStore store = Activator.getDefault().getPreferenceStore();

	public AbstractAddRecordDialog(final Shell parentShell)
	{
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(final Composite parent)
	{
		setTitle(Messages.getString("dialog.addrecord.title"));
		setMessage(Messages.getString("dialog.addrecord.description"), IMessageProvider.INFORMATION);
		setHelpAvailable(false);

		final Composite area = (Composite) super.createDialogArea(parent);
		final Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		final GridLayout layout = new GridLayout(1, false);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(layout);

		createInnerArea(container);

		return area;
	}

	/**
	 * Creates the inner content of the dialog.
	 * <p>The implementation should create the UI component.</p>
	 * @param parent the parent composite
	 */
	protected abstract void createInnerArea(final Composite parent);

	/**
	 * Called when the OK button is pressed. The implementation should store the state of the UI
	 * component into variables and making them available by getter method.
	 */
	protected abstract void processFormContent();

	@Override
	protected final void okPressed()
	{
		processFormContent();
		super.okPressed();
	}
}
