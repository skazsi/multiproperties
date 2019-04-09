package hu.skzs.multiproperties.handler.java.wizard;

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

import hu.skzs.multiproperties.handler.java.Messages;
import hu.skzs.multiproperties.ui.util.EncodingSelector;

public class JavaImporterPage extends WizardPage
{
	private static final String FILE_FILTER_PROP = "*.properties";
	private static final String FILE_FILTER_ALL = "*.*";

	private Text textFile;
	private EncodingSelector encodingSelector;

	public JavaImporterPage()
	{
		super("importer.file.selection");
		setTitle(Messages.getString("wizard.importer.title"));
		setDescription(Messages.getString("wizard.importer.description"));
	}

	public void createControl(final Composite parent)
	{
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		container.setLayout(layout);

		final Label label = new Label(container, SWT.NULL);
		label.setText(Messages.getString("general.filename"));
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
		button.setText(Messages.getString("general.browse"));
		button.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				handleBrowse();
			}
		});

		final Composite encodingComposite = new Composite(container, SWT.NONE);
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
				return Messages.getString("wizard.importer.encoding.title");
			}

			@Override
			public String getDescription()
			{
				return Messages.getString("wizard.importer.encoding.description");
			}

			@Override
			public String getDefaultEncodingLabel()
			{
				return Messages.getString("wizard.importer.encoding.default");
			}

			@Override
			public String getDefaultEncodingValue()
			{
				return "ISO-8859-1";
			}

			@Override
			public String getOtherEncodingLabel()
			{
				return Messages.getString("wizard.importer.encoding.other");
			}

			@Override
			public void onChange()
			{
				dialogChanged();
			}
		};

		setPageComplete(false);
	}

	private void handleBrowse()
	{
		final FileDialog fileDialog = new FileDialog(getShell(), SWT.OPEN);
		fileDialog.setText(Messages.getString("wizard.importer.title"));
		final String[] filterExt = { FILE_FILTER_PROP, FILE_FILTER_ALL };
		fileDialog.setFilterExtensions(filterExt);
		final String filename = fileDialog.open();
		if (filename != null)
		{
			textFile.setText(filename);
			dialogChanged();
		}
	}

	private void dialogChanged()
	{
		if (textFile.getText().trim().equals(""))
		{
			updateStatus(Messages.getString("wizard.importer.error.emptyfilename"));
			return;
		}

		final File file = new File(textFile.getText());
		if (!file.exists())
		{
			updateStatus(Messages.getString("wizard.importer.error.mustexists"));
			return;
		}
		if (!file.isFile())
		{
			updateStatus(Messages.getString("wizard.importer.error.validfile"));
			return;
		}
		if (!file.canRead())
		{
			updateStatus(Messages.getString("wizard.importer.error.readable"));
			return;
		}
		if (!encodingSelector.isEncodingValid())
		{
			updateStatus(Messages.getString("wizard.importer.error.invalidencoding"));
			return;
		}
		updateStatus(null);
	}

	private void updateStatus(final String message)
	{
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getFileName()
	{
		return textFile.getText();
	}

	public String getEncoding()
	{
		return encodingSelector.getEncoding();
	}
}
