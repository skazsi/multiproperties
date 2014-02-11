package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * Property page implementation for editing a {@link CommentRecord}.
 * @author skzs
 */
public class CommentPropertyPage extends AbstractRecordPropertyPage<CommentRecord>
{

	// Form fields
	private Text text;

	/**
	 * Constructor
	 */
	public CommentPropertyPage()
	{
		super();
		setTitle(Messages.getString("property.comment.title")); //$NON-NLS-1$
		setDescription(Messages.getString("property.comment.description")); //$NON-NLS-1$
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	@Override
	protected Control createContents(final Composite parent)
	{
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		createValueContent(composite);

		updateFromModel();

		return composite;
	}

	private void createValueContent(final Composite parent)
	{
		text = new Text(parent, SWT.BORDER);

		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		text.setLayoutData(gridData);
	}

	@Override
	public void updateFromModel()
	{
		if (record.getValue() != null)
		{
			text.setText(record.getValue());
		}
	}

	@Override
	public void storeToModel()
	{
		record.setValue(text.getText());
	}
}