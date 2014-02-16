package hu.skzs.multiproperties.ui.property;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.ui.dialogs.PropertyPage;

/**
 * Abstract implementation of property pages for {@link PropertyRecord}s.
 * @author skzs
 * @param <T> the type of the {@link AbstractRecord}
 *
 */
public abstract class AbstractRecordPropertyPage<T extends AbstractRecord> extends PropertyPage
{
	private boolean visible;
	protected T record;
	protected T originalRecord;
	protected Table table;

	public AbstractRecordPropertyPage()
	{
		super();
		noDefaultAndApplyButton();
	}

	/**
	 * Initializes the property page.
	 */
	void init(final T record, final T originalRecord, final Table table)
	{
		this.record = record;
		this.originalRecord = originalRecord;
		this.table = table;
	}

	@Override
	public final boolean performOk()
	{
		if (visible)
			storeToModel();

		return super.performOk();
	}

	@Override
	public final void setVisible(final boolean visible)
	{
		this.visible = visible;
		super.setVisible(visible);
	}

	/**
	 * Updates the UI from the {@link PropertyRecord}
	 * <p>This method is called by the page change event listener.</p>
	 * @see #storeToModel()
	 */
	public abstract void updateFromModel();

	/**
	 * Stores the UI values into the {@link PropertyRecord}
	 * <p>This method is called by the page change event listener.</p>
	 * @see #updateFromModel()
	 */
	public abstract void storeToModel();
}