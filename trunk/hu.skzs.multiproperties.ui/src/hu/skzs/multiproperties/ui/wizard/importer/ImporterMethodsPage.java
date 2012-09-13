package hu.skzs.multiproperties.ui.wizard.importer;

import hu.skzs.multiproperties.base.api.IImporter;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * The {@link ImporterMethodsPage} wizard page gives the possibility to select the appropriate method for importing:
 * <ul>
 * <li>Importing the structure, including the property, comment and empty lines.</li>
 * <li>Importing only the key-value pairs only. The previously non-existing keys will be added to the end.</li>
 * <li>Importing just the values.</li>
 * </ul>
 * @author sallai
 */
public class ImporterMethodsPage extends WizardPage
{

	private static final int WIDTH_HINT = 500;
	private final Column column;
	private Button importStructureRadio;
	private Button importKeyValueRadio;
	private Button importValue;

	/**
	 * Default constructor.
	 * <p>If the <code>column</code> parameters is not <code>null</code> then the <strong>key-value</strong>
	 * and the <strong>value</strong> methods can be also selected, otherwise cannot.</p>
	 * @param column the given column
	 */
	public ImporterMethodsPage(final Column column)
	{
		super("import.method.page"); //$NON-NLS-1$
		this.column = column;
		setTitle(Messages.getString("wizard.import.method.title")); //$NON-NLS-1$
		setDescription(Messages.getString("wizard.import.method.description")); //$NON-NLS-1$
		setPageComplete(false);
	}

	public void createControl(final Composite parent)
	{
		// Container
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		setControl(composite);

		importStructureRadio = createRadio(composite, Messages.getString("wizard.import.method.structrure")); //$NON-NLS-1$
		createDescriptionText(composite, Messages.getString("wizard.import.method.structrure.description")); //$NON-NLS-1$

		createSpacer(composite);

		importKeyValueRadio = createRadio(composite, Messages.getString("wizard.import.method.keyvalue")); //$NON-NLS-1$
		if (column == null)
			importKeyValueRadio.setEnabled(false);
		createDescriptionText(composite, Messages.getString("wizard.import.method.keyvalue.description")); //$NON-NLS-1$

		createSpacer(composite);

		importValue = createRadio(composite, Messages.getString("wizard.import.method.value")); //$NON-NLS-1$
		if (column == null)
			importValue.setEnabled(false);
		createDescriptionText(composite, Messages.getString("wizard.import.method.value.description")); //$NON-NLS-1$
	}

	@Override
	public boolean isPageComplete()
	{
		if (importStructureRadio.getSelection())
			return true;
		if (importKeyValueRadio.getSelection())
			return true;
		if (importValue.getSelection())
			return true;
		return false;

	}

	/**
	 * Returns the selected method.
	 * @return the selected method
	 * @see IImporter#METHOD_STRUCTURAL
	 * @see IImporter#METHOD_KEY_VALUE
	 * @see IImporter#METHOD_VALUE
	 */
	public int getSelectedMethod()
	{
		if (importStructureRadio.getSelection())
			return IImporter.METHOD_STRUCTURAL;
		if (importKeyValueRadio.getSelection())
			return IImporter.METHOD_KEY_VALUE;
		if (importValue.getSelection())
			return IImporter.METHOD_VALUE;
		throw new RuntimeException("Unimplemented method option"); //$NON-NLS-1$

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
