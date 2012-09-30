package hu.skzs.multiproperties.handler.text.wizard;

import hu.skzs.multiproperties.handler.text.Messages;
import hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

public class TextFileSelectionPage extends WizardPage
{

	private final WorkspaceConfigurator configurator;
	private Text textLocation;
	private Text textFile;

	public TextFileSelectionPage(final WorkspaceConfigurator configurator)
	{
		super("column.configuration.page"); //$NON-NLS-1$
		this.configurator = configurator;
		setTitle(Messages.getString("wizard.configuration.title")); //$NON-NLS-1$
		setDescription(Messages.getString("wizard.configuration.description")); //$NON-NLS-1$
	}

	public void createControl(final Composite parent)
	{
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		container.setLayout(layout);

		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("wizard.configuration.location")); //$NON-NLS-1$
		textLocation = new Text(container, SWT.BORDER | SWT.SINGLE);
		if (configurator.getContainerName() != null)
			textLocation.setText(configurator.getContainerName());
		textLocation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textLocation.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				dialogChanged();
			}
		});

		final Button button = new Button(container, SWT.PUSH);
		button.setText(Messages.getString("wizard.configuration.location.browse")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("wizard.configuration.filename")); //$NON-NLS-1$
		textFile = new Text(container, SWT.BORDER | SWT.SINGLE);
		if (configurator.getFileName() != null)
			textFile.setText(configurator.getFileName());
		textFile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textFile.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				dialogChanged();
			}
		});

		dialogChanged();
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */
	private void handleBrowse()
	{
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace()
				.getRoot(), false, Messages.getString("wizard.configuration.location.browse.description")); //$NON-NLS-1$
		if (dialog.open() == ContainerSelectionDialog.OK)
		{
			final Object[] result = dialog.getResult();
			if (result.length == 1)
			{
				textLocation.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged()
	{
		final IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getLocation()));
		final String fileName = getFileName();

		if (getLocation().length() == 0)
		{
			updateStatus(Messages.getString("wizard.configuration.error.mustspecified")); //$NON-NLS-1$
			return;
		}
		if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0)
		{
			updateStatus(Messages.getString("wizard.configuration.error.mustexists")); //$NON-NLS-1$
			return;
		}
		if (!container.isAccessible())
		{
			updateStatus(Messages.getString("wizard.configuration.error.mustwriteable")); //$NON-NLS-1$
			return;
		}
		if (fileName.length() == 0)
		{
			updateStatus(Messages.getString("wizard.configuration.error.validfile")); //$NON-NLS-1$
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0)
		{
			updateStatus(Messages.getString("wizard.configuration.error.validfilename")); //$NON-NLS-1$
			return;
		}
		updateStatus(null);
	}

	private void updateStatus(final String message)
	{
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getLocation()
	{
		return textLocation.getText();
	}

	public String getFileName()
	{
		return textFile.getText();
	}
}
