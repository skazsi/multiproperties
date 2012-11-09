package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The <code>CommentPage</code> wizard page is able to edit an already existing or a newly created {@link CommentRecord}.
 * <p>The {@link PropertyPage#PropertyPage(CommentRecord, Table)} constructor receives a {@link CommentRecord} instance.
 * Passing <code>null</code> value means this wizard page is used in <strong>new</strong> mode, while an instance means 
 * the wizard page will be initiated based on the given instance resulting <strong>edit</strong> mode.</p>
 * @author skzs
 */
public class CommentPage extends WizardPage
{

	private final CommentRecord record;

	// Form fields
	private Text text;

	/**
	 * Default constructor.
	 * <p>If the given <code>commentRecord</code> is <code>null</code>,
	 * it means this wizard page is used in <strong>new</strong> mode. Otherwise the wizard page
	 * will be initiated based on the given instance resulting <strong>edit</strong> mode.</p>
	 * @param commentRecord the given comment record
	 */
	public CommentPage(final CommentRecord commentRecord)
	{
		super("property.page"); //$NON-NLS-1$
		this.record = commentRecord;
		if (commentRecord == null)
		{
			setTitle(Messages.getString("wizard.comment.add.title")); //$NON-NLS-1$
			setDescription(Messages.getString("wizard.comment.add.description")); //$NON-NLS-1$
		}
		else
		{
			setTitle(Messages.getString("wizard.comment.edit.title")); //$NON-NLS-1$
			setDescription(Messages.getString("wizard.comment.edit.description")); //$NON-NLS-1$
		}
	}

	public void createControl(final Composite parent)
	{
		// Container
		final Composite composite = new Composite(parent, SWT.NULL);
		setControl(composite);
		composite.setLayout(LayoutFactory.createGridLayout(2, 10, 10));

		final Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.getString("wizard.comment.value")); //$NON-NLS-1$

		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		if (record != null)
			text.setText(record.getValue());
	}

	/**
	 * Returns a newly created {@link CommentRecord} instance based on the filled form of this wizard page. 
	 * @return a newly created {@link CommentRecord} instance based on the filled form of this wizard page
	 */
	CommentRecord getCommentRecord()
	{
		return new CommentRecord(text.getText());
	}
}
