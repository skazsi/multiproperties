package hu.skzs.multiproperties.base.api;

import hu.skzs.multiproperties.base.model.Column;

import org.eclipse.swt.widgets.Shell;

/**
 * A {@link IHandlerConfigurator} implementation is responsible for configuring the given handler.
 * <p>The MultiProperties plugin guarantees only storing a String value for each {@link Column},
 * which is understandable by the handler only, and specifies how the handler should write out
 * the particular {@link Column}.</p>
 * @author sallai
 */
public interface IHandlerConfigurator
{

	/**
	 * This method is called by the MultiProperties plugin when the user clicks on the <strong>configure</strong>
	 * button on the <strong>Columns</strong> page for the currently selected {@link Column}.
	 * <p>The plugin provides a {@link Shell} instance to help representing any dialog or wizard by the handler, and it also
	 * provides the previous <code>configuration</code> String object.</p>
	 * <p>The returned String value is the modified handler configuration information, or <code>null</code> which means the configuration is cancelled.</p>
	 * <p>The <code>configuration</code> is String based information which is understandable by the handler only,
	 * and specifies how the handler should write out the particular {@link Column}. 
	 * The MultiProperties plugin guarantees only storing this String for each {@link Column} in the MultiProperties XML file.</p> 
	 *  
	 * @param shell the given {@link Shell}
	 * @param configuration the current configuration String for the current {@link Column} object
	 * @return the modified configuration String or <code>null</code>
	 * @throws HandlerException
	 */
	public String configure(Shell shell, String configuration) throws HandlerException;
}
