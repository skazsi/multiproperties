package hu.skzs.multiproperties.handler.java;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sallai
 * 
 */
public class JavaHandlerTest
{

	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String DESCRIPTION = "description"; //$NON-NLS-1$
	private static final String VALUE = "value"; //$NON-NLS-1$
	private static final String DEFAULT = "default"; //$NON-NLS-1$
	private static final String EN = "en"; //$NON-NLS-1$
	private static final String HU = "hu"; //$NON-NLS-1$
	private static final String COMMENT = "comment"; //$NON-NLS-1$

	private static final String PROP_EN_SIMPLE = "1value=1envalue\r\n2value=2envalue\r\n"; //$NON-NLS-1$
	private static final String PROP_HU_SIMPLE = "1value=1huvalue\r\n2value=2huvalue\r\n"; //$NON-NLS-1$
	private static final String PROP_EN_DESCRIPTION = "# description\r\n\r\n1value=1envalue\r\n2value=2envalue\r\n"; //$NON-NLS-1$
	private static final String PROP_EN_COLUMN_DESCRIPTION = "# endescription\r\n\r\n1value=1envalue\r\n2value=2envalue\r\n"; //$NON-NLS-1$
	private static final String PROP_EN_BOTH_DESCRIPTION = "# description\r\n\r\n# endescription\r\n\r\n1value=1envalue\r\n2value=2envalue\r\n"; //$NON-NLS-1$
	private static final String PROP_EN_DISABLED = "1value=1envalue\r\n"; //$NON-NLS-1$
	private static final String PROP_EN_DISABLED_INCLUDED = "1value=1envalue\r\n#2value=2envalue\r\n"; //$NON-NLS-1$
	private static final String PROP_EN_DEFAULT = "1value=1envalue\r\n2value=default\r\n"; //$NON-NLS-1$
	private static final String PROP_HU_DEFAULT = "1value=1huvalue\r\n2value=2huvalue\r\n"; //$NON-NLS-1$
	private static final String PROP_EN_DEFAULT_DISABLED = "1value=1envalue\r\n"; //$NON-NLS-1$
	private static final String PROP_HU_DEFAULT_DISABLED = "1value=1huvalue\r\n2value=2huvalue\r\n"; //$NON-NLS-1$
	private static final String PROP_EN_RECORDS = "1value=1envalue\r\n\r\n#comment\r\n2value=2envalue\r\n"; //$NON-NLS-1$

	private JavaHandler handler;

	@Before
	public void setUp()
	{
		handler = new JavaHandler();
	}

