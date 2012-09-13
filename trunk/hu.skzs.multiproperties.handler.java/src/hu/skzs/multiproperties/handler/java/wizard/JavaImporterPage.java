package hu.skzs.multiproperties.handler.java.wizard;

import hu.skzs.multiproperties.handler.java.Messages;

import java.io.File;

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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The {@link JavaImporterPage} lets the user select a properties file.
 * @author sallaikr
 *
 */
public class JavaImporterPage extends WizardPage
{

	private static final String FILE_FILTER_PROP = "*.properties"; //$NON-NLS-1$
	private static final String FILE_FILTER_ALL = "*.*"; //$NON-NLS-1$

	private Text textFile;

	public JavaImporterPage()
	{
		super("importer.file.selection"); //$NON-NLS-1$
		setTitle(Messages.getString("wizard.importer.title")); //$NON-NLS-1$
		setDescription(Messages.getString("wizard.importer.description")); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(final Composite parent)
	{
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		container.setLayout(layout);

		final Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("general.filename")); //$NON-NLS-1$
		textFile = new Text(container, SWT.BORDER | SWT.SINGLE);
		textFile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textFile.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent e)
			{
				dialogChanged();
			}
		});

		final Button button = new Button(container, SWT.PUSH);
		button.setText(Messages.getString("general.browse")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				handleBrowse();
			}
		});

		setPageComplete(false);
	}

	/**
	 * Uses the standard file selection dialog to choose a properties file.
	 */
	private void handleBrowse()
	{
		final FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
		fileDialog.setText(Messages.getString("wizard.importer.title")); //$NON-NLS-1$
		final String[] filterExt = { FILE_FILTER_PROP, FILE_FILTER_ALL };
		fileDialog.setFilterExtensions(filterExt);
		final String filename = fileDialog.open();
		if (filename != null)
		{
			textFile.setText(filename);
			dialogChanged();
		}
	}

	/**
	 * Ensures that text field is set properly.
	 */
	private void dialogChanged()
	{
		if (textFile.getText().trim().equals("")) //$NON-NLS-1$
		{
			updateStatus(Messages.getString("wizard.importer.error.emptyfilename")); //$NON-NLS-1$
			return;
		}

		final File file = new File(textFile.getText());
		if (!file.exists())
		{
			updateStatus(Messages.getString("wizard.importer.error.mustexists")); //$NON-NLS-1$
			return;
		}
		if (!file.isFile())
		{
			updateStatus(Messages.getString("wizard.importer.error.validfile")); //$NON-NLS-1$
			return;
		}
		if (!file.canRead())
		{
			updateStatus(Messages.getString("wizard.importer.error.readable")); //$NON-NLS-1$
			return;
		}
		updateStatus(null);
	}

	/**
	 * Updates the wizard dialog error message to the given error message.
	 * <p>When <code>message</code> is <code>null</code>, then the previous error message is
	 * removed and the page is being complete, otherwise it is incomplete.</p>
	 * @param message the given error message or <code>null</code>
	 */
	private void updateStatus(final String message)
	{
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	/**
	 * Returns the selected file name.
	 * @return the selected file name
	 */
	public String getFileName()
	{
		return textFile.getText();
	}
}
