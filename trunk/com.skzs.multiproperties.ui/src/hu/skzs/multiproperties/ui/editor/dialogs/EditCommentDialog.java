/*
 * Created on Oct 8, 2005
 */
package hu.skzs.multiproperties.ui.editor.dialogs;

import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.ui.Activator;
import hu.skzs.multiproperties.ui.editor.Editor;
import hu.skzs.multiproperties.ui.editor.TablePage;

import java.util.StringTokenizer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class EditCommentDialog extends Dialog
{

	private Editor	editor;
	private TablePage				tablepage;
	private CommentRecord			record;
	private int						iStart;
	private int						iEnd;

	public EditCommentDialog( Shell parent )
	{
		super( parent );
	}

	private String getCommentBlock( )
	{
		StringBuffer strbCommentBlock = new StringBuffer( );
		iStart = editor.getTable( ).indexOf( record );
		while ( iStart > 0 && editor.getTable( ).get( iStart - 1 ) instanceof CommentRecord )
			iStart--;
		iEnd = iStart;
		while ( iEnd < editor.getTable( ).size( ) - 1 && editor.getTable( ).get( iEnd + 1 ) instanceof CommentRecord )
			iEnd++;
		for ( int i = iStart; i <= iEnd; i++ )
		{
			CommentRecord commentrecord = ( CommentRecord ) editor.getTable( ).get( i );
			strbCommentBlock.append( commentrecord.getValue( ) + "\n" );
		}
		return strbCommentBlock.toString( );
	}

	public Object open( Editor editor )
	{
		this.editor = editor;
		tablepage = ( TablePage ) editor.getActiveEditor( );
		ISelection selection = tablepage.getTableViewer( ).getSelection( );
		record = ( CommentRecord ) ( ( IStructuredSelection ) selection ).getFirstElement( );
		Shell parent = getParent( );
		final Shell shell = new Shell( parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE );
		shell.setImage( Activator.getDefault( ).getImageRegistry( ).getDescriptor( "edit" ).createImage( ) );
		Display display = parent.getDisplay( );
		shell.setText( "Edit comment" );
		// load the position
		IPreferenceStore prefenrencestore = Activator.getDefault( ).getPreferenceStore( );
		if ( prefenrencestore.contains( "dialog.comment.width" ) && prefenrencestore.contains( "dialog.comment.height" ) )
			shell.setSize( prefenrencestore.getInt( "dialog.comment.width" ), prefenrencestore.getInt( "dialog.comment.height" ) );
		else
			shell.setSize( 600, 200 );
		if ( prefenrencestore.contains( "dialog.comment.left" ) && prefenrencestore.contains( "dialog.comment.top" ) )
			shell.setLocation( prefenrencestore.getInt( "dialog.comment.left" ), prefenrencestore.getInt( "dialog.comment.top" ) );
		else
			shell.setLocation( ( display.getPrimaryMonitor( ).getClientArea( ).width - shell.getSize( ).x ) / 2, ( display.getPrimaryMonitor( ).getClientArea( ).height - shell.getSize( ).y ) / 2 );
		// save the position
		shell.addListener( SWT.Close, new Listener( )
		{

			public void handleEvent( Event event )
			{
				IPreferenceStore prefenrencestore = Activator.getDefault( ).getPreferenceStore( );
				prefenrencestore.setValue( "dialog.comment.width", shell.getSize( ).x );
				prefenrencestore.setValue( "dialog.comment.height", shell.getSize( ).y );
				prefenrencestore.setValue( "dialog.comment.left", shell.getLocation( ).x );
				prefenrencestore.setValue( "dialog.comment.top", shell.getLocation( ).y );
			}
		} );
		createContents( shell );
		shell.open( );
		while ( ! shell.isDisposed( ) )
		{
			if ( ! display.readAndDispatch( ) )
				display.sleep( );
		}
		record = null;
		editor = null;
		return null;
	}

	private void createContents( final Shell shell )
	{
		shell.setLayout( new GridLayout( 2, false ) );
		final Text text = new Text( shell, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL );
		text.setText( getCommentBlock( ) );
		GridData griddata = new GridData( GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL );
		griddata.horizontalSpan = 2;
		text.setLayoutData( griddata );
		Button ok = new Button( shell, SWT.PUSH );
		ok.setText( "OK" );
		ok.addSelectionListener( new SelectionAdapter( )
		{

			public void widgetSelected( SelectionEvent event )
			{
				StringTokenizer st = new StringTokenizer( text.getText( ), "\n" );
				while ( st.hasMoreTokens( ) )
				{
					String strLine = st.nextToken( );
					if ( strLine.trim( ).equals( "" ) )
						continue;
					if ( ! strLine.trim( ).startsWith( "#" ) )
					{
						MessageDialog.openError( shell, "Edit comment", "Every comment line must been started with # character!" );
						return;
					}
				}
				for ( int i = iStart; i <= iEnd; i++ )
				{
					editor.getTable( ).remove( iStart );
				}
				int iCurrent = iStart;
				st = new StringTokenizer( text.getText( ), "\n" );
				while ( st.hasMoreTokens( ) )
				{
					String strLine = st.nextToken( );
					if ( strLine.trim( ).equals( "" ) )
						continue;
					CommentRecord commentrecord = new CommentRecord( strLine.trim( ) );
					editor.getTable( ).insert( commentrecord, iCurrent );
					iCurrent++;
				}
				if ( iCurrent == iStart )
					editor.getTable( ).insert( new CommentRecord( "" ), iCurrent );
				tablepage.getTableViewer( ).refresh( );
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
				shell.close( );
			}
		} );
		griddata = new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING );
		griddata.widthHint = 75;
		cancel.setLayoutData( griddata );
		shell.setDefaultButton( ok );
	}
}
