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
public class PropertyRecordTest
{

	private static final String VALUE = "key"; //$NON-NLS-1$
	private static final String DESCRIPTION = "description"; //$NON-NLS-1$
	private PropertyRecord record;

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
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#PropertyRecord()}.
	 */
	@Test
	public void testConstruction()
	{
		// when
		record = new PropertyRecord();

		// then
		Assert.assertNull(record.getValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#PropertyRecord(String)}.
	 */
	@Test
	public void testConstructionWithValue()
	{
		// when
		record = new PropertyRecord(VALUE);

		// then
		Assert.assertEquals(VALUE, record.getValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#PropertyRecord(PropertyRecord)}.
	 */
	@Test
	public void testCopyConstruction()
	{
		// fixture
		final IRecordChangeListener recordChangeListener = EasyMock.createStrictMock(IRecordChangeListener.class);
		final IStructuralChangeListener structuralChangeListener = EasyMock
				.createStrictMock(IStructuralChangeListener.class);
		record = new PropertyRecord(VALUE);
		record.setDescription(DESCRIPTION);
		record.setMultiLine(true);
		record.setDisabled(true);
		record.setDefaultColumnValue(VALUE);
		record.setRecordChangeListener(recordChangeListener);
		record.setStructuralChangeListener(structuralChangeListener);
		// when
		final PropertyRecord newRecord = new PropertyRecord(record);

		// then
		Assert.assertNotSame(record, newRecord);
		Assert.assertEquals(record.getValue(), newRecord.getValue());
		Assert.assertEquals(record.getDescription(), newRecord.getDescription());
		Assert.assertEquals(record.isMultiLine(), newRecord.isMultiLine());
		Assert.assertEquals(record.isDisabled(), newRecord.isDisabled());
		Assert.assertEquals(record.getDefaultColumnValue(), newRecord.getDefaultColumnValue());
		Assert.assertNull(newRecord.recordChangeListener);
		Assert.assertNull(newRecord.structuralChangeListener);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#set(PropertyRecord)}.
	 */
	@Test
	public void testSet()
	{
		// fixture
		final IRecordChangeListener recordChangeListener = EasyMock.createStrictMock(IRecordChangeListener.class);
		final IStructuralChangeListener structuralChangeListener = EasyMock
				.createStrictMock(IStructuralChangeListener.class);
		final PropertyRecord fromRecord = new PropertyRecord(VALUE);
		fromRecord.setDescription(DESCRIPTION);
		fromRecord.setMultiLine(true);
		fromRecord.setDisabled(true);
		fromRecord.setDefaultColumnValue(VALUE);
		fromRecord.setRecordChangeListener(recordChangeListener);
		fromRecord.setStructuralChangeListener(structuralChangeListener);

		record = new PropertyRecord(VALUE + 111);
		record.setDescription(DESCRIPTION + 111);

		// when
		record.set(fromRecord);

		// then
		Assert.assertNotSame(record, fromRecord);
		Assert.assertEquals(record.getValue(), VALUE);
		Assert.assertEquals(record.getDescription(), DESCRIPTION);
		Assert.assertTrue(record.isMultiLine());
		Assert.assertTrue(record.isDisabled());
		Assert.assertEquals(record.getDefaultColumnValue(), VALUE);
		Assert.assertNull(record.recordChangeListener);
		Assert.assertNull(record.structuralChangeListener);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setValue(String)} without using listener.
	 */
	@Test
	public void testSetValueWithoutListener()
	{
		// fixture
		record = new PropertyRecord();

		// when
		record.setValue(VALUE);

		// then
		Assert.assertEquals(VALUE, record.getValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setValue(String)} with using listener.
	 */
	@Test
	public void testSetValueWithListener()
	{
		// fixture
		final IStructuralChangeListener listenerMock = EasyMock.createStrictMock(IStructuralChangeListener.class);
		record = new PropertyRecord();
		record.setStructuralChangeListener(listenerMock);
		listenerMock.changed();
		EasyMock.replay(listenerMock);
		// when
		record.setValue(VALUE);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(VALUE, record.getValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setValue(String)} with using listener.
	 * Because the value is the same, it should not do anything.
	 */
	@Test
	public void testSetValueSameValue()
	{
		// fixture
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord(VALUE);
		record.setRecordChangeListener(listenerMock);
		EasyMock.replay(listenerMock);
		// when
		record.setValue(VALUE);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(VALUE, record.getValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDescription(String)} without using listener.
	 */
	@Test
	public void testSetDescriptionWithoutListener()
	{
		// fixture
		record = new PropertyRecord();

		// when
		record.setDescription(DESCRIPTION);

		// then
		Assert.assertEquals(DESCRIPTION, record.getDescription());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDescription(String)} with using listener.
	 */
	@Test
	public void testSetDescriptionWithListener()
	{
		// fixture
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.setRecordChangeListener(listenerMock);
		listenerMock.changed(record);
		EasyMock.replay(listenerMock);
		// when
		record.setDescription(DESCRIPTION);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(DESCRIPTION, record.getDescription());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDescription(String)} with using listener.
	 * Because the value is the same, it should not do anything.
	 */
	@Test
	public void testSetDescriptionSameValue()
	{
		// fixture
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.setDescription(DESCRIPTION);
		record.setRecordChangeListener(listenerMock);
		EasyMock.replay(listenerMock);
		// when
		record.setDescription(DESCRIPTION);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(DESCRIPTION, record.getDescription());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#isDisabled()}
	 */
	@Test
	public void testSetDisabled()
	{
		// fixture
		record = new PropertyRecord();

		// then
		Assert.assertEquals(false, record.isDisabled());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDisabled(boolean)} without using listener.
	 */
	@Test
	public void testSetDisabledWithoutListener()
	{
		// fixture
		record = new PropertyRecord();

		// when
		record.setDisabled(true);

		// then
		Assert.assertEquals(true, record.isDisabled());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDisabled(boolean)} with using listener.
	 */
	@Test
	public void testSetDisabledWithListener()
	{
		// fixture
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.setRecordChangeListener(listenerMock);
		listenerMock.changed(record);
		EasyMock.replay(listenerMock);
		// when
		record.setDisabled(true);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(true, record.isDisabled());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDisabled(boolean)} with using listener.
	 * Because the value is the same, it should not do anything.
	 */
	@Test
	public void testSetDisabledSameValue()
	{
		// fixture
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.setDisabled(true);
		record.setRecordChangeListener(listenerMock);
		EasyMock.replay(listenerMock);
		// when
		record.setDisabled(true);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(true, record.isDisabled());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#isMultiLine()}
	 */
	@Test
	public void testSetMultiLine()
	{
		// fixture
		record = new PropertyRecord();

		// then
		Assert.assertFalse(record.isMultiLine());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setMultiLine(boolean)} without using listener.
	 */
	@Test
	public void testSetMultiLineWithoutListener()
	{
		// fixture
		record = new PropertyRecord();

		// when
		record.setMultiLine(true);

		// then
		Assert.assertTrue(record.isMultiLine());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setMultiLine(boolean)} with using listener.
	 */
	@Test
	public void testSetMultiLineWithListener()
	{
		// fixture
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.setRecordChangeListener(listenerMock);
		listenerMock.changed(record);
		EasyMock.replay(listenerMock);
		// when
		record.setMultiLine(true);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(true, record.isMultiLine());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setMultiLine(boolean)} with using listener.
	 * Because the value is the same, it should not do anything.
	 */
	@Test
	public void testSetMultiLineSameValue()
	{
		// fixture
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.setMultiLine(true);
		record.setRecordChangeListener(listenerMock);
		EasyMock.replay(listenerMock);
		// when
		record.setMultiLine(true);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(true, record.isMultiLine());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDefaultColumnValue(String)} without using listener.
	 * 
	 */
	@Test
	public void testSetDefaultColumnValueWithoutListener()
	{
		// fixture
		record = new PropertyRecord();

		// when
		record.setDefaultColumnValue(VALUE);

		// then
		Assert.assertEquals(VALUE, record.getDefaultColumnValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDefaultColumnValue(String)} with using listener.
	 */
	@Test
	public void testSetDefaultColumnValueWithListener()
	{
		// fixture
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.setRecordChangeListener(listenerMock);
		listenerMock.changed(record);
		EasyMock.replay(listenerMock);
		// when
		record.setDefaultColumnValue(VALUE);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(VALUE, record.getDefaultColumnValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDefaultColumnValue(String)} with using listener.
	 * Because the value is the same, it should not do anything.
	 */
	@Test
	public void testSetDefaultColumnValueSameValue()
	{
		// fixture
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.setDefaultColumnValue(VALUE);
		record.setRecordChangeListener(listenerMock);
		EasyMock.replay(listenerMock);
		// when
		record.setDefaultColumnValue(VALUE);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(VALUE, record.getDefaultColumnValue());
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#putColumnValue(Column,String)} without using listener.
	 */
	@Test
	public void testPutColumnValueWithoutListener()
	{
		// fixture
		final Column column = new Column();
		record = new PropertyRecord();
		// when
		record.putColumnValue(column, VALUE);

		// then
		Assert.assertEquals(VALUE, record.getColumnValue(column));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#putColumnValue(Column,String)} with using listener.
	 */
	@Test
	public void testPutColumnValueWithListener()
	{
		// fixture
		final Column column = new Column();
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.setRecordChangeListener(listenerMock);
		listenerMock.changed(record);
		EasyMock.replay(listenerMock);
		// when
		record.putColumnValue(column, VALUE);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(VALUE, record.getColumnValue(column));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#putColumnValue(Column,String)} with using listener.
	 * Because the value is the same, it should not do anything.
	 */
	@Test
	public void testPutColumnValueSameValue()
	{
		// fixture
		final Column column = new Column();
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.putColumnValue(column, VALUE);
		record.setRecordChangeListener(listenerMock);
		EasyMock.replay(listenerMock);
		// when
		record.putColumnValue(column, VALUE);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(VALUE, record.getColumnValue(column));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#removeColumnValue(Column)} without using listener.
	 * Because the value is the same, it should not do anything.
	 */
	@Test
	public void testRemoveColumnValue()
	{
		// fixture
		final Column column = new Column();
		record = new PropertyRecord();
		record.putColumnValue(column, VALUE);
		// when
		final String value = record.removeColumnValue(column);

		// then
		Assert.assertEquals(VALUE, value);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#removeColumnValue(Column)} with using listener.
	 */
	@Test
	public void testRemoveColumnValueWithListener()
	{
		// fixture
		final Column column = new Column();
		final IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.putColumnValue(column, VALUE);
		record.setRecordChangeListener(listenerMock);
		listenerMock.changed(record);
		EasyMock.replay(listenerMock);
		// when
		final String value = record.removeColumnValue(column);

		// then
		EasyMock.verify(listenerMock);
		Assert.assertEquals(VALUE, value);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#removeColumnValue(Column)} with non existing key.
	 * Because the value is the same, it should not do anything.
	 */
	@Test
	public void testRemoveColumnValueWithNull()
	{
		// fixture
		final Column column = new Column();
		record = new PropertyRecord();
		// when
		final String value = record.removeColumnValue(column);

		// then
		Assert.assertNull(value);
	}
}
