package com.ind.multiproperties.ui.editors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.ind.multiproperties.base.model.Column;
import com.ind.multiproperties.ui.Activator;

public class ColumnsBlock extends MasterDetailsBlock
{

	private final Editor editor;
	private final FormPage page;
	private TableViewer viewer;
	private Button add_button;
	private Button remove_button;
	private Button up_button;
	private Button down_button;

	public ColumnsBlock(final FormPage page)
	{
		this.page = page;
		editor = (Editor) page.getEditor();
	}

	class PropertiesMasterContentProvider implements IStructuredContentProvider
	{

		public Object[] getElements(final Object inputElement)
		{
			return editor.getTable().getColumns().toArray();
		}

		public void dispose()
		{
		}

		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput)
		{
		}
	}

	class PropertiesMasterLabelProvider extends LabelProvider implements ITableLabelProvider
	{

		public String getColumnText(final Object obj, final int index)
		{
			final Column column = (Column) obj;
			return column.getName();
		}

		public Image getColumnImage(final Object obj, final int index)
		{
			return Activator.getDefault().getImageRegistry().get("column");
		}
	}

	public void refresh()
	{
		viewer.refresh(); // To refresh block after modification on part
		//viewer.setSelection(viewer.getSelection()); // To refresh the handler configuration button enablement
	}

	public void refreshButtons()
	{
		final ISelection selection = viewer.getSelection();
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
		final GridLayout layout = new GridLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 5;
		container.setLayout(layout);
		// section
		final Section section = toolkit.createSection(container, Section.TITLE_BAR);
		section.setText("Columns");
		section.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		final Composite client = toolkit.createComposite(section);
		client.setLayout(new GridLayout(2, false));
		section.setClient(client);
		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		// tableviewer
		final Table table = toolkit.createTable(client, SWT.NULL);
		table.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		//toolkit.paintBordersFor(client);
		viewer = new TableViewer(table);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(final SelectionChangedEvent event)
			{
				managedForm.fireSelectionChanged(spart, event.getSelection());
				refreshButtons();
			}
		});
		viewer.setContentProvider(new PropertiesMasterContentProvider());
		viewer.setLabelProvider(new PropertiesMasterLabelProvider());
		viewer.setInput(page.getEditor().getEditorInput());
		// buttons
		final Composite button_composite = toolkit.createComposite(client);
		button_composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		button_composite.setLayout(new GridLayout());
		add_button = toolkit.createButton(button_composite, "Add...", SWT.PUSH);
		add_button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		add_button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				final Column column = new Column();
				editor.getTable().getColumns().add(column);
				viewer.refresh();
				refreshButtons();
				viewer.getTable().setSelection(editor.getTable().getColumns().size() - 1);
				managedForm.fireSelectionChanged(spart, viewer.getSelection());
			}
		});
		remove_button = toolkit.createButton(button_composite, "Remove", SWT.PUSH);
		remove_button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		remove_button.setEnabled(false);
		remove_button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				final ISelection selection = viewer.getSelection();
				if (selection.isEmpty())
					return;
				final Column column = (Column) ((IStructuredSelection) selection).getFirstElement();
				if (MessageDialog.openConfirm(editor.getSite().getShell(), "Confirm", "Are you sure to remove \"" + column.getName() + "\" column?"))
				{
					editor.getTable().getColumns().remove(editor.getTable().getColumns().indexOf(column));
					viewer.refresh();
					refreshButtons();
				}
			}
		});
		toolkit.createLabel(button_composite, "");
		up_button = toolkit.createButton(button_composite, "Up", SWT.PUSH);
		up_button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		up_button.setEnabled(false);
		up_button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				final ISelection selection = viewer.getSelection();
				if (selection.isEmpty())
					return;
				final Column column = (Column) ((IStructuredSelection) selection).getFirstElement();
				editor.getTable().getColumns().moveUp(column);
				viewer.refresh();
				refreshButtons();
			}
		});
		down_button = toolkit.createButton(button_composite, "Down", SWT.PUSH);
		down_button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		down_button.setEnabled(false);
		down_button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e)
			{
				final ISelection selection = viewer.getSelection();
				if (selection.isEmpty())
					return;
				final Column column = (Column) ((IStructuredSelection) selection).getFirstElement();
				editor.getTable().getColumns().moveDown(column);
				viewer.refresh();
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
		final Action haction = new Action("hor", Action.AS_RADIO_BUTTON) {

			@Override
			public void run()
			{
				sashForm.setOrientation(SWT.HORIZONTAL);
				form.reflow(true);
			}
		};
		haction.setChecked(true);
		haction.setToolTipText("Horizontal layout");
		haction.setImageDescriptor(Activator.getImageDescriptor("icons/th_horizontal.gif"));
		final Action vaction = new Action("ver", Action.AS_RADIO_BUTTON) {

			@Override
			public void run()
			{
				sashForm.setOrientation(SWT.VERTICAL);
				form.reflow(true);
			}
		};
		vaction.setChecked(false);
		vaction.setToolTipText("Vertical layout");
		vaction.setImageDescriptor(Activator.getImageDescriptor("icons/th_vertical.gif"));
		form.getToolBarManager().add(haction);
		form.getToolBarManager().add(vaction);
	}
}
