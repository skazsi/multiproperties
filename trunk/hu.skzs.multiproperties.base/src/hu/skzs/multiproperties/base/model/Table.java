package hu.skzs.multiproperties.base.model;

import hu.skzs.multiproperties.base.Activator;
import hu.skzs.multiproperties.base.api.IHandler;
import hu.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import hu.skzs.multiproperties.base.model.listener.IStructuralChangeListener;
import hu.skzs.multiproperties.base.model.schema.MultiProperties;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * The <tt>Table</tt> object represents the entire multiproperties object. 
 * @author sallai
 */
public class Table implements IRecordChangeListener, IStructuralChangeListener
{

	private static final String SCHEMA_PACKAGE = "hu.skzs.multiproperties.base.model.schema";

	/**
	 * The name of the multiproperties 
	 */
	private String name;
	/**
	 * The description of the multiproperties 
	 */
	private String description;
	/**
	 * The version of the multiproperties file. 
	 */
	private String version;
	/**
	 * The handler name. 
	 */
	private String handler;
	/**
	 * The key column width 
	 */
	private int width = 200;
	/**
	 * The list variable for storing the columns 
	 */
	private final Columns columns = new Columns(this);
	/**
	 * The list variable for storing the property, comment and empty records 
	 */
	private final List<AbstractRecord> records = new LinkedList<AbstractRecord>();
	/**
	 * The dirty flag 
	 */
	private boolean dirty = false;
	/**
	 * This listener is intended to inform the GUI about a record change
	 */
	private IRecordChangeListener recordChangeListener = null;
	/**
	 * This listener is intended to inform the GUI about a structural change, so the entire table should be refreshed
	 */
	private IStructuralChangeListener structuralChangeListener = null;

	/**
	 * Sets the name of the multiproperties
	 * @param name The name of the multiproperties
	 * @see #getName()
	 * @throws NullPointerException if the name parameter is <tt>null</tt>
	 */
	public void setName(final String name)
	{
		if (name == null)
			throw new NullPointerException("The name can not be null");
		this.name = name;
		setDirty(true);
	}

	/**
	 * Returns the name of the multiproperties
	 * @return the name
	 * @see #setName(String)
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the description of the multiproperties
	 * @param name The description of the multiproperties
	 * @see #getDescription()
	 * @throws NullPointerException if the description parameter is <tt>null</tt>
	 */
	public void setDescription(final String description)
	{
		if (description == null)
			throw new NullPointerException("The description can not be null");
		this.description = description;
		setDirty(true);
	}

	/**
	 * Returns the description of the multiproperties
	 * @see #setDescription(String)
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the record change listener, which object will be informed about a record change.
	 * This listener object must implement the <code>IRecordChangeListener</code> interface.
	 * @param listener the listener object
	 * @see #setStructuralChangeListener(IStructuralChangeListener)
	 * @throws NullPointerException if the listener parameter is <tt>null</tt>
	 */
	public void setRecordChangeListener(final IRecordChangeListener listener)
	{
		if (listener == null)
			throw new NullPointerException("The listener can not be null");
		this.recordChangeListener = listener;
	}

	/**
	 * Sets the structural change listener, which object will be informed about a structural change.
	 * This listener object must implement the <code>IStructuralChangeListener</code> interface.
	 * @param listener the listener object
	 * @see #setRecordChangeListener(IRecordChangeListener)
	 * @throws NullPointerException if the listener parameter is <tt>null</tt>
	 */
	public void setStructuralChangeListener(final IStructuralChangeListener listener)
	{
		if (listener == null)
			throw new NullPointerException("The listener can not be null");
		this.structuralChangeListener = listener;
	}

	/**
	 * Returns the version of the multiproperties file format.
	 * @return the version
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * Sets the name of the handler.
	 * The handlers are used for producing different file formats from a column, like the <code>java.util.Properties</code>.
	 * @param handler The name of the handler
	 * @see #getHandler()
	 * @throws NullPointerException if the handler parameter is <tt>null</tt>
	 */
	public void setHandler(final String handler)
	{
		if (handler == null)
			throw new NullPointerException("The handler name can not be null");
		if (handler.equals(this.handler))
			return;
		this.handler = handler;
		setDirty(true);
	}

	/**
	 * Returns the name of the handler.
	 * The handlers are used for producing different file formats from a column, like the <code>java.util.Properties</code>.
	 * @return the handler
	 * @see #setHandler(String)
	 */
	public String getHandler()
	{
		return handler;
	}

