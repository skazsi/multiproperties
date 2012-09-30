package hu.skzs.multiproperties.handler.text;

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
		setTitle("title   d");
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
		addField(new EncodingFieldEditor2(getFieldEditorParent()));
		//addField(new EncodingFieldEditor("name", "labeltext", getFieldEditorParent()));
	}
}
