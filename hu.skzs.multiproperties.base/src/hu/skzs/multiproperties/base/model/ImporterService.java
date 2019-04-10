package hu.skzs.multiproperties.base.model;

import java.util.List;

public class ImporterService
{

	private final Table table;
	private final Column column;

	public ImporterService(final Table table, final Column column)
	{
		if (table == null)
			throw new IllegalArgumentException("The table cannot be null");
		this.table = table;
		this.column = column;
	}

	Table getTable()
	{
		return table;
	}

	Column getColumn()
	{
		return column;
	}

	public void performImport(final List<AbstractRecord> records, final ImportMode importMode)
	{
		if (records == null)
			throw new IllegalArgumentException("The records cannot be null");

		if (importMode == ImportMode.STRUCTURAL && column != null)
		{
			throw new IllegalArgumentException("The column must be null for structural import");
		}

		for (final AbstractRecord record : records)
		{

			if (importMode == ImportMode.STRUCTURAL)
			{
				normaliseRecord(record);
				table.add(record);
			}

			else if (importMode == ImportMode.KEY_VALUE)
			{
				if (!(record instanceof PropertyRecord))
					continue;

				final PropertyRecord propertyRecord = (PropertyRecord) record;
				final int index = table.indexOf(propertyRecord.getValue());
				if (index > -1)
				{
					final PropertyRecord existingPropertyRecord = (PropertyRecord) table.get(index);
					existingPropertyRecord.putColumnValue(column, propertyRecord.getColumnValue(column));
				}
				else
				{
					normaliseRecord(propertyRecord);
					table.add(propertyRecord);
				}
			}

			else if (importMode == ImportMode.VALUE)
			{
				if (!(record instanceof PropertyRecord))
					continue;

				final PropertyRecord propertyRecord = (PropertyRecord) record;
				final int index = table.indexOf(propertyRecord.getValue());
				if (index > -1)
				{
					final PropertyRecord existingPropertyRecord = (PropertyRecord) table.get(index);
					existingPropertyRecord.putColumnValue(column, propertyRecord.getColumnValue(column));
				}
			}

			else
				throw new RuntimeException("Unimplemented method option");
		}
	}

	/**
	 * Normalize the given {@link AbstractRecord}.
	 * <p>In case of {@link PropertyRecord} it removes the column values
	 * except that one which is associated to the selected <code>column</code>.</p>
	 * @param record the given {@link AbstractRecord}
	 */
	private void normaliseRecord(final AbstractRecord record)
	{
		if (record instanceof PropertyRecord)
		{
			final PropertyRecord propertyRecord = (PropertyRecord) record;
			for (final Column column : table.getColumns().toArray())
			{
				if (column != this.column)
					propertyRecord.removeColumnValue(column);
			}
		}
	}

}
