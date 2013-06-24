package hu.skzs.multiproperties.rcp.wizard;

import hu.skzs.multiproperties.base.io.FileContentAccessor;
import hu.skzs.multiproperties.base.io.FileSystemFileContentAccessor;
import hu.skzs.multiproperties.rcp.Messages;
import hu.skzs.multiproperties.ui.wizard.AbstractNewMultiPropertiesPage;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewMultiPropertiesPage extends AbstractNewMultiPropertiesPage
{
	private Text file;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createFileControl(final Composite parent)
	{
		// File
		Label label = new Label(parent, SWT.NULL);
		label.setText(Messages.getString("wizard.new.general.file")); //$NON-NLS-1$
		file = new Text(parent, SWT.BORDER | SWT.SINGLE);
		file.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		file.addModifyListener(new ModifyListener()
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
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */
	private void handleBrowse()
	{
		// Selecting an input file
		FileDialog fileDialog = new FileDialog(getShell());
		fileDialog.setText(Messages.getString("wizard.new.general.browse.title")); //$NON-NLS-1$
		fileDialog.setFilterExtensions(new String[] { "*.multiproperties" }); //$NON-NLS-1$
		fileDialog.setFilterNames(new String[] { "*.multiproperties" }); //$NON-NLS-1$
		String selected = fileDialog.open();

		if (selected != null)
		{
			file.setText(selected);
			validate();
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

		// FIXME: missing validation
		//		final IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getLocation()));
		//
		//		// Container
		//		if (location.getText().length() == 0)
		//		{
		//			setPageComplete(false);
		//		}
		//		else if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0)
		//		{
		//			setErrorMessage(Messages.getString("wizard.new.general.error.location.nonexists")); //$NON-NLS-1$
		//			setPageComplete(false);
		//		}
		//		else if (!container.isAccessible())
		//		{
		//			setErrorMessage(Messages.getString("wizard.new.general.error.location.nonaccessible")); //$NON-NLS-1$
		//			setPageComplete(false);
		//		}
		//
		//		// Filename
		//		if (file.getText().length() == 0)
		//		{
		//			setPageComplete(false);
		//		}
		//		else if (file.getText().replace('\\', '/').indexOf('/', 1) > 0)
		//		{
		//			if (getErrorMessage() == null)
		//				setErrorMessage(Messages.getString("wizard.new.general.error.filename.nonvalid")); //$NON-NLS-1$
		//			setPageComplete(false);
		//		}
		//		else if (file.getText().lastIndexOf('.') != -1)
		//		{
		//			if (!file.getText().substring(file.getText().lastIndexOf('.') + 1).equalsIgnoreCase("multiproperties")) //$NON-NLS-1$
		//			{
		//				if (getErrorMessage() == null)
		//					setErrorMessage(Messages.getString("wizard.new.general.error.filename.invalidext")); //$NON-NLS-1$
		//				setPageComplete(false);
		//			}
		//		}

		super.validate();
	}

	//	private String getFileName()
	//	{
	//		if (!file.getText().toLowerCase().endsWith(".multiproperties")) //$NON-NLS-1$
	//			return file.getText() + ".multiproperties"; //$NON-NLS-1$
	//		return file.getText();
	//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FileContentAccessor<?> getFileContentAccessor()
	{
		return new FileSystemFileContentAccessor(new File(file.getText()));
	}

}