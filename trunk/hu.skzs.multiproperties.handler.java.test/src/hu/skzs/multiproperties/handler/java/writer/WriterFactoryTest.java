package hu.skzs.multiproperties.handler.java.writer;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.handler.java.configurator.AbstractConfigurator;
import hu.skzs.multiproperties.handler.java.configurator.FileSystemConfigurator;
import hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator;
import junit.framework.Assert;

import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class WriterFactoryTest
{

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WriterFactory#getWriter(hu.skzs.multiproperties.handler.java.configurator.AbstractConfigurator)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testGetWriterFileSystem() throws HandlerException
	{
		// fixture
		final FileSystemConfigurator configurator = new FileSystemConfigurator(""); //$NON-NLS-1$

		// when
		final IWriter writer = WriterFactory.getWriter(configurator);

		// then
		Assert.assertNotNull(writer);
		Assert.assertTrue(writer instanceof FileSystemWriter);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WriterFactory#getWriter(hu.skzs.multiproperties.handler.java.configurator.AbstractConfigurator)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testGetWriterWorkspace() throws HandlerException
	{
		// fixture
		final WorkspaceConfigurator configurator = new WorkspaceConfigurator(""); //$NON-NLS-1$

		// when
		final IWriter writer = WriterFactory.getWriter(configurator);

		// then
		Assert.assertNotNull(writer);
		Assert.assertTrue(writer instanceof WorkspaceWriter);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.writer.WriterFactory#getWriter(hu.skzs.multiproperties.handler.java.configurator.AbstractConfigurator)}.
	 * @throws HandlerException 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetWriterUnknown() throws HandlerException
	{
		// fixture
		final AbstractConfigurator configurator = new AbstractConfigurator("") //$NON-NLS-1$
		{

			@Override
			public void parsePath(final String path)
			{
			}

			@Override
			public String formatPath()
			{
				return null;
			}
		};

		// when
		WriterFactory.getWriter(configurator);
	}

}
