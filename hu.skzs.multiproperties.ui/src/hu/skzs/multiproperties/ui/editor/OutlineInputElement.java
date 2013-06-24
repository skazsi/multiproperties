package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.PropertyRecord;

/**
 * Simple model class for representing the outline elements.
 * @author skzs
 *
 */
public class OutlineInputElement
{

	private final Column column;
	private final PropertyRecord propertyRecord;

	/**
	 * Default constructor
	 * @param column the given {@link Column}
	 * @param propertyRecord the given {@link PropertyRecord}
	 */
	public OutlineInputElement(final Column column, final PropertyRecord propertyRecord)
	{
		this.column = column;
		this.propertyRecord = propertyRecord;
	}

	/**
	 * Returns the {@link Column} instance
	 * @return the {@link Column} instance
	 */
	public Column getColumn()
	{
		return column;
	}

	/**
	 * Returns the {@link PropertyRecord} instance
	 * @return the {@link PropertyRecord} instance
	 */
	public PropertyRecord getPropertyRecord()
	{
		return propertyRecord;
	}
}
