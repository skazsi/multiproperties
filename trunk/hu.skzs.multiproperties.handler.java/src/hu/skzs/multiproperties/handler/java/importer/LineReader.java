package hu.skzs.multiproperties.handler.java.importer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * The {@link LineReader} reads a properties content line by line.
 * <p>All of the property, comment and empty lines are read. If a comment line
 * starts with <code>!</code>, it will be replaced to <code>#</code>.
 * The leading white spaces are excluded. Furthermore is also supports different
 * EOL signs, such as <code>\n</code> or <code>\r\n</code>.
 * If a logical line consists of multiple natural lines, then it concatenates them.</p>
 * <p>This is a re-factored class from {@link Properties} inner <code>LineReader</code>
 * named class.</p>
 * @see Properties
 */
class LineReader
{

	private byte[] inByteBuf;
	private char[] inCharBuf;
	private char[] lineBuf = new char[1024];
	private int inLimit = 0;
	private int inOffset = 0;
	private boolean skipLF = false;
	private InputStream inStream;
	private Reader reader;

	/**
	 * Constructor for creating an instance based on an {@link InputStream}.
	 * @param inputStream the given {@link InputStream}
	 */
	public LineReader(final InputStream inputStream)
	{
		this.inStream = inputStream;
		inByteBuf = new byte[8192];
	}

	/**
	 * Constructor for creating an instance based on an {@link Reader}.
	 * @param reader the given {@link Reader}
	 */
	public LineReader(final Reader reader)
	{
		this.reader = reader;
		inCharBuf = new char[8192];
	}

	/**
	 * Returns the currently read line as a char array.
	 * <p>The array length is usually bigger than how
	 * many characters are used in real. The caller must use the returned value of {@link #readLine()} method,
	 * which specifies how many characters are valid in the returned character array.</p>
	 * <p>Usage:</p>
	 * <pre>
	 * int count = lineReader.readLine();
	 * String line = new String(lineReader.getLineBuffer(), 0, count));
	 * </pre>
	 * @see #readLine()
	 * @return the currently read line as a char array
	 */
	public char[] getLineBuffer()
	{
		return lineBuf;
	}

	/**
	 * Reads the next line of the properties content and returns the number of the characters were read.
	 * <p>If an empty line is read, then <code>0</code> is returned.
	 * If the EOF is reached, then <code>-1</code> is returned.</p>
	 * <p>The actually read line can be get by calling the {@link #getLineBuffer()} method.</p>
	 * @see #getLineBuffer()
	 * @return  the next line of the properties content and returns the number of the characters were read
	 * @throws IOException 
	 */
	public int readLine() throws IOException
	{
		int length = 0;
		char c = 0;

		boolean skipWhiteSpace = true;
		boolean isNewLine = true;
		boolean precedingBackslash = false;

		while (true)
		{
			if (inOffset >= inLimit)
			{
				inLimit = (inStream == null) ? reader.read(inCharBuf) : inStream.read(inByteBuf);
				inOffset = 0;
				if (inLimit <= 0)
				{
					if (length == 0)
						return -1;
					// Zero means empty line while -1 means EOF
					return length;
				}
			}
			if (inStream != null)
			{
				//The line below is equivalent to calling a 
				//ISO8859-1 decoder.
				c = (char) (0xff & inByteBuf[inOffset++]);
			}
			else
			{
				c = inCharBuf[inOffset++];
			}
			if (skipLF)
			{
				skipLF = false;
				if (c == '\n')
				{
					continue;
				}
			}
			if (skipWhiteSpace)
			{
				if (c == ' ' || c == '\t' || c == '\f')
				{
					continue;
				}
				skipWhiteSpace = false;
			}
			if (isNewLine)
			{
				isNewLine = false;
				if (c == '!')
				{
					c = '#';
				}
			}

			if (c != '\n' && c != '\r')
			{
				lineBuf[length++] = c;
				if (length == lineBuf.length)
				{
					int newLength = lineBuf.length * 2;
					if (newLength < 0)
					{
						newLength = Integer.MAX_VALUE;
					}
					final char[] buf = new char[newLength];
					System.arraycopy(lineBuf, 0, buf, 0, lineBuf.length);
					lineBuf = buf;
				}
				//flip the preceding backslash flag
				if (c == '\\')
				{
					precedingBackslash = !precedingBackslash;
				}
				else
				{
					precedingBackslash = false;
				}
			}
			else
			{
				// reached EOL
				if (inOffset >= inLimit)
				{
					inLimit = (inStream == null) ? reader.read(inCharBuf) : inStream.read(inByteBuf);
					inOffset = 0;
					if (inLimit <= 0)
					{
						return length;
					}
				}
				if (c == '\r')
				{
					skipLF = true;
				}
				if (precedingBackslash)
				{
					length -= 1;
					//skip the leading whitespace characters in following line
					skipWhiteSpace = true;
					precedingBackslash = false;
				}
				else
				{
					return length;
				}
			}
		}
	}
}
