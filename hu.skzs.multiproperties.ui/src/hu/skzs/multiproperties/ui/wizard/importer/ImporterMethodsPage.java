package hu.skzs.multiproperties.ui.wizard.importer;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.ImportMode;
import hu.skzs.multiproperties.ui.Messages;

public class ImporterMethodsPage extends WizardPage
{

	private static final int WIDTH_HINT = 500;
	private final Column column;
	private Button importStructureRadio;
	private Button importKeyValueRadio;
	private Button importValueRadio;

	public ImporterMethodsPage(final Column column)
	{
		super("import.method.page");
		this.column = column;
		setTitle(Messages.getString("wizard.import.method.title"));
		setDescription(Messages.getString("wizard.import.method.description"));
		setPageComplete(false);
	}

	public void createControl(final Composite parent)
	{
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		setControl(composite);

		importStructureRadio = createRadio(composite, Messages.getString("wizard.import.method.structrure"));
		createDescriptionText(composite, Messages.getString("wizard.import.method.structrure.description"));

		createSpacer(composite);

		importKeyValueRadio = createRadio(composite, Messages.getString("wizard.import.method.keyvalue"));
		if (column == null)
			importKeyValueRadio.setEnabled(false);
		createDescriptionText(composite, Messages.getString("wizard.import.method.keyvalue.description"));

		createSpacer(composite);

		importValueRadio = createRadio(composite, Messages.getString("wizard.import.method.value"));
		if (column == null)
			importValueRadio.setEnabled(false);
		createDescriptionText(composite, Messages.getString("wizard.import.method.value.description"));
	}

	@Override
	public boolean isPageComplete()
	{
		return importStructureRadio.getSelection() || importKeyValueRadio.getSelection()
				|| importValueRadio.getSelection();
	}

	public ImportMode getImportMode()
	{
		if (importStructureRadio.getSelection())
			return ImportMode.STRUCTURAL;
		if (importKeyValueRadio.getSelection())
			return ImportMode.KEY_VALUE;
		if (importValueRadio.getSelection())
			return ImportMode.VALUE;
		throw new RuntimeException("Unimplemented method option");

	}

	private Button createRadio(final Composite parent, final String label)
	{
		final Button radio = new Button(parent, SWT.RADIO);
		radio.setText(label);
		radio.setSelection(false);
		radio.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				setPageComplete(radio.getSelection());
			}
		});
		return radio;
	}

	private void createDescriptionText(final Composite parent, final String string)
	{
		createText(parent, string, 25);
	}

	private void createText(final Composite parent, final String string, final int indent)
	{
		final Label text = new Label(parent, SWT.WRAP);
		text.setText(string);
		final GridData layoutData = new GridData();
		layoutData.horizontalIndent = indent;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.widthHint = WIDTH_HINT;
		text.setLayoutData(layoutData);
	}

	private void createSpacer(final Composite parent)
	{
		final Label spacer = new Label(parent, SWT.NONE);
		final GridData layoutData = new GridData();
		layoutData.heightHint = 5;
		spacer.setLayoutData(layoutData);
	}

}
