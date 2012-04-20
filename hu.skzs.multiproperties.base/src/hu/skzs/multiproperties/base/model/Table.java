package hu.skzs.multiproperties.base.model;

import hu.skzs.multiproperties.base.model.fileformat.SchemaConverterFactory;
import hu.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import hu.skzs.multiproperties.base.model.listener.IStructuralChangeListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;

/**
 * The <code>Table</code> object represents the entire MultiProperties object. It is basically the root model object of a MultiProperties
 * content.
 * <p>
 * The clients should hold a reference only for an instance of this class. Child objects should, like record or columns should be referenced
 * only for a particular UI action.
 * </p>
 * 
 * @author sallai
 */
public class Table implements IRecordChangeListener, IStructuralChangeListener
{

	/**
	 * The name of the MultiProperties
	 */
	private String name = ""; //$NON-NLS-1$
	/**
	 * The description of the MultiProperties
	 */
	private String description = ""; //$NON-NLS-1$
	/**
	 * The version of the MultiProperties file.
	 */
	private String version = SchemaConverterFactory.NEWEST_VERSION;
	/**
	 * The handler name.
	 */
	private String handler = ""; //$NON-NLS-1$
	/**
	 * The key column width
	 */
	// TODO: it should be part of Columns class
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
	 * Sets the name of the MultiProperties
	 * 
	 * @param name
	 *            The name of the MultiProperties
	 * @see #getName()
	 */
	public void setName(final String name)
	{
		Assert.isNotNull(name);
		// TODO: an if should be here to skip the setting of same value
		this.name = name;
		setDirty(true);
	}

	/**
	 * Returns the name of the MultiProperties
	 * 
	 * @return the name
	 * @see #setName(String)
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the description of the MultiProperties
	 * 
	 * @param name
	 *            The description of the MultiProperties
	 * @see #getDescription()
	 */
	public void setDescription(final String description)
	{
		Assert.isNotNull(description);
		// TODO: an if should be here to skip the setting of same value
		this.description = description;
		setDirty(true);
	}

	/**
	 * Returns the description of the MultiProperties
	 * 
	 * @see #setDescription(String)
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the record change listener, which object will be informed about a record change. This listener object must implement the
	 * {@link IRecordChangeListener} interface.
	 * 
	 * @param listener
	 *            the listener object
	 * @see #setStructuralChangeListener(IStructuralChangeListener)
	 */
	public void setRecordChangeListener(final IRecordChangeListener listener)
	{
		Assert.isNotNull(listener);
		this.recordChangeListener = listener;
	}

	/**
	 * Sets the structural change listener, which object will be informed about a structural change. This listener object must implement the
	 * {@link IStructuralChangeListener} interface.
	 * 
	 * @param listener
	 *            the listener object
	 * @see #setRecordChangeListener(IRecordChangeListener)
	 */
	public void setStructuralChangeListener(final IStructuralChangeListener listener)
	{
		Assert.isNotNull(listener);
		this.structuralChangeListener = listener;
	}

	/**
	 * Sets the version of the MultiProperties file format.
	 * <p>
	 * The {@link #load(IFile)} and {@link #save(IFile)} methods chooses the appropriate schema, or in other word file format based on this
	 * value.
	 * </p>
	 * <p>
	 * In normal cases this setter method cannot be called from outside, except when an older file is loaded with a newer version of
	 * MultiProperties plugin and the user chooses the content to be converted to the newer format.
	 * </p>
	 * 
	 * @param version
	 *            the given version
	 * @see #getVersion()
	 * @see #load(IFile)
	 * @see #save(IFile)
	 */
	public void setVersion(final String version)
	{
		Assert.isNotNull(version);
		if (version.equals(this.version))
			return;
		this.version = version;
		setDirty(true);
	}

	/**
	 * Returns the version of the MultiProperties file format.
	 * 
	 * @return the version
	 * @see #setVersion(String)
	 * @see #load(IFile)
	 * @see #save(IFile)
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * Sets the name of the handler. The handlers are used for producing different file formats from a column, like the
	 * <code>java.util.Properties</code>.
	 * 
	 * @param handler
	 *            The name of the handler
	 * @see #getHandler()
	 */
	public void setHandler(final String handler)
	{
		Assert.isNotNull(handler);
		if (handler.equals(this.handler))
			return;
		this.handler = handler;
		setDirty(true);
	}

