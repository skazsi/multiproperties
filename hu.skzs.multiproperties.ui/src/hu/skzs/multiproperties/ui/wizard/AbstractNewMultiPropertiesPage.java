package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.io.FileContentAccessor;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public abstract class AbstractNewMultiPropertiesPage extends WizardPage
{
	private Text name;
	private Text description;

	public AbstractNewMultiPropertiesPage()
	{
		super("multiproperties.new.general"); //$NON-NLS-1$
		setTitle(Messages.getString("wizard.new.title")); //$NON-NLS-1$
		setDescription(Messages.getString("wizard.new.general.desc")); //$NON-NLS-1$
	}

	/**
	 * Create the file selection control
	 * @param parent the parent composite with 3 columns grid layout
	 */
	public abstract void createFileControl(Composite parent);

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(final Composite parent)
	{
		final Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(LayoutFactory.createGridLayout(3));

		createFileControl(container);

		LayoutFactory.createSeparatorInGrid(container, 3);

		// Name
		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("wizard.new.general.name")); //$NON-NLS-1$
		name = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		name.setLayoutData(gridData);
		name.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				validate();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("wizard.new.general.description")); //$NON-NLS-1$
		description = new Text(container, SWT.BORDER | SWT.MULTI);
		gridData = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		gridData.horizontalSpan = 2;
		description.setLayoutData(gridData);

		initialize();
		validate();
		setControl(container);
	}

	/**
	 * Initializes the current page.
	 */
	protected void initialize()
	{
	}

	/**
	 * Ensures that all text fields are set properly.
	 */
	protected void validate()
	{
		// Name
		if (name.getText().length() == 0)
		{
			setPageComplete(false);
		}
	}

	/**
	 * Returns the user specified name for the new MultiProperties.
	 * @return the user specified name for the new MultiProperties
	 */
	public String getMultiPropertiesName()
	{
		return name.getText();
	}

	/**
	 * Returns the user specified description for the new MultiProperties.
	 * @return the user specified description for the new MultiProperties
	 */
	public String getMultiPropertiesDescription()
	{
		return description.getText();
	}

	/**
	 * Returns a newly constructed instance of FileContentAccessor based on the user specified
	 * file.
	 * @return a newly constructed instance of FileContentAccessor
	 */
	public abstract FileContentAccessor<?> getFileContentAccessor();
}