package hu.skzs.multiproperties.support.handler.configurator;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.support.handler.writer.AbstractWriter;

/**
 * A {@link IConfigurator} implementations represent and describe handler configurations.
 * <p>The usage of the {@link IConfigurator} interface is <strong>not</strong> mandatory for the handlers.
 * It is just a simplification for those handlers which produce a kind of file output, such as
 * the <strong>Java Properties Handler</strong> or the <strong>Test File Handler</strong>.
 * For proper usage, also check the {@link AbstractWriter} interface.</p>
 * @author skzs
 * @see IWorkspaceConfigurator
 * @see IFileSystemConfigurator
 * @see AbstractConfiguratorFactory
 * @see AbstractWriter
 */
public interface IConfigurator
{

	/**
	 * Sets the configuration by parsing the serialised string based configuration
	 * (which is held by the MultiProperties file format). 
	 * @param configuration the given configuration string
	 * @throws HandlerException when unable to parse the configuration string
	 */
	public void setConfiguration(String configuration) throws HandlerException;

	/**
	 * Returns the serialised string based configuration.
	 * @return the serialised string based configuration
	 */
	public String getConfiguration();
}
