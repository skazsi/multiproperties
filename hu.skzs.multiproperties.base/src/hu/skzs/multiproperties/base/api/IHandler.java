package hu.skzs.multiproperties.base.api;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.swt.widgets.Shell;

/**
 * A {@link IHandler} implementation is responsible for writing out any {@link Column} with its all
 * defined {@link PropertyRecord}, {@link CommentRecord} and {@link EmptyRecord} content.
 * <p>The target resource is not specified, in other word its up to the implementation.</p>
 * <p>The MultiProperties plugin guarantees only storing a String value for each {@link Column},
 * which is understandable by the handler only, and specifies how the handler should write out
 * the particular {@link Column}.</p>
 * @author sallai
 */
public interface IHandler
{

	/**
	 * Constant value containing the extension point identifier of handlers.
	 */
	public static final String HANDLER_EXTENSION_ID = "hu.skzs.multiproperties.handler"; //$NON-NLS-1$

	/**
	 * This method is called by the MultiProperties plugin when the user clicks on the <strong>configure</strong>
	 * button on the <strong>Columns</strong> page for the currently selected {@link Column}.
	 * <p>The plugin provides a {@link Shell} instance to help representing any dialog or wizard by the handler, and it also
	 * provides the previous <code>configuration</code> String object.</p>
	 * <p>The returned String value is the modified handler configuration information.</p>
	 * <p>The <code>configuration</code> is String based information which is understandable by the handler only,
	 * and specifies how the handler should write out the particular {@link Column}. 
	 * The MultiProperties plugin guarantees only storing this String for each {@link Column} in the MultiProperties XML file.</p> 
	 *  
	 * @param shell the given {@link Shell}
	 * @param configuration the current configuration String for the current {@link Column} object
	 * @return the modified configuration String
	 * @throws HandlerException
	 * @see {@link #save(String, Table, Column)}
	 */
	public String configure(Shell shell, String configuration) throws HandlerException;

	/**
	 * This method is called by the MultiProperties plugin when the save action is activated.
	 * 
	 * <p>At this point the MultiProperties file (the XML) is saved, and this {@link #save(String, Table, Column)}
	 * method is called as many times, as many columns exist in the {@link Table} with non empty <code>configuration</code>.
	 * If a given {@link Column} has empty configuration, then it will be skipped.</p>
	 * 
	 * <p>The <code>configuration</code> is String based information which is understandable by the handler only,
	 * and specifies how the handler should write out the particular {@link Column}. 
	 * The MultiProperties plugin guarantees only storing this String for each {@link Column} in the MultiProperties XML file.</p> 
	 * 
	 * @param configuration the current configuration String for the current {@link Column} object 
	 * @param table the {@link Table} object
	 * @param column the current {@link Column} object
	 * @throws HandlerException when an unexpected error occur
	 * @see {@link #configure(Shell, String)}
	 */
	public void save(String configuration, Table table, Column column) throws HandlerException;
}
