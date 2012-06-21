package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.ComboPart;
import hu.skzs.multiproperties.ui.util.LayoutFactory;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

public class OverviewPage extends MPEditorFormPage
{

	/**
	 * The <code>PAGE_ID</code> represents the page identifier.
	 * It is used for changing the pages.
	 */
	public static final String PAGE_ID = "overview"; //$NON-NLS-1$

	//GeneralInfoSection
	private Text name;
	private Text description;
	private ComboPart handler;
	//StatisticsSection
	private Label count_of_properties;
	private Label count_of_keys;
	private Label count_of_disabled;
	private Label count_of_comments;
	private Label count_of_emptyline;

	/**
	 * Default constructor.
	 */
	public OverviewPage()
	{
		super();
		setId(PAGE_ID);
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.ui.editors.MPEditorPage#getPageText()
	 */
	@Override
	public String getPageText()
	{
		return Messages.getString("general.overview"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.ui.editors.MPEditorFormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	public void createFormContent(final IManagedForm managedForm)
	{
		final ScrolledForm scrolledForm = managedForm.getForm();
		final Composite body = managedForm.getForm().getBody();

		// Form
		formToolkit.decorateFormHeading(scrolledForm.getForm());
		scrolledForm.setText(Messages.getString("overview.title")); //$NON-NLS-1$
		scrolledForm.setImage(Activator.getDefault().getImageRegistry().get("overview")); //$NON-NLS-1$
		body.setLayout(LayoutFactory.createTableWrapLayout(2, 0, 0));

		// Left container
		final Composite left = formToolkit.createComposite(body);
		left.setLayout(LayoutFactory.createTableWrapLayout());
		left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		fillGeneralInfoSection(left);
		fillHandlerSection(left);

		// Right container
		final Composite right = formToolkit.createComposite(body);
		right.setLayout(LayoutFactory.createTableWrapLayout());
		right.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		fillNavigationSection(right);
		fillStatisticsSection(right);
	}

	private void fillGeneralInfoSection(final Composite parent)
	{
		final Section section = formToolkit.createSection(parent, Section.TITLE_BAR | Section.DESCRIPTION);
		section.setText(Messages.getString("overview.general.section.title")); //$NON-NLS-1$
		section.setDescription(Messages.getString("overview.general.section.description")); //$NON-NLS-1$
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		final Composite composite = formToolkit.createComposite(section);
		composite.setLayout(LayoutFactory.createTableWrapLayout(2, 2, 5));
		section.setClient(composite);
		formToolkit.paintBordersFor(composite);

		// Name
		formToolkit
				.createLabel(composite, Messages.getString("overview.general.name"), SWT.NONE).setForeground(formToolkit.getColors().getColor(IFormColors.TITLE)); //$NON-NLS-1$
		name = formToolkit.createText(composite, editor.getTable().getName(), SWT.SINGLE);
		name.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		name.addModifyListener(new ModifyListener()
		{

			public void modifyText(final ModifyEvent arg0)
			{
				editor.getTable().setName(name.getText());
			}
		});
		// Description
		formToolkit
				.createLabel(composite, Messages.getString("overview.general.description"), SWT.NONE).setForeground(formToolkit.getColors().getColor(IFormColors.TITLE)); //$NON-NLS-1$
		description = formToolkit.createText(composite, editor.getTable().getDescription(), SWT.MULTI | SWT.WRAP
				| SWT.H_SCROLL | SWT.V_SCROLL);
		final TableWrapData tableWrapData = new TableWrapData(TableWrapData.FILL_GRAB);
		tableWrapData.heightHint = 160;
		description.setLayoutData(tableWrapData);
		description.addModifyListener(new ModifyListener()
		{

			public void modifyText(final ModifyEvent arg0)
			{
				editor.getTable().setDescription(description.getText());
			}
		});
	}

	private void fillHandlerSection(final Composite parent)
	{
		final Section section = formToolkit.createSection(parent, Section.TITLE_BAR | Section.DESCRIPTION);
		section.setText(Messages.getString("overview.handler.section.title")); //$NON-NLS-1$
		section.setDescription(Messages.getString("overview.handler.section.description")); //$NON-NLS-1$
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		final Composite composite = formToolkit.createComposite(section);
		composite.setLayout(LayoutFactory.createTableWrapLayout(2, 2, 5));
		section.setClient(composite);
		formToolkit.paintBordersFor(composite);

		// Handler
		formToolkit
				.createLabel(composite, Messages.getString("overview.handler.handler"), SWT.NONE).setForeground(formToolkit.getColors().getColor(IFormColors.TITLE)); //$NON-NLS-1$
		handler = new ComboPart();
		handler.createControl(composite, formToolkit, SWT.READ_ONLY);
		handler.getControl().setLayoutData(new TableWrapData(TableWrapData.FILL));

		final IExtensionRegistry reg = Platform.getExtensionRegistry();
		final IConfigurationElement[] extensions = reg.getConfigurationElementsFor(IHandler.HANDLER_EXTENSION_ID);
		handler.add(Messages.getString("overview.handler.handler.none")); //$NON-NLS-1$
		handler.select(0);
		boolean found_configured_handler = false;
		for (int i = 0; i < extensions.length; i++)
		{
			final IConfigurationElement element = extensions[i];
			handler.add(element.getAttribute("name")); //$NON-NLS-1$
			if (editor.getTable().getHandler().equals(element.getAttribute("name"))) //$NON-NLS-1$
			{
				handler.select(handler.getItems().length - 1);
				found_configured_handler = true;
			}
		}
		if (!found_configured_handler && !editor.getTable().getHandler().equals("")) //$NON-NLS-1$
		{
			handler.add(editor.getTable().getHandler());
			handler.select(handler.getItemCount() - 1);
		}
		handler.addSelectionListener(new SelectionAdapter()
		{

			@Override
			public void widgetSelected(final SelectionEvent arg0)
			{
				if (handler.getSelectionIndex() == 0
						&& editor.getTable().getHandler().equals("") || editor.getTable().getHandler().equals(handler.getText())) //$NON-NLS-1$
					return;
				if (!editor.getTable().getHandler().equals("")) //$NON-NLS-1$
				{
					if (MessageDialog.openConfirm(editor.getSite().getShell(), Messages
							.getString("general.confirm.title"), Messages.getString("overview.handler.confirm.text"))) //$NON-NLS-1$//$NON-NLS-2$
					{
						// Clearing the handler configurations
						for (int i = 0; i < editor.getTable().getColumns().size(); i++)
							editor.getTable().getColumns().get(i).setHandlerConfiguration(""); //$NON-NLS-1$
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
					editor.getTable().setHandler(""); //$NON-NLS-1$
				else
					editor.getTable().setHandler(handler.getText());
			}
		});
	}

	private void fillNavigationSection(final Composite parent)
	{
		final Section section = formToolkit.createSection(parent, Section.TITLE_BAR | Section.DESCRIPTION);
		section.setText(Messages.getString("overview.navigation.section.title")); //$NON-NLS-1$
		section.setDescription(Messages.getString("overview.navigation.section.description")); //$NON-NLS-1$
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		final Composite composite = formToolkit.createComposite(section);
		composite.setLayout(LayoutFactory.createTableWrapLayout(2, 0, 0));
		section.setClient(composite);
		// Text
		final FormText text = formToolkit.createFormText(composite, true);
		text.setImage("columns", Activator.getDefault().getImageRegistry().get("columns")); //$NON-NLS-1$ //$NON-NLS-2$
		text.setImage("table", Activator.getDefault().getImageRegistry().get("table")); //$NON-NLS-1$ //$NON-NLS-2$
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<form><li style=\"image\" value=\"columns\"><a href=\"columns\">"); //$NON-NLS-1$
		stringBuilder.append(Messages.getString("general.columns")); //$NON-NLS-1$
		stringBuilder.append("</a>: "); //$NON-NLS-1$
		stringBuilder.append(Messages.getString("overview.navigation.columns")); //$NON-NLS-1$
		stringBuilder.append("</li><li style=\"image\" value=\"table\"><a href=\"table\">"); //$NON-NLS-1$
		stringBuilder.append(Messages.getString("general.table")); //$NON-NLS-1$
		stringBuilder.append("</a>: "); //$NON-NLS-1$
		stringBuilder.append(Messages.getString("overview.navigation.table")); //$NON-NLS-1$
		stringBuilder.append("</li></form>"); //$NON-NLS-1$

		text.setText(stringBuilder.toString(), true, false);
		text.addHyperlinkListener(new IHyperlinkListener()
		{

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

	private void fillStatisticsSection(final Composite parent)
	{
		final Section section = formToolkit.createSection(parent, Section.TITLE_BAR | Section.DESCRIPTION);
		section.setText(Messages.getString("overview.statistics.section.title")); //$NON-NLS-1$
		section.setDescription(Messages.getString("overview.statistics.section.description")); //$NON-NLS-1$
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		final Composite composite = formToolkit.createComposite(section);
		composite.setLayout(LayoutFactory.createTableWrapLayout(2, 0, 0));
		formToolkit.paintBordersFor(composite);
		section.setClient(composite);
		// Counters
		formToolkit
				.createLabel(composite, Messages.getString("overview.statistics.countof.columns"), SWT.NONE).setForeground(formToolkit.getColors().getColor(IFormColors.TITLE)); //$NON-NLS-1$
		count_of_properties = formToolkit.createLabel(composite, "", SWT.NONE); //$NON-NLS-1$
		count_of_properties.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		formToolkit
				.createLabel(composite, Messages.getString("overview.statistics.countof.property"), SWT.NONE).setForeground(formToolkit.getColors().getColor(IFormColors.TITLE)); //$NON-NLS-1$
		count_of_keys = formToolkit.createLabel(composite, "", SWT.NONE); //$NON-NLS-1$
		count_of_keys.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		formToolkit
				.createLabel(composite, Messages.getString("overview.statistics.countof.disabled"), SWT.NONE).setForeground(formToolkit.getColors().getColor(IFormColors.TITLE)); //$NON-NLS-1$
		count_of_disabled = formToolkit.createLabel(composite, "", SWT.NONE); //$NON-NLS-1$
		count_of_disabled.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		formToolkit
				.createLabel(composite, Messages.getString("overview.statistics.countof.comment"), SWT.NONE).setForeground(formToolkit.getColors().getColor(IFormColors.TITLE)); //$NON-NLS-1$
		count_of_comments = formToolkit.createLabel(composite, "", SWT.NONE); //$NON-NLS-1$
		count_of_comments.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		formToolkit
				.createLabel(composite, Messages.getString("overview.statistics.countof.empty"), SWT.NONE).setForeground(formToolkit.getColors().getColor(IFormColors.TITLE)); //$NON-NLS-1$
		count_of_emptyline = formToolkit.createLabel(composite, "", SWT.NONE); //$NON-NLS-1$
		count_of_emptyline.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.ui.editors.MPEditorPage#setActive()
	 */
	@Override
	public void setActive()
	{
		final Table table = editor.getTable();
		count_of_properties.setText(String.valueOf(table.getColumns().size()));
		count_of_keys.setText(String.valueOf(table.sizeOfProperties()));
		count_of_disabled.setText(String.valueOf(table.sizeOfDisabled()));
		count_of_comments.setText(String.valueOf(table.sizeOfComments()));
		count_of_emptyline.setText(String.valueOf(table.sizeOfEmpties()));
	}

}
