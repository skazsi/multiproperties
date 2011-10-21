package hu.skzs.multiproperties.ui.wizards;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.ContentAssistant;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class PropertyEditPage extends WizardPage
{

	private final PropertyRecord record;
	private Text propertytext;
	private Button disabled;
	private Button[] checkboxes;
	private Text[] textfields;
	private Button[] fillcheckedbuttons;
	private Button[] fillallbuttons;
	private Text description;

	public PropertyEditPage(final PropertyRecord record)
	{
		super("property.edit.page");
		this.record = record;
		if (record == null)
		{
			setTitle("Insert new property");
			setDescription("Specify the key and the values for the new property.");
		}
		else
		{
			setTitle("Edit property");
			setDescription("Specify the key and the values for the property.");
		}
	}

	public void createControl(final Composite parent)
	{
		// Container
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		final GridLayout gridlayout = new GridLayout(1, false);
		gridlayout.marginWidth = 10;
		gridlayout.marginHeight = 10;
		container.setLayout(gridlayout);

		// Property group
		final Composite propertycomposite = new Composite(container, SWT.NONE);
		propertycomposite.setLayout(new GridLayout(2, false));
		GridData griddata = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		propertycomposite.setLayoutData(griddata);
		Label label = new Label(propertycomposite, SWT.NONE);
		label.setText("Key: ");
		propertytext = new Text(propertycomposite, SWT.BORDER);
		griddata = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		propertytext.setLayoutData(griddata);
		if (record != null)
			propertytext.setText(record.getValue());
		propertytext.addModifyListener(new ModifyListener() {

			public void modifyText(final ModifyEvent arg0)
			{
				check();
			}
		});
		label = new Label(propertycomposite, SWT.NONE);
		disabled = new Button(propertycomposite, SWT.CHECK);
		disabled.setText("Disabled");
		if (record != null)
			disabled.setSelection(record.isDisabled());

		// Values group
		final Group valuesgroup = new Group(container, SWT.NONE);
		valuesgroup.setText("Values");
		valuesgroup.setLayout(new FillLayout());
		griddata = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL);
		valuesgroup.setLayoutData(griddata);
		final ScrolledComposite sc = new ScrolledComposite(valuesgroup, SWT.H_SCROLL | SWT.V_SCROLL);
		final Composite child = new Composite(sc, SWT.NONE);
		child.setLayout(new GridLayout(5, false));
		checkboxes = new Button[ContentAssistant.getTable().getColumns().size()];
		textfields = new Text[ContentAssistant.getTable().getColumns().size()];
		fillcheckedbuttons = new Button[ContentAssistant.getTable().getColumns().size()];
		fillallbuttons = new Button[ContentAssistant.getTable().getColumns().size()];
		for (int i = 0; i < ContentAssistant.getTable().getColumns().size(); i++)
		{
			label = new Label(child, SWT.NONE);
			final String targetfile = ContentAssistant.getTable().getColumns().get(i).getName();
			label.setText(targetfile);
			checkboxes[i] = new Button(child, SWT.CHECK);
			checkboxes[i].setData(new Integer(i));
			textfields[i] = new Text(child, SWT.BORDER);
			final GridData data = new GridData(GridData.FILL_HORIZONTAL);
			textfields[i].setLayoutData(data);
			fillcheckedbuttons[i] = new Button(child, SWT.PUSH | SWT.FLAT);
			fillcheckedbuttons[i].setImage(Activator.getDefault().getImageRegistry().get("fill_checked"));
			fillcheckedbuttons[i].setToolTipText("Fill checked values with this one");
			fillcheckedbuttons[i].setData(new Integer(i));
			fillallbuttons[i] = new Button(child, SWT.PUSH | SWT.FLAT);
			fillallbuttons[i].setImage(Activator.getDefault().getImageRegistry().get("fill_all"));
			fillallbuttons[i].setToolTipText("Fill all values with this one");
			fillallbuttons[i].setData(new Integer(i));
			if (record != null && record.getColumnValue(ContentAssistant.getTable().getColumns().get(i)) != null)
			{
				checkboxes[i].setSelection(true);
				textfields[i].setEnabled(true);
				textfields[i].setText(record.getColumnValue(ContentAssistant.getTable().getColumns().get(i)));
				fillcheckedbuttons[i].setEnabled(true);
			}
			else
			{
				checkboxes[i].setSelection(false);
				textfields[i].setEnabled(false);
				fillcheckedbuttons[i].setEnabled(false);
			}
			checkboxes[i].addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(final SelectionEvent event)
				{
					final Button checkbox = (Button) event.getSource();
					final int index = ((Integer) checkbox.getData()).intValue();
					textfields[index].setEnabled(checkbox.getSelection());
					textfields[index].setText("");
					fillcheckedbuttons[index].setEnabled(checkbox.getSelection());
				}
			});
			fillcheckedbuttons[i].addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(final SelectionEvent event)
				{
					final Button checkbox = (Button) event.getSource();
					final int index = ((Integer) checkbox.getData()).intValue();
					for (int i = 0; i < ContentAssistant.getTable().getColumns().size(); i++)
					{
						if (i == index)
							continue;
						if (!textfields[i].getEnabled())
							continue;
						textfields[i].setText(textfields[index].getText());
					}
				}
			});
			fillallbuttons[i].addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(final SelectionEvent event)
				{
					final Button checkbox = (Button) event.getSource();
					final int index = ((Integer) checkbox.getData()).intValue();
					for (int i = 0; i < ContentAssistant.getTable().getColumns().size(); i++)
					{
						if (i == index)
							continue;
						checkboxes[i].setSelection(checkboxes[index].getSelection());
						textfields[i].setText(textfields[index].getText());
						textfields[i].setEnabled(textfields[index].getEnabled());
						fillcheckedbuttons[i].setEnabled(checkboxes[index].getSelection());
					}
				}
			});
		}
		sc.setContent(child);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(final ControlEvent e)
			{
				final Rectangle r = sc.getClientArea();
				sc.setMinSize(child.computeSize(r.width, SWT.DEFAULT));
			}
		});

		// Description group
		final Group descriptiongroup = new Group(container, SWT.NONE);
		descriptiongroup.setText("Description");
		descriptiongroup.setLayout(new FillLayout());
		griddata = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		griddata.widthHint = SWT.DEFAULT;
		griddata.heightHint = 60;
		descriptiongroup.setLayoutData(griddata);
		description = new Text(descriptiongroup, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		if (record != null && record.getDescription() != null)
			description.setText(record.getDescription());
		check();
	}

	private void check()
	{
		if (propertytext.getText().trim().equals(""))
		{
			setMessage("The key field is empty!", DialogPage.WARNING);
			return;
		}

		int counter = 0;
		int position = -1;
		while ((position = ContentAssistant.getTable().indexOf(propertytext.getText().trim(), position + 1)) != -1)
			if (record == null || position != ContentAssistant.getTable().indexOf(record))
			{
				counter++;
				break;
			}
		if (counter > 0)
		{
			setMessage("The key already exists!", DialogPage.WARNING);
			return;
		}
		setMessage(null);
	}

	PropertyRecord getPropertyRecord()
	{
		final PropertyRecord propertyrecord = new PropertyRecord(propertytext.getText().trim());
		propertyrecord.setDisabled(disabled.getSelection());
		for (int i = 0; i < ContentAssistant.getTable().getColumns().size(); i++)
		{
			if (checkboxes[i].getSelection())
				propertyrecord.putColumnValue(ContentAssistant.getTable().getColumns().get(i), textfields[i].getText());
		}
		propertyrecord.setDescription(description.getText());
		return propertyrecord;
	}
}
