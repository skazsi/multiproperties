package hu.skzs.multiproperties.base.model;

public class CommentRecord extends AbstractRecord
{

	private String value;

	public CommentRecord()
	{
		super();
	}

	public CommentRecord(final String value)
	{
		super();
		this.value = value;
	}

	/**
	 * Constructor for creating a new {@link CommentRecord} based on the given instance.
	 * <p>The <code>recordChange</code> and <code>structuralChange</code> listeners are remain uninitialized.</p>
	 * @param commentRecord the given instance of {@link CommentRecord}
	 */
	public CommentRecord(final CommentRecord commentRecord)
	{
		super();
		value = commentRecord.value;
	}

	/**
	 * Sets this {@link CommentRecord}'s properties based on the given instance in the parameter.
	 * <p>The <code>recordChange</code> and <code>structuralChange</code> listeners are remain unset.</p>
	 * @param commentRecord the given instance of {@link CommentRecord}
	 */
	public void set(final CommentRecord commentRecord)
	{
		setValue(commentRecord.getValue());
	}

	public void setValue(final String value)
	{
		if (this.value != null && this.value.equals(value))
			return;
		this.value = value;
		if (recordChangeListener != null)
			recordChangeListener.changed(this);
	}

	public String getValue()
	{
		return value;
	}
}
