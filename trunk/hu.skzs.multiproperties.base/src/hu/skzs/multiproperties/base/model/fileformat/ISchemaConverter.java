package hu.skzs.multiproperties.base.model.fileformat;

import hu.skzs.multiproperties.base.model.Table;

import org.eclipse.core.resources.IFile;

/**
 * The <code>ISchemaConverter</code> interface and it implementations are responsible for
 * converting between an {@link IFile} and the models.  
 * @author sallai
 */
public interface ISchemaConverter
{

	/**
	 * Returns an instance of {@link Table} converted from the given {@link IFile}.
	 * @param inputStream the given inputStream
	 * @return an instance of {@link Table}
	 * @throws SchemaConverterException
	 */
	public Table convert(IFile file) throws SchemaConverterException;

	/**
	 * Persists the given {@link Table} instance into the given {@link IFile}..
	 * @param file the given file
	 * @param table the given table
	 * @throws SchemaConverterException
	 */
	public void convert(IFile file, Table table) throws SchemaConverterException;
}
