package hu.skzs.multiproperties.handler.text;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.handler.text.configurator.TextHandlerConfigurator;
import hu.skzs.multiproperties.handler.text.configurator.WorkspaceConfigurator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author skzs
 * 
 */
public class TextHandlerTest
{

	private static final String PATTERN_HEADER = "h ${name} r"; //$NON-NLS-1$
	private static final String PATTERN_FOOTER = "${description} footer"; //$NON-NLS-1$
	private static final String PATTERN_PROPERTY = "hello ${key} ${value}"; //$NON-NLS-1$
	private static final String PATTERN_COMMENT = "hello ${value}"; //$NON-NLS-1$
	private static final String PATTERN_EMPTY = "#hello ${name}"; //$NON-NLS-1$

	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String DESCRIPTION = "description"; //$NON-NLS-1$
	private static final String VALUE = "value"; //$NON-NLS-1$
	private static final String DEFAULT = "default"; //$NON-NLS-1$
	private static final String EN = "en"; //$NON-NLS-1$
	private static final String HU = "hu"; //$NON-NLS-1$
	private static final String COMMENT = "comment"; //$NON-NLS-1$

	private static final String TEXT_EN_SIMPLE = "h name rhello 1value 1envaluehello 2value 2envaluedescription footer"; //$NON-NLS-1$
	private static final String TEXT_HU_SIMPLE = "h name rhello 1value 1huvaluehello 2value 2huvaluedescription footer"; //$NON-NLS-1$
	private static final String TEXT_EN_DISABLED = "h name rhello 1value 1envaluedescription footer"; //$NON-NLS-1$
	private static final String TEXT_EN_DEFAULT = "h name rhello 1value 1envaluehello 2value defaultdescription footer"; //$NON-NLS-1$
	private static final String TEXT_HU_DEFAULT = "h name rhello 1value 1huvaluehello 2value 2huvaluedescription footer"; //$NON-NLS-1$
	private static final String TEXT_EN_RECORDS = "h name rhello 1value 1envalue#hello namehello commenthello 2value 2envaluedescription footer"; //$NON-NLS-1$

	private TextHandler handler;

	@Before
	public void setUp()
	{
		handler = new TextHandler();
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.TextHandler#convert(TextHandlerConfigurator, Table, Column)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConvertSimple() throws HandlerException
	{
		// fixture
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				2 + EN + VALUE, 2 + HU + VALUE }));
		final TextHandlerConfigurator configurator = createConfigurator();

		// when
		final byte[] enBytes = handler.convert(configurator, table, table.getColumns().get(0));
		final byte[] huBytes = handler.convert(configurator, table, table.getColumns().get(1));

		// then
		Assert.assertEquals(TEXT_EN_SIMPLE, new String(enBytes));
		Assert.assertEquals(TEXT_HU_SIMPLE, new String(huBytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.TextHandler#convert(TextHandlerConfigurator, Table, Column)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConvertDisabled() throws HandlerException
	{
		// fixture
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, null, true, table.getColumns().toArray(), new String[] {
				2 + EN + VALUE, 2 + HU + VALUE }));
		final TextHandlerConfigurator configurator = createConfigurator();

		// when
		final byte[] bytes = handler.convert(configurator, table, table.getColumns().get(0));

		// then
		Assert.assertEquals(TEXT_EN_DISABLED, new String(bytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.TextHandler#convert(TextHandlerConfigurator, Table, Column)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConvertDefaultValue() throws HandlerException
	{
		// fixture
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(createPropertyRecord(2 + VALUE, DEFAULT, false, table.getColumns().toArray(), new String[] { null,
				2 + HU + VALUE }));
		final TextHandlerConfigurator configurator = createConfigurator();

		// when
		final byte[] enBytes = handler.convert(configurator, table, table.getColumns().get(0));
		final byte[] huBytes = handler.convert(configurator, table, table.getColumns().get(1));

		// then
		Assert.assertEquals(TEXT_EN_DEFAULT, new String(enBytes));
		Assert.assertEquals(TEXT_HU_DEFAULT, new String(huBytes));
	}

	/**
	 * Test method for {@link hu.skzs.multiproperties.handler.text.TextHandler#convert(TextHandlerConfigurator, Table, Column)}.
	 * @throws HandlerException 
	 */
	@Test
	public void testConvertRecords() throws HandlerException
	{
		// fixture
		final Table table = createTable();
		table.add(createPropertyRecord(1 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				1 + EN + VALUE, 1 + HU + VALUE }));
		table.add(new EmptyRecord());
		table.add(new CommentRecord(COMMENT));
		table.add(createPropertyRecord(2 + VALUE, null, false, table.getColumns().toArray(), new String[] {
				2 + EN + VALUE, 2 + HU + VALUE }));
		final TextHandlerConfigurator configurator = createConfigurator();

		// when
		final byte[] bytes = handler.convert(configurator, table, table.getColumns().get(0));

		// then
		Assert.assertEquals(TEXT_EN_RECORDS, new String(bytes));
	}

	/**
	 * Returns a newly constructed test {@link TextHandlerConfigurator} with default values.
	 * @return
	 */
	private TextHandlerConfigurator createConfigurator()
	{
		return createConfigurator(PATTERN_HEADER, PATTERN_FOOTER, PATTERN_PROPERTY, PATTERN_COMMENT, PATTERN_EMPTY);
	}

	/**
	 * Returns a newly constructed test {@link TextHandlerConfigurator} instance based on the given parameters.
	 * @param header
	 * @param footer
	 * @param property
	 * @param comment
	 * @param empty
	 * @return
	 */
	private TextHandlerConfigurator createConfigurator(final String header, final String footer, final String property,
			final String comment, final String empty)
	{
		try
		{
			final TextHandlerConfigurator configurator = new WorkspaceConfigurator();
			configurator.setConfiguration(""); //$NON-NLS-1$
			configurator.setHeaderPattern(header);
			configurator.setFooterPattern(footer);
			configurator.setPropertyPattern(property);
			configurator.setCommentPattern(comment);
			configurator.setEmptyPattern(empty);
			return configurator;
		}
		catch (final HandlerException e)
		{
			throw new RuntimeException(e);
		}
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
