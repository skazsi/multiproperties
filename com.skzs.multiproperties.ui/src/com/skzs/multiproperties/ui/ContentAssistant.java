package com.skzs.multiproperties.ui;

import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.ui.PlatformUI;

import com.skzs.multiproperties.ui.editors.Editor;

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
