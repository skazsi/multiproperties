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
	public static final String HANDLER_EXTENSION_ID = "hu.skzs.multiproperties.handler"; //$NON-NLS-1$

	public String configure(Shell shell, String configuration) throws CoreException;

	/**
	 * This method is called by the MultiProperties plugin when the save action is activated.
	 * 
	 * <p>At this point the MultiProperties file (the XML) is saved, and this {@link #save(String, Table, Column)}
	 * method is called as many times, as many columns exist in the {@link Table} with non empty <code>configuration</code>.
	 * If a given {@link Column} has empty configuration, then it will be skipped.</p>
	 * 
	 * <p>The <code>configuration</code> is String based, and the handler implementation is responsible for its content.
	 * See {@link #configure(Shell, String)} method too. The MultiProperties plugin guarantees only the storage of this
	 * String for each {@link Column} in the MultiProperties XML file.</p> 
	 * 
	 * @param configuration the current configuration String for the current {@link Column} object 
	 * @param table the {@link Table} object
	 * @param column the current {@link Column} object
	 * @throws CoreException
	 * @see {@link #configure(Shell, String)}
	 */
	public void save(String configuration, Table table, Column column) throws CoreException;
}
