package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.ErrorDialogWithStackTrace;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class ColumnPart implements IDetailsPage
{

	private final ColumnsMasterDetailsBlock block;
	private Column column;
	private boolean name_changed;
	private boolean description_changed;
	private boolean handler_configuration_changed;
	private Text nametext;
	private Text descriptiontext;
	private String handler_configuration;
	private Button configure;

	public ColumnPart(final ColumnsMasterDetailsBlock block)
	{
		this.block = block;
	}

	public void createContents(final Composite parent)
	{
		parent.setLayout(new FillLayout());
		final FormToolkit toolkit = Activator.getToolkit();
		final Composite container = toolkit.createComposite(parent);
		final GridLayout layout = new GridLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 5;
		container.setLayout(layout);

		// Section
		final Section section = toolkit.createSection(container, Section.TITLE_BAR);
		section.setText(Messages.getString("columns.column.section.title")); //$NON-NLS-1$
		section.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));
		final Composite client = toolkit.createComposite(section);
		client.setLayout(new GridLayout(2, false));
		section.setClient(client);

		// Fields
		toolkit.createLabel(client, Messages.getString("columns.column.name")); //$NON-NLS-1$
		nametext = toolkit.createText(client, "", SWT.SINGLE); //$NON-NLS-1$
		nametext.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		nametext.addModifyListener(new ModifyListener()
		{

			public void modifyText(final ModifyEvent e)
			{
				name_changed = true;
			}
		});
		final Label label = toolkit.createLabel(client, Messages.getString("columns.column.description")); //$NON-NLS-1$
		final GridData griddata = new GridData();
		griddata.verticalAlignment = GridData.BEGINNING;
		label.setLayoutData(griddata);
		descriptiontext = toolkit.createText(client, "", SWT.MULTI); //$NON-NLS-1$
		descriptiontext.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL
				| GridData.GRAB_VERTICAL));
		descriptiontext.addModifyListener(new ModifyListener()
		{

			public void modifyText(final ModifyEvent e)
			{
				description_changed = true;
			}
		});
		toolkit.createLabel(client, Messages.getString("columns.column.handler")); //$NON-NLS-1$
		configure = toolkit.createButton(client, Messages.getString("columns.column.handler.button"), SWT.PUSH); //$NON-NLS-1$
		configure.addSelectionListener(new SelectionListener()
		{

			public void widgetDefaultSelected(final SelectionEvent arg0)
			{
			}

			public void widgetSelected(final SelectionEvent arg0)
			{
				try
				{
					IConfigurationElement element = null;
					final IExtensionRegistry reg = Platform.getExtensionRegistry();
					final IConfigurationElement[] extensions = reg
							.getConfigurationElementsFor(IHandler.HANDLER_EXTENSION_ID);
					for (int i = 0; i < extensions.length; i++)
					{
						if (extensions[i].getAttribute("name").equals(block.getEditor().getTable().getHandler())) //$NON-NLS-1$
						{
							element = extensions[i];
							break;
						}
					}
					final IHandler handler = (IHandler) element.createExecutableExtension("class"); //$NON-NLS-1$
					final String new_handler_configuration = handler.configure(block.getEditor().getSite().getShell(),
							handler_configuration);
					if (!new_handler_configuration.equals(handler_configuration))
					{
						handler_configuration = new_handler_configuration;
						handler_configuration_changed = true;
					}
				}
				catch (final Exception e)
				{
					Activator.logError(e);
					ErrorDialogWithStackTrace.openError(null, Messages.getString("editor.error.handler.configure"), e); //$NON-NLS-1$
				}
			}
		});
	}

	public void initialize(final IManagedForm form)
	{
	}

	public void dispose()
	{
	}

	public boolean isDirty()
	{
		return name_changed || description_changed || handler_configuration_changed;
	}

	public void commit(final boolean onSave)
	{
		if (name_changed)
		{
			column.setName(nametext.getText());
		}
		if (description_changed)
		{
			column.setDescription(descriptiontext.getText());
		}
		if (handler_configuration_changed)
		{
			column.setHandlerConfiguration(handler_configuration);
		}
		block.refresh();
	}

	public boolean setFormInput(final Object input)
	{
		return false;
	}

	public void setFocus()
	{
	}

	public boolean isStale()
	{
		return false;
	}

	public void refresh()
	{
	}

	public void selectionChanged(final IFormPart part, final ISelection selection)
	{
		column = (Column) ((IStructuredSelection) selection).getFirstElement();
		nametext.setText(column.getName());
		name_changed = false;
		descriptiontext.setText(column.getDescription());
		description_changed = false;
		handler_configuration = column.getHandlerConfiguration();
		handler_configuration_changed = false;
		configure.setEnabled(!block.getEditor().getTable().getHandler().equals("")); //$NON-NLS-1$
	}
}
