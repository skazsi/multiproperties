package hu.skzs.multiproperties.ui.preferences;

import hu.skzs.multiproperties.ui.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * The <code>PreferenceInitializer</code> is used by the <code>plugin.xml</code> for initializing
 * the preference values.  
 * @author sallai
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer
{

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences()
	{
		// Initial page
		Activator.getDefault().getPreferenceStore().setDefault(PreferenceConstants.INITIAL_PAGE, "0"); //$NON-NLS-1$

		// Coloring the table page
		Activator.getDefault().getPreferenceStore().setDefault(PreferenceConstants.COLOR_PROPERTY_FOREGROUND, "0,0,0"); //$NON-NLS-1$
		Activator.getDefault().getPreferenceStore().setDefault(PreferenceConstants.COLOR_DISABLED_PROPERTY_FOREGROUND, "255,0,0"); //$NON-NLS-1$
		Activator.getDefault().getPreferenceStore().setDefault(PreferenceConstants.COLOR_COMMENT_FOREGROUND, "192,192,192"); //$NON-NLS-1$

		// Outline
		Activator.getDefault().getPreferenceStore().setDefault(PreferenceConstants.OUTLINE_COLUMN_WIDTH, 100);
		Activator.getDefault().getPreferenceStore().setDefault(PreferenceConstants.OUTLINE_VALUE_WIDTH, 100);
	}
}
