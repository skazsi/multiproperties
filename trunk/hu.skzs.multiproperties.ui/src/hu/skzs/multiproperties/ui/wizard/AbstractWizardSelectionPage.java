package hu.skzs.multiproperties.ui.wizard;

import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Abstract implementation of a wizard selection page which simply displays a
 * list of specified wizard elements and allows the user to select one to be
 * launched.
 * <p>Refactored from <code>org.eclipse.ui.internal.dialogs.WorkbenchWizardSelectionPage</code>
 * and <code>org.eclipse.ui.internal.dialogs.WorkbenchWizardListSelectionPage</code>.</p>
 * @author skzs
 */
public abstract class AbstractWizardSelectionPage extends WizardSelectionPage implements ISelectionChangedListener,
		IDoubleClickListener
{

	private final String message;
	protected TableViewer tableViewer;

	/**
	 * Default constructor.
	 * 
	 * @param pageName the name of the page
	 * @param message the message displayed above the viewer
	 */
	public AbstractWizardSelectionPage(final String pageName, final String message)
	{
		super(pageName);
		this.message = message;
		setPageComplete(false);
	}

	/**
	 * Returns the wizard page that would to be shown if the user was to
	 * press the Next button.
	 *
	 * @return the next wizard page, or <code>null</code> if none
	 */
	@Override
	public IWizardPage getNextPage()
	{
		if (getWizard() == null)
		{
			return null;
		}
		return getWizard().getNextPage(this);
	}

	public void createControl(final Composite parent)
	{
		// Container
		final Composite composite = new Composite(parent, SWT.NULL);
		setControl(composite);
		composite.setLayout(LayoutFactory.createGridLayout(1, 10, 10));

		final Label label = new Label(composite, SWT.NONE);
		label.setText(message);

		tableViewer = new TableViewer(composite, SWT.BORDER | SWT.V_SCROLL);
		tableViewer.getTable().setLayoutData(
				new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		tableViewer.setContentProvider(getContentProvider());
		tableViewer.setLabelProvider(getLabelProvider());
		tableViewer.addSelectionChangedListener(this);
		tableViewer.addDoubleClickListener(this);
		tableViewer.setInput(getInput());
	}

	/**
	 * An item in a viewer has been double-clicked.
	 */
	public void doubleClick(final DoubleClickEvent event)
	{
		selectionChanged(new SelectionChangedEvent(event.getViewer(), event.getViewer().getSelection()));
		getContainer().showPage(getNextPage());
	}

	/**
	 * Notes the newly-selected wizard element and updates the page
	 * accordingly.
	 * 
	 * @param event the selection changed event
	 */
	public void selectionChanged(final SelectionChangedEvent event)
	{
		setErrorMessage(null);
		final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		final Object element = selection.getFirstElement();
		if (element == null)
		{
			setSelectedNode(null);
			return;
		}
		setSelectedNode(createWizardNode(element));
	}

	/**
	 * Returns an <code>IWizardNode</code> representing the specified
	 * wizard which has been selected by the user. <b>Subclasses
	 * </b> must override this abstract implementation.
	 * 
	 * @param element the wizard element that an <code>IWizardNode</code> is needed for
	 * @return org.eclipse.jface.wizards.IWizardNode
	 */
	protected abstract IWizardNode createWizardNode(Object element);

	/**
	 * Returns the content provider. Client classes must implement it. 
	 * @return the created content provider
	 */
	public abstract IStructuredContentProvider getContentProvider();

	/**
	 * Returns the label provider. Client classes must implement it. 
	 * @return the created label provider
	 */
	public abstract ColumnLabelProvider getLabelProvider();

	/**
	 * Returns the input. Client classes must implement it. 
	 * @return the input
	 */
	public abstract Object getInput();
}
