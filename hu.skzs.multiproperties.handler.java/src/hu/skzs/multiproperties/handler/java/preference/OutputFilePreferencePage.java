package hu.skzs.multiproperties.handler.java.preference;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import hu.skzs.multiproperties.handler.java.Messages;
import hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator;
import hu.skzs.multiproperties.ui.util.EncodingSelector;

public class OutputFilePreferencePage extends PreferencePage
{
	private final WorkspaceConfigurator configurator;
	private boolean isCreatedPage;
	private Button enableOutputFile;
	private Button browseButton;
	private Text textLocation;
	private Text textFile;
	private Button checkDescription;
	private Button checkColumnDescription;
	private Button checkDisabled;
	private Button checkDisableDefault;
	private EncodingSelector encodingSelector;

	public OutputFilePreferencePage(final WorkspaceConfigurator configurator)
	{
		super();
		this.configurator = configurator;
		setTitle(Messages.getString("preference.output.title"));
		setDescription(Messages.getString("preference.output.description"));
	}

	private boolean isEnabled()
	{
		return configurator.getContainerName() != null && configurator.getFileName() != null;
	}

	@Override
	protected Control createContents(final Composite parent)
	{
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		createEnableCheckPanel(composite);
		createFilePanel(composite);
		createEncodingPanel(composite);
		createCheckboxesPanel(composite);

		// Updating the status
		dialogChanged();

		isCreatedPage = true;
		return composite;
	}

