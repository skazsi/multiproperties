package hu.skzs.multiproperties.handler.text.writer;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

/**
 * Eclipse workspace based {@link IWriter} implementation.
 * @author sallai
 */
public class WorkspaceWriter implements IWriter
{

	private final WorkspaceConfigurator configurator;

	/**
	 * Package level constructor.
	 * @param configurator the given instance of {@link WorkspaceConfigurator} to be used.
	 */
	WorkspaceWriter(final WorkspaceConfigurator configurator)
	{
		this.configurator = configurator;
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.handler.text.writer.AbstractWriter#write(byte[])
	 */
	public void write(final byte[] bytes) throws HandlerException
	{
		try
		{
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			final IResource resource = root.findMember(new Path(configurator.getContainerName()));
			if (resource == null)
				throw new HandlerException("Non existing workspace location: " //$NON-NLS-1$
						+ configurator.getContainerName());
			final IContainer container = (IContainer) resource;

			final IFile file = container.getFile(new Path(configurator.getFileName()));

			// Writing the content
			final ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			if (file.exists())
			{
				file.setContents(stream, false, true, null);
			}
			else
			{
				file.create(stream, false, null);
			}
			//file.setCharset("UTF-8", null); //$NON-NLS-1$
		}
		catch (final CoreException e)
		{
			throw new HandlerException("Unable to write the content", e); //$NON-NLS-1$
		}
	}
}
