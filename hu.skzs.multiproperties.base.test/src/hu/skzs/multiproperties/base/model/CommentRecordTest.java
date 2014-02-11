package hu.skzs.multiproperties.base.model;

import hu.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import hu.skzs.multiproperties.base.model.listener.IStructuralChangeListener;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author skzs
 * 
 */
public class CommentRecordTest
{

	private static final String VALUE = "comment"; //$NON-NLS-1$
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
	 * Test method for {@link hu.skzs.multiproperties.base.model.CommentRecord#CommentRecord()}.
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
	 * Test method for {@link hu.skzs.multiproperties.base.model.CommentRecord#CommentRecord(String)}.
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
	 * Test method for {@link hu.skzs.multiproperties.base.model.CommentRecord#CommentRecord(CommentRecord)}.
	 */
	@Test
	public void testCopyConstruction()
	{
		// fixture
		final IRecordChangeListener recordChangeListener = EasyMock.createStrictMock(IRecordChangeListener.class);
		final IStructuralChangeListener structuralChangeListener = EasyMock
				.createStrictMock(IStructuralChangeListener.class);
		record = new CommentRecord(VALUE);
		record.setRecordChangeListener(recordChangeListener);
		record.setStructuralChangeListener(structuralChangeListener);

		// when
		final CommentRecord newRecord = new CommentRecord(record);

		// then
		Assert.assertNotSame(record, newRecord);
		Assert.assertEquals(record.getValue(), newRecord.getValue());
		Assert.assertNull(newRecord.recordChangeListener);
		Assert.assertNull(newRecord.structuralChangeListener);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.CommentRecord#set(CommentRecord)}.
	 */
	@Test
	public void testSet()
	{
		// fixture
		final IRecordChangeListener recordChangeListener = EasyMock.createStrictMock(IRecordChangeListener.class);
		final IStructuralChangeListener structuralChangeListener = EasyMock
				.createStrictMock(IStructuralChangeListener.class);
		final CommentRecord fromRecord = new CommentRecord(VALUE);
		fromRecord.setRecordChangeListener(recordChangeListener);
		fromRecord.setStructuralChangeListener(structuralChangeListener);

		record = new CommentRecord(VALUE + 111);

		// when
		record.set(fromRecord);

		// then
		Assert.assertNotSame(record, fromRecord);
		Assert.assertEquals(record.getValue(), VALUE);
		Assert.assertNull(record.recordChangeListener);
		Assert.assertNull(record.structuralChangeListener);
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
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
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
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new CommentRecord(VALUE);
		record.setRecordChangeListener(listenerMock);
		EasyMock.replay(listenerMock);
		// when
		record.setValue(VALUE);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(VALUE, record.getValue());
	}
}
