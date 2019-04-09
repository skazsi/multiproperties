package hu.skzs.multiproperties.handler.java;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.handler.java.configurator.JavaHandlerConfigurator;
import hu.skzs.multiproperties.handler.java.configurator.WorkspaceConfigurator;

public class JavaHandlerTest
{

	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String VALUE = "value";
	private static final String DEFAULT = "default";
	private static final String EN = "en";
	private static final String HU = "hu";
	private static final String COMMENT = "comment";

	private static final String PROP_EN_SIMPLE = "1value=1envalue\r\n2value=2envalue\r\n";
	private static final String PROP_HU_SIMPLE = "1value=1huvalue\r\n2value=2huvalue\r\n";
	private static final String PROP_MULTILINE = "1value=1\\n\\\n\ten\\n\\\n\tvalue\r\n";
	private static final String PROP_EN_DESCRIPTION = "# description\r\n\r\n1value=1envalue\r\n2value=2envalue\r\n";
	private static final String PROP_EN_COLUMN_DESCRIPTION = "# endescription\r\n\r\n1value=1envalue\r\n2value=2envalue\r\n";
	private static final String PROP_EN_BOTH_DESCRIPTION = "# description\r\n\r\n# endescription\r\n\r\n1value=1envalue\r\n2value=2envalue\r\n";
	private static final String PROP_EN_DISABLED = "1value=1envalue\r\n";
	private static final String PROP_EN_DISABLED_INCLUDED = "1value=1envalue\r\n#2value=2envalue\r\n";
	private static final String PROP_EN_DEFAULT = "1value=1envalue\r\n2value=default\r\n";
	private static final String PROP_HU_DEFAULT = "1value=1huvalue\r\n2value=2huvalue\r\n";
	private static final String PROP_EN_DEFAULT_DISABLED = "1value=1envalue\r\n";
	private static final String PROP_HU_DEFAULT_DISABLED = "1value=1huvalue\r\n2value=2huvalue\r\n";
	private static final String PROP_EN_RECORDS = "1value=1envalue\r\n\r\n#comment\r\n2value=2envalue\r\n";
	private static final String PROP_HU_RECORDS_UTF8 = "1value=1huvalue\r\n\r\n#comment\r\n2value=2hu\u0151\r\n";

	private final JavaHandler underTest = new JavaHandler();

	private final Table table = createTable();