	/**
	 * Returns the name of the handler. The handlers are used for producing different file formats from a column, like the
	 * <code>java.util.Properties</code>.
	 * 
	 * @return the handler
	 * @see #setHandler(String)
	 */
	public String getHandler()
	{
		return handler;
	}

	/**
	 * This method is called from child objects reporting a record change event. Clients may not invoke this method.
	 * 
	 * @param record
	 *            the changed record
	 * @see #changed()
	 */
	// TODO: it could be package scoped method
	public void changed(final AbstractRecord record)
	{
		setDirty(true, record);
	}

	/**
	 * This method is called from child objects reporting a structural change event. Clients may not invoke this method.
	 * 
	 * @see #changed(AbstractRecord)
	 */
	// TODO: it could be package scoped method
	public void changed()
	{
		setDirty(true);
	}

	/**
	 * Sets the actual dirty state.
	 * 
	 * @param dirty
	 *            the dirty state
	 * @see #setDirty(boolean, AbstractRecord)
	 * @see #isDirty()
	 */
	public void setDirty(final boolean dirty)
	{
		validate();
		this.dirty = dirty;
		if (structuralChangeListener != null)
			structuralChangeListener.changed();
	}

	/**
	 * Sets the actual dirty state.
	 * 
	 * @param dirty
	 *            the dirty state
	 * @param record
	 *            the record that caused the dirty state
	 * @see #setDirty(boolean)
	 * @see #isDirty()
	 */
	private void setDirty(final boolean dirty, final AbstractRecord record)
	{
		this.dirty = dirty;
		if (recordChangeListener != null)
			recordChangeListener.changed(record);
	}

	/**
	 * Returns the actual dirty flag.
	 * 
	 * @return the current dirty state
	 * @see #setDirty(boolean)
	 * @see #setDirty(boolean, AbstractRecord)
	 */
	public boolean isDirty()
	{
		return dirty;
	}

	/**
	 * Returns the <code>Columns</code> object.
	 * 
	 * @return columns
	 */
	public Columns getColumns()
	{
		return columns;
	}

	/**
	 * Sets the key column width.
	 * 
	 * @param width
	 *            The desired with
	 */
	// TODO: it should be part of Columns class
	public void setKeyColumnWidth(final int width)
	{
		this.width = width;
		setDirty(true);
	}

	/**
	 * Returns the key column width.
	 * 
	 * @return The key column width
	 */
	// TODO: it should be part of Columns class
	public int getKeyColumnWidth()
	{
		return width;
	}

	/**
	 * Adds a new record to the end of the MultiProperties.
	 * 
	 * @param record
	 *            the new record
	 * @see #set(AbstractRecord, int)
	 * @see #insert(AbstractRecord, int)
	 */
	public void add(final AbstractRecord record)
	{
		Assert.isNotNull(record);
		records.add(record);
		record.setRecordChangeListener(this);
		record.setStructuralChangeListener(this);
		if (structuralChangeListener != null) // At loading stage, the listener currently is not set
			setDirty(true);
	}

