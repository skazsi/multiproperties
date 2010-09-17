package com.skzs.multiproperties.base.model;

import java.util.LinkedList;
import java.util.List;

import com.skzs.multiproperties.base.model.listener.IColumnChangeListener;
import com.skzs.multiproperties.base.model.listener.IStructuralChangeListener;

public class Columns implements IColumnChangeListener
{

	private final List<Column> columns = new LinkedList<Column>();
	private boolean stale = false;
	protected IStructuralChangeListener structuralChangeListener = null;

	public Columns(final IStructuralChangeListener listener)
	{
		structuralChangeListener = listener;
	}

	public void setStale(final boolean stale)
	{
		this.stale = stale;
	}

	public boolean isStale()
	{
		return stale;
	}

	public void add(final Column column)
	{
		columns.add(column);
		column.setColumnChangeListener(this);
		structuralChangeListener.changed();
		setStale(true);
	}

	public int indexOf(final Column column)
	{
		return columns.indexOf(column);
	}

	public void moveUp(final Column column)
	{
		final int index = columns.indexOf(column);
		columns.set(index, columns.get(index - 1));
		columns.set(index - 1, column);
		structuralChangeListener.changed();
		setStale(true);
	}

	public void moveDown(final Column column)
	{
		final int index = columns.indexOf(column);
		columns.set(index, columns.get(index + 1));
		columns.set(index + 1, column);
		structuralChangeListener.changed();
		setStale(true);
	}

	public Column get(final int index)
	{
		return columns.get(index);
	}

	public Column remove(final int index)
	{
		structuralChangeListener.changed();
		setStale(true);
		return columns.remove(index);
	}

	public int size()
	{
		return columns.size();
	}

	public Object[] toArray()
	{
		return columns.toArray();
	}

	public void columnChanged(final Column column)
	{
		setStale(true);
		structuralChangeListener.changed();
	}
}
