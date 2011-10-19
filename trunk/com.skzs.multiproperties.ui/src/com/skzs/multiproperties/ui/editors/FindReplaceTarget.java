/*
 * Created on Dec 6, 2005
 */
package com.skzs.multiproperties.ui.editors;

import hu.skzs.multiproperties.base.model.AbstractRecord;

import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Point;


public class FindReplaceTarget implements IFindReplaceTarget
{

	private Editor	editor;

	public FindReplaceTarget( Editor editor )
	{
		this.editor = editor;
	}

	public boolean canPerformFind( )
	{
		return true;
	}

	public int findAndSelect( int widgetOffset, String findString, boolean searchForward, boolean caseSensitive, boolean wholeWord )
	{
		if ( ! ( editor.getActiveEditor( ) instanceof TablePage ) )
			return - 1;
		int position = widgetOffset;
		while ( ( searchForward && position < editor.getTable( ).size( ) - 1 ) || ( ! searchForward && position > 0 ) )
		{
			if ( searchForward )
				position++;
			if ( editor.getTable( ).find( position, findString, caseSensitive, wholeWord ) )
			{
				TablePage tablepage = ( TablePage ) editor.getActiveEditor( );
				TableViewer tableviewer = tablepage.getTableViewer( );
				tableviewer.getTable( ).setSelection( position );
				if ( editor.getOutlinePage( ) != null )
					editor.getOutlinePage( ).update( editor.getTable().get( position ) );
				return position;
			}
			if ( ! searchForward )
				position--;
		}
		return - 1;
	}

	public Point getSelection( )
	{
		if ( ! ( editor.getActiveEditor( ) instanceof TablePage ) )
			return new Point( -1, 0 );
		TablePage tablepage = ( TablePage ) editor.getActiveEditor( );
		TableViewer tableviewer = tablepage.getTableViewer( );
		ISelection selection = tableviewer.getSelection( );
		if ( selection.isEmpty( ) )
			return new Point( -1, 0 );
		AbstractRecord record = ( AbstractRecord ) ( ( IStructuredSelection ) selection ).getFirstElement( );
		return new Point( editor.getTable( ).indexOf( record ), 0 );
	}

	public String getSelectionText( )
	{
		return "";
	}

	public boolean isEditable( )
	{
		return false;
	}

	public void replaceSelection( String text )
	{
	}
}
