package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * Property page implementation for editing one particular column value for a {@link PropertyRecord}.
 * @author skzs
 */
public class PropertyRecordValuePropertyPage extends AbstractRecordPropertyPage<PropertyRecord>
{
	private final Column column;

	private Button checkbox;
	private Text valueField;

	/**
	 * Constructor
	 * @param column the selected column
	 */
	public PropertyRecordValuePropertyPage(final Column column)
	{
		super();
		this.column = column;
		setTitle(column.getName());
		setDescription(Messages.getString("property.property.value.description")); //$NON-NLS-1$
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	@Override
	protected Control createContents(final Composite parent)
	{
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		createValuesContent(composite);

		updateFromModel();

		return composite;
	}

	private void createValuesContent(final Composite parent)
	{
		checkbox = new Button(parent, SWT.CHECK);
		checkbox.setText(Messages.getString("property.property.value.enabled")); //$NON-NLS-1$

		valueField = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);

		final GridData gridData = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		gridData.widthHint = 64;
		gridData.heightHint = 64;
		valueField.setLayoutData(gridData);

		checkbox.addSelectionListener(new SelectionAdapter()
		{

			@Override
			public void widgetSelected(final SelectionEvent event)
			{
				final Button checkbox = (Button) event.getSource();
				valueField.setEnabled(checkbox.getSelection());
				if (checkbox.getSelection())
				{
					valueField.setText(""); //$NON-NLS-1$
					valueField.setEnabled(true);
				}
				else
				{
					if (record.getDefaultColumnValue() != null)
						valueField.setText(record.getDefaultColumnValue());
					else
						valueField.setText(""); //$NON-NLS-1$
					valueField.setEnabled(false);
				}
			}
		});
	}

	@Override
	public void updateFromModel()
	{
		if (record.getColumnValue(column) != null)
		{
			checkbox.setSelection(true);
			valueField.setEnabled(true);
			valueField.setText(record.getColumnValue(column));
		}
		else
		{
			checkbox.setSelection(false);
			valueField.setEnabled(false);
			if (record.getDefaultColumnValue() != null)
				valueField.setText(record.getDefaultColumnValue());
			else
				valueField.setText(""); //$NON-NLS-1$
		}
	}

	@Override
	public void storeToModel()
	{
		if (checkbox.getSelection())
		{
			record.putColumnValue(column, valueField.getText());
		}
		else
		{
			record.removeColumnValue(column);
		}
	}
}