package hu.skzs.multiproperties.base.model.fileformat;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author skzs
 * 
 */
public class SchemaConverterFactoryTest
{

	private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
	private static final String SUCCESS_1_0 = HEADER
			+ "<MultiProperties xmlns=\"hu.skzs.multiproperties\"><Version>1.0</Version><Name>MultiProperties UI</Name></MultiProperties>";
	private static final String SUCCESS_1_1 = HEADER
			+ "<MultiProperties xmlns=\"hu.skzs.multiproperties\"><Version>1.1</Version><Name>MultiProperties UI</Name></MultiProperties>";
	private static final String MISSIN_TAG = HEADER
			+ "<MultiProperties xmlns=\"hu.skzs.multiproperties\"><V_ersion>1.1</V_ersion><Name>MultiProperties UI</Name></MultiProperties>";
	private static final String INVALID_XML = HEADER
			+ "<MultiProperties xmlns=\"hu.skzs.multiproperties\"><V_ersion>1.1</Version><Name>MultiProperties UI</Name></MultiProperties>";

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
		final int major = Integer.parseInt("" + newestVersion.charAt(0));
		int minor = Integer.parseInt("" + newestVersion.charAt(2));

		// when
		minor++;
		Class.forName(SchemaConverterFactory.SCHEMA_CONVERTER_PACKAGE_PREFIX + major + "_" + minor
				+ "." + SchemaConverterFactory.SCHEMA_CONVERTER_CLASS_NAME); //$NON-NLS-1$

	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory#getSchemaConverter(byte[])}.
	 * @throws SchemaConverterException 
	 */
	@Test
	public void testgetSchemaConverter_1_0() throws SchemaConverterException
	{
		// when
		final ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter(SUCCESS_1_0.getBytes());

		// then
		Assert.assertNotNull(schemaConverter);
		Assert.assertEquals(hu.skzs.multiproperties.base.model.fileformat_1_0.SchemaConverter.VERSION,
				schemaConverter.getVersion());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory#getSchemaConverter(byte[])}.
	 * @throws SchemaConverterException 
	 */
	@Test
	public void testgetSchemaConverter_1_1() throws SchemaConverterException
	{
		// when
		final ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter(SUCCESS_1_1.getBytes());

		// then
		Assert.assertNotNull(schemaConverter);
		Assert.assertEquals(hu.skzs.multiproperties.base.model.fileformat_1_1.SchemaConverter.VERSION,
				schemaConverter.getVersion());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory#getSchemaConverter(byte[])}.
	 * @throws SchemaConverterException 
	 */
	@Test(expected = SchemaConverterException.class)
	public void testgetSchemaConverterMissingTag() throws SchemaConverterException
	{
		// when
		SchemaConverterFactory.getSchemaConverter(MISSIN_TAG.getBytes());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory#getSchemaConverter(byte[])}.
	 * @throws SchemaConverterException 
	 */
	@Test(expected = SchemaConverterException.class)
	public void testgetSchemaConverterInvalidXml() throws SchemaConverterException
	{
		// when
		SchemaConverterFactory.getSchemaConverter(INVALID_XML.getBytes());
	}
}
