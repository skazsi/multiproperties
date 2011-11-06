/*
 * Created on Oct 8, 2005
 */
package hu.skzs.multiproperties.ui.editor.dialogs;

import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.editor.Editor;
import hu.skzs.multiproperties.ui.editor.TablePage;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


public class InsertDialog extends Dialog
{

	private Editor		editor;
	private boolean		bSubmit	= true;
	private TablePage	tablepage;
	private Button[ ]	radios;

	public InsertDialog( Shell parent )
	{
		super( parent );
	}

	public boolean open( Editor editor )
	{
		this.editor = editor;
		tablepage = ( TablePage ) editor.getActiveEditor( );
		Shell parent = getParent( );
		final Shell shell = new Shell( parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
		Display display = parent.getDisplay( );
		shell.setText( "Insert" );
		shell.setSize( 400, 150 );
		// load the position
		IPreferenceStore prefenrencestore = Activator.getDefault( ).getPreferenceStore( );
		if ( prefenrencestore.contains( "dialog.insert.left" ) && prefenrencestore.contains( "dialog.insert.top" ) )
			shell.setLocation( prefenrencestore.getInt( "dialog.insert.left" ), prefenrencestore.getInt( "dialog.insert.top" ) );
		else
			shell.setLocation( ( display.getPrimaryMonitor( ).getClientArea( ).width - shell.getSize( ).x ) / 2, ( display.getPrimaryMonitor( ).getClientArea( ).height - shell.getSize( ).y ) / 2 );
		// save the position
		shell.addListener( SWT.Close, new Listener( )
		{

			public void handleEvent( Event event )
			{
				IPreferenceStore prefenrencestore = Activator.getDefault( ).getPreferenceStore( );
				prefenrencestore.setValue( "dialog.insert.left", shell.getLocation( ).x );
				prefenrencestore.setValue( "dialog.insert.top", shell.getLocation( ).y );
			}
		} );
		createContents( shell );
		shell.open( );
		while ( ! shell.isDisposed( ) )
		{
			if ( ! display.readAndDispatch( ) )
				display.sleep( );
		}
		return bSubmit;
	}

	private void createContents( final Shell shell )
	{
		shell.setLayout( new GridLayout( 2, false ) );
		Group group = new Group( shell, SWT.NONE );
		group.setLayout( new RowLayout( SWT.VERTICAL ) );
		group.setText( "Select the desired type for the new record." );
		GridData griddata = new GridData( GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL );
		griddata.horizontalSpan = 2;
		group.setLayoutData( griddata );
		radios = new Button [ 3 ];
		radios [ 0 ] = new Button( group, SWT.RADIO );
		radios [ 0 ].setSelection( true );
		radios [ 0 ].setText( "Property" );
		radios [ 1 ] = new Button( group, SWT.RADIO );
		radios [ 1 ].setSelection( false );
		radios [ 1 ].setText( "Comment" );
		radios [ 2 ] = new Button( group, SWT.RADIO );
		radios [ 2 ].setSelection( false );
		radios [ 2 ].setText( "Empty" );
		Button ok = new Button( shell, SWT.PUSH );
		ok.setText( "OK" );
		ok.addSelectionListener( new SelectionAdapter( )
		{

			public void widgetSelected( SelectionEvent event )
			{
				AbstractRecord newrecord;
				if ( radios [ 0 ].getSelection( ) )
					newrecord = new PropertyRecord( "" );
				else if ( radios [ 1 ].getSelection( ) )
					newrecord = new CommentRecord( "" );
				else
					newrecord = new EmptyRecord( );
				ISelection selection = tablepage.getTableViewer( ).getSelection( );
				if ( selection.isEmpty( ) )
				{
					editor.getTable( ).add( newrecord );
				}
				else
				{
					AbstractRecord record = ( AbstractRecord ) ( ( IStructuredSelection ) selection ).getFirstElement( );
					editor.getTable( ).insert( newrecord, editor.getTable( ).indexOf( record ) );
				}
				tablepage.getTableViewer( ).refresh( false );
				tablepage.getTableViewer( ).getTable( ).setSelection( editor.getTable( ).indexOf( newrecord ) );
				shell.close( );
			}
		} );
		griddata = new GridData( GridData.HORIZONTAL_ALIGN_END | GridData.GRAB_HORIZONTAL );
		griddata.widthHint = 75;
		ok.setLayoutData( griddata );
		Button cancel = new Button( shell, SWT.PUSH );
		cancel.setText( "Cancel" );
		cancel.addSelectionListener( new SelectionAdapter( )
		{

			public void widgetSelected( SelectionEvent event )
			{
				bSubmit = false;
				shell.close( );
			}
		} );
		griddata = new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING );
		griddata.widthHint = 75;
		cancel.setLayoutData( griddata );
		shell.setDefaultButton( ok );
	}
}
