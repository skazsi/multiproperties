package hu.skzs.multiproperties.handler.java.importer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hu.skzs.multiproperties.base.api.ImporterException;

public class JavaImporterInputTest
{

	@Test
	public void constructor() throws ImporterException
	{
		// When
		final JavaImporterInput underTest = new JavaImporterInput("fileName", "UTF-8");

		// Then
		assertEquals("fileName", underTest.getFileName());
		assertEquals("UTF-8", underTest.getEncoding());
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_FileNameNull() throws ImporterException
	{
		// When
		new JavaImporterInput(null, "UTF-8");
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_EncodingNull() throws ImporterException
	{
		// When
		new JavaImporterInput("fileName", null);
	}
}
