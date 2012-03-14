package hu.skzs.multiproperties.base.model;

import hu.skzs.multiproperties.base.model.fileformat.AbstractSchemaConverterTest;
import hu.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class CommentRecordTest extends AbstractSchemaConverterTest
{

	private static final String VALUE = "comment";
	private CommentRecord record;

	@Before
	public void setUp()
	{
	}

	@After
	public void tearDown()
	{
		record = null;
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.CommentRecord()}.
	 * 
	 */
	@Test
	public void testConstruction()
	{
		// when
		record = new CommentRecord();

		// then
		Assert.assertNull(record.getValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.CommentRecord(String)}.
	 * 
	 */
	@Test
	public void testConstructionWithValue()
	{
		// when
		record = new CommentRecord(VALUE);

		// then
		Assert.assertEquals(VALUE, record.getValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.CommentRecord#setValue(String)} without using listener.
	 * 
	 */
	@Test
	public void testSetValueWithoutListener()
	{
		// fixture
		record = new CommentRecord();

		// when
		record.setValue(VALUE);

		// then
		Assert.assertEquals(VALUE, record.getValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.CommentRecord#setValue(String)} with using listener.
	 * 
	 */
	@Test
	public void testSetValueWithListener()
	{
		// fixture
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new CommentRecord();
		record.setRecordChangeListener(listenerMock);
		listenerMock.changed(record);
		EasyMock.replay(listenerMock);
		// when
		record.setValue(VALUE);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(VALUE, record.getValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.CommentRecord#setValue(String)} with using listener.
	 * Because the value is the same, it should not do anything.
	 */
	@Test
	public void testSetValueSameValue()
	{
		// fixture
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new CommentRecord(VALUE);
		record.setRecordChangeListener(listenerMock);
		EasyMock.replay(listenerMock);
		// when
		record.setValue(VALUE);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(VALUE, record.getValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.CommentRecord#clone()}.
	 * @throws CloneNotSupportedException 
	 */
	@Test
	public void testClone() throws CloneNotSupportedException
	{
		// fixture
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new CommentRecord(VALUE);
		record.setRecordChangeListener(listenerMock);
		// when
		CommentRecord cloned = (CommentRecord) record.clone();

		// then
		Assert.assertNotSame(record, cloned);
		Assert.assertEquals(record.getValue(), cloned.getValue());
		Assert.assertEquals(listenerMock, cloned.recordChangeListener);
	}
}
