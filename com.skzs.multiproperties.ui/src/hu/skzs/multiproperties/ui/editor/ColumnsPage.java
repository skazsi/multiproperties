package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class ColumnsPage extends MPEditorFormPage
{

	private ColumnsMasterDetailsBlock columnsMasterDetailsBlock;

	@Override
	public void setActive()
	{
		//		if (active)
		//		{
		columnsMasterDetailsBlock.refresh();
		//		}
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.ui.editors.MPEditorPage#getPageText()
	 */
	@Override
	public String getPageText()
	{
		return Messages.getString("general.columns"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.ui.editors.MPEditorFormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	public void createFormContent(IManagedForm managedForm)
	{
		ScrolledForm scrolledForm = managedForm.getForm();

		// Form
		formToolkit.decorateFormHeading(scrolledForm.getForm());
		scrolledForm.setText(Messages.getString("columns.title")); //$NON-NLS-1$
		scrolledForm.setImage(Activator.getDefault().getImageRegistry().get("columns")); //$NON-NLS-1$

		fillBody(managedForm);
	}

	private void fillBody(final IManagedForm managedForm)
	{
		columnsMasterDetailsBlock = new ColumnsMasterDetailsBlock(this);
		columnsMasterDetailsBlock.createContent(managedForm);
	}
}
