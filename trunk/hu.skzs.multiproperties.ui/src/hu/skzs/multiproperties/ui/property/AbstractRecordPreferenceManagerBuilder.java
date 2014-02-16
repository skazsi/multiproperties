package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;

/**
 * Abstract {@link PreferenceManager} builder implementation. 
 * @author skzs
 * @param <T> the type of the AbstractRecord
 */
public abstract class AbstractRecordPreferenceManagerBuilder<T extends AbstractRecord>
{

	protected final T record;
	protected final T originalRecord;
	protected final Table table;

	/**
	 * Constructor
	 * @param record the copied record where the preference manager is built to
	 * @param originalRecord the original record of the copied record
	 * @param table the table instance which holds the record
	 */
	public AbstractRecordPreferenceManagerBuilder(final T record, final T originalRecord, final Table table)
	{
		this.record = record;
		this.originalRecord = originalRecord;
		this.table = table;
	}

	public T getRecord()
	{
		return record;
	}

	public T getOriginalRecord()
	{
		return originalRecord;
	}

	public Table getTable()
	{
		return table;
	}

	/**
	 * Returns a newly created {@link PreferenceManager} instance which can be used for creating a new {@link PreferenceDialog}.
	 * @return a newly created {@link PreferenceManager} instance
	 */
	public abstract PreferenceManager buildPreferenceManager();
}
