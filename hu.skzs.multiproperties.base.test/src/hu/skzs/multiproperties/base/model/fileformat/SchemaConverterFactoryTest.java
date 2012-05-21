package hu.skzs.multiproperties.base.model.fileformat;

import java.io.ByteArrayInputStream;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

/**
 * @author sallai
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
		int major = Integer.parseInt("" + newestVersion.charAt(0));
		int minor = Integer.parseInt("" + newestVersion.charAt(2));

		// when
		minor++;
		Class.forName(SchemaConverterFactory.SCHEMA_CONVERTER_PACKAGE_PREFIX + major + "_" + minor
				+ "." + SchemaConverterFactory.SCHEMA_CONVERTER_CLASS_NAME); //$NON-NLS-1$

	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory#getSchemaConverter(org.eclipse.core.resources.IFile)}.
	 * @throws CoreException 
	 * @throws SchemaConverterException 
	 */
	@Test
	public void testgetSchemaConverter_1_0() throws CoreException, SchemaConverterException
	{
		// fixture
		IFile fileMock = EasyMock.createStrictMock(IFile.class);

		ByteArrayInputStream inputStream = new ByteArrayInputStream(SUCCESS_1_0.getBytes());

		EasyMock.expect(fileMock.getContents(EasyMock.anyBoolean())).andReturn(inputStream);
		EasyMock.replay(fileMock);

		// when
		ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter(fileMock);

		// then
		EasyMock.verify(fileMock);
		Assert.assertNotNull(schemaConverter);
		Assert.assertEquals(hu.skzs.multiproperties.base.model.fileformat_1_0.SchemaConverter.VERSION,
				schemaConverter.getVersion());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory#getSchemaConverter(org.eclipse.core.resources.IFile)}.
	 * @throws CoreException 
	 * @throws SchemaConverterException 
	 */
	@Test
	public void testgetSchemaConverter_1_1() throws CoreException, SchemaConverterException
	{
		// fixture
		IFile fileMock = EasyMock.createStrictMock(IFile.class);

		ByteArrayInputStream inputStream = new ByteArrayInputStream(SUCCESS_1_1.getBytes());

		EasyMock.expect(fileMock.getContents(EasyMock.anyBoolean())).andReturn(inputStream);
		EasyMock.replay(fileMock);

		// when
		ISchemaConverter schemaConverter = SchemaConverterFactory.getSchemaConverter(fileMock);

		// then
		EasyMock.verify(fileMock);
		Assert.assertNotNull(schemaConverter);
		Assert.assertEquals(hu.skzs.multiproperties.base.model.fileformat_1_1.SchemaConverter.VERSION,
				schemaConverter.getVersion());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory#getSchemaConverter(org.eclipse.core.resources.IFile)}.
	 * @throws CoreException 
	 * @throws SchemaConverterException 
	 */
	@Test(expected = SchemaConverterException.class)
	public void testgetSchemaConverterMissingTag() throws CoreException, SchemaConverterException
	{
		// fixture
		IFile fileMock = EasyMock.createStrictMock(IFile.class);

		ByteArrayInputStream inputStream = new ByteArrayInputStream(MISSIN_TAG.getBytes());

		EasyMock.expect(fileMock.getContents(EasyMock.anyBoolean())).andReturn(inputStream);
		EasyMock.replay(fileMock);

		// when
		SchemaConverterFactory.getSchemaConverter(fileMock);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory#getSchemaConverter(org.eclipse.core.resources.IFile)}.
	 * @throws CoreException 
	 * @throws SchemaConverterException 
	 */
	@Test(expected = SchemaConverterException.class)
	public void testgetSchemaConverterInvalidXml() throws CoreException, SchemaConverterException
	{
		// fixture
		IFile fileMock = EasyMock.createStrictMock(IFile.class);

		ByteArrayInputStream inputStream = new ByteArrayInputStream(INVALID_XML.getBytes());

		EasyMock.expect(fileMock.getContents(EasyMock.anyBoolean())).andReturn(inputStream);
		EasyMock.replay(fileMock);

		// when
		SchemaConverterFactory.getSchemaConverter(fileMock);
	}
}
