package hu.skzs.multiproperties.support.handler.configurator;

import hu.skzs.multiproperties.support.handler.writer.WorkspaceWriter;

/**
 * A {@link IWorkspaceConfigurator} implementations represent and describe handler configurations which produce
 * some kind of output files into the Eclipse workspace.
 * <p>Primarily the {@link WorkspaceWriter} uses this kind of {@link IConfigurator} implementation.</p>
 * @author skzs
 * @see WorkspaceWriter
 * @see IFileSystemConfigurator
 */
public interface IWorkspaceConfigurator extends IConfigurator
{
	/**
	 * Returns the container name where the output file should be written
	 * <p>It is allowed to return <code>null</code> which means that the output file writing should be skipped.</p>
	 * @return the container name
	 */
	public String getContainerName();

	/**
	 * Returns the file name of the output file
	 * <p>It is allowed to return <code>null</code> which means that the output file writing should be skipped.</p>
	 * @return the file name
	 */
	public String getFileName();
}
