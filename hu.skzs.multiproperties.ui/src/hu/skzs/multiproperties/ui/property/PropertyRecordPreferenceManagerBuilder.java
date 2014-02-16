package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;

/**
 * {@link PropertyRecord} based {@link AbstractRecordPreferenceManagerBuilder} implementation. 
 * @author skzs
 */
class PropertyRecordPreferenceManagerBuilder extends AbstractRecordPreferenceManagerBuilder<PropertyRecord>
{

	/**
	 * Constructor
	 * @param record the copied record where the preference manager is built to
	 * @param originalRecord the original record of the copied record
	 * @param table the table instance which holds the record
	 */
	public PropertyRecordPreferenceManagerBuilder(final PropertyRecord record, final PropertyRecord originalRecord,
			final Table table)
	{
		super(record, originalRecord, table);
	}

	@Override
	public PreferenceManager buildPreferenceManager()
	{
		final PreferenceManager preferenceManager = new PreferenceManager();
		preferenceManager.addToRoot(buildGeneralNode());
		if (record.isMultiLine())
			preferenceManager.addToRoot(buildValuesNode());
		if (record.isMultiLine())
		{
			for (final Column column : table.getColumns().toArray())
			{
				preferenceManager.addTo("values", buildValueNode(column)); //$NON-NLS-1$
			}
		}
		preferenceManager.addToRoot(buildDescriptionNode());

		return preferenceManager;
	}

	private IPreferenceNode buildGeneralNode()
	{
		AbstractRecordPropertyPage<PropertyRecord> generalPropertyPage = null;
		if (record.isMultiLine())
			generalPropertyPage = new PropertyRecordMultilineGeneralPropertyPage();
		else
			generalPropertyPage = new PropertyRecordGeneralPropertyPage();
		generalPropertyPage.init(record, originalRecord, table);
		return new PreferenceNode("general", generalPropertyPage); //$NON-NLS-1$
	}

	private IPreferenceNode buildValuesNode()
	{
		final PropertyRecordValuesPropertyPage propertyValuesPropertyPage = new PropertyRecordValuesPropertyPage();
		propertyValuesPropertyPage.init(record, originalRecord, table);
		return new PreferenceNode("values", propertyValuesPropertyPage); //$NON-NLS-1$
	}

	private IPreferenceNode buildValueNode(final Column column)
	{
		final PropertyRecordValuePropertyPage propertyValuePropertyPage = new PropertyRecordValuePropertyPage(column);
		propertyValuePropertyPage.init(record, originalRecord, table);
		return new PreferenceNode(column.getName(), propertyValuePropertyPage);
	}

	private IPreferenceNode buildDescriptionNode()
	{
		final PropertyRecordDescriptionPropertyPage descriptionPropertyPage = new PropertyRecordDescriptionPropertyPage();
		descriptionPropertyPage.init(record, originalRecord, table);
		return new PreferenceNode("description", descriptionPropertyPage); //$NON-NLS-1$
	}
}
