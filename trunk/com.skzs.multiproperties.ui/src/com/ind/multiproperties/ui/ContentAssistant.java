package com.ind.multiproperties.ui;

import org.eclipse.ui.PlatformUI;

import com.ind.multiproperties.base.model.Table;
import com.ind.multiproperties.ui.editors.Editor;

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
