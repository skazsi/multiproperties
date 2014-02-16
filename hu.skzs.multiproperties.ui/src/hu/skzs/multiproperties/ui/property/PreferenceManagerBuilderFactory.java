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
	private final AbstractRecord originalRecord;
	private final Table table;

	/**
	 * Constructor
	 * @param record the copied record where the preference manager is built to
	 * @param originalRecord the original record of the copied record
	 * @param table the given table
	 */
	public PreferenceManagerBuilderFactory(final AbstractRecord record, final AbstractRecord originalRecord,
			final Table table)
	{
		this.record = record;
		this.originalRecord = originalRecord;
		this.table = table;
	}

	/**
	 * Returns a newly created {@link AbstractRecordPreferenceManagerBuilder} instance
	 * @return a newly created {@link AbstractRecordPreferenceManagerBuilder} instance
	 */
	public AbstractRecordPreferenceManagerBuilder<? extends AbstractRecord> getBuilder()
	{
		if (record instanceof PropertyRecord)
			return new PropertyRecordPreferenceManagerBuilder((PropertyRecord) record, (PropertyRecord) originalRecord,
					table);
		else if (record instanceof CommentRecord)
			return new CommentRecordPreferenceManagerBuilder((CommentRecord) record, (CommentRecord) originalRecord,
					table);
		else
			return null;
	}
}
