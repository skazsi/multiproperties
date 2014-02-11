package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;

/**
 * {@link CommentRecord} based {@link AbstractRecordPreferenceManagerBuilder} implementation. 
 * @author skzs
 */
class CommentRecordPreferenceManagerBuilder extends AbstractRecordPreferenceManagerBuilder<CommentRecord>
{

	/**
	 * Constructor
	 * @param record the record where the preference manager is built to
	 * @param table the table instance which holds the record
	 */
	public CommentRecordPreferenceManagerBuilder(final CommentRecord record, final Table table)
	{
		super(record, table);
	}

	@Override
	public PreferenceManager buildPreferenceManager()
	{
		final PreferenceManager preferenceManager = new PreferenceManager();
		preferenceManager.addToRoot(buildGeneralNode());
		return preferenceManager;
	}

	private IPreferenceNode buildGeneralNode()
	{
		final CommentPropertyPage commentPropertyPage = new CommentPropertyPage();
		commentPropertyPage.init(record, table);
		return new PreferenceNode("general", commentPropertyPage); //$NON-NLS-1$
	}
}
