package hu.skzs.multiproperties.ui.editor;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;

public class ColumnsPage extends MPEditorFormPage {

	/**
	 * The <code>PAGE_ID</code> represents the page identifier. It is used for
	 * changing the pages.
	 */
	public static final String PAGE_ID = "columns";

	private ColumnsMasterDetailsBlock columnsMasterDetailsBlock;

	public ColumnsPage() {
		super();
		setId(PAGE_ID);
	}

	@Override
	public void setActive() {
		refresh();
	}

	void refresh() {
		columnsMasterDetailsBlock.refresh();
	}

	@Override
	public String getPageText() {
		return Messages.getString("general.columns");
	}

	@Override
	public void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm scrolledForm = managedForm.getForm();

		formToolkit.decorateFormHeading(scrolledForm.getForm());
		scrolledForm.setText(Messages.getString("columns.title"));
		scrolledForm.setImage(Activator.getDefault().getImageRegistry().get("columns"));

		fillBody(managedForm);
	}

	private void fillBody(final IManagedForm managedForm) {
		columnsMasterDetailsBlock = new ColumnsMasterDetailsBlock(this);
		columnsMasterDetailsBlock.createContent(managedForm);
	}
}
