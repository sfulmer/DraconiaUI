package net.draconia.ui.listdetails.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class DetailsPanelModel<ModelType extends Observable> extends Observable implements Observer, Serializable
{
	private static final long serialVersionUID = -5513097865894054630L;
	
	private Boolean mbDirty, mbEditing;
	private List<PropertyChangeListener> mLstPropertyChangeListeners;
	private ModelType mObjModel, mObjWorkingModel;
	
	public DetailsPanelModel()
	{ }
	
	public DetailsPanelModel(final ModelType objModel)
	{
		setModel(objModel);
	}
	
	protected void addModelObservers()
	{
		getModel().addObserver(this);
	}
	
	public void addPropertyChangeListener(final PropertyChangeListener objPropertyChangeListener)
	{
		getPropertyChangeListeners().add(objPropertyChangeListener);
	}
	
	protected void addWorkingModelObservers()
	{
		getWorkingModel().addObserver(this);
	}
	
	protected abstract ModelType createNewModel();
	
	protected void firePropertyChangeListeners(final String sPropertyName, final Object objOldValue, final Object objNewValue)
	{
		for(PropertyChangeListener objListener : getPropertyChangeListeners())
			objListener.propertyChange(new PropertyChangeEvent(this, sPropertyName, objOldValue, objNewValue));
	}
	public ModelType getModel()
	{
		return(mObjModel);
	}
	
	protected List<PropertyChangeListener> getPropertyChangeListeners()
	{
		if(mLstPropertyChangeListeners == null)
			mLstPropertyChangeListeners = new ArrayList<PropertyChangeListener>();
		
		return(mLstPropertyChangeListeners);
	}
	
	public ModelType getWorkingModel()
	{
		return(mObjWorkingModel);
	}
	
	public boolean isDirty()
	{
		if(mbDirty == null)
			mbDirty = false;
		
		return(mbDirty);
	}
	
	public boolean isEditing()
	{
		if(mbEditing == null)
			mbEditing = false;
		
		return(mbEditing);
	}
	
	public void setDirty(final Boolean bDirty)
	{
		if(bDirty == null)
			mbDirty = false;
		else
			mbDirty = bDirty;
		
		setChanged();
		notifyObservers();
	}
	
	public void setEditing(final Boolean bEditing)
	{
		boolean bOldValue = mbEditing;
		
		if(bEditing == null)
			mbEditing = false;
		else
			mbEditing = bEditing;
		
		firePropertyChangeListeners("Editing", bOldValue, bEditing);
		
		setChanged();
		notifyObservers();
	}
	
	@SuppressWarnings("unchecked")
	public void setModel(final ModelType objModel)
	{
		if(mObjModel != null)
			mObjModel.deleteObservers();
		
		if(objModel == null)
			mObjModel = createNewModel();
		else
			mObjModel = objModel;
		
		try
			{
			Method funcClone = mObjModel.getClass().getMethod("clone", new Class<?>[0]);
			ModelType objClone;
			
			if(!funcClone.isAccessible())
				funcClone.setAccessible(true);
			
			objClone = ((ModelType)(funcClone.invoke(mObjModel, new Object[0])));
			
			setWorkingModel(objClone);
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			}
		
		addModelObservers();
		
		setChanged();
		notifyObservers();
	}
	
	public void setWorkingModel(final ModelType objWorkingModel)
	{
		if(mObjWorkingModel != null)
			mObjWorkingModel.deleteObservers();
		
		if(objWorkingModel == null)
			mObjWorkingModel = createNewModel();
		else
			mObjWorkingModel = objWorkingModel;
		
		addWorkingModelObservers();
		
		setChanged();
		notifyObservers();
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		setDirty(!getModel().equals(getWorkingModel()));
		
		setChanged();
		notifyObservers();
	}
}