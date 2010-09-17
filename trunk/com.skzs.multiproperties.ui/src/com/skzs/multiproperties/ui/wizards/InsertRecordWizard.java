package com.skzs.multiproperties.ui.wizards;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import com.skzs.multiproperties.base.model.AbstractRecord;
import com.skzs.multiproperties.base.model.EmptyRecord;
import com.skzs.multiproperties.ui.Activator;
import com.skzs.multiproperties.ui.ContentAssistant;

public class InsertRecordWizard extends Wizard
{

	private final int position;
	private SelectRecordTypePage selectRecordTypePage;
	private PropertyEditPage propertyEditPage;
	private CommentEditPage commentEditPage;

	public InsertRecordWizard(final int position)
	{
		this.position = position;
	}

	@Override
	public void addPages()
	{
		setWindowTitle("Insert new property");
		setDefaultPageImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor("edit_wiz"));
		selectRecordTypePage = new SelectRecordTypePage();
		addPage(selectRecordTypePage);
		propertyEditPage = new PropertyEditPage(null);
		addPage(propertyEditPage);
		commentEditPage = new CommentEditPage(null);
		addPage(commentEditPage);
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage page)
	{
		if (page == selectRecordTypePage)
		{
			if (selectRecordTypePage.getType() == SelectRecordTypePage.TYPE_PROPERTY)
				return propertyEditPage;
			else if (selectRecordTypePage.getType() == SelectRecordTypePage.TYPE_COMMENT)
				return commentEditPage;
			else
				return null;
		}
		return super.getNextPage(page);
	}

	@Override
	public boolean performFinish()
	{
		AbstractRecord record = null;
		if (selectRecordTypePage.getType() == SelectRecordTypePage.TYPE_PROPERTY)
			record = propertyEditPage.getPropertyRecord();
		else if (selectRecordTypePage.getType() == SelectRecordTypePage.TYPE_COMMENT)
			record = commentEditPage.getCommentRecord();
		else
			record = new EmptyRecord();

		if (position == -1)
			ContentAssistant.getTable().add(record);
		else
			ContentAssistant.getTable().insert(record, position);
		return true;
	}
}
