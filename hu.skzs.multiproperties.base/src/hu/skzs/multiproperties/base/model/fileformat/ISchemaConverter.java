package hu.skzs.multiproperties.base.model.fileformat;

import hu.skzs.multiproperties.base.model.Table;

import java.io.File;

import org.eclipse.core.resources.IFile;

/**
 * The <code>ISchemaConverter</code> interface and it implementations are responsible for
 * converting between an {@link IFile} and the models.  
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
	 * Returns an instance of {@link Table} converted from the given {@link IFile}.
	 * @param inputStream the given inputStream
	 * @return an instance of {@link Table}
	 * @throws SchemaConverterException
	 */
	public Table convert(IFile file) throws SchemaConverterException;

	/**
	 * Returns an instance of {@link Table} converted from the given {@link File}.
	 * <p><strong>Note:</strong> This method should be used only for non-standard uses cases, such
	 * as command line, debug, etc. Furthermore there is no opposing method with <code>convert(File, Table)</code>
	 * signature.</p>
	 * @param inputStream the given inputStream
	 * @return an instance of {@link Table}
	 * @throws SchemaConverterException
	 */
	public Table convert(File file) throws SchemaConverterException;

	/**
	 * Persists the given {@link Table} instance into the given {@link IFile}.
	 * <p>If the <code>file</code> does not exists, then it will be created.</p>
	 * @param file the given file
	 * @param table the given table
	 * @throws SchemaConverterException
	 */
	public void convert(IFile file, Table table) throws SchemaConverterException;
}
