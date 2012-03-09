package hu.skzs.multiproperties.base.model.fileformat;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.Columns;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;

public abstract class AbstractSchemaConverterTest {

    protected static final String NAME = "name";
    protected static final String DESCRIPTION = "description";
    protected static final String HANDLER = "handler";
    protected static final String COMMENT = "comment";

    /**
     * Assert whether the {@link Column} properties equals to the given properties
     * 
     * @param name
     *            the name property
     * @param description
     *            the description property
     * @param width
     *            the width property
     * @param configuration
     *            the configuration property
     * @param column
     *            the {@link Column} which properties is checked against the other properties
     */
    protected void assertEquals(String name, String description, int width, String configuration, Column column)
    {
        Assert.assertEquals(name, column.getName());
        Assert.assertEquals(description, column.getDescription());
        Assert.assertEquals(width, column.getWidth());
        Assert.assertEquals(configuration, column.getHandlerConfiguration());
    }

    /**
     * Assert whether a {@link CommentRecord} equals to the given properties
     * 
     * @param value
     *            the value property
     * @param comment
     *            the {@link CommentRecord} which properties is checked against the other properties
     */
    protected void assertEquals(String value, CommentRecord comment)
    {
        Assert.assertEquals(value, comment.getValue());
    }

    /**
     * Assert whether a {@link PropertyRecord} equals to the given properties
     * 
     * @param name
     *            the name property
     * @param description
     *            the description property
     * @param disabled
     *            the disabled property
     * @param values
     *            the column values
     * @param columns
     *            the columns
     * @param comment
     *            the {@link PropertyRecord} which properties is checked against the other properties
     */
    protected void assertEquals(String name, String description, boolean disabled, String[] values, Columns columns,
            PropertyRecord property)
    {
        Assert.assertEquals(name, property.getValue());
        Assert.assertEquals(description, property.getDescription());
        Assert.assertEquals(disabled, property.isDisabled());
        Assert.assertEquals(values.length, columns.size());
        for (int i = 0; i < values.length; i++) {
            Assert.assertEquals(values[i], property.getColumnValue(columns.get(i)));
        }

    }

    /**
     * Assert whether a {@link InputStream} equals to an another one
     * 
     * @param expected
     *            the expected input stream
     * @param got
     *            the got input stream
     * @throws IOException
     */
    protected void assertEquals(InputStream expected, InputStream got) throws IOException
    {
        byte[] expectedBytes = readBytes(expected);
        byte[] gotBytes = readBytes(got);

        Assert.assertArrayEquals(expectedBytes, gotBytes);
    }

    private byte[] readBytes(InputStream inputStream) throws IOException
    {
        byte[] bytes = new byte[inputStream.available()];
        int position = 0;
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = inputStream.read(buffer)) > -1)
        {
            System.arraycopy(buffer, 0, bytes, position, read);
            position = position + read;
        }
        return bytes;
    }
}