	/**
	 * This method is called from the nested objects reporting the record change event. Clients may not invoke this method.
	 * @param record the changed record
	 * @see #changed()
	 */
	public void changed(final AbstractRecord record)
	{
		setDirty(true, record);
	}

	/**
	 * This method is called from the nested objects reporting the structural change event. Clients may not invoke this method.
	 * @see #changed(AbstractRecord)
	 */
	public void changed()
	{
		setDirty(true);
	}

	/**
	 * Sets the actual dirty state.
	 * @param dirty The dirty state
	 */
	private void setDirty(final boolean dirty)
	{
		validate();
		this.dirty = dirty;
		if (structuralChangeListener != null)
			structuralChangeListener.changed();
	}

	/**
	 * Sets the actual dirty state.
	 * @param dirty The dirty state
	 * @param record The record that caused the dirty state
	 */
	private void setDirty(final boolean dirty, final AbstractRecord record)
	{
		this.dirty = dirty;
		if (recordChangeListener != null)
			recordChangeListener.changed(record);
	}

	/**
	 * Returns the actual dirty flag.
	 * @return The current dirty state
	 */
	public boolean isDirty()
	{
		return dirty;
	}

	/**
	 * Returns the <tt>Columns</tt> object.
	 * @return columns
	 */
	public Columns getColumns()
	{
		return columns;
	}

	/**
	 * Sets the key column width.
	 * @param width The desired with
	 */
	public void setKeyColumnWidth(final int width)
	{
		this.width = width;
		setDirty(true);
	}

	/**
	 * Returns the key column width.
	 * @return The key column width
	 */
	public int getKeyColumnWidth()
	{
		return width;
	}

	/**
	 * Adds a new record to the end of the multiproperties.
	 * @param record The new record
	 * @see #set(AbstractRecord, int)
	 * @see #insert(AbstractRecord, int)
	 * @throws NullPointerException if the record is null
	 */
	public void add(final AbstractRecord record)
	{
		if (record == null)
			throw new NullPointerException("The record can not be null");
		records.add(record);
		record.setRecordChangeListener(this);
		record.setStructuralChangeListener(this);
		if (structuralChangeListener != null) // At loading stage, the listener currently is not set
			setDirty(true);
	}

	/**
	 * Insert a new record at a position.
	 * @param record The new records
	 * @param index The position of insert
	 * @see #add(AbstractRecord)
	 * @see #set(AbstractRecord, int)
	 * @throws NullPointerException if the record is null
	 * @throws IllegalArgumentException if the index is out of the bounds
	 */
	public void insert(final AbstractRecord record, final int index)
	{
		if (record == null)
			throw new NullPointerException("The record can not be null");
		if (index < 0 || index > records.size())
			throw new IllegalArgumentException("The index is out of bounds (" + index + "/" + records.size() + ")");
		records.add(index, record);
		record.setRecordChangeListener(this);
		record.setStructuralChangeListener(this);
		setDirty(true);
	}

	/**
	 * Gives back a record at the desired position. 
	 * @param index The position
	 * @return the record
	 */
	public AbstractRecord get(final int index)
	{
		return records.get(index);
	}

	/**
	 * Returns the first position of the specified record. 
	 * @param record The record to search for
	 * @see #indexOf(String)
	 * @return the index of the first occurrence
	 */
	public int indexOf(final AbstractRecord record)
	{
		return records.indexOf(record);
	}

