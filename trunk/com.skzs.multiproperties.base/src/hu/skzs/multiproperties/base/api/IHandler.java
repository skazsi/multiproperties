package hu.skzs.multiproperties.base.api;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Shell;


public interface IHandler
{
	public String configure(Shell shell, String configuration) throws CoreException;

	public void save(String configuration, Table table, Column column) throws CoreException;
}
