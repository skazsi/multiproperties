package hu.skzs.multiproperties.ui;

import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.ui.editors.Editor;

import org.eclipse.ui.PlatformUI;


public class ContentAssistant
{
	public static Editor getEditor()
	{
		return (Editor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	}

	public static Table getTable()
	{
		return getEditor().getTable();
	}
}
