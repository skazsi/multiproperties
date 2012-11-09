package hu.skzs.multiproperties.support.handler.writer;

import hu.skzs.multiproperties.base.api.HandlerException;

/**
 * An {@link IWriter} implementations are responsible for writing output files.
 * @author skzs
 */
public interface IWriter
{

	/**
	 * Writes the given <code>bytes</code> content.
	 * @param bytes the given content in byte array
	 * @throws HandlerException when writing failed
	 */
	public abstract void write(byte[] bytes) throws HandlerException;
}
