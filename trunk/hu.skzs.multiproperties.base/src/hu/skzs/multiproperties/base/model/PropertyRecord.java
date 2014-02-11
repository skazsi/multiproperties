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
	private final Map<Column, String> columnValues = new HashMap<Column, String>();
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

	/**
	 * Constructor for creating a new {@link PropertyRecord} based on the given instance.
	 * <p>The <code>recordChange</code> and <code>structuralChange</code> listeners are remain uninitialized.</p>
	 * @param propertyRecord the given instance of {@link PropertyRecord}
	 */
	public PropertyRecord(final PropertyRecord propertyRecord)
	{
		value = propertyRecord.value;
		description = propertyRecord.description;
		disabled = propertyRecord.disabled;
		multiLine = propertyRecord.multiLine;
		defaultColumnValue = propertyRecord.defaultColumnValue;
		final Iterator<Column> iterator = propertyRecord.columnValues.keySet().iterator();
		while (iterator.hasNext())
		{
			final Column column = iterator.next();
			columnValues.put(column, propertyRecord.getColumnValue(column));
		}
	}

	/**
	 * Sets this {@link PropertyRecord}'s properties based on the given instance in the parameter.
	 * <p>The <code>recordChange</code> and <code>structuralChange</code> listeners are remain unset.</p>
	 * @param propertyRecord the given instance of {@link PropertyRecord}
	 */
	public void set(final PropertyRecord propertyRecord)
	{
		setValue(propertyRecord.getValue());
		setDescription(propertyRecord.getDescription());
		setDisabled(propertyRecord.isDisabled());
		setMultiLine(propertyRecord.isMultiLine());
		setDefaultColumnValue(propertyRecord.getDefaultColumnValue());

		// Removing the unwanted column values
		final Column[] columns = columnValues.keySet().toArray(new Column[0]);
		for (final Column column : columns)
		{
			if (propertyRecord.getColumnValue(column) == null)
				removeColumnValue(column);
		}

		// Setting the new column values
		final Iterator<Column> setIterator = propertyRecord.columnValues.keySet().iterator();
		while (setIterator.hasNext())
		{
			final Column column = setIterator.next();
			putColumnValue(column, propertyRecord.getColumnValue(column));
		}
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
}
