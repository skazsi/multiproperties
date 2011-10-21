package hu.skzs.multiproperties.ui.preferences;

import hu.skzs.multiproperties.ui.Activator;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


public class ColorsPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{

	public ColorsPreferencesPage( )
	{
		super( GRID );
		setPreferenceStore( Activator.getDefault( ).getPreferenceStore( ) );
		setDescription( "Multi Properties Editor color settings." );
	}

	public void createFieldEditors( )
	{
		addField( new ColorFieldEditor( "color_property_foreground", "Property record's foreground color:", getFieldEditorParent( ) ) );
		addField( new ColorFieldEditor( "color_property_disengaged_foreground", "Disengaged property record's foreground color:", getFieldEditorParent( ) ) );
		addField( new ColorFieldEditor( "color_property_background", "Property record's background color:", getFieldEditorParent( ) ) );
		addField( new ColorFieldEditor( "color_comment_foreground", "Comment record's foreground color:", getFieldEditorParent( ) ) );
		addField( new ColorFieldEditor( "color_comment_background", "Comment record's background color:", getFieldEditorParent( ) ) );
		addField( new ColorFieldEditor( "color_empty_background", "Empty record's background color:", getFieldEditorParent( ) ) );
	}

	public void init( IWorkbench workbench )
	{
	}
}
