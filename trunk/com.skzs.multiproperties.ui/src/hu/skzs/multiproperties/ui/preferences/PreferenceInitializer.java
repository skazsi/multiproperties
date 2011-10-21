/*
 * Created on Dec 2, 2005
 */
package hu.skzs.multiproperties.ui.preferences;

import hu.skzs.multiproperties.ui.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;


public class PreferenceInitializer extends AbstractPreferenceInitializer
{

	@Override
	public void initializeDefaultPreferences()
	{
		Activator.getDefault().getPreferenceStore().setDefault("initial_page", "0");
		Activator.getDefault().getPreferenceStore().setDefault("color_property_foreground", "0,0,0");
		Activator.getDefault().getPreferenceStore().setDefault("color_property_disengaged_foreground", "255,0,0");
		Activator.getDefault().getPreferenceStore().setDefault("color_property_background", "255,255,255");
		Activator.getDefault().getPreferenceStore().setDefault("color_comment_foreground", "192,192,192");
		Activator.getDefault().getPreferenceStore().setDefault("color_comment_background", "255,255,255");
		Activator.getDefault().getPreferenceStore().setDefault("color_empty_background", "255,255,255");
		Activator.getDefault().getPreferenceStore().setDefault("outline_keycolumn_width", "100");
		Activator.getDefault().getPreferenceStore().setDefault("outline_valuecolumn_width", "100");
	}
}
