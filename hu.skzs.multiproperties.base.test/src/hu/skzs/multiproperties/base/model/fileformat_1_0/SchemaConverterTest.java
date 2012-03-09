package hu.skzs.multiproperties.base.model.fileformat_1_0;

import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.model.fileformat.AbstractSchemaConverterTest;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterException;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class SchemaConverterTest extends AbstractSchemaConverterTest {

    private static final String VERSION = "1.0";
    private static final String NORMAL_FILE = "normal.multiproperties";
    private static final String EMPTY_FILE = "empty.multiproperties";
    private SchemaConverter schemaConverter = new SchemaConverter();

    /**
     * Test method for {@link hu.skzs.multiproperties.base.model.fileformat_1_0.SchemaConverter#convert(org.eclipse.core.resources.IFile)}.
     * 
     * @throws CoreException
     * @throws SchemaConverterException
     */
    @Test
    public void testConvertIFile() throws CoreException, SchemaConverterException {
        // fixture
        IFile fileMock = EasyMock.createStrictMock(IFile.class);
        InputStream inputStream = SchemaConverterTest.class.getResourceAsStream(NORMAL_FILE);
        EasyMock.expect(fileMock.getContents(EasyMock.anyBoolean())).andReturn(inputStream);
        EasyMock.replay(fileMock);

        // when
        Table table = schemaConverter.convert(fileMock);

        // then
        EasyMock.verify(fileMock);
        Assert.assertNotNull(table);
        Assert.assertEquals(VERSION, table.getVersion());
        Assert.assertEquals(NAME, table.getName());
        Assert.assertEquals(DESCRIPTION, table.getDescription());
        Assert.assertEquals(HANDLER, table.getHandler());
        // columns
        Assert.assertEquals(100, table.getKeyColumnWidth());
        Assert.assertEquals(2, table.getColumns().size());
        assertEquals("EN", "EN " + DESCRIPTION, 200, "EN " + HANDLER, table.getColumns().get(0));
        assertEquals("HU", "HU " + DESCRIPTION, 200, "HU " + HANDLER, table.getColumns().get(1));
        // records
        Assert.assertEquals(4, table.size());
        Assert.assertTrue(table.get(0) instanceof CommentRecord);
        assertEquals(COMMENT, (CommentRecord) table.get(0));
        Assert.assertTrue(table.get(1) instanceof PropertyRecord);
        assertEquals(NAME, DESCRIPTION, false, new String[] { "EN value", "HU value" }, table.getColumns(), (PropertyRecord) table.get(1));
        Assert.assertTrue(table.get(2) instanceof PropertyRecord);
        assertEquals(NAME, DESCRIPTION, true, new String[] { "EN value", "HU value" }, table.getColumns(), (PropertyRecord) table.get(2));
        Assert.assertTrue(table.get(3) instanceof EmptyRecord);
    }

    /**
     * Test method for {@link hu.skzs.multiproperties.base.model.fileformat_1_0.SchemaConverter#convert(org.eclipse.core.resources.IFile)}.
     * 
     * @throws CoreException
     * @throws SchemaConverterException
     */
    @Test
    public void testConvertIFileEmpty() throws CoreException, SchemaConverterException {
        // fixture
        IFile fileMock = EasyMock.createStrictMock(IFile.class);
        InputStream inputStream = SchemaConverterTest.class.getResourceAsStream(EMPTY_FILE);
        EasyMock.expect(fileMock.getContents(EasyMock.anyBoolean())).andReturn(inputStream);
        EasyMock.replay(fileMock);

        // when
        Table table = schemaConverter.convert(fileMock);

        // then
        EasyMock.verify(fileMock);
        Assert.assertNotNull(table);
        Assert.assertEquals(VERSION, table.getVersion());
        Assert.assertEquals(NAME, table.getName());
        Assert.assertEquals(DESCRIPTION, table.getDescription());
        Assert.assertEquals(HANDLER, table.getHandler());
        // columns
        Assert.assertEquals(100, table.getKeyColumnWidth());
        Assert.assertEquals(0, table.getColumns().size());
        // records
        Assert.assertEquals(0, table.size());
    }

    /**
     * Test method for {@link hu.skzs.multiproperties.base.model.fileformat_1_0.SchemaConverter#convert(org.eclipse.core.resources.IFile)}.
     * 
     * @throws CoreException
     * @throws SchemaConverterException
     */
    @Test(expected = SchemaConverterException.class)
    public void testConvertIFileNull() throws CoreException, SchemaConverterException {
        // when
        schemaConverter.convert(null);
    }

    /**
     * Test method for
     * {@link hu.skzs.multiproperties.base.model.fileformat_1_0.SchemaConverter#convert(org.eclipse.core.resources.IFile, hu.skzs.multiproperties.base.model.Table)}
     * .
     * 
     * @throws SchemaConverterException
     * @throws CoreException
     * @throws IOException
     */
    @Test
    public void testConvertIFileTable() throws SchemaConverterException, CoreException, IOException {
        // fixture
        IFile fileMock = EasyMock.createNiceMock(IFile.class);
        Table table = new Table();
        table.setVersion(VERSION);
        table.setName(NAME);
        table.setDescription(DESCRIPTION);
        table.setHandler(HANDLER);
        table.setKeyColumnWidth(100);

        Capture<InputStream> capture = new Capture<InputStream>();
        fileMock.create(EasyMock.capture(capture), EasyMock.eq(true), (IProgressMonitor) EasyMock.isNull());
        EasyMock.replay(fileMock);

        // when
        schemaConverter.convert(fileMock, table);

        // then
        EasyMock.verify(fileMock);
        assertEquals(SchemaConverterTest.class.getResourceAsStream(EMPTY_FILE), capture.getValue());
    }
}
