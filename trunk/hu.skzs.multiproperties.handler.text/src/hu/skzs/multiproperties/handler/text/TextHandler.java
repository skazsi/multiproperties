package hu.skzs.multiproperties.handler.text;

import hu.skzs.multiproperties.base.api.HandlerException;
import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.model.AbstractRecord;
import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.handler.text.configurator.TextConfiguratorFactory;
import hu.skzs.multiproperties.handler.text.configurator.TextHandlerConfigurator;
import hu.skzs.multiproperties.support.handler.writer.IWriter;
import hu.skzs.multiproperties.support.handler.writer.WriterFactory;

public class TextHandler implements IHandler
{

	private static final String MARKER_NAME = "${name}"; //$NON-NLS-1$
	private static final String MARKER_DESCRIPTION = "${description}"; //$NON-NLS-1$
	private static final String MARKER_COLUMNNAME = "${columnName}"; //$NON-NLS-1$
	private static final String MARKER_COLUMNDESCRIPTION = "${columnDescription}"; //$NON-NLS-1$
	private static final String MARKER_KEY = "${key}"; //$NON-NLS-1$
	private static final String MARKER_VALUE = "${value}"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * @see hu.skzs.multiproperties.base.api.IHandler#save(java.lang.String, hu.skzs.multiproperties.base.model.Table, hu.skzs.multiproperties.base.model.Column)
	 */
	public void save(final String configuration, final Table table, final Column column) throws HandlerException
	{
		final TextHandlerConfigurator configurator = TextConfiguratorFactory.getInstance().getConfigurator(
				configuration);
		final IWriter writer = WriterFactory.getWriter(configurator);
		writer.write(convert(configurator, table, column));
	}

	/**
	 * Converts and returns the content of the given {@link Column} based on the given {@link TextHandlerConfigurator}
	 * @param configuration the given {@link TextHandlerConfigurator} instance
	 * @param table the given table
	 * @param column the given column
	 * @return the converted content of the given column
	 * @throws HandlerException 
	 */
	public byte[] convert(final TextHandlerConfigurator configuration, final Table table, final Column column)
			throws HandlerException
	{
		try
		{
			final StringBuilder strb = new StringBuilder();
			strb.append(replaceMarkers(configuration.getHeaderPattern(), table, column, null));
			for (int i = 0; i < table.size(); i++)
			{
				final AbstractRecord record = table.get(i);
				String pattern = null;
				if (record instanceof PropertyRecord)
				{
					final PropertyRecord propertyRecord = (PropertyRecord) record;
					if (propertyRecord.isDisabled())
						continue;
					pattern = configuration.getPropertyPattern();
				}
				else if (record instanceof CommentRecord)
					pattern = configuration.getCommentPattern();
				else
					pattern = configuration.getEmptyPattern();
				strb.append(replaceMarkers(pattern, table, column, record));
			}
			strb.append(replaceMarkers(configuration.getFooterPattern(), table, column, null));

			if (configuration.getEncoding() != null)
				return strb.toString().getBytes(configuration.getEncoding());
			else
			{
				return strb.toString().getBytes();
			}
		}
		catch (final Exception e)
		{
			throw new HandlerException("Unable to produce the content", e); //$NON-NLS-1$
		}
	}

	/**
	 * Returns the replaced pattern.
	 * <p>The following replacement are done</p>
	 * <ul>
	 * <li>{@link TextHandler#MARKER_NAME} to {@link Table#getName()}</li> 
	 * <li>{@link TextHandler#MARKER_DESCRIPTION} to {@link Table#getDescription()}</li> 
	 * <li>{@link TextHandler#MARKER_COLUMNNAME} to {@link Column#getName()} </li>
	 * <li>{@link TextHandler#MARKER_COLUMNDESCRIPTION} to {@link Column#getDescription()}</li> 
	 * <li>{@link TextHandler#MARKER_KEY} to {@link PropertyRecord#getValue()},
	 * but only in that case if the given <code>record</code> is a {@link PropertyRecord}</li> 
	 * <li>{@link TextHandler#MARKER_VALUE} to {@link PropertyRecord#getColumnValue(Column)},
	 * but only in that case if the given <code>record</code> is a {@link PropertyRecord}.
	 * If the {@link PropertyRecord#getColumnValue(Column)} returns <code>null</code>, then the
	 * {@link PropertyRecord#getDefaultColumnValue()} will be used.</li> 
	 * <li>{@link TextHandler#MARKER_VALUE} to {@link CommentRecord#getValue()},
	 * but only in that case if the given <code>record</code> is a {@link CommentRecord}.</li> 
	 * </ul>
	 * @param pattern the original value
	 * @param table the given {@link Table} instance
	 * @param column the given {@link Column} instance
	 * @param record the given {@link AbstractRecord} instance
	 * @return the replaced pattern
	 */
	private String replaceMarkers(final String pattern, final Table table, final Column column,
			final AbstractRecord record)
	{
		// TODO: the "replace" can be quite time sensitive operation. A kind of caching could speed up the overall speed 

		String replaced = pattern.replace(MARKER_NAME, table.getName());
		replaced = replaced.replace(MARKER_DESCRIPTION, table.getDescription());
		replaced = replaced.replace(MARKER_COLUMNNAME, column.getName());
		replaced = replaced.replace(MARKER_COLUMNDESCRIPTION, column.getDescription());
		if (record != null)
		{
			String key = null;
			String value = null;
			if (record instanceof PropertyRecord)
			{
				final PropertyRecord propertyRecord = (PropertyRecord) record;
				key = propertyRecord.getValue();
				value = propertyRecord.getColumnValue(column);
				if (value == null)
					value = propertyRecord.getDefaultColumnValue();
			}
			else if (record instanceof CommentRecord)
			{
				final CommentRecord commentRecord = (CommentRecord) record;
				value = commentRecord.getValue();
			}
			if (key != null)
				replaced = replaced.replace(MARKER_KEY, key);
			if (value != null)
				replaced = replaced.replace(MARKER_VALUE, value);
		}
		return replaced;
	}
}
