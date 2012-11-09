package hu.skzs.multiproperties.ui.editor;

import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.preferences.PreferenceConstants;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * The <code>OutlineValueColumnLabelProvider</code> is the label provider for the outline page value column.
 * @author skzs
 */
public class OutlineValueColumnLabelProvider extends ColumnLabelProvider
{

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element)
	{
		final OutlineInputElement outlineInputElement = (OutlineInputElement) element;
		final PropertyRecord propertyRecord = outlineInputElement.getPropertyRecord();

		// Value
		final String value = propertyRecord.getColumnValue(outlineInputElement.getColumn());
		if (value != null)
			return value;

		// Default value
		final String defaultValue = propertyRecord.getDefaultColumnValue();
		if (defaultValue != null)
			return defaultValue;

		return ""; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getForeground(java.lang.Object)
	 */
	@Override
	public Color getForeground(final Object element)
	{
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		final OutlineInputElement outlineInputElement = (OutlineInputElement) element;
		final PropertyRecord propertyRecord = outlineInputElement.getPropertyRecord();

		// Disabled
		if (propertyRecord.isDisabled())
			return parseColor(store.getString(PreferenceConstants.COLOR_DISABLED_PROPERTY_FOREGROUND));
		else
		{
			if (propertyRecord.getColumnValue(outlineInputElement.getColumn()) != null)
				return parseColor(store.getString(PreferenceConstants.COLOR_PROPERTY_FOREGROUND));
			else if (propertyRecord.getDefaultColumnValue() != null)
				return parseColor(store.getString(PreferenceConstants.COLOR_PROPERTY_DEFAULTVALUE_FOREGROUND));
		}
		return null;
	}

	/**
	 * Returns a newly constructed {@link Color} instance based on the given <code>value</code> holding a <code>R,G,B</code> value.
	 * @param value the given <code>R,G,B</code> value
	 * @return a newly constructed {@link Color}
	 */
	private Color parseColor(final String value)
	{
		// TODO: The created Color instances should be disposed after the usage. Some color registry should be used maybe.
		return new Color(Display.getDefault(), StringConverter.asRGB(value));
	}
}
