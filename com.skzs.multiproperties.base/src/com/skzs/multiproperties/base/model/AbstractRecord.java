package com.skzs.multiproperties.base.model;

import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import com.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import com.skzs.multiproperties.base.model.listener.IStructuralChangeListener;

public abstract class AbstractRecord implements Cloneable
{

	protected IRecordChangeListener recordChangeListener = null;
	protected IStructuralChangeListener structuralChangeListener = null;

	@SuppressWarnings("unchecked")
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
			e.printStackTrace();
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
