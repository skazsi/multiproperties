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

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		final CommentRecord commentrecord = new CommentRecord();
		commentrecord.value = value;
		commentrecord.setRecordChangeListener(recordChangeListener);
		return commentrecord;
	}
}
