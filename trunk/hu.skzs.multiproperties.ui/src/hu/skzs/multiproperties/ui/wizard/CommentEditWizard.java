package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.jface.wizard.Wizard;

/**
 * The <code>CommentEditWizard</code> wizard is able to edit a comment record. 
 * @author skzs
 */
public class CommentEditWizard extends Wizard
{

	private final CommentRecord commentRecord;
	private CommentPage commentPage;

	public CommentEditWizard(CommentRecord propertyRecord)
	{
		this.commentRecord = propertyRecord;
	}

	@Override
	public void addPages()
	{
		setWindowTitle(Messages.getString("wizard.property.edit.title")); //$NON-NLS-1$
		setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz")); //$NON-NLS-1$
		commentPage = new CommentPage(commentRecord);
		addPage(commentPage);
	}

	@Override
	public boolean performFinish()
	{
		final CommentRecord modifiedRecord = commentPage.getCommentRecord();
		commentRecord.setValue(modifiedRecord.getValue());
		return true;
	}
}