	@After
	public void tearDown()
	{
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.JavaHandler#convert()}.
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testConvertSimple() throws IOException
	{
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				2 + EN + VALUE, 2 + HU + VALUE }));
		final ConfigurationConverter converter = createConfigurationConverter(false, false, false, true);
		// when

		final byte[] enBytes = handler.convert(converter, table, table.getColumns().get(0));
		final byte[] huBytes = handler.convert(converter, table, table.getColumns().get(1));

		// then
		Assert.assertEquals(PROP_EN_SIMPLE, new String(enBytes));
		Assert.assertEquals(PROP_HU_SIMPLE, new String(huBytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.JavaHandler#convert()}.
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testConvertIncludeDescription() throws IOException
	{
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				2 + EN + VALUE, 2 + HU + VALUE }));
		final ConfigurationConverter converter = createConfigurationConverter(true, false, false, true);
		// when

		final byte[] bytes = handler.convert(converter, table, table.getColumns().get(0));

		// then
		Assert.assertEquals(PROP_EN_DESCRIPTION, new String(bytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.JavaHandler#convert()}.
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testConvertIncludeColumnDescription() throws IOException
	{
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				2 + EN + VALUE, 2 + HU + VALUE }));
		final ConfigurationConverter converter = createConfigurationConverter(false, true, false, true);
		// when

		final byte[] bytes = handler.convert(converter, table, table.getColumns().get(0));

		// then
		Assert.assertEquals(PROP_EN_COLUMN_DESCRIPTION, new String(bytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.JavaHandler#convert()}.
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testConvertIncludeBothDescription() throws IOException
	{
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				2 + EN + VALUE, 2 + HU + VALUE }));
		final ConfigurationConverter converter = createConfigurationConverter(true, true, false, true);
		// when

		final byte[] bytes = handler.convert(converter, table, table.getColumns().get(0));

		// then
		Assert.assertEquals(PROP_EN_BOTH_DESCRIPTION, new String(bytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.JavaHandler#convert()}.
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testConvertDisabled() throws IOException
	{
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, true, table.getColumns().toArray(), new String[] {
				2 + EN + VALUE, 2 + HU + VALUE }));
		final ConfigurationConverter converter = createConfigurationConverter(false, false, false, true);
		// when

		final byte[] bytes = handler.convert(converter, table, table.getColumns().get(0));

		// then
		Assert.assertEquals(PROP_EN_DISABLED, new String(bytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.JavaHandler#convert()}.
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testConvertDisabledIncluded() throws IOException
	{
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, true, table.getColumns().toArray(), new String[] {
				2 + EN + VALUE, 2 + HU + VALUE }));
		final ConfigurationConverter converter = createConfigurationConverter(false, false, true, true);
		// when

		final byte[] bytes = handler.convert(converter, table, table.getColumns().get(0));

		// then
		Assert.assertEquals(PROP_EN_DISABLED_INCLUDED, new String(bytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.JavaHandler#convert()}.
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testConvertDefaultValue() throws IOException
	{
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, DEFAULT, false, table.getColumns().toArray(), new String[] { null,
				2 + HU + VALUE }));
		final ConfigurationConverter converter = createConfigurationConverter(false, false, false, false);
		// when

		final byte[] enBytes = handler.convert(converter, table, table.getColumns().get(0));
		final byte[] huBytes = handler.convert(converter, table, table.getColumns().get(1));

		// then
		Assert.assertEquals(PROP_EN_DEFAULT, new String(enBytes));
		Assert.assertEquals(PROP_HU_DEFAULT, new String(huBytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.JavaHandler#convert()}.
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testConvertDefaultValueDisabled() throws IOException
	{
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, DEFAULT, false, table.getColumns().toArray(), new String[] { null,
				2 + HU + VALUE }));
		final ConfigurationConverter converter = createConfigurationConverter(false, false, false, true);
		// when

		final byte[] enBytes = handler.convert(converter, table, table.getColumns().get(0));
		final byte[] huBytes = handler.convert(converter, table, table.getColumns().get(1));

		// then
		Assert.assertEquals(PROP_EN_DEFAULT_DISABLED, new String(enBytes));
		Assert.assertEquals(PROP_HU_DEFAULT_DISABLED, new String(huBytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.java.JavaHandler#convert()}.
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testConvertRecords() throws IOException
	{
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(new EmptyRecord());
		table.add(new CommentRecord(COMMENT));
		table.add(createPropertyRecord(2 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				2 + EN + VALUE, 2 + HU + VALUE }));
		final ConfigurationConverter converter = createConfigurationConverter(false, false, false, false);
		// when

		final byte[] bytes = handler.convert(converter, table, table.getColumns().get(0));

		// then
		Assert.assertEquals(PROP_EN_RECORDS, new String(bytes));
	}

	/**
	 * Returns a newly constructed test {@link ConfigurationConverter} instance based on the given parameters.
	 * @param description
	 * @param columnDescription
	 * @param disabled
	 * @param defaultValues
	 * @return
	 */
	private ConfigurationConverter createConfigurationConverter(final boolean description,
			final boolean columnDescription, final boolean disabled, final boolean defaultValues)
	{
		final ConfigurationConverter converter = new ConfigurationConverter(""); //$NON-NLS-1$
		converter.setIncludeDescription(description);
		converter.setIncludeColumnDescription(columnDescription);
		converter.setIncludeDisabled(disabled);
		converter.setDisableDefaultValues(defaultValues);
		return converter;
	}

	/**
	 * Returns a newly constructed test {@link Table} instance. The parameters of the table are hard coded.
	 * @return
	 */
	private Table createTable()
	{
		final Table table = new Table();
		table.setName(NAME);
		table.setDescription(DESCRIPTION);

		//Columns
		final Column enColumn = new Column();
		enColumn.setName(EN);
		enColumn.setDescription(EN + DESCRIPTION);
		table.getColumns().add(enColumn);
		final Column huColumn = new Column();
		huColumn.setName(HU);
		huColumn.setDescription(HU + DESCRIPTION);
		table.getColumns().add(huColumn);

		return table;
	}

	/**
	 * Returns a newly constructed test {@link PropertyRecord} based on the the parameters.
	 * @param value
	 * @param defaultValue
	 * @param disabled
	 * @param columns
	 * @param values
	 * @return
	 */
	private PropertyRecord createPropertyRecord(final String value, final String defaultValue, final boolean disabled,
			final Column[] columns, final String[] values)
	{
		Assert.assertEquals(columns.length, values.length);

		final PropertyRecord record = new PropertyRecord();
		record.setValue(value);
		record.setDefaultColumnValue(defaultValue);
		record.setDisabled(disabled);
		for (int i = 0; i < columns.length; i++)
		{
			if (values[i] == null)
				continue;
			record.putColumnValue(columns[i], values[i]);
		}

		return record;
	}
}
