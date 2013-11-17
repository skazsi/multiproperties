package hu.skzs.multiproperties.support.handler.writer;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.support.handler.configurator.IConfigurator;
import hu.skzs.multiproperties.support.handler.configurator.IFileSystemConfigurator;
import hu.skzs.multiproperties.support.handler.configurator.IWorkspaceConfigurator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author skzs
 * 
 */
public class WriterFactoryTest
{

	/**
	 * Test method for {@link hu.skzs.multiproperties.support.handler.writer.WriterFactory#getWriter(IConfigurator)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testGetWriterFileSystem() throws HandlerException
	{
		// fixture
		final IFileSystemConfigurator configurator = new IFileSystemConfigurator()
		{

			public void setConfiguration(final String configuration)
			{
			}

			public String getConfiguration()
			{
				return null;
			}

			public String getFileName()
			{
				return null;
			}
		};

		// when
		final IWriter writer = WriterFactory.getWriter(configurator);

		// then
		Assert.assertNotNull(writer);
		Assert.assertTrue(writer instanceof FileSystemWriter);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.support.handler.writer.WriterFactory#getWriter(IConfigurator)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testGetWriterWorkspace() throws HandlerException
	{
		// fixture
		final IWorkspaceConfigurator configurator = new IWorkspaceConfigurator()
		{

			public void setConfiguration(final String configuration)
			{
			}

			public String getConfiguration()
			{
				return null;
			}

			public String getFileName()
			{
				return null;
			}

			public String getContainerName()
			{
				return null;
			}
		};

		// when
		final IWriter writer = WriterFactory.getWriter(configurator);

		// then
		Assert.assertNotNull(writer);
		Assert.assertTrue(writer instanceof WorkspaceWriter);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.support.handler.writer.WriterFactory#getWriter(IConfigurator)}.
	 * @throws HandlerException 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetWriterUnknown() throws HandlerException
	{
		// fixture
		final IConfigurator configurator = new IConfigurator()
		{

			public void setConfiguration(final String configuration)
			{
			}

			public String getConfiguration()
			{
				return null;
			}
		};

		// when
		WriterFactory.getWriter(configurator);
	}

}
