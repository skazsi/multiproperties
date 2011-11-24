package hu.skzs.multiproperties.base.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PropertyRecord extends AbstractRecord
{

	private String value;
	private String description;
	private boolean disabled;
	private Map<Column, String> columnvalues = new HashMap<Column, String>();
	private boolean duplicated;

	@Override
	public String toString()
	{
		return value;
	}

	public PropertyRecord()
	{
		super();
	}

	public PropertyRecord(final String value)
	{
		super();
		this.value = value;
	}

	public void setValue(final String value)
	{
		if (this.value != null && this.value.equals(value))
			return;
		this.value = value;
		if (structuralChangeListener != null)
			structuralChangeListener.changed();
	}

	public String getValue()
	{
		return value;
	}

	public void setDescription(final String description)
	{
		if (this.description != null && this.description.equals(description))
			return;
		this.description = description;
		if (recordChangeListener != null)
			recordChangeListener.changed(this);
	}

	public String getDescription()
	{
		return description;
	}

	public void setDisabled(final boolean disabled)
	{
		if (this.disabled == disabled)
			return;
		this.disabled = disabled;
		if (recordChangeListener != null)
			recordChangeListener.changed(this);
	}

	public boolean isDisabled()
	{
		return disabled;
	}

	public void putColumnValue(final Column column, final String value)
	{
		if (columnvalues.get(column) != null && columnvalues.get(column).equals(value) || columnvalues.get(column) == null && value == null)
			return;
		columnvalues.put(column, value);
		if (recordChangeListener != null)
			recordChangeListener.changed(this);
	}

	public String getColumnValue(final Column column)
	{
		return columnvalues.get(column);
	}

	public String removeColumnValue(final Column column)
	{
		if (columnvalues.get(column) == null)
			return null;
		final String value = columnvalues.remove(column);
		if (recordChangeListener != null)
			recordChangeListener.changed(this);
		return value;
	}

	void setDuplicated(final boolean duplicated)
	{
		if (this.duplicated == duplicated)
			return;
		this.duplicated = duplicated;
		if (recordChangeListener != null)
			recordChangeListener.changed(this);
	}

	public boolean isDuplicated()
	{
		return duplicated;
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		final PropertyRecord propertyrecord = new PropertyRecord();
		propertyrecord.value = value;
		propertyrecord.description = description;
		propertyrecord.disabled = disabled;
		final Map<Column, String> columnvalues = new HashMap<Column, String>();
		final Iterator<Column> iterator = this.columnvalues.keySet().iterator();
		while (iterator.hasNext())
		{
			final Column column = iterator.next();
			columnvalues.put(column, getColumnValue(column));
		}
		propertyrecord.columnvalues = columnvalues;
		propertyrecord.setRecordChangeListener(recordChangeListener);
		propertyrecord.setStructuralChangeListener(structuralChangeListener);
		return propertyrecord;
	}
}
