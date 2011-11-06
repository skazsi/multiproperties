package hu.skzs.multiproperties.base.api;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Shell;

public interface IHandler
{

	/**
	 * Constant value containing the extension point identifier of handlers.
	 */
	public static final String HANDLER_EXTENSION_ID = "hu.skzs.multiproperties.handler";

	public String configure(Shell shell, String configuration) throws CoreException;

	public void save(String configuration, Table table, Column column) throws CoreException;
}
