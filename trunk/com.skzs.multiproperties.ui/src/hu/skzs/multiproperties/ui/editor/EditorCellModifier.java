/*
 * Created on Oct 7, 2005
 */
package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Item;


public class EditorCellModifier implements ICellModifier
{

	private final Editor editor;
	private final TableViewer tableviewer;
	private boolean isAltPressed = false;

	public EditorCellModifier(final Editor editor, final TableViewer tableviewer)
	{
		this.editor = editor;
		this.tableviewer = tableviewer;
		this.tableviewer.getTable().addKeyListener(new KeyListener() {

			public void keyPressed(final KeyEvent e)
			{
				if (e.keyCode == SWT.ALT)
					isAltPressed = true;
			}

			public void keyReleased(final KeyEvent e)
			{
				if (e.keyCode == SWT.ALT)
					isAltPressed = false;
			}
		});
	}

	public boolean shouldEdit()
	{
		if (!isAltPressed)
			return false;
		// Must reset this value here because if an editor
		// is opened, we don?t get the Alt key up event.
		isAltPressed = false;
		return true;
	}

	public boolean canModify(final Object element, final String property)
	{
		final int index = Integer.parseInt(property);
		final AbstractRecord record = (AbstractRecord) element;
		if (record instanceof PropertyRecord)
		{
			final PropertyRecord propertyrecord = (PropertyRecord) record;
			if (index > 0)
			{
				final String value = propertyrecord.getColumnValue(editor.getTable().getColumns().get(index - 1));
				if (value != null)
					return shouldEdit();
				else
					return false;
			}
			else
				return shouldEdit();
		}
		else if (record instanceof CommentRecord)
		{
			if (index == 0)
				return shouldEdit();
			else
				return false;
		}
		else
			return false;
	}

	public Object getValue(final Object element, final String property)
	{
		final int index = Integer.parseInt(property);
		final AbstractRecord record = (AbstractRecord) element;
		if (record instanceof PropertyRecord)
		{
			final PropertyRecord propertyrecord = (PropertyRecord) record;
			if (index > 0)
				return propertyrecord.getColumnValue(editor.getTable().getColumns().get(index - 1));
			else
				return propertyrecord.getValue();
		}
		else
		{
			final CommentRecord commentrecord = (CommentRecord) record;
			return commentrecord.getValue();
		}
	}

	public void modify(Object element, final String property, final Object value)
	{
		if (value == null)
			return;
		if (element instanceof Item)
			element = ((Item) element).getData();
		final int index = Integer.parseInt(property);
		final AbstractRecord record = (AbstractRecord) element;
		if (record instanceof PropertyRecord)
		{
			final PropertyRecord propertyrecord = (PropertyRecord) record;
			if (index > 0)
			{
				if (propertyrecord.getColumnValue(editor.getTable().getColumns().get(index - 1)).equals(value.toString()))
					return;
				propertyrecord.putColumnValue(editor.getTable().getColumns().get(index - 1), value.toString());
			}
			else
			{
				if (propertyrecord.getValue().equals(value.toString()))
					return;
				propertyrecord.setValue(value.toString());
			}
		}
		else
		{
			final CommentRecord commentrecord = (CommentRecord) record;
			commentrecord.setValue(value.toString());
		}
		if (editor.getOutlinePage() != null)
			editor.getOutlinePage().update(record);
	}
}
