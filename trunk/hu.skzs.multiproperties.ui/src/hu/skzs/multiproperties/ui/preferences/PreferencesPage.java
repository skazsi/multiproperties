/*
 * Created on Dec 8, 2005
 */
package hu.skzs.multiproperties.ui.preferences;

import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.Messages;
import hu.skzs.multiproperties.ui.editor.ColumnsPage;
import hu.skzs.multiproperties.ui.editor.OverviewPage;
import hu.skzs.multiproperties.ui.editor.TablePage;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The <code>PreferencesPage</code> is responsible for editing the general settings of MultiProperties
 * Editor.
 * @author skzs
 */
public class PreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{

	/**
	 * Default constructor. Sets the preference store for the {@link FieldEditorPreferencePage} and
	 * set the description of the preference page.
	 */
	public PreferencesPage()
	{
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.getString("preference.general.page.description")); //$NON-NLS-1$
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
		String[] overviewArray = new String[] { Messages.getString("general.overview"), OverviewPage.PAGE_ID }; //$NON-NLS-1$
		String[] columnsArray = new String[] { Messages.getString("general.columns"), ColumnsPage.PAGE_ID }; //$NON-NLS-1$
		String[] tableArray = new String[] { Messages.getString("general.table"), TablePage.PAGE_ID }; //$NON-NLS-1$
		final String[][] straInitialPage = new String[][] { overviewArray, columnsArray, tableArray };
		addField(new RadioGroupFieldEditor(PreferenceConstants.INITIAL_PAGE, Messages.getString("preference.general.initialpage"), 1, straInitialPage, getFieldEditorParent(), true)); //$NON-NLS-1$
	}

}
