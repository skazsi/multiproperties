package hu.skzs.multiproperties.handler.java.wizard;

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

public class TargetPropertiesSelectionPage extends WizardPage
{

	private final String target;
	private Text textLocation;
	private Text textFile;

	public TargetPropertiesSelectionPage(final String target)
	{
		super("target.properties.selection.page");
		this.target = target;
		setTitle("Target properties file");
		setDescription("Specify the target properties file.");
	}

	public void createControl(final Composite parent)
	{
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;

		Label label = new Label(container, SWT.NULL);
		label.setText("&Location:");
		textLocation = new Text(container, SWT.BORDER | SWT.SINGLE);
		if (target.lastIndexOf("/") != -1)
			textLocation.setText(target.substring(0, target.lastIndexOf("/")));
		textLocation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textLocation.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e)
			{
				dialogChanged();
			}
		});

		final Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&File name:");
		textFile = new Text(container, SWT.BORDER | SWT.SINGLE);
		if (target.lastIndexOf("/") != -1)
			textFile.setText(target.substring(target.lastIndexOf("/") + 1));
		textFile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textFile.addModifyListener(new ModifyListener() {
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
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false, "Select new file container");
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
			updateStatus("File location must be specified");
			return;
		}
		if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0)
		{
			updateStatus("File location must exist");
			return;
		}
		if (!container.isAccessible())
		{
			updateStatus("Project must be writable");
			return;
		}
		if (fileName.length() == 0)
		{
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0)
		{
			updateStatus("File name must be valid");
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
