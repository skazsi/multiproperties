package hu.skzs.multiproperties.rcp.preference;

import hu.skzs.multiproperties.rcp.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;

public class PreferenceInitializer extends AbstractPreferenceInitializer
{

	@Override
	public void initializeDefaultPreferences()
	{
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setDefault(PreferenceConstants.NOTIFICATION_VER_ALPHA, MessageDialogWithToggle.PROMPT);
	}
}
