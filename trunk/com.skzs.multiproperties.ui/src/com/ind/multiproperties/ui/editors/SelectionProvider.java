package com.ind.multiproperties.ui.editors;

import java.util.Iterator;

import org.eclipse.jface.viewers.StructuredSelection;

import com.ind.multiproperties.base.model.AbstractRecord;
import com.ind.multiproperties.base.model.Table;

public class SelectionProvider
{

	public static boolean isContinuousSelection( Table table, StructuredSelection selection )
	{
		Iterator itSelection = selection.iterator( );
		int iMin = - 1;
		int iMax = - 1;
		while ( itSelection.hasNext( ) )
		{
			AbstractRecord record = ( AbstractRecord ) itSelection.next( );
			int index = table.indexOf( record );
			if ( iMin == - 1 && iMax == - 1 )
			{
				iMin = index;
				iMax = index;
			}
			else
			{
				if ( index < iMin )
					iMin = index;
				if ( index > iMax )
					iMax = index;
			}
		}
		if ( iMax - iMin + 1 == selection.size( ) )
			return true;
		return false;
	}

	public static boolean isMoveUpEnabled( Table table, StructuredSelection selection )
	{
		if ( selection.isEmpty( ) )
			return false;
		AbstractRecord record = ( AbstractRecord ) selection.getFirstElement( );
		if ( table.indexOf( record ) == 0 )
			return false;
		return true;
	}

	public static boolean isMoveDownEnabled( Table table, StructuredSelection selection )
	{
		if ( selection.isEmpty( ) )
			return false;
		Object[ ] aRecord = selection.toArray( );
		AbstractRecord record = ( AbstractRecord ) aRecord [ aRecord.length - 1 ];
		if ( table.indexOf( record ) == table.size( ) - 1 )
			return false;
		return true;
	}
}
