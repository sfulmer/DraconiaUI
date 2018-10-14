package net.draconia.utilities;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class PropertyChangeable implements Serializable
{
	private static final long serialVersionUID = 3482102012300106617L;
	
	private List<PropertyChangeListener> mLstPropertyChangeListeners;
	
	public boolean addPropertyChangeListener(final PropertyChangeListener objPropertyChangeListener)
	{
		return(getPropertyChangeListeners().add(objPropertyChangeListener));
	}
	
	protected void firePropertyChangeListeners(final String sProperty, final Object objOldValue, final Object objNewValue)
	{
		for(PropertyChangeListener objPropertyChangeListener : getPropertyChangeListeners())
			objPropertyChangeListener.propertyChange(new PropertyChangeEvent(this, sProperty, objOldValue, objNewValue));
	}
	
	protected List<PropertyChangeListener> getPropertyChangeListeners()
	{
		if(mLstPropertyChangeListeners == null)
			mLstPropertyChangeListeners = new ArrayList<PropertyChangeListener>();
		
		return(mLstPropertyChangeListeners);
	}
	
	public boolean removePropertyChangeListener(final PropertyChangeListener objPropertyChangeListener)
	{
		return(getPropertyChangeListeners().remove(objPropertyChangeListener));
	}
}