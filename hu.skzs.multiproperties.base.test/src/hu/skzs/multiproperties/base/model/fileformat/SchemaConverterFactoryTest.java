package hu.skzs.multiproperties.base.model.fileformat;

import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class SchemaConverterFactoryTest
{

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory#NEWEST_VERSION}.
	 * <p>No newer major version can be available.</p>
	 * @throws ClassNotFoundException 
	 * 
	 */
	@Test(expected = ClassNotFoundException.class)
	public void testNewestVersionMajor() throws ClassNotFoundException
	{
		// fixture
		final String newestVersion = SchemaConverterFactory.NEWEST_VERSION;
		int major = Integer.parseInt("" + newestVersion.charAt(0));

		// when
		major++;
		Class.forName(SchemaConverterFactory.SCHEMA_CONVERTER_PACKAGE_PREFIX + major + "_0"
				+ "." + SchemaConverterFactory.SCHEMA_CONVERTER_CLASS_NAME); //$NON-NLS-1$

	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory#NEWEST_VERSION}.
	 * <p>No newer minor version can be available.</p>
	 * @throws ClassNotFoundException 
	 * 
	 */
	@Test(expected = ClassNotFoundException.class)
	public void testNewestVersionMinor() throws ClassNotFoundException
	{
		// fixture
		final String newestVersion = SchemaConverterFactory.NEWEST_VERSION;
		int major = Integer.parseInt("" + newestVersion.charAt(0));
		int minor = Integer.parseInt("" + newestVersion.charAt(2));

		// when
		minor++;
		Class.forName(SchemaConverterFactory.SCHEMA_CONVERTER_PACKAGE_PREFIX + major + "_" + minor
				+ "." + SchemaConverterFactory.SCHEMA_CONVERTER_CLASS_NAME); //$NON-NLS-1$

	}
}
