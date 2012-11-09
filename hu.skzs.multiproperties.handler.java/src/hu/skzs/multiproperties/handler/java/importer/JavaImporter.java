package hu.skzs.multiproperties.handler.java.importer;

import hu.skzs.multiproperties.base.api.IImporter;
import hu.skzs.multiproperties.base.api.ImporterException;
import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * The {@link JavaImporter} is the default implementation of {@link IImporter}.
 * It is able to importer records from <code>java.util.Properties</code> input.
 * @author skzs
 * @see Properties
 *
 */
public class JavaImporter implements IImporter
{

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.api.IImporter#getRecords(java.lang.Object)
	 */
	public List<AbstractRecord> getRecords(final Object input, final Column column) throws ImporterException
	{
		final String fileName = (String) input;
		InputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(fileName);
			final LineReader lineReader = new LineReader(new FileInputStream(fileName));

			final List<AbstractRecord> records = new LinkedList<AbstractRecord>();

			final char[] convtBuf = new char[1024];
			int limit;
			int keyLen;
			int valueStart;
			char c;
			boolean hasSep;
			boolean precedingBackslash;

			while ((limit = lineReader.readLine()) >= 0)
			{
				// Whether it is empty record
				if (limit == 0)
				{
					records.add(new EmptyRecord());
					continue;
				}

				// Whether it is comment record
				if (lineReader.getLineBuffer()[0] == '#')
				{
					final char[] lineBuffer = lineReader.getLineBuffer();
					final CommentRecord commentRecord = new CommentRecord(new String(lineBuffer, 1, limit - 1));
					records.add(commentRecord);
					continue;
				}

				// It must be a property record
				c = 0;
				keyLen = 0;
				valueStart = limit;
				hasSep = false;

				//System.out.println("line=<" + new String(lineBuf, 0, limit) + ">");
				precedingBackslash = false;
				while (keyLen < limit)
				{
					c = lineReader.getLineBuffer()[keyLen];
					//need check if escaped.
					if ((c == '=' || c == ':') && !precedingBackslash)
					{
						valueStart = keyLen + 1;
						hasSep = true;
						break;
					}
					else if ((c == ' ' || c == '\t' || c == '\f') && !precedingBackslash)
					{
						valueStart = keyLen + 1;
						break;
					}
					if (c == '\\')
					{
						precedingBackslash = !precedingBackslash;
					}
					else
					{
						precedingBackslash = false;
					}
					keyLen++;
				}
				while (valueStart < limit)
				{
					c = lineReader.getLineBuffer()[valueStart];
					if (c != ' ' && c != '\t' && c != '\f')
					{
						if (!hasSep && (c == '=' || c == ':'))
						{
							hasSep = true;
						}
						else
						{
							break;
						}
					}
					valueStart++;
				}
				final String key = loadConvert(lineReader.getLineBuffer(), 0, keyLen, convtBuf);
				final String value = loadConvert(lineReader.getLineBuffer(), valueStart, limit - valueStart, convtBuf);

				final PropertyRecord propertyRecord = new PropertyRecord();
				propertyRecord.setValue(key);
				if (column != null)
					propertyRecord.putColumnValue(column, value);
				records.add(propertyRecord);
			}

			return records;
		}
		catch (final Exception e)
		{
			throw new ImporterException("Unexpected error occurred during importing", e); //$NON-NLS-1$
		}
		finally
		{
			if (inputStream != null)
				try
				{
					inputStream.close();
				}
				catch (final IOException e)
				{
					// Not interested
				}
		}
	}

	/**
	 * Converts encoded &#92;uxxxx to unicode chars
	 * and changes special saved chars to their original forms
	 */
	private String loadConvert(final char[] in, int off, final int len, char[] convtBuf)
	{
		if (convtBuf.length < len)
		{
			int newLen = len * 2;
			if (newLen < 0)
			{
				newLen = Integer.MAX_VALUE;
			}
			convtBuf = new char[newLen];
		}
		char aChar;
		final char[] out = convtBuf;
		int outLen = 0;
		final int end = off + len;

		while (off < end)
		{
			aChar = in[off++];
			if (aChar == '\\')
			{
				aChar = in[off++];
				if (aChar == 'u')
				{
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++)
					{
						aChar = in[off++];
						switch (aChar)
						{
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed \\uxxxx encoding."); //$NON-NLS-1$
						}
					}
					out[outLen++] = (char) value;
				}
				else
				{
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					out[outLen++] = aChar;
				}
			}
			else
			{
				out[outLen++] = aChar;
			}
		}
		return new String(out, 0, outLen);
	}

}