	private void createEnableCheckPanel(final Composite parent)
	{
		// Container
		enableOutputFile = new Button(parent, SWT.CHECK);
		enableOutputFile.setText(Messages.getString("preference.output.enabled"));
		enableOutputFile.setSelection(isEnabled());
		enableOutputFile.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				if (enableOutputFile.getSelection())
				{
					textLocation.setEnabled(true);
					browseButton.setEnabled(true);
					textFile.setEnabled(true);
					dialogChanged();
				}
				else
					performDefaults();
			}
		});
	}

	private void createFilePanel(final Composite parent)
	{
		// Composite
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		// Container
		Label label = new Label(composite, SWT.NULL);
		label.setText(Messages.getString("preference.output.location"));
		textLocation = new Text(composite, SWT.BORDER | SWT.SINGLE);
		if (configurator.getContainerName() != null)
			textLocation.setText(configurator.getContainerName());
		textLocation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textLocation.setEnabled(enableOutputFile.getSelection());
		textLocation.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				dialogChanged();
			}
		});
		browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText(Messages.getString("preference.output.location.browse"));
		browseButton.setEnabled(enableOutputFile.getSelection());
		browseButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				handleBrowse();
			}
		});
		// Filename
		label = new Label(composite, SWT.NULL);
		label.setText(Messages.getString("preference.output.filename"));
		textFile = new Text(composite, SWT.BORDER | SWT.SINGLE);
		if (configurator.getFileName() != null)
			textFile.setText(configurator.getFileName());
		textFile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textFile.setEnabled(enableOutputFile.getSelection());
		textFile.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				dialogChanged();
			}
		});
	}

	private void createCheckboxesPanel(final Composite parent)
	{
		checkDescription = new Button(parent, SWT.CHECK);
		checkDescription.setText(Messages.getString("preference.output.include.description"));
		checkDescription.setSelection(configurator.isDescriptionIncluded());
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		checkDescription.setLayoutData(gridData);
		checkColumnDescription = new Button(parent, SWT.CHECK);
		checkColumnDescription.setText(Messages.getString("preference.output.include.columndescription"));
		checkColumnDescription.setSelection(configurator.isColumnDescriptionIncluded());
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		checkColumnDescription.setLayoutData(gridData);
		checkDisabled = new Button(parent, SWT.CHECK);
		checkDisabled.setText(Messages.getString("preference.output.include.disabled"));
		checkDisabled.setSelection(configurator.isDisabledPropertiesIncluded());
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		checkDisabled.setLayoutData(gridData);
		checkDisableDefault = new Button(parent, SWT.CHECK);
		checkDisableDefault.setText(Messages.getString("preference.output.disable.defaultvalues"));
		checkDisableDefault.setSelection(configurator.isDisableDefaultValues());
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		checkDisableDefault.setLayoutData(gridData);
	}

	private void createEncodingPanel(final Composite parent)
	{
		final Composite encodingComposite = new Composite(parent, SWT.NONE);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		gridData.verticalIndent = 10;
		gridData.horizontalSpan = 3;
		encodingComposite.setLayoutData(gridData);
		encodingComposite.setLayout(new GridLayout());

		encodingSelector = new EncodingSelector(encodingComposite)
		{

			@Override
			public String getTitle()
			{
				return Messages.getString("preference.output.encoding.title");
			}

			@Override
			public String getDefaultEncodingLabel()
			{
				return Messages.getString("preference.output.encoding.default");
			}

			@Override
			public String getDefaultEncodingValue()
			{
				return "ISO-8859-1";
			}

			@Override
			public String getOtherEncodingLabel()
			{
				return Messages.getString("preference.output.encoding.other");
			}

			@Override
			public void onChange()
			{
				dialogChanged();
			}
		};
		encodingSelector.setEncoding(configurator.getEncoding());
	}

	private void handleBrowse()
	{
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(),
				ResourcesPlugin.getWorkspace().getRoot(), false,
				Messages.getString("preference.output.location.browse.description"));
		if (dialog.open() == ContainerSelectionDialog.OK)
		{
			final Object[] result = dialog.getResult();
			if (result.length == 1)
			{
				textLocation.setText(((Path) result[0]).toString());
			}
		}
	}

	private void dialogChanged()
	{
		if (enableOutputFile.getSelection())
		{
			final IResource container = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(new Path(textLocation.getText()));
			final String fileName = textFile.getText();

			if (textLocation.getText().length() == 0)
			{
				updateStatus(Messages.getString("preference.output.error.mustspecified"));
				return;
			}
			if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0)
			{
				updateStatus(Messages.getString("preference.output.error.mustexists"));
				return;
			}
			if (!container.isAccessible())
			{
				updateStatus(Messages.getString("preference.output.error.mustwriteable"));
				return;
			}
			if (fileName.length() == 0)
			{
				updateStatus(Messages.getString("preference.output.error.validfile"));
				return;
			}
			if (fileName.replace('\\', '/').indexOf('/', 1) > 0)
			{
				updateStatus(Messages.getString("preference.output.error.validfilename"));
				return;
			}
		}
		if (!encodingSelector.isEncodingValid())
		{
			updateStatus(Messages.getString("preference.output.error.invalid.encoding"));
			return;
		}

		updateStatus(null);
	}

	private void updateStatus(final String message)
	{
		setErrorMessage(message);
		setValid(message == null);
	}

	@Override
	protected void performDefaults()
	{
		enableOutputFile.setSelection(false);
		textLocation.setEnabled(false);
		textLocation.setText("");
		browseButton.setEnabled(false);
		textFile.setEnabled(false);
		textFile.setText("");
		checkDescription.setSelection(false);
		checkColumnDescription.setSelection(false);
		checkDisabled.setSelection(false);
		checkDisableDefault.setSelection(false);
		encodingSelector.setEncoding("ISO-8859-1");
		dialogChanged();
		super.performDefaults();
	}

	@Override
	public boolean performOk()
	{
		if (isCreatedPage)
		{
			if (enableOutputFile.getSelection())
			{
				configurator.setContainerName(textLocation.getText());
				configurator.setFileName(textFile.getText());
			}
			else
			{
				configurator.setContainerName(null);
				configurator.setFileName(null);
			}
			configurator.setIncludeDescription(checkDescription.getSelection());
			configurator.setIncludeColumnDescription(checkColumnDescription.getSelection());
			configurator.setIncludeDisabled(checkDisabled.getSelection());
			configurator.setDisableDefaultValues(checkDisableDefault.getSelection());
			configurator.setEncoding(encodingSelector.getEncoding());
		}
		return super.performOk();
	}

}
