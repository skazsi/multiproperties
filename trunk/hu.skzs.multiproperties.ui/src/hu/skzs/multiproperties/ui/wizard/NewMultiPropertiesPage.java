package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class NewMultiPropertiesPage extends WizardPage
{
	private Text location;
	private Text file;
	private Text name;
	private Text description;
	private final ISelection selection;

	public NewMultiPropertiesPage(final ISelection selection)
	{
		super("multiproperties.new.general"); //$NON-NLS-1$
		setTitle(Messages.getString("wizard.new.title")); //$NON-NLS-1$
		setDescription(Messages.getString("wizard.new.general.desc")); //$NON-NLS-1$
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(final Composite parent)
	{
		final Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(LayoutFactory.createGridLayout(3));

		// Location
		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("wizard.new.general.location")); //$NON-NLS-1$
		location = new Text(container, SWT.BORDER | SWT.SINGLE);
		location.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		location.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				validate();
			}
		});
		final Button button = new Button(container, SWT.PUSH);
		button.setText(Messages.getString("wizard.new.general.browse")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				handleBrowse();
			}
		});

		// Filename
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("wizard.new.general.filename")); //$NON-NLS-1$
		file = new Text(container, SWT.BORDER | SWT.SINGLE);
		file.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		file.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				validate();
			}
		});
		new Label(container, SWT.NONE);

		LayoutFactory.createSeparatorInGrid(container, 3);

		// Name

		label = new Label(container, SWT.NULL);
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
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize()
	{
		if (selection != null && selection.isEmpty() == false && selection instanceof IStructuredSelection)
		{
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			final Object obj = ssel.getFirstElement();
			if (obj instanceof IResource)
			{
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				location.setText(container.getFullPath().toString());
			}
		}
		file.setText("file.multiproperties"); //$NON-NLS-1$
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse()
	{
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace()
				.getRoot(), false, Messages.getString("wizard.new.general.browse.title")); //$NON-NLS-1$
		if (dialog.open() == ContainerSelectionDialog.OK)
		{
			final Object[] result = dialog.getResult();
			if (result.length == 1)
			{
				location.setText(((Path) result[0]).toString());
				validate();
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void validate()
	{
		setErrorMessage(null);
		setPageComplete(true);

		final IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getLocation()));

		// Container
		if (location.getText().length() == 0)
		{
			setPageComplete(false);
		}
		else if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0)
		{
			setErrorMessage(Messages.getString("wizard.new.general.error.location.nonexists")); //$NON-NLS-1$
			setPageComplete(false);
		}
		else if (!container.isAccessible())
		{
			setErrorMessage(Messages.getString("wizard.new.general.error.location.nonaccessible")); //$NON-NLS-1$
			setPageComplete(false);
		}

		// Filename
		if (file.getText().length() == 0)
		{
			setPageComplete(false);
		}
		else if (file.getText().replace('\\', '/').indexOf('/', 1) > 0)
		{
			if (getErrorMessage() == null)
				setErrorMessage(Messages.getString("wizard.new.general.error.filename.nonvalid")); //$NON-NLS-1$
			setPageComplete(false);
		}
		else if (file.getText().lastIndexOf('.') != -1)
		{
			if (!file.getText().substring(file.getText().lastIndexOf('.') + 1).equalsIgnoreCase("multiproperties")) //$NON-NLS-1$
			{
				if (getErrorMessage() == null)
					setErrorMessage(Messages.getString("wizard.new.general.error.filename.invalidext")); //$NON-NLS-1$
				setPageComplete(false);
			}
		}

		// Name
		if (name.getText().length() == 0)
		{
			setPageComplete(false);
		}
	}

	public String getLocation()
	{
		return location.getText();
	}

	public String getFileName()
	{
		if (!file.getText().toLowerCase().endsWith(".multiproperties")) //$NON-NLS-1$
			return file.getText() + ".multiproperties"; //$NON-NLS-1$
		return file.getText();
	}

	public String getMultiPropertiesName()
	{
		return name.getText();
	}

	public String getMultiPropertiesDescription()
	{
		return description.getText();
	}
}