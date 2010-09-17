/*
 * Created on Oct 2, 2005
 */
package com.ind.multiproperties.ui.editors;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class EditorContentProvider implements IStructuredContentProvider
{

	private Editor	editor;

	public EditorContentProvider( Editor editor )
	{
		super( );
		this.editor = editor;
	}

	public void inputChanged( Viewer v, Object oldInput, Object newInput )
	{
	}

	public void dispose( )
	{
	}

	public Object[ ] getElements( Object parent )
	{
		return editor.getTable( ).toArray( );
	}
}
