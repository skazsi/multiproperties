package hu.skzs.multiproperties.base.api;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;

import java.util.List;

/**
 * A {@link IImporter} implementation is responsible for importing content into a {@link Column}.
 * @author sallai
 */
public interface IImporter
{

	/**
	 * Constant value containing the extension point identifier of importers.
	 */
	public static final String IMPORTER_EXTENSION_ID = "hu.skzs.multiproperties.importer"; //$NON-NLS-1$

	/**
	 * Constant value containing the name attribute of importer extension point.
	 */
	public static final String NAME = "name"; //$NON-NLS-1$

	/**
	 * Constant value containing the class attribute of importer extension point.
	 */
	public static final String CLASS = "class"; //$NON-NLS-1$

	/**
	 * Constant value containing the wizard class attribute of importer extension point.
	 */
	public static final String WIZARD_CLASS = "wizardClass"; //$NON-NLS-1$

	/**
	 * Constant value containing the icon attribute of importer extension point.
	 */
	public static final String ICON = "icon"; //$NON-NLS-1$

	/**
	 * Constant for <strong>structural</strong> option.
	 */
	public static final int METHOD_STRUCTURAL = 0;
	/**
	 * Constant for <strong>key-value</strong> option.
	 */
	public static final int METHOD_KEY_VALUE = 1;
	/**
	 * Constant for <strong>value only</strong> option.
	 */
	public static final int METHOD_VALUE = 2;

	/**
	 * Returns a list of records.
	 * <p>The method returns a list of {@link AbstractRecord}s which is the result of the importing.
	 * According to the user's selection, these records can be used in following ways by the importing framework:</p>
	 * <ul>
	 * <li><strong>Structural import:</strong> imports the {@link PropertyRecord}s together with {@link CommentRecord}s and 
	 * {@link EmptyRecord}s, but for the {@link PropertyRecord}s no value is imported.
	 * In this case the <code>column</code> parameter is <code>null</code>.</li>
	 * 
	 * <li><strong>Key-value import:</strong> imports only the {@link PropertyRecord}s.
	 * The previously non-existing {@link PropertyRecord}s are added to the end of table. The <code>column</code>
	 * parameters holds the given {@link Column} where the values should be associated to. If the returned list
	 * contains {@link CommentRecord}s or {@link EmptyRecord}s, they will be skipped by the importing framework.</li>
	 * 
	 * <li><strong>Value import:</strong> imports only the {@link PropertyRecord}s.
	 * The previously non-existing {@link PropertyRecord}s are <strong>not</strong> added to the table.
	 * The <code>column</code> parameters holds the given {@link Column} where the values should be associated to.
	 * If the returned list contains {@link CommentRecord}s or {@link EmptyRecord}s, they will be skipped by
	 * the importing framework.</li>
	 * </ul>
	 * 
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
	 * @param column the column where the values will be imported or <code>null</code> if structural import is selected
	 * @param input the input object
	 * @throws ImporterException when an unexpected error occur
	 */
	public List<AbstractRecord> getRecords(Object input, Column column) throws ImporterException;

}
