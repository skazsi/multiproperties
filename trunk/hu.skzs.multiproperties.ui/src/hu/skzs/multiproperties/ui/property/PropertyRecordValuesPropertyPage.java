package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

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
 * Property page implementation for editing the column values for a {@link PropertyRecord}.
 * @author skzs
 */
public class PropertyRecordValuesPropertyPage extends AbstractRecordPropertyPage<PropertyRecord>
{
	private Button[] checkboxes;
	private Text[] valueFields;

	/**
	 * Constructor
	 */
	public PropertyRecordValuesPropertyPage()
	{
		super();
		setTitle(Messages.getString("property.property.values.title")); //$NON-NLS-1$
		setDescription(Messages.getString("property.property.values.description")); //$NON-NLS-1$
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
		checkboxes = new Button[table.getColumns().size()];
		valueFields = new Text[table.getColumns().size()];
		for (int i = 0; i < table.getColumns().size(); i++)
		{
			if (i > 0)
				LayoutFactory.createSpaceInGrid(parent, 10, 1);

			checkboxes[i] = new Button(parent, SWT.CHECK);
			checkboxes[i].setText(table.getColumns().get(i).getName());
			checkboxes[i].setData(new Integer(i));
			valueFields[i] = new Text(parent, SWT.BORDER | SWT.MULTI);
			final GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
			gridData.heightHint = 80;
			valueFields[i].setLayoutData(gridData);
			checkboxes[i].addSelectionListener(new SelectionAdapter()
			{

				@Override
				public void widgetSelected(final SelectionEvent event)
				{
					final Button checkbox = (Button) event.getSource();
					final int index = ((Integer) checkbox.getData()).intValue();
					valueFields[index].setEnabled(checkbox.getSelection());
					if (checkbox.getSelection())
					{
						valueFields[index].setText(""); //$NON-NLS-1$
						valueFields[index].setEnabled(true);
					}
					else
					{
						if (record.getDefaultColumnValue() != null)
							valueFields[index].setText(record.getDefaultColumnValue());
						else
							valueFields[index].setText(""); //$NON-NLS-1$
						valueFields[index].setEnabled(false);
					}
				}
			});
		}
	}

	@Override
	public void updateFromModel()
	{
		for (int i = 0; i < table.getColumns().size(); i++)
		{
			if (record.getColumnValue(table.getColumns().get(i)) != null)
			{
				checkboxes[i].setSelection(true);
				valueFields[i].setEnabled(true);
				valueFields[i].setText(record.getColumnValue(table.getColumns().get(i)));
			}
			else
			{
				checkboxes[i].setSelection(false);
				valueFields[i].setEnabled(false);
				if (record.getDefaultColumnValue() != null)
					valueFields[i].setText(record.getDefaultColumnValue());
				else
					valueFields[i].setText(""); //$NON-NLS-1$
			}
		}
	}

	@Override
	public void storeToModel()
	{
		for (int i = 0; i < table.getColumns().size(); i++)
		{
			if (checkboxes[i].getSelection())
			{
				record.putColumnValue(table.getColumns().get(i), valueFields[i].getText());
			}
			else
			{
				record.removeColumnValue(table.getColumns().get(i));
			}
		}
	}
}