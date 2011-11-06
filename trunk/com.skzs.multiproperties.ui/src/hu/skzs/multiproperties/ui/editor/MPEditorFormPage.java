package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.ui.Activator;

import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * The <code>MPEditorFormPage</code> class is an extended implementation of {@link MPEditorPage}
 * for supporting forms.
 * @author sallai
 */
public abstract class MPEditorFormPage extends MPEditorPage
{

	/**
	 * Instance of {@link FormToolkit}
	 */
	protected FormToolkit formToolkit;

	/**
	 * Default constructor.
	 * @param editor the given instance of MultiProperties Editor
	 */
	public MPEditorFormPage()
	{
		formToolkit = Activator.getToolkit();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public final void createPartControl(Composite parent)
	{
		final ScrolledForm scrolledForm = formToolkit.createScrolledForm(parent);
		final IManagedForm managedForm = new ManagedForm(formToolkit, scrolledForm);

		BusyIndicator.showWhile(parent.getDisplay(), new Runnable() {
			public void run()
			{
				createFormContent(managedForm);
			}
		});
	}

	/**
	 * Creates the content of the form.
	 * @param managedForm the given instance of the managed form
	 */
	public abstract void createFormContent(IManagedForm managedForm);
}
