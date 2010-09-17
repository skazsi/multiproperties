package com.ind.multiproperties.base.model.setting;

import java.util.Observable;
import java.util.Observer;

public class Settings extends Observable
{

	private String DEFAULT_HEADER = "# Edited by MultiProperties Editor\n# MODIFY AT YOUR OWN RISK!\n#\n# Name: {name}";
	private boolean bSaveDisabled = true;
	private boolean bSkipSavingEmptyBlock = true;
	private String strHeader = DEFAULT_HEADER;

	public Settings(Observer observer)
	{
		deleteObservers();
		addObserver(observer);
	}

	public void setSaveDisabled(boolean value)
	{
		this.bSaveDisabled = value;
		setChanged();
		notifyObservers();
	}

	public boolean getSaveDisabled()
	{
		return bSaveDisabled;
	}

	public void setSkipSavingEmptyBlock(boolean value)
	{
		this.bSkipSavingEmptyBlock = value;
		setChanged();
		notifyObservers();
	}

	public boolean getSkipSavingEmptyBlock()
	{
		return bSkipSavingEmptyBlock;
	}

	public void setHeader(String value)
	{
		this.strHeader = value;
		setChanged();
		notifyObservers();
	}

	public String getHeader()
	{
		return strHeader;
	}
}