	private Table createTable()
	{
		final Table table = new Table();
		table.setName(NAME);
		table.setDescription(DESCRIPTION);

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

	@Test
	public void convertSimple() throws HandlerException
	{
		// Given
		table.add(createPropertyRecord(1 + VALUE, null, false, new String[] { 1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, false, new String[] { 2 + EN + VALUE, 2 + HU + VALUE }));
		final JavaHandlerConfigurator configurator = createConfigurator(false, false, false, true, "ISO-8859-1");

		// When
		final byte[] enBytes = underTest.convert(configurator, table, table.getColumns().get(0));
		final byte[] huBytes = underTest.convert(configurator, table, table.getColumns().get(1));

		// Then
		assertEquals(PROP_EN_SIMPLE, new String(enBytes));
		assertEquals(PROP_HU_SIMPLE, new String(huBytes));
	}

	@Test
	public void convertMultiline() throws HandlerException
	{
		// Given
		final PropertyRecord record = createPropertyRecord(1 + VALUE, null, false,
				new String[] { 1 + "\n" + EN + "\n" + VALUE, null });
		record.setMultiLine(true);
		table.add(record);
		final JavaHandlerConfigurator configurator = createConfigurator(false, false, false, true, "ISO-8859-1");

		// When
		final byte[] bytes = underTest.convert(configurator, table, table.getColumns().get(0));

		// Then
		assertEquals(PROP_MULTILINE, new String(bytes));
	}

	@Test
	public void convertIncludeDescription() throws HandlerException
	{
		// Given
		table.add(createPropertyRecord(1 + VALUE, null, false, new String[] { 1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, false, new String[] { 2 + EN + VALUE, 2 + HU + VALUE }));
		final JavaHandlerConfigurator configurator = createConfigurator(true, false, false, true, "ISO-8859-1");

		// When
		final byte[] bytes = underTest.convert(configurator, table, table.getColumns().get(0));

		// Then
		assertEquals(PROP_EN_DESCRIPTION, new String(bytes));
	}

	@Test
	public void convertIncludeColumnDescription() throws HandlerException
	{
		// Given
		table.add(createPropertyRecord(1 + VALUE, null, false, new String[] { 1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, false, new String[] { 2 + EN + VALUE, 2 + HU + VALUE }));
		final JavaHandlerConfigurator configurator = createConfigurator(false, true, false, true, "ISO-8859-1");

		// When
		final byte[] bytes = underTest.convert(configurator, table, table.getColumns().get(0));

		// Then
		assertEquals(PROP_EN_COLUMN_DESCRIPTION, new String(bytes));
	}

	@Test
	public void convertIncludeBothDescription() throws HandlerException
	{
		// Given
		table.add(createPropertyRecord(1 + VALUE, null, false, new String[] { 1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, false, new String[] { 2 + EN + VALUE, 2 + HU + VALUE }));
		final JavaHandlerConfigurator configurator = createConfigurator(true, true, false, true, "ISO-8859-1");

		// When
		final byte[] bytes = underTest.convert(configurator, table, table.getColumns().get(0));

		// Then
		assertEquals(PROP_EN_BOTH_DESCRIPTION, new String(bytes));
	}

	@Test
	public void convertDisabled() throws HandlerException
	{
		// Given
		table.add(createPropertyRecord(1 + VALUE, null, false, new String[] { 1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, true, new String[] { 2 + EN + VALUE, 2 + HU + VALUE }));
		final JavaHandlerConfigurator configurator = createConfigurator(false, false, false, true, "ISO-8859-1");

		// When
		final byte[] bytes = underTest.convert(configurator, table, table.getColumns().get(0));

		// Then
		assertEquals(PROP_EN_DISABLED, new String(bytes));
	}

	@Test
	public void convertDisabledIncluded() throws HandlerException
	{
		// Given
		table.add(createPropertyRecord(1 + VALUE, null, false, new String[] { 1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, true, new String[] { 2 + EN + VALUE, 2 + HU + VALUE }));
		final JavaHandlerConfigurator configurator = createConfigurator(false, false, true, true, "ISO-8859-1");

		// When
		final byte[] bytes = underTest.convert(configurator, table, table.getColumns().get(0));

		// Then
		assertEquals(PROP_EN_DISABLED_INCLUDED, new String(bytes));
	}

	@Test
	public void convertDefaultValue() throws HandlerException
	{
		// Given
		table.add(createPropertyRecord(1 + VALUE, null, false, new String[] { 1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, DEFAULT, false, new String[] { null, 2 + HU + VALUE }));
		final JavaHandlerConfigurator configurator = createConfigurator(false, false, false, false, "ISO-8859-1");

		// When
		final byte[] enBytes = underTest.convert(configurator, table, table.getColumns().get(0));
		final byte[] huBytes = underTest.convert(configurator, table, table.getColumns().get(1));

		// Then
		assertEquals(PROP_EN_DEFAULT, new String(enBytes));
		assertEquals(PROP_HU_DEFAULT, new String(huBytes));
	}

	@Test
	public void convertDefaultValueDisabled() throws HandlerException
	{
		// Given
		table.add(createPropertyRecord(1 + VALUE, null, false, new String[] { 1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, DEFAULT, false, new String[] { null, 2 + HU + VALUE }));
		final JavaHandlerConfigurator configurator = createConfigurator(false, false, false, true, "ISO-8859-1");

		// When
		final byte[] enBytes = underTest.convert(configurator, table, table.getColumns().get(0));
		final byte[] huBytes = underTest.convert(configurator, table, table.getColumns().get(1));

		// Then
		assertEquals(PROP_EN_DEFAULT_DISABLED, new String(enBytes));
		assertEquals(PROP_HU_DEFAULT_DISABLED, new String(huBytes));
	}

	@Test
	public void convertRecords() throws HandlerException
	{
		// Given
		table.add(createPropertyRecord(1 + VALUE, null, false, new String[] { 1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(new EmptyRecord());
		table.add(new CommentRecord(COMMENT));
		table.add(createPropertyRecord(2 + VALUE, null, false, new String[] { 2 + EN + VALUE, 2 + HU + VALUE }));
		final JavaHandlerConfigurator configurator = createConfigurator(false, false, false, false, "ISO-8859-1");

		// When
		final byte[] bytes = underTest.convert(configurator, table, table.getColumns().get(0));

		// Then
		assertEquals(PROP_EN_RECORDS, new String(bytes));
	}

	@Test
	public void convertRecords_UTF8() throws Exception
	{
		// Given
		table.add(createPropertyRecord(1 + VALUE, null, false, new String[] { 1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(new EmptyRecord());
		table.add(new CommentRecord(COMMENT));
		table.add(createPropertyRecord(2 + VALUE, null, false, new String[] { 2 + EN + VALUE, 2 + HU + "\u0151" }));
		final JavaHandlerConfigurator configurator = createConfigurator(false, false, false, false, "UTF-8");

		// When
		final byte[] bytes = underTest.convert(configurator, table, table.getColumns().get(1));

		// Then
		assertEquals(PROP_HU_RECORDS_UTF8, new String(bytes, "UTF-8"));
	}

	private JavaHandlerConfigurator createConfigurator(final boolean description, final boolean columnDescription,
			final boolean disabled, final boolean defaultValues, final String encoding)
	{
		try
		{
			final JavaHandlerConfigurator configurator = new WorkspaceConfigurator();
			configurator.setConfiguration("/Test/not_important.properties" + JavaHandlerConfigurator.DELIM + description
					+ JavaHandlerConfigurator.DELIM + columnDescription + JavaHandlerConfigurator.DELIM + disabled
					+ JavaHandlerConfigurator.DELIM + defaultValues + JavaHandlerConfigurator.DELIM + encoding);
			return configurator;
		}
		catch (final HandlerException e)
		{
			throw new RuntimeException(e);
		}
	}

	private PropertyRecord createPropertyRecord(final String value, final String defaultValue, final boolean disabled,
			final String[] values)
	{
		assertEquals(table.getColumns().size(), values.length);

		final PropertyRecord record = new PropertyRecord();
		record.setValue(value);
		record.setDefaultColumnValue(defaultValue);
		record.setDisabled(disabled);
		for (int i = 0; i < table.getColumns().size(); i++)
		{
			if (values[i] == null)
				continue;
			record.putColumnValue(table.getColumns().get(i), values[i]);
		}

		return record;
	}
}
