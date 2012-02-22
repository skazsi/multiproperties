package hu.skzs.multiproperties.base.model;

import hu.skzs.multiproperties.base.Activator;
import hu.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import hu.skzs.multiproperties.base.model.listener.IStructuralChangeListener;

import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public abstract class AbstractRecord implements Cloneable
{

	protected IRecordChangeListener recordChangeListener = null;
	protected IStructuralChangeListener structuralChangeListener = null;

	@SuppressWarnings("rawtypes")
	protected Observer getObserver()
	{
		try
		{
			final Field fField = Observable.class.getDeclaredField("obs");
			fField.setAccessible(true);
			final Vector vector = (Vector) fField.get(this);
			return (Observer) vector.get(0);
		}
		catch (final Throwable e)
		{
			Activator.logError(e);
		}
		return null;
	}

	void setRecordChangeListener(final IRecordChangeListener listener)
	{
		this.recordChangeListener = listener;
	}

	void setStructuralChangeListener(final IStructuralChangeListener listener)
	{
		this.structuralChangeListener = listener;
	}

	/**
	 * This method is cloning the current record.
	 */
	@Override
	public abstract Object clone() throws CloneNotSupportedException;
}
