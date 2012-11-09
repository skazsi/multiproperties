package hu.skzs.multiproperties.support.handler.configurator;

import hu.skzs.multiproperties.support.handler.writer.FileSystemWriter;
import hu.skzs.multiproperties.support.handler.writer.WorkspaceWriter;

/**
 * A {@link IWorkspaceConfigurator} implementations represent and describe handler configurations which produce
 * some kind of output files on the file system.
 * <p>Primarily the {@link FileSystemWriter} uses this kind of {@link IConfigurator} implementation.</p>
 * @author skzs
 * @see WorkspaceWriter
 * @see IWorkspaceConfigurator
 */
public interface IFileSystemConfigurator extends IConfigurator
{

	/**
	 * Returns the file name including the optional path part of the output file
	 * <p>It is allowed to return <code>null</code> which means that the output file writing should be skipped.</p>
	 * @return the file name
	 */
	public String getFileName();
}
