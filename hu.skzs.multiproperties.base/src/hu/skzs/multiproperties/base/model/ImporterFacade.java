package hu.skzs.multiproperties.base.model;

import hu.skzs.multiproperties.base.api.IImporter;

import java.util.List;

/**
 * The {@link ImporterFacade} performs any importing into a {@link Table}.
 * <p>After initializing the facade it can be reused several times.</p>
 * @author skzs
 */
public class ImporterFacade
{

	private Table table;
	private Column column;

	/**
	 * Initializes the facade
	 * <p>Client must call it before any other method.</p>
	 * @param table the given {@link Table} instance to be used
	 * @param the selected {@link Column} instance or <code>null</code> if structural method is selected
	 * @throws IllegalArgumentException if the <code>table</code> is null
	 */
	public void init(final Table table, final Column column)
	{
		if (table == null)
			throw new IllegalArgumentException("The table cannot be null"); //$NON-NLS-1$
		this.table = table;
		this.column = column;
	}

	/**
	 * Returns the previously set {@link Table} instance. 
	 * @return the previously set {@link Table} instance
	 */
	Table getTable()
	{
		return table;
	}

	/**
	 * Returns the previously set {@link Column} instance. 
	 * @return the previously set {@link Column} instance
	 */
	Column getColumn()
	{
		return column;
	}

	/**
	 * Performs the import. The given list of {@link AbstractRecord} will be imported by using the
	 * given <code>method</code> into the previously set {@link Table} instance by {@link #init(Table)} method.
	 * @param records the given list of {@link AbstractRecord}s
	 * @param method the given method
	 */
	public void performImport(final List<AbstractRecord> records, final int method)
	{
		if (records == null)
			throw new IllegalArgumentException("The records cannot be null"); //$NON-NLS-1$
		verifyMethod(method);

		for (final AbstractRecord record : records)
		{
			switch (method)
			{
			case IImporter.METHOD_STRUCTURAL:
				normaliseRecord(record);
				table.add(record);
				break;
			case IImporter.METHOD_KEY_VALUE:
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
				break;
			}
			case IImporter.METHOD_VALUE:
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
				break;
			}
			default:
				throw new RuntimeException("Unimplemented method option"); //$NON-NLS-1$
			}
		}

	}

	/**
	 * Verifies the <code>method</code> parameter, whether it is a valid importer method or not.
	 * @param method the method to be verified
	 * @throws IllegalArgumentException if the verification fails
	 */
	private void verifyMethod(final int method)
	{
		if (method != IImporter.METHOD_STRUCTURAL && method != IImporter.METHOD_KEY_VALUE
				&& method != IImporter.METHOD_VALUE)
			throw new IllegalArgumentException("Invalid method " + method); //$NON-NLS-1$

		if (column != null && method == IImporter.METHOD_STRUCTURAL)
			throw new IllegalArgumentException("Invalid usage of structural method when column is selected"); //$NON-NLS-1$
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
