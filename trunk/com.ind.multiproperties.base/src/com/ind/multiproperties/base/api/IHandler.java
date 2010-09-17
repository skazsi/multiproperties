package com.ind.multiproperties.base.api;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Shell;

import com.ind.multiproperties.base.model.Column;
import com.ind.multiproperties.base.model.Table;

public interface IHandler
{
	public String configure(Shell shell, String configuration) throws CoreException;

	public void save(String configuration, Table table, Column column) throws CoreException;
}
