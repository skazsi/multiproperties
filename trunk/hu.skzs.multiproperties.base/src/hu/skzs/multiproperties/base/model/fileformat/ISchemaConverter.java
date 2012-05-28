package hu.skzs.multiproperties.base.model.fileformat;

import hu.skzs.multiproperties.base.model.Table;

/**
 * The <code>ISchemaConverter</code> interface and it implementations are responsible for
 * converting between a byte array and the models.  
 * @author sallai
 */
public interface ISchemaConverter
{

	/**
	 * Returns the version information.
	 * <p>Example: <code>1.0</code></p>
	 * @return the version information
	 */
	public String getVersion();

	/**
	 * Returns an instance of {@link Table} converted from the given <code>content<code>.
	 * @param content the given content
	 * @return an instance of {@link Table}
	 * @throws SchemaConverterException
	 */
	public Table convert(byte[] content) throws SchemaConverterException;

	/**
	 * Returns a newly constructed byte array converted form the given {@link Table}.
	 * @param table the given table
	 * @return the converted content as byte array
	 * @throws SchemaConverterException
	 */
	public byte[] convert(Table table) throws SchemaConverterException;
}
