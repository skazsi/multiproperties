package hu.skzs.multiproperties.support.handler.writer;

import hu.skzs.multiproperties.support.handler.configurator.IConfigurator;
import hu.skzs.multiproperties.support.handler.configurator.IFileSystemConfigurator;
import hu.skzs.multiproperties.support.handler.configurator.IWorkspaceConfigurator;

/**
 * Factory implementation for providing {@link AbstractWriter} implementation.
 * @author skzs
 */
public class WriterFactory
{

	/**
	 * Returns an {@link AbstractWriter} implementation based on the given {@link IConfigurator} parameter.
	 * <p>Clients may override the method.</p>
	 * @param configurator the given {@link IConfigurator} instance
	 * @return an {@link AbstractWriter} implementation
	 */
	public static <C extends IConfigurator> IWriter getWriter(final C configurator)
	{
		if (configurator instanceof IFileSystemConfigurator)
		{
			final IFileSystemConfigurator fileSystemConfigurator = (IFileSystemConfigurator) configurator;
			return new FileSystemWriter(fileSystemConfigurator);
		}
		else if (configurator instanceof IWorkspaceConfigurator)
		{
			final IWorkspaceConfigurator workspaceConfigurator = (IWorkspaceConfigurator) configurator;
			return new WorkspaceWriter(workspaceConfigurator);
		}
		else
			throw new IllegalArgumentException("Unsupported configurator instance, " + configurator); //$NON-NLS-1$
	}
}
