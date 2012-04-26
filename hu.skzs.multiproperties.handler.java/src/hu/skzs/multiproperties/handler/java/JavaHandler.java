package hu.skzs.multiproperties.handler.java;

import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.handler.java.wizard.TargetPropertiesSelectionWizard;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * The <code>JavaHandler</code> is the default implementation of {@link IHandler}. It produces <code>java.util.Properties</code> typed output.
 * @author sallai
 * @see Properties
 */
public class JavaHandler implements IHandler
{

	/** A table of hex digits */
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.api.IHandler#configure(org.eclipse.swt.widgets.Shell, java.lang.String)
	 */
	public String configure(final Shell shell, final String configuration) throws CoreException
	{
		try
		{
			final ConfigurationConverter configurationConverter = new ConfigurationConverter(configuration);
			final TargetPropertiesSelectionWizard wizard = new TargetPropertiesSelectionWizard(configurationConverter);
			final WizardDialog wizarddialog = new WizardDialog(shell, wizard);
			wizarddialog.open();
			return configurationConverter.toString();
		}
		catch (final Throwable e)
		{
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR, e.getMessage(), e));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.api.IHandler#save(java.lang.String, hu.skzs.multiproperties.base.model.Table, hu.skzs.multiproperties.base.model.Column)
	 */
	public void save(final String configuration, final Table table, final Column column) throws CoreException
	{
		try
		{
			final ConfigurationConverter converter = new ConfigurationConverter(configuration);
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			final IResource resource = root.findMember(new Path(converter.getContainerName()));
			final IContainer container = (IContainer) resource;
			final IFile file = container.getFile(new Path(converter.getFileName()));

			// Writing the content
			final ByteArrayInputStream stream = new ByteArrayInputStream(convert(converter, table, column));
			if (file.exists())
			{
				file.setContents(stream, false, true, null);
			}
			else
			{
				file.create(stream, false, null);
			}
			stream.close();
		}
		catch (final Exception e)
		{
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR, e.getMessage(), e));
		}
	}

	/**
	 * Converts and returns the content of the given {@link Column} based on the given {@link ConfigurationConverter}
	 * @param converter the given converter configuration
	 * @param table the given table
	 * @param column the given column
	 * @return the converted content of the given column 
	 * @throws IOException 
	 */
	public byte[] convert(final ConfigurationConverter converter, final Table table, final Column column)
			throws IOException
	{
		final StringBuilder strb = new StringBuilder();

		// Writing the description
		if (converter.isDescriptionIncluded())
		{
			writeString(strb, table.getDescription());
			strb.append("\r\n"); //$NON-NLS-1$
		}

		// Writing the column description
		if (converter.isColumnDescriptionIncluded())
		{
			writeString(strb, column.getDescription());
			strb.append("\r\n"); //$NON-NLS-1$
		}

		// Writing the records
		for (int i = 0; i < table.size(); i++)
		{
			if (table.get(i) instanceof PropertyRecord)
			{
				final PropertyRecord record = (PropertyRecord) table.get(i);
				String value = record.getColumnValue(column);
				if (value == null)
					if (record.getDefaultColumnValue() != null && !converter.isDisableDefaultValues())
						value = record.getDefaultColumnValue();
				if (value == null)
					continue;

				// If disabled, then it will be written as a comment
				if (record.isDisabled())
					if (converter.isDisabledPropertiesIncluded())
						strb.append("#"); //$NON-NLS-1$
					else
						continue;

				strb.append(saveConvert(record.getValue(), true));
				strb.append("="); //$NON-NLS-1$
				strb.append(saveConvert(value, false));
				strb.append("\r\n"); //$NON-NLS-1$
			}
			else if (table.get(i) instanceof CommentRecord)
			{
				final CommentRecord record = (CommentRecord) table.get(i);
				strb.append("#" + record.getValue() + "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			else if (table.get(i) instanceof EmptyRecord)
			{
				strb.append("\r\n"); //$NON-NLS-1$
			}
		}

		return strb.toString().getBytes();
	}

	/**
	 * Writes a multi lined String object on the given {@link StringBuilder}.
	 * @param stringBuilder
	 * @param content
	 * @throws IOException 
	 */
	private void writeString(final StringBuilder stringBuilder, final String content) throws IOException
	{
		final BufferedReader reader = new BufferedReader(new StringReader(content));
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			stringBuilder.append("# " + line + "\r\n"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Convert a nibble to a hex character
	 * @param	nibble	the nibble to convert.
	 */
	private static char toHex(final int nibble)
	{
		return hexDigit[(nibble & 0xF)];
	}

	/*
	 * Converts unicodes to encoded &#92;uxxxx and escapes
	 * special characters with a preceding slash
	 * 
	 * Refactored from java.util.Properties class.
	 */
	private String saveConvert(final String theString, final boolean escapeSpace)
	{
		final int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0)
		{
			bufLen = Integer.MAX_VALUE;
		}
		final StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++)
		{
			final char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127))
			{
				if (aChar == '\\')
				{
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar)
			{
			case ' ':
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if ((aChar < 0x0020) || (aChar > 0x007e))
				{
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				}
				else
				{
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}
}
