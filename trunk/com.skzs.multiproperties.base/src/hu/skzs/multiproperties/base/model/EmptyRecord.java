package hu.skzs.multiproperties.base.model;

public class EmptyRecord extends AbstractRecord
{

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		final EmptyRecord emptyrecord = new EmptyRecord();
		return emptyrecord;
	}
}
