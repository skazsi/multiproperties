package com.skzs.multiproperties.ui.editors;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import com.skzs.multiproperties.base.model.Table;
import com.skzs.multiproperties.ui.Activator;
import com.skzs.multiproperties.ui.util.LayoutFactory;

public class OverviewPage extends FormPage
{

	private final Editor editor;
	//GeneralInfoSection
	private Text name;
	private Text description;
	private Combo handler;
	//StatisticsSection
	private Label count_of_properties;
	private Label count_of_keys;
	private Label count_of_disabled;
	private Label count_of_comments;
	private Label count_of_emptyline;

	public OverviewPage(final Editor editor)
	{
		super(editor, "overview_page", "Overview");
		this.editor = editor;
	}

	@Override
	public void setActive(final boolean active)
	{
		if (active)
		{
			final Table table = editor.getTable();
			count_of_properties.setText("" + table.getColumns().size());
			count_of_keys.setText("" + table.sizeOfProperties());
			count_of_disabled.setText("" + table.sizeOfDisabled());
			count_of_comments.setText("" + table.sizeOfComments());
			count_of_emptyline.setText("" + table.sizeOfEmpties());
		}
		super.setActive(active);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm)
	{
		super.createFormContent(managedForm);
		final ScrolledForm form = managedForm.getForm();
		final FormToolkit toolkit = managedForm.getToolkit();
		toolkit.decorateFormHeading(form.getForm());
		form.setText("Overview");
		form.setImage(Activator.getDefault().getImageRegistry().get("overview"));
		fillBody(managedForm, toolkit);
		managedForm.refresh();
	}

	private void fillBody(final IManagedForm managedForm, final FormToolkit toolkit)
	{
		final Composite body = managedForm.getForm().getBody();
		body.setLayout(LayoutFactory.createTableWrapLayout(2, 0, 0));

		// Left container
		final Composite left = toolkit.createComposite(body);
		left.setLayout(LayoutFactory.createTableWrapLayout());
		left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		fillGeneralInfoSection(left, toolkit);
		fillHandlerSection(left, toolkit);

		// Right container
		final Composite right = toolkit.createComposite(body);
		right.setLayout(LayoutFactory.createTableWrapLayout());
		right.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		fillNavigationSection(right, toolkit);
		fillStatisticsSection(right, toolkit);
	}

