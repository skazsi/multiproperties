package hu.skzs.multiproperties.handler.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.handler.java.configurator.JavaConfiguratorFactory;
import hu.skzs.multiproperties.handler.java.configurator.JavaHandlerConfigurator;
import hu.skzs.multiproperties.support.handler.writer.IWriter;
import hu.skzs.multiproperties.support.handler.writer.WriterFactory;

/**
 * The <code>JavaHandler</code> is the default implementation of {@link IHandler}. It produces <code>java.util.Properties</code> typed output.
 * @author skzs
 * @see Properties
 */
public class JavaHandler implements IHandler
{

	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public void save(final String configuration, final Table table, final Column column) throws HandlerException
	{
		final JavaHandlerConfigurator configurator = JavaConfiguratorFactory.getInstance()
				.getConfigurator(configuration);
		final IWriter writer = WriterFactory.getWriter(configurator);
		writer.write(convert(configurator, table, column));
	}

	/**
	 * Converts and returns the content of the given {@link Column} based on the given {@link JavaHandlerConfigurator}
	 * @param configuration the given {@link JavaHandlerConfigurator} instance
	 * @param table the given table
	 * @param column the given column
	 * @return the converted content of the given column
	 * @throws HandlerException
	 */
	public byte[] convert(final JavaHandlerConfigurator configuration, final Table table, final Column column)
			throws HandlerException
	{
		try
		{
			final StringBuilder strb = new StringBuilder();

			// Writing the description
			if (configuration.isDescriptionIncluded())
			{
				writeString(strb, table.getDescription());
				strb.append("\r\n");
			}

			// Writing the column description
			if (configuration.isColumnDescriptionIncluded())
			{
				writeString(strb, column.getDescription());
				strb.append("\r\n");
			}

			// Writing the records
			for (int i = 0; i < table.size(); i++)
			{
				if (table.get(i) instanceof PropertyRecord)
				{
					final PropertyRecord record = (PropertyRecord) table.get(i);
					String value = record.getColumnValue(column);
					if (value == null)
						if (record.getDefaultColumnValue() != null && !configuration.isDisableDefaultValues())
							value = record.getDefaultColumnValue();
					if (value == null)
						continue;

					// If disabled, then it will be written as a comment
					if (record.isDisabled())
						if (configuration.isDisabledPropertiesIncluded())
							strb.append("#");
						else
							continue;

					strb.append(configuration.getEncoding().equalsIgnoreCase("ISO-8859-1")
							? saveConvert(record.getValue(), true)
							: record.getValue());
					strb.append("=");
					final BufferedReader reader = new BufferedReader(new StringReader(value));
					String line = null;
					boolean furtherLine = false;
					while ((line = reader.readLine()) != null)
					{
						if (furtherLine)
							strb.append("\\n\\\n\t");
						strb.append(
								configuration.getEncoding().equalsIgnoreCase("ISO-8859-1") ? saveConvert(line, false)
										: line);
						furtherLine = true;
					}
					strb.append("\r\n");
				}
				else if (table.get(i) instanceof CommentRecord)
				{
					final CommentRecord record = (CommentRecord) table.get(i);
					strb.append("#" + record.getValue() + "\r\n");
				}
				else if (table.get(i) instanceof EmptyRecord)
				{
					strb.append("\r\n");
				}
			}
			return strb.toString().getBytes(configuration.getEncoding());
		}
		catch (final Exception e)
		{
			throw new HandlerException("Unable to produce the content", e);
		}
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
			stringBuilder.append("# " + line + "\r\n");
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
