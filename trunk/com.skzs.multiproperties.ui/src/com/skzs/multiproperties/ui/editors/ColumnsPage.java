package com.skzs.multiproperties.ui.editors;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.skzs.multiproperties.ui.Activator;

public class ColumnsPage extends FormPage
{

	private ColumnsBlock columnsblock;

	public ColumnsPage(final Editor editor)
	{
		super(editor, "columns_page", "Columns");
	}

	@Override
	public void setActive(final boolean active)
	{
		if (active)
		{
			columnsblock.refresh();
		}
		super.setActive(active);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm)
	{
		super.createFormContent(managedForm);
		final ScrolledForm form = managedForm.getForm();
		managedForm.getToolkit().decorateFormHeading(form.getForm());
		form.setText("Columns");
		form.setImage(Activator.getDefault().getImageRegistry().get("columns"));
		fillBody(managedForm);
	}

	private void fillBody(final IManagedForm managedForm)
	{
		columnsblock = new ColumnsBlock(this);
		columnsblock.createContent(managedForm);
	}
}
