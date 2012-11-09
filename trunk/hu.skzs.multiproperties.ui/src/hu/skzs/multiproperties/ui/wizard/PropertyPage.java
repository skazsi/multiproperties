package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.WizardPage;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The <code>PropertyPage</code> wizard page is able to edit an already existing or a newly created {@link PropertyRecord}.
 * <p>The {@link PropertyPage#PropertyPage(PropertyRecord, Table)} constructor receives a {@link PropertyRecord} instance.
 * Passing <code>null</code> value means this wizard page is used in <strong>new</strong> mode, while an instance means 
 * the wizard page will be initiated based on the given instance resulting <strong>edit</strong> mode.</p>
 * @author skzs
 */
public class PropertyPage extends WizardPage
{

	private final PropertyRecord record;
	private final Table table;

	// Form fields
	private Text keyText;
	private Button defaultValueBox;
	private Text defaultValueText;
	private Button disabled;
	private Button[] checkboxes;
	private Text[] valueFields;
	private Button[] fillCheckedButtons;
	private Button[] fillAllButtons;
	private Text description;

	/**
	 * Default constructor.
	 * <p>If the given <code>propertyRecord</code> is <code>null</code>,
	 * it means this wizard page is used in <strong>new</strong> mode. Otherwise the wizard page
	 * will be initiated based on the given instance resulting <strong>edit</strong> mode.</p>
	 * @param propertyRecord the given property record
	 * @param table the given table
	 */
	public PropertyPage(final PropertyRecord propertyRecord, final Table table)
	{
		super("property.page"); //$NON-NLS-1$
		this.record = propertyRecord;
		this.table = table;
		if (propertyRecord == null)
		{
			setTitle(Messages.getString("wizard.property.add.title")); //$NON-NLS-1$
			setDescription(Messages.getString("wizard.property.add.description")); //$NON-NLS-1$
		}
		else
		{
			setTitle(Messages.getString("wizard.property.edit.title")); //$NON-NLS-1$
			setDescription(Messages.getString("wizard.property.edit.description")); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(final Composite parent)
	{
		// Container
		final Composite composite = new Composite(parent, SWT.NULL);
		setControl(composite);
		composite.setLayout(LayoutFactory.createGridLayout(1, 10, 10));

		createKeyContent(composite);
		createColumnValuesGroup(composite);
		createDescriptionGroup(composite);
		check();
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
		label.setText(Messages.getString("wizard.property.key")); //$NON-NLS-1$
		keyText = new Text(composite, SWT.BORDER);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		gridData.horizontalSpan = 2;
		keyText.setLayoutData(gridData);
		if (record != null)
			keyText.setText(record.getValue());
		keyText.addModifyListener(new ModifyListener()
		{

			public void modifyText(final ModifyEvent arg0)
			{
				check();
			}
		});
		// Default value
		label = new Label(composite, SWT.NONE);
		label.setText(""); //$NON-NLS-1$
		defaultValueBox = new Button(composite, SWT.CHECK);
		defaultValueBox.setSelection(record != null && record.getDefaultColumnValue() != null);
		defaultValueBox.setText(Messages.getString("wizard.property.defaultvalue")); //$NON-NLS-1$
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
		if (record != null && record.getDefaultColumnValue() != null)
		{
			defaultValueText.setEnabled(true);
			defaultValueText.setText(record.getDefaultColumnValue());
		}
		else
		{
			defaultValueText.setEnabled(false);
			defaultValueText.setText(""); //$NON-NLS-1$
		}
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
		disabled.setText(Messages.getString("wizard.property.disabled")); //$NON-NLS-1$
		if (record != null)
			disabled.setSelection(record.isDisabled());
	}

	/**
	 * Creates the values SWT widgets
	 * @param parent the parent {@link Composite}
	 */
	private void createColumnValuesGroup(final Composite parent)
	{
		final Group valuesGroup = new Group(parent, SWT.NONE);
		valuesGroup.setText(Messages.getString("wizard.property.values")); //$NON-NLS-1$
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
			fillCheckedButtons[i].setToolTipText(Messages.getString("wizard.property.fill.checked")); //$NON-NLS-1$
			fillCheckedButtons[i].setData(new Integer(i));
			fillAllButtons[i] = new Button(valuesComposite, SWT.PUSH | SWT.FLAT);
			fillAllButtons[i].setImage(Activator.getDefault().getImageRegistry().get("fill_all")); //$NON-NLS-1$
			fillAllButtons[i].setToolTipText(Messages.getString("wizard.property.fill.all")); //$NON-NLS-1$
			fillAllButtons[i].setData(new Integer(i));
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
	 * Creates the description SWT widgets
	 * @param parent the parent {@link Composite}
	 */
	private void createDescriptionGroup(final Composite parent)
	{
		final Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.getString("wizard.property.description")); //$NON-NLS-1$
		group.setLayout(new FillLayout());
		final GridData griddata = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		griddata.widthHint = SWT.DEFAULT;
		griddata.heightHint = 60;
		group.setLayoutData(griddata);
		description = new Text(group, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		if (record != null && record.getDescription() != null)
			description.setText(record.getDescription());
	}

	/**
	 * Check the content of the wizard page form, and set the wizard page's message according to the current state.
	 * <p>It checks whether the key empty or it already existing.</p>
	 */
	private void check()
	{
		if (keyText.getText().trim().equals("")) //$NON-NLS-1$
		{
			setMessage(Messages.getString("wizard.property.warning.keyempty"), DialogPage.WARNING); //$NON-NLS-1$
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
			setMessage(Messages.getString("wizard.property.warning.keyexists"), DialogPage.WARNING); //$NON-NLS-1$
			return;
		}
		setMessage(null);
	}

	/**
	 * Returns a newly created {@link PropertyRecord} instance based on the filled form of this wizard page. 
	 * @return a newly created {@link PropertyRecord} instance based on the filled form of this wizard page
	 */
	PropertyRecord getPropertyRecord()
	{
		final PropertyRecord propertyrecord = new PropertyRecord(keyText.getText().trim());

		// Default value
		if (defaultValueBox.getSelection())
			propertyrecord.setDefaultColumnValue(defaultValueText.getText());
		else
			propertyrecord.setDefaultColumnValue(null);

		// Disabled
		propertyrecord.setDisabled(disabled.getSelection());

		// Column values
		for (int i = 0; i < table.getColumns().size(); i++)
		{
			if (checkboxes[i].getSelection())
				propertyrecord.putColumnValue(table.getColumns().get(i), valueFields[i].getText());
		}

		// Description
		propertyrecord.setDescription(description.getText());
		return propertyrecord;
	}
}