	/**
	 * Returns the first position of the specified property record. The comment and empty records are not involved in the search. 
	 * @param record The property string to search for
	 * @see #indexOf(AbstractRecord)
	 * @return the index of the first occurrence
	 */
	public int indexOf(final String property)
	{
		for (int i = 0; i < records.size(); i++)
		{
			if (records.get(i) instanceof PropertyRecord)
			{
				final PropertyRecord record = (PropertyRecord) records.get(i);
				if (record.getValue().equals(property))
					return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the first position of the specified property record. The comment and empty records are not involved in the search. 
	 * @param record The property string to search for
	 * @param position The position where the search should be started
	 * @see #indexOf(AbstractRecord)
	 * @return the index of the first occurrence
	 */
	public int indexOf(final String property, final int position)
	{
		for (int i = position; i < records.size(); i++)
		{
			if (records.get(i) instanceof PropertyRecord)
			{
				final PropertyRecord record = (PropertyRecord) records.get(i);
				if (record.getValue().equals(property))
					return i;
			}
		}
		return -1;
	}

	/**
	 * Replaces the current record with the aboves one 
	 * @param record The record to move up
	 */
	public void moveUp(final AbstractRecord record)
	{
		final int index = indexOf(record);
		if (index < 0)
			throw new IllegalArgumentException("The record does not exists in the table");
		records.set(index, records.get(index - 1));
		records.set(index - 1, record);
		setDirty(true);
	}

	/**
	 * Replaces the current record with the below one 
	 * @param record The record to move down
	 */
	public void moveDown(final AbstractRecord record)
	{
		final int index = indexOf(record);
		if (index < 0)
			throw new IllegalArgumentException("The record does not exists in the table");
		records.set(index, records.get(index + 1));
		records.set(index + 1, record);
		setDirty(true);
	}

	/**
	 * Removes the record the specified position.
	 * @param index The position to be removed
	 * @return The removed record
	 * @throws IllegalArgumentException if the index is out of the bounds
	 */
	public AbstractRecord remove(final int index)
	{
		if (index < 0 || index >= records.size())
			throw new IllegalArgumentException("The index is out of bounds (" + index + "/" + records.size() + ")");
		final AbstractRecord record = records.remove(index);
		setDirty(true);
		return record;
	}

	/**
	 * Removes the given records.
	 * @param records the given records
	 * @return the number of removed records
	 */
	public int remove(final AbstractRecord[] records)
	{
		int count = 0;
		for (AbstractRecord record : records)
		{
			if (this.records.remove(record))
				count++;
		}
		setDirty(true);
		return count;
	}

	/**
	 * Returns the current size of the multiproperties.
	 * @see #sizeOfProperties()
	 * @see #sizeOfDisabled()
	 * @see #sizeOfComments()
	 * @see #sizeOfEmpties()
	 * @return Counts the records
	 */
	public int size()
	{
		return records.size();
	}

	/**
	 * Returns the current size of the properties only.
	 * @see #size()
	 * @see #sizeOfDisabled()
	 * @see #sizeOfComments()
	 * @see #sizeOfEmpties()
	 * @return Counts the properties
	 */
	public int sizeOfProperties()
	{
		int count = 0;
		for (int i = 0; i < records.size(); i++)
		{
			if ((records.get(i) instanceof PropertyRecord) && !((PropertyRecord) records.get(i)).isDisabled())
				count++;
		}
		return count;
	}

	/**
	 * Returns the current size of the disabled only.
	 * @see #size()
	 * @see #sizeOfProperties()
	 * @see #sizeOfComments()
	 * @see #sizeOfEmpties()
	 * @return Counts the disabled properties
	 */
	public int sizeOfDisabled()
	{
		int count = 0;
		for (int i = 0; i < records.size(); i++)
		{
			if ((records.get(i) instanceof PropertyRecord) && ((PropertyRecord) records.get(i)).isDisabled())
				count++;
		}
		return count;
	}

	/**
	 * Returns the current size of the comments only.
	 * @see #size()
	 * @see #sizeOfProperties()
	 * @see #sizeOfDisabled()
	 * @see #sizeOfEmpties()
	 * @return Counts the comments
	 */
	public int sizeOfComments()
	{
		int count = 0;
		for (int i = 0; i < records.size(); i++)
		{
			if (records.get(i) instanceof CommentRecord)
				count++;
		}
		return count;
	}

	/**
	 * Returns the current size of the empties only.
	 * @see #size()
	 * @see #sizeOfProperties()
	 * @see #sizeOfDisabled()
	 * @see #sizeOfComments()
	 * @return Counts the empties
	 */
	public int sizeOfEmpties()
	{
		int count = 0;
		for (int i = 0; i < records.size(); i++)
		{
			if (records.get(i) instanceof EmptyRecord)
				count++;
		}
		return count;
	}

	/**
	 * Returns an array containing all of the records in this list in proper sequence (from first to last element).
	 * @return An array containing all of the records in this list in proper sequence 
	 */
	public Object[] toArray()
	{
		return records.toArray();
	}

	/**
	 * Is the current record satisfies the search criteria or not. 
	 * @param findString The string to search
	 * @param currentString The current string of the record
	 * @param caseSensitive Is the search case sensitive
	 * @param wholeWord Is the search stric to whole word
	 * @return Is the current record satisfies the search criteria
	 */
	private boolean findValue(final String findString, final String currentString, final boolean caseSensitive, final boolean wholeWord)
	{
		if (wholeWord && (findString.equalsIgnoreCase(currentString) && !caseSensitive || caseSensitive && findString.equals(currentString)))
			return true;
		if (!wholeWord && (caseSensitive && currentString.indexOf(findString) >= 0 || !caseSensitive && currentString.toLowerCase().indexOf(findString.toLowerCase()) >= 0))
			return true;
		return false;
	}

	/**
	 * Is the current record satisfies the search criteria or not.
	 * @param index The current position to search
	 * @param findString The string to search
	 * @param caseSensitive Is the search case sensitive
	 * @param wholeWord Is the search stric to whole word
	 * @return Is the current record satisfies the search criteria
	 */
	public boolean find(final int index, final String findString, final boolean caseSensitive, final boolean wholeWord)
	{
		final AbstractRecord record = get(index);
		if (record instanceof EmptyRecord)
			return false;
		if (record instanceof CommentRecord)
		{
			final CommentRecord commentrecord = (CommentRecord) record;
			if (findValue(findString, commentrecord.getValue(), caseSensitive, wholeWord))
				return true;
		}
		if (record instanceof PropertyRecord)
		{
			final PropertyRecord propertyrecord = (PropertyRecord) record;
			if (findValue(findString, propertyrecord.getValue(), caseSensitive, wholeWord))
				return true;
			for (int i = 0; i < getColumns().size(); i++)
			{
				final Column target = getColumns().get(i);
				if (propertyrecord.getColumnValue(target) == null)
					continue;
				if (findValue(findString, propertyrecord.getColumnValue(target), caseSensitive, wholeWord))
					return true;
			}
		}
		return false;
	}

	/**
	 * Validates the property based records. If there are duplicated property record with same key value, they will be marked as duplicated.
	 */
	private void validate()
	{
		final Map<String, Integer> counters = new HashMap<String, Integer>();
		for (int i = 0; i < records.size(); i++)
		{
			if (!(records.get(i) instanceof PropertyRecord))
				continue;
			final PropertyRecord record = (PropertyRecord) records.get(i);
			if (counters.get(record.getValue()) == null)
			{
				counters.put(record.getValue(), 1);
				continue;
			}
			final int counter = counters.get(record.getValue());
			counters.put(record.getValue(), counter + 1);
		}

		for (int i = 0; i < records.size(); i++)
		{
			if (!(records.get(i) instanceof PropertyRecord))
				continue;
			final PropertyRecord record = (PropertyRecord) records.get(i);
			record.setDuplicated(counters.get(record.getValue()) > 1);
		}
	}

	public void load(final IFile file)
	{
		try
		{
			final JAXBContext jaxbContext = JAXBContext.newInstance(SCHEMA_PACKAGE);
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			final MultiProperties multiproperties = (MultiProperties) unmarshaller.unmarshal(file.getContents());

			// Fill up the models
			version = multiproperties.getVersion();
			name = multiproperties.getName();
			description = multiproperties.getDescription();
			handler = multiproperties.getHandler();
			// Columns
			width = multiproperties.getColumns().getKey().getWidth();
			for (int i = 0; i < multiproperties.getColumns().getColumn().size(); i++)
			{
				final Column column = new Column();
				column.setName(multiproperties.getColumns().getColumn().get(i).getName());
				column.setDescription(multiproperties.getColumns().getColumn().get(i).getDescription());
				column.setWidth(multiproperties.getColumns().getColumn().get(i).getWidth());
				column.setHandlerConfiguration(multiproperties.getColumns().getColumn().get(i).getHandlerConfiguration());
				columns.add(column);
			}
			// Records
			final List<Object> recordlist = multiproperties.getRecords().getPropertyOrCommentOrEmpty();
			for (int i = 0; i < recordlist.size(); i++)
			{
				if (recordlist.get(i) instanceof MultiProperties.Records.Property)
				{
					final MultiProperties.Records.Property property = (MultiProperties.Records.Property) recordlist.get(i);
					final PropertyRecord record = new PropertyRecord(property.getName());
					record.setDescription(property.getDescription());
					record.setDisabled(property.isDisabled());
					for (int j = 0; j < columns.size(); j++)
					{
						if (!property.getValue().get(j).isDisabled())
							record.putColumnValue(columns.get(j), property.getValue().get(j).getValue());

					}
					add(record);
				}
				else if (recordlist.get(i) instanceof MultiProperties.Records.Comment)
				{
					final MultiProperties.Records.Comment comment = (MultiProperties.Records.Comment) recordlist.get(i);
					add(new CommentRecord(comment.getValue()));
				}
				else if (recordlist.get(i) instanceof MultiProperties.Records.Empty)
				{
					add(new EmptyRecord());
				}
			}
			setDirty(false);
		}
		catch (final Throwable e)
		{
			MessageDialog.openError(null, "Fatal", e.getMessage());
			Activator.logError(e);
		}
	}

	public void save(final IFile file)
	{
		try
		{
			// Fill up the schema
			final MultiProperties multiproperties = new MultiProperties();
			multiproperties.setVersion("2.0");
			multiproperties.setName(name);
			multiproperties.setDescription(description);
			multiproperties.setHandler(handler);
			// Columns
			multiproperties.setColumns(new MultiProperties.Columns());
			multiproperties.getColumns().setKey(new MultiProperties.Columns.Key());
			multiproperties.getColumns().getKey().setWidth(width);
			for (int i = 0; i < columns.size(); i++)
			{
				final MultiProperties.Columns.Column column = new MultiProperties.Columns.Column();
				column.setName(columns.get(i).getName());
				column.setDescription(columns.get(i).getDescription());
				column.setWidth(columns.get(i).getWidth());
				column.setHandlerConfiguration(columns.get(i).getHandlerConfiguration());
				multiproperties.getColumns().getColumn().add(column);
			}
			// Records
			multiproperties.setRecords(new MultiProperties.Records());
			for (int i = 0; i < records.size(); i++)
			{
				if (records.get(i) instanceof PropertyRecord)
				{
					final PropertyRecord propertyRecord = (PropertyRecord) records.get(i);
					final MultiProperties.Records.Property property = new MultiProperties.Records.Property();
					property.setName(propertyRecord.getValue());
					property.setDescription(propertyRecord.getDescription());
					property.setDisabled(propertyRecord.isDisabled());
					final List<MultiProperties.Records.Property.Value> values = property.getValue();
					for (int j = 0; j < columns.size(); j++)
					{
						final MultiProperties.Records.Property.Value value = new MultiProperties.Records.Property.Value();
						value.setDisabled(propertyRecord.getColumnValue(columns.get(j)) == null);
						final String propertyValue = propertyRecord.getColumnValue(columns.get(j));
						value.setValue(propertyValue != null ? propertyValue : "");
						values.add(value);
					}
					multiproperties.getRecords().getPropertyOrCommentOrEmpty().add(property);
				}
				if (records.get(i) instanceof CommentRecord)
				{
					final CommentRecord commentRecord = (CommentRecord) records.get(i);
					final MultiProperties.Records.Comment comment = new MultiProperties.Records.Comment();
					comment.setValue(commentRecord.getValue());
					multiproperties.getRecords().getPropertyOrCommentOrEmpty().add(comment);
				}
				if (records.get(i) instanceof EmptyRecord)
				{
					final MultiProperties.Records.Empty empty = new MultiProperties.Records.Empty();
					multiproperties.getRecords().getPropertyOrCommentOrEmpty().add(empty);
				}
			}

			// Saving
			file.setCharset("UTF-8", null);
			final JAXBContext jaxbContext = JAXBContext.newInstance(SCHEMA_PACKAGE);
			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			final ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
			marshaller.marshal(multiproperties, outputstream);
			final ByteArrayInputStream inputstream = new ByteArrayInputStream(outputstream.toByteArray());
			file.setContents(inputstream, IFile.KEEP_HISTORY, null);

			// Saving the columns with the configured handler
			if (!handler.equals(""))
			{
				IConfigurationElement element = null;
				final IExtensionRegistry reg = Platform.getExtensionRegistry();
				final IConfigurationElement[] extensions = reg.getConfigurationElementsFor("hu.skzs.multiproperties.handler");
				for (int i = 0; i < extensions.length; i++)
				{
					if (extensions[i].getAttribute("name").equals(handler))
					{
						element = extensions[i];
						break;
					}
				}
				final IHandler handler = (IHandler) element.createExecutableExtension("class");
				for (int i = 0; i < columns.size(); i++)
				{
					if (!columns.get(i).getHandlerConfiguration().equals(""))
					{
						try
						{
							handler.save(columns.get(i).getHandlerConfiguration(), this, columns.get(i));
						}
						catch (final CoreException e)
						{
							MessageDialog.openError(null, "Error", e.getMessage());
							Activator.log(e.getStatus());
						}
					}
				}
			}

			setDirty(false);
		}
		catch (final Throwable e)
		{
			MessageDialog.openError(null, "Fatal", e.getMessage());
			Activator.logError(e);
		}
	}

}
