package hu.skzs.multiproperties.base.model;

import hu.skzs.multiproperties.base.Activator;
import hu.skzs.multiproperties.base.model.listener.IRecordChangeListener;
import hu.skzs.multiproperties.base.model.listener.IStructuralChangeListener;

import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public abstract class AbstractRecord
{

	protected IRecordChangeListener recordChangeListener = null;
	protected IStructuralChangeListener structuralChangeListener = null;

	@SuppressWarnings("rawtypes")
	protected Observer getObserver()
	{
		try
		{
			final Field fField = Observable.class.getDeclaredField("obs"); //$NON-NLS-1$
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
}