	private void fillGeneralInfoSection(final Composite parent, final FormToolkit toolkit)
	{
		final Section section = toolkit.createSection(parent, Section.TITLE_BAR | Section.DESCRIPTION);
		section.setText("General Information");
		section.setDescription("This section describes general information about this multiproperties.");
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		final Composite composite = toolkit.createComposite(section);
		composite.setLayout(LayoutFactory.createTableWrapLayout(2, 0, 0));
		section.setClient(composite);
		// Name
		toolkit.createLabel(composite, "Name: ", SWT.NONE).setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		name = toolkit.createText(composite, editor.getTable().getName(), SWT.NONE);
		name.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		name.addModifyListener(new ModifyListener() {

			public void modifyText(final ModifyEvent arg0)
			{
				editor.getTable().setName(name.getText());
			}
		});
		// Description
		toolkit.createLabel(composite, "Description: ", SWT.NONE).setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		description = toolkit.createText(composite, editor.getTable().getDescription(), SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
		final TableWrapData tableWrapData = new TableWrapData(TableWrapData.FILL_GRAB);
		tableWrapData.heightHint = 160;
		description.setLayoutData(tableWrapData);
		description.addModifyListener(new ModifyListener() {

			public void modifyText(final ModifyEvent arg0)
			{
				editor.getTable().setDescription(description.getText());
			}
		});
	}

	private void fillHandlerSection(final Composite parent, final FormToolkit toolkit)
	{
		final Section section = toolkit.createSection(parent, Section.TITLE_BAR | Section.DESCRIPTION);
		section.setText("Handler");
		section.setDescription("The handlers are able to produce different kind of outputs from the multiproperties.");
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		final Composite composite = toolkit.createComposite(section);
		composite.setLayout(LayoutFactory.createTableWrapLayout(2, 0, 0));
		section.setClient(composite);
		// Handler
		toolkit.createLabel(composite, "Handler: ", SWT.NONE).setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		handler = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		handler.setLayoutData(new TableWrapData(TableWrapData.FILL));

		final IExtensionRegistry reg = Platform.getExtensionRegistry();
		final IConfigurationElement[] extensions = reg.getConfigurationElementsFor("com.skzs.multiproperties.handler");
		handler.add("none");
		handler.select(0);
		boolean found_configured_handler = false;
		for (int i = 0; i < extensions.length; i++)
		{
			final IConfigurationElement element = extensions[i];
			handler.add(element.getAttribute("name"));
			if (editor.getTable().getHandler().equals(element.getAttribute("name")))
			{
				handler.select(handler.getItems().length - 1);
				found_configured_handler = true;
			}
		}
		if (!found_configured_handler && !editor.getTable().getHandler().equals(""))
		{
			handler.add(editor.getTable().getHandler());
			handler.select(handler.getItemCount() - 1);
		}
		handler.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(final SelectionEvent arg0)
			{
			}

			public void widgetSelected(final SelectionEvent arg0)
			{
				if (handler.getSelectionIndex() == 0 && editor.getTable().getHandler().equals("") || editor.getTable().getHandler().equals(handler.getText()))
					return;
				if (!editor.getTable().getHandler().equals(""))
				{
					if (MessageDialog.openConfirm(editor.getSite().getShell(), "Confirm", "Changing the handler implementation will cause the current handler configurations get lost. Are you sure to continue?"))
					{
						// Clearing the handler configurations
						for (int i = 0; i < editor.getTable().getColumns().size(); i++)
							editor.getTable().getColumns().get(i).setHandlerConfiguration("");
					}
					else
					{
						// Restore the combo selection
						for (int i = 0; i < handler.getItems().length; i++)
						{
							if (handler.getItem(i).equals(editor.getTable().getHandler()))
							{
								handler.select(i);
								return;
							}
						}
					}
				}
				if (handler.getSelectionIndex() == 0)
					editor.getTable().setHandler("");
				else
					editor.getTable().setHandler(handler.getText());
			}
		});
	}

	private void fillNavigationSection(final Composite parent, final FormToolkit toolkit)
	{
		final Section section = toolkit.createSection(parent, Section.TITLE_BAR | Section.DESCRIPTION);
		section.setText("Navigation");
		section.setDescription("This section describes the pages of the editor shortly.");
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		final Composite composite = toolkit.createComposite(section);
		composite.setLayout(LayoutFactory.createTableWrapLayout(2, 0, 0));
		section.setClient(composite);
		// Text
		final FormText text = toolkit.createFormText(composite, true);
		text.setImage("columns", Activator.getDefault().getImageRegistry().get("columns"));
		text.setImage("table", Activator.getDefault().getImageRegistry().get("table"));
		text.setText("<form><li style=\"image\" value=\"columns\"><a href=\"columns_page\">Columns</a>: list of all columns contained by this multiproperties.</li><li style=\"image\" value=\"table\"><a href=\"table_page\">Table</a>: editable interface of columns in table format.</li></form>", true,
				false);
		text.addHyperlinkListener(new IHyperlinkListener() {

			public void linkEntered(final org.eclipse.ui.forms.events.HyperlinkEvent e)
			{
			}

			public void linkExited(final org.eclipse.ui.forms.events.HyperlinkEvent e)
			{
			}

			public void linkActivated(final org.eclipse.ui.forms.events.HyperlinkEvent e)
			{
				editor.setActivePage(e.getHref().toString());
			}
		});
		text.setLayoutData(new TableWrapData(TableWrapData.FILL));
	}

	private void fillStatisticsSection(final Composite parent, final FormToolkit toolkit)
	{
		final Section section = toolkit.createSection(parent, Section.TITLE_BAR | Section.DESCRIPTION);
		section.setText("Statistics");
		section.setDescription("This section gives statistical information about this multiproperties.");
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		final Composite composite = toolkit.createComposite(section);
		composite.setLayout(LayoutFactory.createTableWrapLayout(2, 0, 0));
		section.setClient(composite);
		// Counters
		toolkit.createLabel(composite, "Count of properties files: ", SWT.NONE).setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		count_of_properties = toolkit.createLabel(composite, "", SWT.NONE);
		toolkit.createLabel(composite, "Count of property lines: ", SWT.NONE).setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		count_of_keys = toolkit.createLabel(composite, "", SWT.NONE);
		toolkit.createLabel(composite, "Count of disabled property lines: ", SWT.NONE).setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		count_of_disabled = toolkit.createLabel(composite, "", SWT.NONE);
		toolkit.createLabel(composite, "Count of comment lines: ", SWT.NONE).setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		count_of_comments = toolkit.createLabel(composite, "", SWT.NONE);
		toolkit.createLabel(composite, "Count of empty lines: ", SWT.NONE).setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		count_of_emptyline = toolkit.createLabel(composite, "", SWT.NONE);
	}
}
