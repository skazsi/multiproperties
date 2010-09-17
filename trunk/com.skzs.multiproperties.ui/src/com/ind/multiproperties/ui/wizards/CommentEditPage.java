package com.ind.multiproperties.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ind.multiproperties.base.model.CommentRecord;

public class CommentEditPage extends WizardPage
{

	private final CommentRecord record;
	private Text text;

	public CommentEditPage(final CommentRecord record)
	{
		super("comment.edit.page");
		this.record = record;
		if (record == null)
		{
			setTitle("Insert new comment");
			setDescription("Specify the value for the new comment.");
		}
		else
		{
			setTitle("Edit comment");
			setDescription("Specify the value for the comment.");
		}
	}

	public void createControl(final Composite parent)
	{
		// Container
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		final GridLayout gridlayout = new GridLayout(1, false);
		gridlayout.marginWidth = 10;
		gridlayout.marginHeight = 10;
		container.setLayout(gridlayout);
		container.setLayout(new GridLayout(2, false));

		final Label label = new Label(container, SWT.NONE);
		label.setText("Value:");

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		if (record != null)
			text.setText(record.getValue());
	}

	CommentRecord getCommentRecord()
	{
		return new CommentRecord(text.getText());
	}
}
