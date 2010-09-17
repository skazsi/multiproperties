/*
 * Created on 2006.03.28.
 */
package com.ind.multiproperties.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

import com.ind.multiproperties.base.model.CommentRecord;
import com.ind.multiproperties.ui.Activator;

public class EditCommentWizard extends Wizard
{
	private final CommentRecord record;
	private CommentEditPage commentEditPage;

	public EditCommentWizard(final CommentRecord record)
	{
		this.record = record;
	}

	@Override
	public void addPages()
	{
		setWindowTitle("Edit comment");
		setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz"));
		commentEditPage = new CommentEditPage(record);
		addPage(commentEditPage);
	}

	@Override
	public boolean performFinish()
	{
		final CommentRecord newrecord = commentEditPage.getCommentRecord();
		record.setValue(newrecord.getValue());
		return true;
	}
}
