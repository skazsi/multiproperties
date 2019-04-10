package hu.skzs.multiproperties.base.api;

import java.util.List;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.PropertyRecord;

/**
 * A {@link IImporter} implementation is responsible for importing content into a {@link Column}.
 * @author skzs
 */
public interface IImporter
{

	/**
	 * Constant value containing the extension point identifier of importers.
	 */
	public static final String IMPORTER_EXTENSION_ID = "hu.skzs.multiproperties.importer";

	/**
	 * Constant value containing the name attribute of importer extension point.
	 */
	public static final String NAME = "name";

	/**
	 * Constant value containing the class attribute of importer extension point.
	 */
	public static final String CLASS = "class";

	/**
	 * Constant value containing the wizard class attribute of importer extension point.
	 */
	public static final String WIZARD_CLASS = "wizardClass";

	/**
	 * Constant value containing the icon attribute of importer extension point.
	 */
	public static final String ICON = "icon";

	/**
	 * Returns a list of records.
	 * <p>The method returns a list of {@link AbstractRecord}s which are going to be imported.</p>
	 * <p>The {@link PropertyRecord}s should be constructed in the following way if a <code>column</code> is not
	 * <code>null</code>:
	 * <pre>final PropertyRecord propertyRecord = new PropertyRecord();
	 *propertyRecord.setValue(key);
	 *if (column != null) {
	 *	propertyRecord.putColumnValue(column, value);
	 *}</pre>
	 * </p>
	 * <p>The <code>input</code> parameter is the input of the importing. Typically the
	 * selected {@link AbstractImporterWizard} constructs this object based on the its wizard pages.</p>
	 * <p><strong>Note:</strong> The implementations must not modify the given {@link Column} parameter.</p>
	 * @param input the input object
	 * @param column the column where the values will be imported or <code>null</code> if structural import is selected
	 * @return a list of records
	 * @throws ImporterException when an unexpected error occur
	 */
	public List<AbstractRecord> getRecords(Object input, Column column) throws ImporterException;

}
