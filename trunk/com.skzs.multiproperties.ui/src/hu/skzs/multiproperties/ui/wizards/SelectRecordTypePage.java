package hu.skzs.multiproperties.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class SelectRecordTypePage extends WizardPage
{

	static final int TYPE_PROPERTY = 0;
	static final int TYPE_COMMENT = 1;
	static final int TYPE_EMPTY = 2;
	private int type = TYPE_PROPERTY;

	public SelectRecordTypePage()
	{
		super("select.record.type.page");
		setTitle("Insert new record");
		setDescription("Select the desired type of the new record.");
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

		// Radio group
		final Group group = new Group(container, SWT.NONE);
		group.setLayout(new RowLayout(SWT.VERTICAL));
		group.setText("Select the desired type of the new record");
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		final Button[] radios = new Button[3];
		radios[0] = new Button(group, SWT.RADIO);
		radios[0].setSelection(type == TYPE_PROPERTY);
		radios[0].setText("Property");
		radios[1] = new Button(group, SWT.RADIO);
		radios[1].setSelection(type == TYPE_COMMENT);
		radios[1].setText("Comment");
		radios[2] = new Button(group, SWT.RADIO);
		radios[2].setSelection(type == TYPE_EMPTY);
		radios[2].setText("Empty");

		final SelectionListener listener = new SelectionListener() {

			public void widgetDefaultSelected(final SelectionEvent arg0)
			{
			}

			public void widgetSelected(final SelectionEvent arg0)
			{
				if (arg0.getSource().equals(radios[0]))
					type = TYPE_PROPERTY;
				else if (arg0.getSource().equals(radios[1]))
					type = TYPE_COMMENT;
				else
					type = TYPE_EMPTY;
				getContainer().updateButtons();
			}
		};
		radios[0].addSelectionListener(listener);
		radios[1].addSelectionListener(listener);
		radios[2].addSelectionListener(listener);
	}

	int getType()
	{
		return type;
	}
}
