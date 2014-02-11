package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;

/**
 * Factory implementation for constructing {@link AbstractRecordPreferenceManagerBuilder} instances. 
 * @author skzs
 */
public class PreferenceManagerBuilderFactory
{

	private final AbstractRecord record;
	private final Table table;

	/**
	 * Constructor
	 * @param record the record where the preference manager is built to
	 * @param table the given table
	 */
	public PreferenceManagerBuilderFactory(final AbstractRecord record, final Table table)
	{
		this.record = record;
		this.table = table;
	}

	/**
	 * Returns a newly created {@link AbstractRecordPreferenceManagerBuilder} instance
	 * @return a newly created {@link AbstractRecordPreferenceManagerBuilder} instance
	 */
	public AbstractRecordPreferenceManagerBuilder<? extends AbstractRecord> getBuilder()
	{
		if (record instanceof PropertyRecord)
			return new PropertyRecordPreferenceManagerBuilder((PropertyRecord) record, table);
		else if (record instanceof CommentRecord)
			return new CommentRecordPreferenceManagerBuilder((CommentRecord) record, table);
		else
			return null;
	}
}
