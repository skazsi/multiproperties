package hu.skzs.multiproperties.support.handler.writer;

import hu.skzs.multiproperties.support.handler.configurator.IConfigurator;

/**
 * An {@link AbstractWriter} implementations are responsible for writing output files.
 * <p>The parameters of the output file (like the file name) is described by {@link IConfigurator}.</p>
 * @author skzs
 * @param <T> the type of the {@link IConfigurator} to be used by this writer
 */
public abstract class AbstractWriter<T extends IConfigurator> implements IWriter
{

	protected T configurator;

	/**
	 * Package protected constructor.
	 * @param configurator the given {@link IConfigurator} instance
	 * @see WriterFactory
	 */
	AbstractWriter(final T configurator)
	{
		this.configurator = configurator;
	}

	/**
	 * Returns the {@link IConfigurator} instance held by this writer
	 * @return the {@link IConfigurator}
	 */
	T getConfigurator()
	{
		return configurator;
	}
}
