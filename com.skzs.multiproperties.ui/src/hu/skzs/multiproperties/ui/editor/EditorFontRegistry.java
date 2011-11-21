package hu.skzs.multiproperties.ui.editor;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

/**
 * The <code>EditorFontRegistry</code> hold predefined {@link Font}.
 * @author sallai
 */
public class EditorFontRegistry
{

	/**
	 * The <code>TOOLTIP_TITLE</code> constant represents the tooltip's title font.
	 */
	public static final String TOOLTIP_TITLE = "title"; //$NON-NLS-1$

	private static FontRegistry registry = new FontRegistry();
	static
	{
		registry.put(TOOLTIP_TITLE, new FontData[] { new FontData("Arial", 8, SWT.BOLD) }); //$NON-NLS-1$
	}

	/**
	 * Returns a shared {@link Font} instance for the given <code>key</code> or
	 * returns the default font if there is no special value associated with that key. 
	 * @param key the given key
	 * @return a shared {@link Font} instance
	 */
	public static Font get(String key)
	{
		return registry.get(key);
	}
}
