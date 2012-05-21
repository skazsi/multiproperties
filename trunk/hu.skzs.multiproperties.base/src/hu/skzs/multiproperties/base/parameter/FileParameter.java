package hu.skzs.multiproperties.base.parameter;

import java.io.File;

/**
 * The {@link FileParameter} represents the input MultiProperties file
 * @author sallai
 *
 */
public class FileParameter extends ValuedParameter<File>
{

	private final File file;

	/**
	 * Default constructor
	 * @param value the given parameter value as String
	 */
	public FileParameter(final String value)
	{
		super(value);
		file = new File(value);
		if (!file.exists())
			throw new IllegalArgumentException("The file '" + value + "' does not exists"); //$NON-NLS-1$ //$NON-NLS-2$
		if (!file.isFile())
			throw new IllegalArgumentException("The '" + value + "' is not a file"); //$NON-NLS-1$ //$NON-NLS-2$
		if (!file.canRead())
			throw new IllegalArgumentException("The file '" + value + "' cannot be read"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.parameter.ValuedParameter#getValue()
	 */
	@Override
	public File getValue()
	{
		return file;
	}

}
