package hu.skzs.multiproperties.support.handler.writer;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.support.handler.configurator.IEncodingAwareConfigurator;
import hu.skzs.multiproperties.support.handler.configurator.IWorkspaceConfigurator;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

/**
 * Eclipse workspace based {@link AbstractWriter} implementation.
 * @author skzs
 */
public class WorkspaceWriter extends AbstractWriter<IWorkspaceConfigurator>
{

	/**
	 * Package protected constructor.
	 * @param configurator the given {@link IWorkspaceConfigurator} instance
	 * @see WriterFactory
	 */
	WorkspaceWriter(final IWorkspaceConfigurator configurator)
	{
		super(configurator);
	}

	/**
	 * {@inheritDoc}
	 * If the provided {@link IWorkspaceConfigurator} instance also implements the
	 * {@link IEncodingAwareConfigurator} interface, then the {@link IEncodingAwareConfigurator#getEncoding()}
	 * encoding will be set on the output file.
	 */
	public void write(final byte[] bytes) throws HandlerException
	{
		if (configurator.getContainerName() == null || configurator.getFileName() == null)
			return;
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

			// Setting the encoding
			if (configurator instanceof IEncodingAwareConfigurator)
			{
				final IEncodingAwareConfigurator encodingAwareConfigurator = (IEncodingAwareConfigurator) configurator;
				if (encodingAwareConfigurator.getEncoding() != null)
					file.setCharset(encodingAwareConfigurator.getEncoding(), null);
				else
					file.setCharset(null, null);
			}
		}
		catch (final CoreException e)
		{
			throw new HandlerException("Unable to write the content", e); //$NON-NLS-1$
		}
	}
}
