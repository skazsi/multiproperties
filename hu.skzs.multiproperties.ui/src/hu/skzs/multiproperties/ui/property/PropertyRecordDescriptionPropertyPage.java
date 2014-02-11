package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * Property page implementation for editing the description for a {@link PropertyRecord}.
 * @author skzs
 */
public class PropertyRecordDescriptionPropertyPage extends AbstractRecordPropertyPage<PropertyRecord>
{
	private Text descriptionField;

	/**
	 * Constructor
	 */
	public PropertyRecordDescriptionPropertyPage()
	{
		super();
		setTitle(Messages.getString("property.property.description.title")); //$NON-NLS-1$
		setDescription(Messages.getString("property.property.description.description")); //$NON-NLS-1$
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	@Override
	protected Control createContents(final Composite parent)
	{
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		createDescriptionContent(composite);

		updateFromModel();

		return composite;
	}

	private void createDescriptionContent(final Composite parent)
	{
		descriptionField = new Text(parent, SWT.BORDER | SWT.MULTI);

		final GridData gridData = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		descriptionField.setLayoutData(gridData);
	}

	@Override
	public void updateFromModel()
	{
		if (record.getDescription() != null)
		{
			descriptionField.setText(record.getDescription());
		}
	}

	@Override
	public void storeToModel()
	{
		record.setDescription(descriptionField.getText());
	}
}