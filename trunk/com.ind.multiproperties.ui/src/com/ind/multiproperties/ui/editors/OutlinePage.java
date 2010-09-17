/*
 * Created on Oct 20, 2005
 */
package com.ind.multiproperties.ui.editors;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.ind.multiproperties.base.model.AbstractRecord;
import com.ind.multiproperties.base.model.PropertyRecord;
import com.ind.multiproperties.ui.Activator;

public class OutlinePage implements IContentOutlinePage
{

	private final Editor editor;
	private Composite composite;
	private Label keylabel;
	private Table table;

	public OutlinePage(final Editor editor)
	{
		this.editor = editor;
	}

	public void createControl(final Composite parent)
	{
		composite = new Composite(parent, SWT.NONE);
		final GridLayout gridlayout = new GridLayout(1, false);
		gridlayout.marginHeight = 0;
		gridlayout.marginWidth = 0;
		composite.setLayout(gridlayout);
		keylabel = new Label(composite, SWT.NONE);
		keylabel.setText("");
		GridData griddata = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		keylabel.setLayoutData(griddata);
		table = new Table(composite, SWT.FULL_SELECTION);
		griddata = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		table.setLayoutData(griddata);
		TableColumn tc = new TableColumn(table, SWT.NONE);
		tc.setText("Column");
		tc.setWidth(Activator.getDefault().getPreferenceStore().getInt("outline_keycolumn_width"));
		tc.addControlListener(new ControlListener() {

			public void controlMoved(final ControlEvent e)
			{
			}

			public void controlResized(final ControlEvent e)
			{
				final TableColumn tablecolumn = (TableColumn) e.getSource();
				Activator.getDefault().getPreferenceStore().setValue("outline_keycolumn_width", tablecolumn.getWidth());
			}
		});
		tc = new TableColumn(table, SWT.NONE);
		tc.setText("Value");
		tc.setWidth(Activator.getDefault().getPreferenceStore().getInt("outline_valuecolumn_width"));
		tc.addControlListener(new ControlListener() {

			public void controlMoved(final ControlEvent e)
			{
			}

			public void controlResized(final ControlEvent e)
			{
				final TableColumn tablecolumn = (TableColumn) e.getSource();
				Activator.getDefault().getPreferenceStore().setValue("outline_valuecolumn_width", tablecolumn.getWidth());
			}
		});
		table.setHeaderVisible(true);
	}

	public void update(final AbstractRecord record)
	{
		table.removeAll();
		if (record != null && record instanceof PropertyRecord)
		{
			final PropertyRecord propertyrecord = (PropertyRecord) record;
			keylabel.setText((propertyrecord.isDisabled() ? "#" : "") + propertyrecord.getValue());
			for (int i = 0; i < editor.getTable().getColumns().size(); i++)
			{
				if (propertyrecord.getColumnValue(editor.getTable().getColumns().get(i)) != null)
				{
					final TableItem tableitem = new TableItem(table, SWT.NONE);
					tableitem.setText(0, editor.getTable().getColumns().get(i).getName());
					tableitem.setText(1, propertyrecord.getColumnValue(editor.getTable().getColumns().get(i)));
				}
			}
		}
		else
		{
			keylabel.setText("");
		}
	}

	public Control getControl()
	{
		return composite;
	}

	public void setFocus()
	{
	}

	public void addSelectionChangedListener(final ISelectionChangedListener listener)
	{
	}

	public ISelection getSelection()
	{
		return null;
	}

	public void removeSelectionChangedListener(final ISelectionChangedListener listener)
	{
	}

	public void setSelection(final ISelection selection)
	{
	}

	public void dispose()
	{
	}

	public void setActionBars(final IActionBars actionBars)
	{
	}
}
