package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PropertyRecordGeneralPropertyPage extends AbstractRecordPropertyPage<PropertyRecord>
{
	private Text keyText;
	private Button defaultValueBox;
	private Text defaultValueText;
	private Button disabled;
	private Button[] checkboxes;
	private Text[] valueFields;
	private Button[] fillCheckedButtons;
	private Button[] fillAllButtons;

	/**
	 * Constructor
	 */
	public PropertyRecordGeneralPropertyPage()
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
		final Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(LayoutFactory.createGridLayout(1, 0, 0));

		createKeyContent(composite);
		createColumnValuesGroup(composite);

		updateFromModel();

		return composite;
	}

	/**
	 * Creates the key, default value and disabled SWT widgets
	 * @param parent the parent {@link Composite}
	 */
	private void createKeyContent(final Composite parent)
	{
		// Key group
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(LayoutFactory.createGridLayout(3));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		// Key
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.getString("property.property.general.key")); //$NON-NLS-1$
		keyText = new Text(composite, SWT.BORDER);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		gridData.horizontalSpan = 2;
		keyText.setLayoutData(gridData);
		keyText.addModifyListener(new ModifyListener()
		{

			public void modifyText(final ModifyEvent arg0)
			{
				checkKey();
			}
		});
		// Default value
		label = new Label(composite, SWT.NONE);
		label.setText(""); //$NON-NLS-1$
		defaultValueBox = new Button(composite, SWT.CHECK);
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
		defaultValueText = new Text(composite, SWT.BORDER);
		defaultValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		defaultValueText.addModifyListener(new ModifyListener()
		{
			public void modifyText(final ModifyEvent arg0)
			{
				for (int i = 0; i < table.getColumns().size(); i++)
				{
					if (!valueFields[i].getEnabled())
						valueFields[i].setText(defaultValueText.getText());
				}
			}
		});

		// Disabled
		label = new Label(composite, SWT.NONE);
		disabled = new Button(composite, SWT.CHECK);
		disabled.setText(Messages.getString("property.property.general.disabled")); //$NON-NLS-1$
	}

	/**
	 * Creates the values SWT widgets
	 * @param parent the parent {@link Composite}
	 */
	private void createColumnValuesGroup(final Composite parent)
	{
		final Group valuesGroup = new Group(parent, SWT.NONE);
		valuesGroup.setText(Messages.getString("property.property.values")); //$NON-NLS-1$
		valuesGroup.setLayout(new FillLayout());
		valuesGroup.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL));
		final ScrolledComposite scrolledComposite = new ScrolledComposite(valuesGroup, SWT.H_SCROLL | SWT.V_SCROLL);
		final Composite valuesComposite = new Composite(scrolledComposite, SWT.NONE);
		valuesComposite.setLayout(new GridLayout(5, false));
		checkboxes = new Button[table.getColumns().size()];
		valueFields = new Text[table.getColumns().size()];
		fillCheckedButtons = new Button[table.getColumns().size()];
		fillAllButtons = new Button[table.getColumns().size()];
		for (int i = 0; i < table.getColumns().size(); i++)
		{
			final Label label = new Label(valuesComposite, SWT.NONE);
			label.setText(table.getColumns().get(i).getName());
			checkboxes[i] = new Button(valuesComposite, SWT.CHECK);
			checkboxes[i].setData(new Integer(i));
			valueFields[i] = new Text(valuesComposite, SWT.BORDER);
			valueFields[i].setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			fillCheckedButtons[i] = new Button(valuesComposite, SWT.PUSH | SWT.FLAT);
			fillCheckedButtons[i].setImage(Activator.getDefault().getImageRegistry().get("fill_checked")); //$NON-NLS-1$
			fillCheckedButtons[i].setToolTipText(Messages.getString("property.property.fill.checked")); //$NON-NLS-1$
			fillCheckedButtons[i].setData(new Integer(i));
			fillAllButtons[i] = new Button(valuesComposite, SWT.PUSH | SWT.FLAT);
			fillAllButtons[i].setImage(Activator.getDefault().getImageRegistry().get("fill_all")); //$NON-NLS-1$
			fillAllButtons[i].setToolTipText(Messages.getString("property.property.fill.all")); //$NON-NLS-1$
			fillAllButtons[i].setData(new Integer(i));
			checkboxes[i].addSelectionListener(new SelectionAdapter()
			{

				@Override
				public void widgetSelected(final SelectionEvent event)
				{
					final Button checkbox = (Button) event.getSource();
					final int index = ((Integer) checkbox.getData()).intValue();
					valueFields[index].setEnabled(checkbox.getSelection());
					if (checkbox.getSelection())
						valueFields[index].setText(""); //$NON-NLS-1$
					else
						valueFields[index].setText(defaultValueText.getText());
					fillCheckedButtons[index].setEnabled(checkbox.getSelection());
				}
			});
			fillCheckedButtons[i].addSelectionListener(new SelectionAdapter()
			{

				@Override
				public void widgetSelected(final SelectionEvent event)
				{
					final Button checkbox = (Button) event.getSource();
					final int index = ((Integer) checkbox.getData()).intValue();
					for (int i = 0; i < table.getColumns().size(); i++)
					{
						if (i == index)
							continue;
						if (!valueFields[i].getEnabled())
							continue;
						valueFields[i].setText(valueFields[index].getText());
					}
				}
			});
			fillAllButtons[i].addSelectionListener(new SelectionAdapter()
			{

				@Override
				public void widgetSelected(final SelectionEvent event)
				{
					final Button checkbox = (Button) event.getSource();
					final int index = ((Integer) checkbox.getData()).intValue();
					for (int i = 0; i < table.getColumns().size(); i++)
					{
						if (i == index)
							continue;
						checkboxes[i].setSelection(checkboxes[index].getSelection());
						valueFields[i].setText(valueFields[index].getText());
						valueFields[i].setEnabled(valueFields[index].getEnabled());
						fillCheckedButtons[i].setEnabled(checkboxes[index].getSelection());
					}
				}
			});
		}
		scrolledComposite.setContent(valuesComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.addControlListener(new ControlAdapter()
		{

			@Override
			public void controlResized(final ControlEvent e)
			{
				final Rectangle r = scrolledComposite.getClientArea();
				scrolledComposite.setMinSize(valuesComposite.computeSize(r.width, SWT.DEFAULT));
			}
		});

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

		for (int i = 0; i < table.getColumns().size(); i++)
		{
			if (record != null && record.getColumnValue(table.getColumns().get(i)) != null)
			{
				checkboxes[i].setSelection(true);
				valueFields[i].setEnabled(true);
				valueFields[i].setText(record.getColumnValue(table.getColumns().get(i)));
				fillCheckedButtons[i].setEnabled(true);
			}
			else
			{
				checkboxes[i].setSelection(false);
				valueFields[i].setEnabled(false);
				valueFields[i].setText(defaultValueText.getText());
				fillCheckedButtons[i].setEnabled(false);
			}
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

		for (int i = 0; i < table.getColumns().size(); i++)
		{
			if (checkboxes[i].getSelection())
				record.putColumnValue(table.getColumns().get(i), valueFields[i].getText());
			else
				record.removeColumnValue(table.getColumns().get(i));
		}
	}
}