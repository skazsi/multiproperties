package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.io.FileContentAccessor;
import hu.skzs.multiproperties.base.io.WorkspaceFileContentAccessor;
import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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

public class NewMultiPropertiesPage extends AbstractNewMultiPropertiesPage
{
	private Text location;
	private Text file;
	private final ISelection selection;

	public NewMultiPropertiesPage(final ISelection selection)
	{
		super();
		this.selection = selection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createFileControl(final Composite parent)
	{
		// Location
		Label label = new Label(parent, SWT.NULL);
		label.setText(Messages.getString("wizard.new.general.location")); //$NON-NLS-1$
		location = new Text(parent, SWT.BORDER | SWT.SINGLE);
		location.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		location.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				validate();
			}
		});
		final Button button = new Button(parent, SWT.PUSH);
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
		label = new Label(parent, SWT.NULL);
		label.setText(Messages.getString("wizard.new.general.filename")); //$NON-NLS-1$
		file = new Text(parent, SWT.BORDER | SWT.SINGLE);
		file.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		file.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				validate();
			}
		});
		new Label(parent, SWT.NONE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize()
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
		super.initialize();
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
	 * {@inheritDoc}
	 */
	@Override
	protected void validate()
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

		super.validate();
	}

	private String getLocation()
	{
		return location.getText();
	}

	private String getFileName()
	{
		if (!file.getText().toLowerCase().endsWith(".multiproperties")) //$NON-NLS-1$
			return file.getText() + ".multiproperties"; //$NON-NLS-1$
		return file.getText();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FileContentAccessor<?> getFileContentAccessor()
	{
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = root.findMember(new Path(getLocation()));
		final IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(getFileName()));

		return new WorkspaceFileContentAccessor(file);
	}

}