/*
 * Created on Dec 8, 2005
 */
package com.skzs.multiproperties.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.skzs.multiproperties.ui.Activator;

public class PreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{

	public PreferencesPage()
	{
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Multi Properties Editor general settings.");
	}

	@Override
	public void createFieldEditors()
	{
		final String[][] straInitialPage = new String[][] { { "Overview", "0" }, { "Columns", "1" }, { "Table", "2" } };
		addField(new RadioGroupFieldEditor("initial_page", "Initial page", 1, straInitialPage, getFieldEditorParent(), true));
	}

	public void init(final IWorkbench workbench)
	{
	}
}
