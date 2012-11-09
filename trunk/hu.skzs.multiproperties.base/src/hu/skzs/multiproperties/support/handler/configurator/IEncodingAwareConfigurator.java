package hu.skzs.multiproperties.support.handler.configurator;

import hu.skzs.multiproperties.support.handler.writer.WorkspaceWriter;

/**
 * Interface to be implemented by {@link IConfigurator} instances that need to deal with encoding.
 * @author skzs
 * @see IConfigurator
 * @see WorkspaceWriter
 */
public interface IEncodingAwareConfigurator
{

	/**
	 * Returns the encoding to be used or <code>null</code> if default settings should be used.
	 * @return the encoding to be used or <code>null</code> if default settings should be used
	 */
	public String getEncoding();
}
