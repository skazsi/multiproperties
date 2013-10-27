package hu.skzs.multiproperties.base.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PropertyRecord extends AbstractRecord
{

	private String value;
	private String description;
	private boolean disabled;
	private boolean multiLine;
	private String defaultColumnValue;
	private Map<Column, String> columnValues = new HashMap<Column, String>();
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
		// The structural change listener is used, because the key value change is a that kind of modification
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

	public void setMultiLine(final boolean multiLine)
	{
		if (this.multiLine == multiLine)
			return;
		this.multiLine = multiLine;
		if (recordChangeListener != null)
			recordChangeListener.changed(this);
	}

	public boolean isMultiLine()
	{
		return multiLine;
	}

	public void setDefaultColumnValue(final String defaultValue)
	{
		if (this.defaultColumnValue != null && this.defaultColumnValue.equals(defaultValue))
			return;
		this.defaultColumnValue = defaultValue;
		if (recordChangeListener != null)
			recordChangeListener.changed(this);
	}

	public String getDefaultColumnValue()
	{
		return defaultColumnValue;
	}

	public void putColumnValue(final Column column, final String value)
	{
		if (columnValues.get(column) != null && columnValues.get(column).equals(value)
				|| columnValues.get(column) == null && value == null)
			return;
		columnValues.put(column, value);
		if (recordChangeListener != null)
			recordChangeListener.changed(this);
	}

	public String getColumnValue(final Column column)
	{
		return columnValues.get(column);
	}

	public String removeColumnValue(final Column column)
	{
		if (columnValues.get(column) == null)
			return null;
		final String value = columnValues.remove(column);
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
		propertyrecord.multiLine = multiLine;
		propertyrecord.defaultColumnValue = defaultColumnValue;
		final Map<Column, String> columnvalues = new HashMap<Column, String>();
		final Iterator<Column> iterator = this.columnValues.keySet().iterator();
		while (iterator.hasNext())
		{
			final Column column = iterator.next();
			columnvalues.put(column, getColumnValue(column));
		}
		propertyrecord.columnValues = columnvalues;
		propertyrecord.setRecordChangeListener(recordChangeListener);
		propertyrecord.setStructuralChangeListener(structuralChangeListener);
		return propertyrecord;
	}
}
