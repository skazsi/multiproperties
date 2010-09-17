/*
 * Created on Jan 2, 2006
 */
package com.ind.multiproperties.ui.editors;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

public class EditorFontRegistry
{

	public static final String	TOOLTIP_TITLE		= "title";
	private static FontRegistry	registry	= new FontRegistry( );
	static
	{
		registry.put( TOOLTIP_TITLE, new FontData []{ new FontData( "Arial", 8, SWT.BOLD ) } );
	}

	public static Font get( String key )
	{
		return registry.get( key );
	}
}
