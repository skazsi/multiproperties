package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.command.InlineEditHandler;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.RegistryToggleState;

/**
 * The <code>TableEditingSupport</code> is the editing support implementation for the table page.
 * @author skzs
 */
public class TableEditingSupport extends EditingSupport
{
	/**
	 * The used cell editor instance
	 */
	private final TextCellEditor cellEditor;

	/**
	 * The <code>column</code> member hold a reference to the {@link Column} instance.
	 * <p>When it is <code>null</code>, it represents the <strong>key</strong> column.</p>
	 */
	private Column column;

	/**
	 * Constructor for the key column.
	 * <p>The editing support constructed in this way works for the <strong>key</strong> column.</p>
	 * @param viewer the given {@link TableViewer}
	 */
	public TableEditingSupport(final TableViewer viewer)
	{
		super(viewer);
		cellEditor = new TextCellEditor(viewer.getTable());
	}

	/**
	 * Constructor for a value column.
	 * <p>The editing support constructed in this way works for the given {@link Column}.</p>
	 * @param viewer the given {@link TableViewer}
	 * @param column the given {@link Column} instance
	 */
	public TableEditingSupport(final TableViewer viewer, final Column column)
	{
		super(viewer);
		cellEditor = new TextCellEditor(viewer.getTable());
		this.column = column;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
	 */
	@Override
	protected boolean canEdit(final Object element)
	{
		// Checking the inline editing enabled state
		final ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(
				ICommandService.class);
		final Boolean inlineEdit = (Boolean) commandService.getCommand(InlineEditHandler.COMMAND_ID)
				.getState(RegistryToggleState.STATE_ID).getValue();
		if (!inlineEdit)
			return false;

		if (element instanceof PropertyRecord)
		{
			final PropertyRecord propertyRecord = (PropertyRecord) element;
			if (column != null)
			{
				if (!propertyRecord.isMultiLine() && propertyRecord.getColumnValue(column) != null)
					return true;
				else
					return false;
			}
			else
				return true;
		}
		else if (element instanceof CommentRecord)
		{
			if (column == null)
				return true;
			else
				return false;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
	 */
	@Override
	protected CellEditor getCellEditor(final Object element)
	{
		return cellEditor;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
	 */
	@Override
	protected Object getValue(final Object element)
	{
		if (element instanceof PropertyRecord)
		{
			final PropertyRecord propertyRecord = (PropertyRecord) element;
			if (column != null)
			{
				if (propertyRecord.getColumnValue(column) != null)
					return propertyRecord.getColumnValue(column);
			}
			else
				return propertyRecord.getValue();
		}
		else if (element instanceof CommentRecord)
		{
			if (column == null)
			{
				final CommentRecord commentRecord = (CommentRecord) element;
				return commentRecord.getValue();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void setValue(final Object element, final Object value)
	{
		if (element instanceof PropertyRecord)
		{
			final PropertyRecord propertyRecord = (PropertyRecord) element;
			if (column != null)
				propertyRecord.putColumnValue(column, value.toString());
			else
				propertyRecord.setValue(value.toString());
		}
		else if (element instanceof CommentRecord)
		{
			if (column == null)
			{
				final CommentRecord commentRecord = (CommentRecord) element;
				commentRecord.setValue(value.toString());
			}
		}
	}
}