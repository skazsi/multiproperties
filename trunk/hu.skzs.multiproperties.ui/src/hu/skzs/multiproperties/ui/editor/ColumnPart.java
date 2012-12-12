package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.registry.element.HandlerRegistryElement;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.util.ErrorDialogWithStackTrace;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
	private Text nametext;
	private final ModifyListener textModifyListener = new ModifyListener()
	{

		public void modifyText(final ModifyEvent e)
		{
			column.setName(nametext.getText());
			name_changed = true;
		}
	};
	private Text descriptiontext;
	private final ModifyListener descriptionModifyListener = new ModifyListener()
	{

		public void modifyText(final ModifyEvent e)
		{
			column.setDescription(descriptiontext.getText());
		}
	};
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
		toolkit.paintBordersFor(client);

		// Fields
		toolkit.createLabel(client, Messages.getString("columns.column.name")); //$NON-NLS-1$
		nametext = toolkit.createText(client, "", SWT.SINGLE); //$NON-NLS-1$
		nametext.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		nametext.addModifyListener(textModifyListener);
		final Label label = toolkit.createLabel(client, Messages.getString("columns.column.description")); //$NON-NLS-1$
		final GridData griddata = new GridData();
		griddata.verticalAlignment = GridData.BEGINNING;
		label.setLayoutData(griddata);
		descriptiontext = toolkit.createText(client, "", SWT.MULTI); //$NON-NLS-1$
		descriptiontext.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL
				| GridData.GRAB_VERTICAL));
		descriptiontext.addModifyListener(descriptionModifyListener);
		toolkit.createLabel(client, Messages.getString("columns.column.handler")); //$NON-NLS-1$
		configure = toolkit.createButton(client, Messages.getString("columns.column.handler.button"), SWT.PUSH); //$NON-NLS-1$
		configure.addSelectionListener(new SelectionAdapter()
		{

			@Override
			public void widgetSelected(final SelectionEvent arg0)
			{
				try
				{
					// Getting the handler element
					final HandlerRegistryElement element = Activator.getDefault().getHandlerRegistry()
							.getElementByName(block.getEditor().getTable().getHandler());
					if (element == null)
					{
						MessageDialog.openError(
								null,
								Messages.getString("general.error.title"), Messages.getString("columns.column.handler.unknown")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}

					// Invoking the handler configuration
					final String new_handler_configuration = element.getHandlerConfigurator().configure(
							block.getEditor().getSite().getShell(), handler_configuration);
					if (new_handler_configuration != null && !new_handler_configuration.equals(handler_configuration))
					{
						handler_configuration = new_handler_configuration;
						column.setHandlerConfiguration(handler_configuration);
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
		return name_changed;
	}

	public void commit(final boolean onSave)
	{
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
		nametext.removeModifyListener(textModifyListener);
		nametext.setText(column.getName());
		nametext.addModifyListener(textModifyListener);
		name_changed = false;
		descriptiontext.removeModifyListener(descriptionModifyListener);
		descriptiontext.setText(column.getDescription());
		descriptiontext.addModifyListener(descriptionModifyListener);
		handler_configuration = column.getHandlerConfiguration();
		configure.setEnabled(!block.getEditor().getTable().getHandler().equals("")); //$NON-NLS-1$
	}
}
