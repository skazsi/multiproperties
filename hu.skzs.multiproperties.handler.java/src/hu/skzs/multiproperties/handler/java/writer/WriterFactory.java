package hu.skzs.multiproperties.handler.java.writer;

/**
 * Factory implementation for providing {@link Writer} implementation.
 * @author sallai
 *
 */
public class WriterFactory
{

	/**
	 * Returns a {@link Writer} implementation.
	 * <p>In case of Eclipse plugin it returns a {@link WorkspaceWriter} implementation,
	 * otherwise it returns a {@link FileSystemWriter} implementation.</p>
	 * <p>Because the {@link Writer} instantiation also includes the configuration parsing, thus
	 * the this {@link #getWriter(String)} method can throw {@link WriterConfigurationException} when the
	 * given <code>configuration</code> cannot be parsed. See {@link Writer} constructor.</p>
	 * @param configuration the given configuration
	 * @return a {@link Writer} implementation
	 * @throws WriterConfigurationException when the configuration format is invalid
	 */
	public static Writer getWriter(final String configuration) throws WriterConfigurationException
	{
		if (presenceOfEclipse())
			return new WorkspaceWriter(configuration);
		else
			return new FileSystemWriter(configuration);
	}

	/**
	 * Returns a boolean value about the presence of Eclipse.
	 * <p>The check is based on the presence of AbstractUIPlugin class.</p>
	 * @return a boolean value about the presence of Eclipse
	 */
	private static boolean presenceOfEclipse()
	{
		try
		{
			Class.forName("org.eclipse.ui.plugin.AbstractUIPlugin"); //$NON-NLS-1$
			return true;
		}
		catch (final ClassNotFoundException e)
		{
			return false;
		}
	}
}
