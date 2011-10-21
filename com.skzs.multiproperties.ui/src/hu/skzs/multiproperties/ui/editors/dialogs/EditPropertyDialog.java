/*
 * Created on Oct 8, 2005
 */
package hu.skzs.multiproperties.ui.editors.dialogs;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.editors.Editor;
import hu.skzs.multiproperties.ui.editors.TablePage;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class EditPropertyDialog extends Dialog
{

	private Editor editor;
	private TablePage tablepage;
	private PropertyRecord record;
	private Button[] checkboxes;
	private Text[] textfields;
	private Button[] fillcheckedbuttons;
	private Button[] fillallbuttons;
	private Text description;

	public EditPropertyDialog(final Shell parent)
	{
		super(parent);
	}

	public Object open(Editor editor)
	{
		this.editor = editor;
		tablepage = (TablePage) editor.getActiveEditor();
		final ISelection selection = tablepage.getTableViewer().getSelection();
		record = (PropertyRecord) ((IStructuredSelection) selection).getFirstElement();
		final Shell parent = getParent();
		final Shell shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		shell.setImage(Activator.getDefault().getImageRegistry().getDescriptor("edit").createImage());
		final Display display = parent.getDisplay();
		shell.setText(record.getValue());
		// load the position
		final IPreferenceStore prefenrencestore = Activator.getDefault().getPreferenceStore();
		if (prefenrencestore.contains("dialog.property.width") && prefenrencestore.contains("dialog.property.height"))
			shell.setSize(prefenrencestore.getInt("dialog.property.width"), prefenrencestore.getInt("dialog.property.height"));
		else
			shell.setSize(600, 400);
		if (prefenrencestore.contains("dialog.property.left") && prefenrencestore.contains("dialog.property.top"))
			shell.setLocation(prefenrencestore.getInt("dialog.property.left"), prefenrencestore.getInt("dialog.property.top"));
		else
			shell.setLocation((display.getPrimaryMonitor().getClientArea().width - shell.getSize().x) / 2, (display.getPrimaryMonitor().getClientArea().height - shell.getSize().y) / 2);
		// save the position
		shell.addListener(SWT.Close, new Listener() {

			public void handleEvent(final Event event)
			{
				final IPreferenceStore prefenrencestore = Activator.getDefault().getPreferenceStore();
				prefenrencestore.setValue("dialog.property.width", shell.getSize().x);
				prefenrencestore.setValue("dialog.property.height", shell.getSize().y);
				prefenrencestore.setValue("dialog.property.left", shell.getLocation().x);
				prefenrencestore.setValue("dialog.property.top", shell.getLocation().y);
			}
		});
		createContents(shell);
		shell.open();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
				display.sleep();
		}
		record = null;
		editor = null;
		return null;
	}

	private void createContents(final Shell shell)
	{
		shell.setLayout(new GridLayout(2, false));
		// property group
		final Composite propertycomposite = new Composite(shell, SWT.NONE);
		propertycomposite.setLayout(new GridLayout(2, false));
		GridData griddata = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		griddata.horizontalSpan = 2;
		propertycomposite.setLayoutData(griddata);
		Label label = new Label(propertycomposite, SWT.NONE);
		label.setText("Property: ");
		final Text propertytext = new Text(propertycomposite, SWT.BORDER);
		griddata = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		propertytext.setLayoutData(griddata);
		propertytext.setText(record.getValue());
		label = new Label(propertycomposite, SWT.NONE);
		final Button disabled = new Button(propertycomposite, SWT.CHECK);
		disabled.setText("Disabled");
		disabled.setSelection(record.isDisabled());
		// values group
		final Group valuesgroup = new Group(shell, SWT.NONE);
		valuesgroup.setText("Values");
		valuesgroup.setLayout(new FillLayout());
		griddata = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL);
		griddata.horizontalSpan = 2;
		valuesgroup.setLayoutData(griddata);
		final ScrolledComposite sc = new ScrolledComposite(valuesgroup, SWT.H_SCROLL | SWT.V_SCROLL);
		final Composite child = new Composite(sc, SWT.NONE);
		child.setLayout(new GridLayout(5, false));
		checkboxes = new Button[editor.getTable().getColumns().size()];
		textfields = new Text[editor.getTable().getColumns().size()];
		fillcheckedbuttons = new Button[editor.getTable().getColumns().size()];
		fillallbuttons = new Button[editor.getTable().getColumns().size()];
		for (int i = 0; i < editor.getTable().getColumns().size(); i++)
		{
			label = new Label(child, SWT.NONE);
			final String targetfile = editor.getTable().getColumns().get(i).getName();
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
			if (record.getColumnValue(editor.getTable().getColumns().get(i)) != null)
			{
				checkboxes[i].setSelection(true);
				textfields[i].setEnabled(true);
				textfields[i].setText(record.getColumnValue(editor.getTable().getColumns().get(i)));
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
					for (int i = 0; i < editor.getTable().getColumns().size(); i++)
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
					for (int i = 0; i < editor.getTable().getColumns().size(); i++)
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
		// description group
		final Group descriptiongroup = new Group(shell, SWT.NONE);
		descriptiongroup.setText("Description");
		descriptiongroup.setLayout(new FillLayout());
		griddata = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		griddata.horizontalSpan = 2;
		griddata.widthHint = SWT.DEFAULT;
		griddata.heightHint = 60;
		descriptiongroup.setLayoutData(griddata);
		description = new Text(descriptiongroup, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		if (record.getDescription() != null)
			description.setText(record.getDescription());
		// buttons
		final Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		ok.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent event)
			{
				record.setValue(propertytext.getText());
				final String strDescription = description.getText().trim().replaceAll("\\\r\\\n", "\\\n");
				if (!strDescription.equals(""))
					record.setDescription(strDescription);
				else
					record.setDescription(null);
				record.setDisabled(disabled.getSelection());
				for (int i = 0; i < editor.getTable().getColumns().size(); i++)
				{
					if (!checkboxes[i].getSelection() && record.getColumnValue(editor.getTable().getColumns().get(i)) != null)
						record.removeColumnValue(editor.getTable().getColumns().get(i));
					if (checkboxes[i].getSelection())
						record.putColumnValue(editor.getTable().getColumns().get(i), textfields[i].getText());
				}
				tablepage.getTableViewer().update(record, null);
				if (editor.getOutlinePage() != null)
					editor.getOutlinePage().update(record);
				shell.close();
			}
		});
		griddata = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.GRAB_HORIZONTAL);
		griddata.widthHint = 75;
		ok.setLayoutData(griddata);
		final Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent event)
			{
				shell.close();
			}
		});
		griddata = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		griddata.widthHint = 75;
		cancel.setLayoutData(griddata);
		shell.setDefaultButton(ok);
	}
}
