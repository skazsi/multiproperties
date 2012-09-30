package hu.skzs.multiproperties.handler.text.writer;

import hu.skzs.multiproperties.handler.text.configurator.AbstractConfigurator;
import hu.skzs.multiproperties.handler.text.configurator.FileSystemConfigurator;
import hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator;

/**
 * Factory implementation for providing {@link IWriter} implementation.
 * @author sallai
 */
public class WriterFactory
{

	/**
	 * Returns an {@link IWriter} implementation based on the given <code>configurator</code> parameter.
	 * @param configurator the given {@link AbstractConfigurator} instance
	 * @return an {@link IWriter} implementation
	 */
	public static IWriter getWriter(final AbstractConfigurator configurator)
	{
		if (configurator instanceof FileSystemConfigurator)
		{
			final FileSystemConfigurator fileSystemConfigurator = (FileSystemConfigurator) configurator;
			return new FileSystemWriter(fileSystemConfigurator);
		}
		else if (configurator instanceof WorkspaceConfigurator)
		{
			final WorkspaceConfigurator workspaceConfigurator = (WorkspaceConfigurator) configurator;
			return new WorkspaceWriter(workspaceConfigurator);
		}
		else
			throw new IllegalArgumentException("Unsupported configurator instance, " + configurator); //$NON-NLS-1$
	}
}
