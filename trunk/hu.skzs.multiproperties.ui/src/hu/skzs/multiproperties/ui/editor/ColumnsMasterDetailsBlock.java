package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class ColumnsMasterDetailsBlock extends MasterDetailsBlock
{

	private final Editor editor;
	private final MPEditorFormPage page;
	private TableViewer tableViewer;
	private Button add_button;
	private Button remove_button;
	private Button up_button;
	private Button down_button;

	public ColumnsMasterDetailsBlock(final MPEditorFormPage page)
	{
		this.page = page;
		editor = page.getEditor();
	}

	public void refresh()
	{
		tableViewer.refresh(); // To refresh block after modification on part
		//viewer.setSelection(viewer.getSelection()); // To refresh the handler configuration button enablement
	}

	public void refreshButtons()
	{
		final ISelection selection = tableViewer.getSelection();
		if (selection.isEmpty())
		{
			remove_button.setEnabled(false);
			up_button.setEnabled(false);
			down_button.setEnabled(false);
		}
		else
		{
			remove_button.setEnabled(true);
			final Column column = (Column) ((IStructuredSelection) selection).getFirstElement();
			up_button.setEnabled(editor.getTable().getColumns().indexOf(column) > 0);
			down_button.setEnabled(editor.getTable().getColumns().indexOf(column) < editor.getTable().getColumns().size() - 1);
		}
	}

	Editor getEditor()
	{
		return editor;
	}

	@Override
	protected void createMasterPart(final IManagedForm managedForm, final Composite parent)
	{
		final FormToolkit toolkit = managedForm.getToolkit();
		final Composite container = toolkit.createComposite(parent);
		container.setLayout(LayoutFactory.createGridLayout());

		// Section
		final Section section = toolkit.createSection(container, Section.TITLE_BAR);
		section.setText(Messages.getString("general.columns")); //$NON-NLS-1$
		section.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		final Composite composite = toolkit.createComposite(section);
		composite.setLayout(LayoutFactory.createGridLayout(2, 5, 5));
		section.setClient(composite);
		final SectionPart sectionPart = new SectionPart(section);
		managedForm.addPart(sectionPart);

		// TableViewer
		final Table table = toolkit.createTable(composite, SWT.NULL);
		table.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		//toolkit.paintBordersFor(client);
		tableViewer = new TableViewer(table);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(final SelectionChangedEvent event)
			{
				managedForm.fireSelectionChanged(sectionPart, event.getSelection());
				refreshButtons();
			}
		});
		tableViewer.setContentProvider(new ColumnsPageContentProvider());
		tableViewer.setLabelProvider(new ColumnsPageLabelProvider());
		tableViewer.setInput(page.getEditor().getTable().getColumns());

		// Buttons
		final Composite buttonComposite = toolkit.createComposite(composite);
		buttonComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		buttonComposite.setLayout(new GridLayout());
		// Add
		add_button = toolkit.createButton(buttonComposite, Messages.getString("columns.button.add"), SWT.PUSH); //$NON-NLS-1$
		add_button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		add_button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				final Column column = new Column();
				editor.getTable().getColumns().add(column);
				tableViewer.refresh();
				refreshButtons();
				tableViewer.getTable().setSelection(editor.getTable().getColumns().size() - 1);
				managedForm.fireSelectionChanged(sectionPart, tableViewer.getSelection());
			}
		});
		// Remove
		remove_button = toolkit.createButton(buttonComposite, Messages.getString("columns.button.remove"), SWT.PUSH); //$NON-NLS-1$
		remove_button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		remove_button.setEnabled(false);
		remove_button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				final ISelection selection = tableViewer.getSelection();
				if (selection.isEmpty())
					return;
				final Column column = (Column) ((IStructuredSelection) selection).getFirstElement();
				if (MessageDialog.openConfirm(editor.getSite().getShell(), Messages.getString("general.confirm.title"), Messages.getString("columns.button.remove.confirm.text"))) //$NON-NLS-1$//$NON-NLS-2$
				{
					editor.getTable().getColumns().remove(editor.getTable().getColumns().indexOf(column));
					tableViewer.refresh();
					refreshButtons();
				}
			}
		});
		// Up
		toolkit.createLabel(buttonComposite, ""); //$NON-NLS-1$
		up_button = toolkit.createButton(buttonComposite, Messages.getString("columns.button.up"), SWT.PUSH); //$NON-NLS-1$
		up_button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		up_button.setEnabled(false);
		up_button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				final ISelection selection = tableViewer.getSelection();
				if (selection.isEmpty())
					return;
				final Column column = (Column) ((IStructuredSelection) selection).getFirstElement();
				editor.getTable().getColumns().moveUp(column);
				tableViewer.refresh();
				refreshButtons();
			}
		});
		// Down
		down_button = toolkit.createButton(buttonComposite, Messages.getString("columns.button.down"), SWT.PUSH); //$NON-NLS-1$
		down_button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		down_button.setEnabled(false);
		down_button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				final ISelection selection = tableViewer.getSelection();
				if (selection.isEmpty())
					return;
				final Column column = (Column) ((IStructuredSelection) selection).getFirstElement();
				editor.getTable().getColumns().moveDown(column);
				tableViewer.refresh();
				refreshButtons();
			}
		});
	}

	@Override
	protected void registerPages(final DetailsPart detailsPart)
	{
		detailsPart.registerPage(Column.class, new ColumnPart(this));
	}

	@Override
	protected void createToolBarActions(final IManagedForm managedForm)
	{
		final ScrolledForm form = managedForm.getForm();
		final Action horizontalAction = new Action(Messages.getString("columns.button.horizontal"), Action.AS_RADIO_BUTTON) { //$NON-NLS-1$

			@Override
			public void run()
			{
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		horizontalAction.setChecked(true);
		horizontalAction.setToolTipText(Messages.getString("columns.button.horizontal")); //$NON-NLS-1$
		horizontalAction.setImageDescriptor(Activator.getImageDescriptor("icons/th_horizontal.gif")); //$NON-NLS-1$
		final Action verticalAction = new Action(Messages.getString("columns.button.vertical"), Action.AS_RADIO_BUTTON) { //$NON-NLS-1$

			@Override
			public void run()
			{
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		verticalAction.setChecked(false);
		verticalAction.setToolTipText(Messages.getString("columns.button.vertical")); //$NON-NLS-1$
		verticalAction.setImageDescriptor(Activator.getImageDescriptor("icons/th_vertical.gif")); //$NON-NLS-1$
		form.getToolBarManager().add(horizontalAction);
		form.getToolBarManager().add(verticalAction);
	}
}
