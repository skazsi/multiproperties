package com.ind.multiproperties.base.model;

import com.ind.multiproperties.base.model.listener.IColumnChangeListener;

public class Column
{

	private String strName = "";
	private String strDescription = "";
	private String strHandlerConfiguration = "";
	private int iWidth = 150;
	private IColumnChangeListener columnChangeListener = null;

	public void setName(final String name)
	{
		this.strName = name;
		if (columnChangeListener != null)
			columnChangeListener.columnChanged(this);
	}

	public String getName()
	{
		return strName;
	}

	public void setDescription(final String description)
	{
		this.strDescription = description;
		if (columnChangeListener != null)
			columnChangeListener.columnChanged(this);
	}

	public String getDescription()
	{
		return strDescription;
	}

	public void setHandlerConfiguration(final String configuration)
	{
		this.strHandlerConfiguration = configuration;
		if (columnChangeListener != null)
			columnChangeListener.columnChanged(this);
	}

	public String getHandlerConfiguration()
	{
		return strHandlerConfiguration;
	}

	public void setWidth(final int width)
	{
		this.iWidth = width;
		if (columnChangeListener != null)
			columnChangeListener.columnChanged(this);
	}

	public int getWidth()
	{
		return iWidth;
	}

	public void setColumnChangeListener(final IColumnChangeListener listener)
	{
		this.columnChangeListener = listener;
	}
}
