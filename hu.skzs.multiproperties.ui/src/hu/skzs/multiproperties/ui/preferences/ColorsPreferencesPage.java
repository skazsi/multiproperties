package hu.skzs.multiproperties.ui.preferences;

import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.editor.TablePage;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The <code>ColorsPreferencesPage</code> is responsible for editing the color settings of MultiProperties
 * editor's table page.
 * <p>The {@link TablePage} listens for the property change events, thus changing the color settings
 * results refreshing the table page immediately.</p>
 * @author sallai
 */
public class ColorsPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{

	/**
	 * Default constructor. Sets the preference store for the {@link FieldEditorPreferencePage} and
	 * set the description of the preference page.
	 */
	public ColorsPreferencesPage()
	{
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.getString("preference.color.page.description")); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(final IWorkbench workbench)
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	public void createFieldEditors()
	{
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_PROPERTY_FOREGROUND,
				Messages.getString("preference.color.enabled.property"), getFieldEditorParent())); //$NON-NLS-1$
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_PROPERTY_DEFAULTVALUE_FOREGROUND,
				Messages.getString("preference.color.enabled.def.property"), getFieldEditorParent())); //$NON-NLS-1$
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_DISABLED_PROPERTY_FOREGROUND,
				Messages.getString("preference.color.disabled.property"), getFieldEditorParent())); //$NON-NLS-1$
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_DISABLED_PROPERTY_DEFAULTVALUE_FOREGROUND,
				Messages.getString("preference.color.disabled.def.property"), getFieldEditorParent())); //$NON-NLS-1$
		addField(new ColorFieldEditor(PreferenceConstants.COLOR_COMMENT_FOREGROUND,
				Messages.getString("preference.color.comment"), getFieldEditorParent())); //$NON-NLS-1$
	}
}
