package hu.skzs.multiproperties.handler.text.preference;

import hu.skzs.multiproperties.handler.text.Messages;
import hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
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

/**
 * The {@link OutputFilePreferencePage} offers specifying the target output file.
 * @author sallai
 */
public class OutputFilePreferencePage extends PreferencePage
{

	private Text textLocation;
	private Text textFile;
	private final WorkspaceConfigurator configurator;
	private EncodingFieldEditor encodingEditor;

	/**
	 * Default constructor. Sets the preference store for the {@link FieldEditorPreferencePage} and
	 * set the description of the preference page.
	 */
	public OutputFilePreferencePage(final WorkspaceConfigurator configurator)
	{
		super();
		this.configurator = configurator;
		setTitle(Messages.getString("preference.output.title")); //$NON-NLS-1$
		setDescription(Messages.getString("preference.output.description")); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(final Composite parent)
	{
		final Composite composite = createComposite(parent);

		// Container
		Label label = new Label(composite, SWT.NULL);
		label.setText(Messages.getString("preference.output.location")); //$NON-NLS-1$
		textLocation = new Text(composite, SWT.BORDER | SWT.SINGLE);
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
		final Button button = new Button(composite, SWT.PUSH);
		button.setText(Messages.getString("preference.output.location.browse")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				handleBrowse();
			}
		});

		// Filename
		label = new Label(composite, SWT.NULL);
		label.setText(Messages.getString("preference.output.filename")); //$NON-NLS-1$
		textFile = new Text(composite, SWT.BORDER | SWT.SINGLE);
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

		// Encoding group
		encodingEditor = new EncodingFieldEditor(configurator, parent);
		encodingEditor.load();
		encodingEditor.setPropertyChangeListener(new IPropertyChangeListener()
		{
			/* (non-Javadoc)
			 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
			 */
			public void propertyChange(final PropertyChangeEvent event)
			{
				dialogChanged();
			}
		});

		// Updating the status
		dialogChanged();

		return composite;
	}

	/**
	 * Creates the composite which will contain all the preference controls for
	 * this page.
	 * @param parent the parent composite
	 * @return the composite for this page
	 */
	private Composite createComposite(final Composite parent)
	{
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return composite;
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */
	private void handleBrowse()
	{
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace()
				.getRoot(), false, Messages.getString("preference.output.location.browse.description")); //$NON-NLS-1$
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
		final IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(textLocation.getText()));
		final String fileName = textFile.getText();

		if (textLocation.getText().length() == 0)
		{
			updateStatus(Messages.getString("preference.output.error.mustspecified")); //$NON-NLS-1$
			return;
		}
		if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0)
		{
			updateStatus(Messages.getString("preference.output.error.mustexists")); //$NON-NLS-1$
			return;
		}
		if (!container.isAccessible())
		{
			updateStatus(Messages.getString("preference.output.error.mustwriteable")); //$NON-NLS-1$
			return;
		}
		if (fileName.length() == 0)
		{
			updateStatus(Messages.getString("preference.output.error.validfile")); //$NON-NLS-1$
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0)
		{
			updateStatus(Messages.getString("preference.output.error.validfilename")); //$NON-NLS-1$
			return;
		}
		if (!encodingEditor.isValid())
		{
			updateStatus(Messages.getString("preference.output.error.invalid.encoding")); //$NON-NLS-1$
			return;
		}

		updateStatus(null);
	}

	private void updateStatus(final String message)
	{
		setErrorMessage(message);
		setValid(message == null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults()
	{
		textLocation.setText(""); //$NON-NLS-1$
		textFile.setText(""); //$NON-NLS-1$
		encodingEditor.loadDefault();
		super.performDefaults();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk()
	{
		configurator.setContainerName(textLocation.getText());
		configurator.setFileName(textFile.getText());
		encodingEditor.store();
		return super.performOk();
	}
}
