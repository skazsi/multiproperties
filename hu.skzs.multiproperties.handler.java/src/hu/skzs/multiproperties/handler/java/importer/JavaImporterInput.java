package hu.skzs.multiproperties.handler.java.importer;

public class JavaImporterInput
{
	private final String fileName;
	private final String encoding;

	public JavaImporterInput(final String fileName, final String encoding)
	{
		if (fileName == null)
			throw new IllegalArgumentException("The fileName cannot be null");
		if (encoding == null)
			throw new IllegalArgumentException("The encoding cannot be null");
		this.fileName = fileName;
		this.encoding = encoding;
	}

	public String getFileName()
	{
		return fileName;
	}

	public String getEncoding()
	{
		return encoding;
	}
}
