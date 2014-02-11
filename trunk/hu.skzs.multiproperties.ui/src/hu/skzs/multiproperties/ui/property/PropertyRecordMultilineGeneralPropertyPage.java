package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.jface.dialogs.DialogPage;
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

public class PropertyRecordMultilineGeneralPropertyPage extends AbstractRecordPropertyPage<PropertyRecord>
{
	private Text keyText;
	private Button defaultValueBox;
	private Text defaultValueText;
	private Button disabled;

	/**
	 * Constructor
	 */
	public PropertyRecordMultilineGeneralPropertyPage()
	{
		super();
		setTitle(Messages.getString("property.property.general.title")); //$NON-NLS-1$
		setDescription(Messages.getString("property.property.general.description")); //$NON-NLS-1$
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	@Override
	protected Control createContents(final Composite parent)
	{
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());

		createGeneralContent(composite);

		updateFromModel();

		return composite;
	}

	/**
	 * Creates the key, default value and disabled SWT widgets
	 * @param parent the parent {@link Composite}
	 */
	private void createGeneralContent(final Composite parent)
	{
		// Key group
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(LayoutFactory.createGridLayout(3));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

		// Key
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.getString("property.property.general.key")); //$NON-NLS-1$
		keyText = new Text(composite, SWT.BORDER);
		keyText.setLayoutData(LayoutFactory.createGridData(true, false, 2));
		keyText.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent arg0)
			{
				checkKey();
			}
		});

		// Disabled
		label = new Label(composite, SWT.NONE);
		disabled = new Button(composite, SWT.CHECK);
		disabled.setText(Messages.getString("property.property.general.disabled")); //$NON-NLS-1$

		// Default value
		LayoutFactory.createSpaceInGrid(composite, 3);
		defaultValueBox = new Button(composite, SWT.CHECK);
		defaultValueBox.setLayoutData(LayoutFactory.createGridData(true, false, 3));
		defaultValueBox.setText(Messages.getString("property.property.general.defaultvalue")); //$NON-NLS-1$
		defaultValueBox.addSelectionListener(new SelectionAdapter()
		{

			@Override
			public void widgetSelected(final SelectionEvent event)
			{
				defaultValueText.setEnabled(defaultValueBox.getSelection());
				if (!defaultValueBox.getSelection())
					defaultValueText.setText(""); //$NON-NLS-1$
			}
		});
		defaultValueText = new Text(composite, SWT.BORDER | SWT.MULTI);
		defaultValueText.setLayoutData(LayoutFactory.createGridData(true, true, 3));
	}

	/**
	 * Checks the property key for missing values or duplications
	 */
	private void checkKey()
	{
		if (keyText.getText().trim().equals("")) //$NON-NLS-1$
		{
			setMessage(Messages.getString("property.property.general.warning.keyempty"), DialogPage.WARNING); //$NON-NLS-1$
			return;
		}

		int counter = 0;
		int position = -1;
		while ((position = table.indexOf(keyText.getText().trim(), position + 1)) != -1)
			if (record == null || position != table.indexOf(record))
			{
				counter++;
				break;
			}
		if (counter > 0)
		{
			setMessage(Messages.getString("property.property.general.warning.keyexists"), DialogPage.WARNING); //$NON-NLS-1$
			return;
		}
		setMessage(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateFromModel()
	{
		keyText.setText(record.getValue());
		disabled.setSelection(record.isDisabled());
		defaultValueBox.setSelection(record.getDefaultColumnValue() != null);
		if (record.getDefaultColumnValue() != null)
		{
			defaultValueText.setEnabled(true);
			defaultValueText.setText(record.getDefaultColumnValue());
		}
		else
		{
			defaultValueText.setEnabled(false);
			defaultValueText.setText(""); //$NON-NLS-1$
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeToModel()
	{
		record.setValue(keyText.getText());
		record.setDisabled(disabled.getSelection());
		if (defaultValueBox.getSelection())
		{
			record.setDefaultColumnValue(defaultValueText.getText());
		}
		else
		{
			record.setDefaultColumnValue(null);
		}
	}
}