	/**
	 * Inserts a new record at a index.
	 * 
	 * @param record
	 *            the new records
	 * @param index
	 *            the position of insert
	 * @see #add(AbstractRecord)
	 * @see #set(AbstractRecord, int)
	 * @throws IllegalArgumentException
	 *             if the index is out of the bounds
	 */
	public void insert(final AbstractRecord record, final int index)
	{
		Assert.isNotNull(record);
		if (index < 0 || index > records.size())
			throw new IllegalArgumentException("The index is out of bounds (" + index + "/" + records.size() + ")"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		records.add(index, record);
		record.setRecordChangeListener(this);
		record.setStructuralChangeListener(this);
		setDirty(true);
	}

	/**
	 * Returns a record at the given index.
	 * 
	 * @param index
	 *            the position
	 * @return the record
	 */
	public AbstractRecord get(final int index)
	{
		return records.get(index);
	}

	/**
	 * Returns the index of the specified record.
	 * 
	 * @param record
	 *            the record to search for
	 * @see #indexOf(String)
	 * @return the index of the first occurrence
	 */
	public int indexOf(final AbstractRecord record)
	{
		return records.indexOf(record);
	}

	/**
	 * Returns the index of the specified property record. The comment and empty records are not involved in the search.
	 * 
	 * @param record
	 *            the property string to search for
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
	 * 
	 * @param record
	 *            The property string to search for
	 * @param position
	 *            The position where the search should be started
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
	 * Moves the given record up
	 * 
	 * @param record
	 *            the record to be moved up
	 * @throws IllegalArgumentException
	 *             if the record cannot be found
	 */
	public void moveUp(final AbstractRecord record)
	{
		final int index = indexOf(record);
		if (index < 0)
			throw new IllegalArgumentException("The record does not exists in the table"); //$NON-NLS-1$
		records.set(index, records.get(index - 1));
		records.set(index - 1, record);
		setDirty(true);
	}

	/**
	 * Moves the given record down
	 * 
	 * @param record
	 *            the record to be moved down
	 * @throws IllegalArgumentException
	 *             if the record cannot be found
	 */
	public void moveDown(final AbstractRecord record)
	{
		final int index = indexOf(record);
		if (index < 0)
			throw new IllegalArgumentException("The record does not exists in the table"); //$NON-NLS-1$
		records.set(index, records.get(index + 1));
		records.set(index + 1, record);
		setDirty(true);
	}

	/**
	 * Removes the record at the given index.
	 * 
	 * @param index
	 *            the index to be removed
	 * @return the removed record
	 * @throws IllegalArgumentException
	 *             if the index is out of the bounds
	 */
	public AbstractRecord remove(final int index)
	{
		if (index < 0 || index >= records.size())
			throw new IllegalArgumentException("The index is out of bounds (" + index + "/" + records.size() + ")"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		final AbstractRecord record = records.remove(index);
		setDirty(true);
		return record;
	}

	/**
	 * Removes the given records.
	 * 
	 * @param records
	 *            the records to be removed
	 * @return the number of removed records
	 */
	public int remove(final AbstractRecord[] records)
	{
		int count = 0;
		for (final AbstractRecord record : records)
		{
			if (this.records.remove(record))
				count++;
		}
		setDirty(true);
		return count;
	}

	/**
	 * Returns the cardinality of all records.
	 * 
	 * @return cardinality of all records
	 * @see #sizeOfProperties()
	 * @see #sizeOfDisabled()
	 * @see #sizeOfComments()
	 * @see #sizeOfEmpties()
	 */
	public int size()
	{
		return records.size();
	}

	/**
	 * Returns the cardinality of property records. The disabled records are not calculated.
	 * 
	 * @return cardinality of property records
	 * 
	 * @see #size()
	 * @see #sizeOfDisabled()
	 * @see #sizeOfComments()
	 * @see #sizeOfEmpties()
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
	 * Returns the cardinality of disabled property records.
	 * 
	 * @return cardinality of disabled property records
	 * @see #size()
	 * @see #sizeOfProperties()
	 * @see #sizeOfComments()
	 * @see #sizeOfEmpties()
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
	 * Returns the cardinality of disabled comment records.
	 * 
	 * @return cardinality of disabled comment records
	 * 
	 * @see #size()
	 * @see #sizeOfProperties()
	 * @see #sizeOfDisabled()
	 * @see #sizeOfEmpties()
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
	 * Returns the cardinality of disabled empty records.
	 * 
	 * @return cardinality of disabled empty records
	 * 
	 * @see #size()
	 * @see #sizeOfProperties()
	 * @see #sizeOfDisabled()
	 * @see #sizeOfComments()
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
	 * 
	 * @return An array containing all of the records in this list in proper sequence
	 */
	public Object[] toArray()
	{
		return records.toArray();
	}

	/**
	 * Is the current record satisfies the search criteria or not.
	 * 
	 * @param findString
	 *            The string to search
	 * @param currentString
	 *            The current string of the record
	 * @param caseSensitive
	 *            Is the search case sensitive
	 * @param wholeWord
	 *            Is the search strict to whole word
	 * @return Is the current record satisfies the search criteria
	 */
	private boolean findValue(final String findString, final String currentString, final boolean caseSensitive,
			final boolean wholeWord)
	{
		if (wholeWord
				&& (findString.equalsIgnoreCase(currentString) && !caseSensitive || caseSensitive
						&& findString.equals(currentString)))
			return true;
		if (!wholeWord
				&& (caseSensitive && currentString.indexOf(findString) >= 0 || !caseSensitive
						&& currentString.toLowerCase().indexOf(findString.toLowerCase()) >= 0))
			return true;
		return false;
	}

	/**
	 * Is the current record satisfies the search criteria or not.
	 * 
	 * @param index
	 *            The current position to search
	 * @param findString
	 *            The string to search
	 * @param caseSensitive
	 *            Is the search case sensitive
	 * @param wholeWord
	 *            Is the search strict to whole word
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

}
