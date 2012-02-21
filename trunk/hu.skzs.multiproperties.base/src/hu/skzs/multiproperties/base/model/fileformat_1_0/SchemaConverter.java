package hu.skzs.multiproperties.base.model.fileformat_1_0;

import hu.skzs.multiproperties.base.model.Column;
import hu.skzs.multiproperties.base.model.CommentRecord;
import hu.skzs.multiproperties.base.model.EmptyRecord;
import hu.skzs.multiproperties.base.model.PropertyRecord;
import hu.skzs.multiproperties.base.model.Table;
import hu.skzs.multiproperties.base.model.fileformat.ISchemaConverter;
import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.resources.IFile;

/**
 * The <code>SchemaConverter</code> is an implementation of {@link ISchemaConverter} for <code>1.0</code> format.
 * 
 * @author sallai
 */
public class SchemaConverter implements ISchemaConverter
{

	private static final String SCHEMA_PACKAGE = "hu.skzs.multiproperties.base.model.fileformat_1_0"; //$NON-NLS-1$
	private static final String SCHEMA_CHARSET = "UTF-8"; //$NON-NLS-1$

	public Table convert(IFile file) throws SchemaConverterException
	{
		try
		{
			final JAXBContext jaxbContext = JAXBContext.newInstance(SCHEMA_PACKAGE);
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			final MultiProperties multiproperties = (MultiProperties) unmarshaller.unmarshal(file.getContents());

			Table table = new Table();
			// Fill up the models
			table.setVersion(multiproperties.getVersion());
			table.setName(multiproperties.getName());
			table.setDescription(multiproperties.getDescription());
			table.setHandler(multiproperties.getHandler());
			// Columns
			table.setKeyColumnWidth(multiproperties.getColumns().getKey().getWidth());
			for (int i = 0; i < multiproperties.getColumns().getColumn().size(); i++)
			{
				final Column column = new Column();
				column.setName(multiproperties.getColumns().getColumn().get(i).getName());
				column.setDescription(multiproperties.getColumns().getColumn().get(i).getDescription());
				column.setWidth(multiproperties.getColumns().getColumn().get(i).getWidth());
				column.setHandlerConfiguration(multiproperties.getColumns().getColumn().get(i)
						.getHandlerConfiguration());
				table.getColumns().add(column);
			}
			// Records
			final List<Object> recordlist = multiproperties.getRecords().getPropertyOrCommentOrEmpty();
			for (int i = 0; i < recordlist.size(); i++)
			{
				if (recordlist.get(i) instanceof MultiProperties.Records.Property)
				{
					final MultiProperties.Records.Property property = (MultiProperties.Records.Property) recordlist
							.get(i);
					final PropertyRecord record = new PropertyRecord(property.getName());
					record.setDescription(property.getDescription());
					record.setDisabled(property.isDisabled());
					for (int j = 0; j < table.getColumns().size(); j++)
					{
						if (!property.getValue().get(j).isDisabled())
							record.putColumnValue(table.getColumns().get(j), property.getValue().get(j).getValue());

					}
					table.add(record);
				}
				else if (recordlist.get(i) instanceof MultiProperties.Records.Comment)
				{
					final MultiProperties.Records.Comment comment = (MultiProperties.Records.Comment) recordlist.get(i);
					table.add(new CommentRecord(comment.getValue()));
				}
				else if (recordlist.get(i) instanceof MultiProperties.Records.Empty)
				{
					table.add(new EmptyRecord());
				}
			}
			// TODO: this should be moved somewhere else
			table.setDirty(false);

			return table;
		}
		catch (final Exception e)
		{
			throw new SchemaConverterException("Unexpected error in schema conversion", e); //$NON-NLS-1$
		}
	}

	public void convert(IFile file, Table table) throws SchemaConverterException
	{
		try
		{
			// Fill up the schema
			final MultiProperties multiproperties = new MultiProperties();
			multiproperties.setVersion(table.getVersion());
			multiproperties.setName(table.getName());
			multiproperties.setDescription(table.getDescription());
			multiproperties.setHandler(table.getHandler());
			// Columns
			multiproperties.setColumns(new MultiProperties.Columns());
			multiproperties.getColumns().setKey(new MultiProperties.Columns.Key());
			multiproperties.getColumns().getKey().setWidth(table.getKeyColumnWidth());
			for (int i = 0; i < table.getColumns().size(); i++)
			{
				final MultiProperties.Columns.Column column = new MultiProperties.Columns.Column();
				column.setName(table.getColumns().get(i).getName());
				column.setDescription(table.getColumns().get(i).getDescription());
				column.setWidth(table.getColumns().get(i).getWidth());
				column.setHandlerConfiguration(table.getColumns().get(i).getHandlerConfiguration());
				multiproperties.getColumns().getColumn().add(column);
			}
			// Records
			multiproperties.setRecords(new MultiProperties.Records());
			for (int i = 0; i < table.size(); i++)
			{
				if (table.get(i) instanceof PropertyRecord)
				{
					final PropertyRecord propertyRecord = (PropertyRecord) table.get(i);
					final MultiProperties.Records.Property property = new MultiProperties.Records.Property();
					property.setName(propertyRecord.getValue());
					property.setDescription(propertyRecord.getDescription());
					property.setDisabled(propertyRecord.isDisabled());
					final List<MultiProperties.Records.Property.Value> values = property.getValue();
					for (int j = 0; j < table.getColumns().size(); j++)
					{
						final MultiProperties.Records.Property.Value value = new MultiProperties.Records.Property.Value();
						value.setDisabled(propertyRecord.getColumnValue(table.getColumns().get(j)) == null);
						final String propertyValue = propertyRecord.getColumnValue(table.getColumns().get(j));
						value.setValue(propertyValue != null ? propertyValue : ""); //$NON-NLS-1$
						values.add(value);
					}
					multiproperties.getRecords().getPropertyOrCommentOrEmpty().add(property);
				}
				if (table.get(i) instanceof CommentRecord)
				{
					final CommentRecord commentRecord = (CommentRecord) table.get(i);
					final MultiProperties.Records.Comment comment = new MultiProperties.Records.Comment();
					comment.setValue(commentRecord.getValue());
					multiproperties.getRecords().getPropertyOrCommentOrEmpty().add(comment);
				}
				if (table.get(i) instanceof EmptyRecord)
				{
					final MultiProperties.Records.Empty empty = new MultiProperties.Records.Empty();
					multiproperties.getRecords().getPropertyOrCommentOrEmpty().add(empty);
				}
			}

			// Saving
			file.setCharset(SCHEMA_CHARSET, null);
			final JAXBContext jaxbContext = JAXBContext.newInstance(SCHEMA_PACKAGE);
			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			final ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
			marshaller.marshal(multiproperties, outputstream);
			final ByteArrayInputStream inputstream = new ByteArrayInputStream(outputstream.toByteArray());
			file.setContents(inputstream, IFile.KEEP_HISTORY, null);

			// TODO: this should be moved somewhere else
			table.setDirty(false);
		}
		catch (final Throwable e)
		{
			throw new SchemaConverterException("Unexpected error in schema conversion", e); //$NON-NLS-1$
		}
	}

}
