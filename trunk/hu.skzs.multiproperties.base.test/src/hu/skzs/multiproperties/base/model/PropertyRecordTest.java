package hu.skzs.multiproperties.base.model;

import hu.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import hu.skzs.multiproperties.base.model.listener.IStructuralChangeListener;
import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class PropertyRecordTest
{

	private static final String VALUE = "key";
	private static final String DESCRIPTION = "description";
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
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord()}.
	 * 
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
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord(String)}.
	 * 
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
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setValue(String)} without using listener.
	 * 
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
	 * 
	 */
	@Test
	public void testSetValueWithListener()
	{
		// fixture
		IStructuralChangeListener listenerMock = EasyMock.createStrictMock(IStructuralChangeListener.class);
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
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
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
	 * 
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
	 * 
	 */
	@Test
	public void testSetDescriptionWithListener()
	{
		// fixture
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
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
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
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
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDisabled(String)}
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
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDisabled(String)} without using listener.
	 * 
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
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDisabled(String)} with using listener.
	 * 
	 */
	@Test
	public void testSetDisabledWithListener()
	{
		// fixture
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
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
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#setDisabled(String)} with using listener.
	 * Because the value is the same, it should not do anything.
	 */
	@Test
	public void testSetDisabledSameValue()
	{
		// fixture
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
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
	 * 
	 */
	@Test
	public void testSetDefaultColumnValueWithListener()
	{
		// fixture
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
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
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
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
		Column column = new Column();
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
		Column column = new Column();
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
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
		Column column = new Column();
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
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
		Column column = new Column();
		record = new PropertyRecord();
		record.putColumnValue(column, VALUE);
		// when
		String value = record.removeColumnValue(column);

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
		Column column = new Column();
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord();
		record.putColumnValue(column, VALUE);
		record.setRecordChangeListener(listenerMock);
		listenerMock.changed(record);
		EasyMock.replay(listenerMock);
		// when
		String value = record.removeColumnValue(column);

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
		Column column = new Column();
		record = new PropertyRecord();
		// when
		String value = record.removeColumnValue(column);

		// then
		Assert.assertNull(value);
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.base.model.PropertyRecord#clone()}.
	 * @throws CloneNotSupportedException 
	 */
	@Test
	public void testClone() throws CloneNotSupportedException
	{
		// fixture
		IRecordChangeListener listenerMock = EasyMock.createStrictMock(IRecordChangeListener.class);
		record = new PropertyRecord(VALUE);
		record.setDescription(DESCRIPTION);
		record.setDisabled(true);
		record.setDefaultColumnValue(VALUE);
		record.setRecordChangeListener(listenerMock);
		// when
		PropertyRecord cloned = (PropertyRecord) record.clone();

		// then
		Assert.assertNotSame(record, cloned);
		Assert.assertEquals(record.getValue(), cloned.getValue());
		Assert.assertEquals(record.getDescription(), cloned.getDescription());
		Assert.assertEquals(record.isDisabled(), cloned.isDisabled());
		Assert.assertEquals(record.getDefaultColumnValue(), cloned.getDefaultColumnValue());
		Assert.assertEquals(listenerMock, cloned.recordChangeListener);
	}
}
