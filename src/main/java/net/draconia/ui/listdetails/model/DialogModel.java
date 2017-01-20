package net.draconia.ui.listdetails.model;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class DialogModel<ModelType extends Observable> extends Observable implements Observer, Serializable
{
	private static final long serialVersionUID = -2994049884964208586L;
	
	private List<ModelType> mLstModel;
	
	public boolean addModelType(final ModelType objModelType)
	{
		boolean bReturnValue = getModelListInternal().add(objModelType);
		
		saveDAO(objModelType);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean addModelTypes(final Collection<ModelType> collModelTypes)
	{
		boolean bReturnValue = getModelListInternal().addAll(collModelTypes);
		
		for(ModelType objModelType : collModelTypes)
			saveDAO(objModelType);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public abstract void create();
	
	public List<ModelType> getModelList()
	{
		return(Collections.unmodifiableList(getModelListInternal()));
	}
	
	protected List<ModelType> getModelListInternal()
	{
		if(mLstModel == null)
			{
			mLstModel = listDAO();
			
			for(ModelType objModelType : mLstModel)
				objModelType.addObserver(this);
			}
		
		return(mLstModel);
	}
	
	protected abstract List<ModelType> listDAO();
	
	protected abstract void removeDAO(final ModelType objModelType);
	
	public boolean removeModelType(final ModelType objModelType)
	{
		boolean bReturnValue = getModelListInternal().remove(objModelType);
		
		removeDAO(objModelType);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	public boolean removeModelTypes(final Collection<ModelType> collModelTypes)
	{
		boolean bReturnValue = getModelListInternal().removeAll(collModelTypes);
		
		for(ModelType objModelType : collModelTypes)
			removeDAO(objModelType);
		
		setChanged();
		notifyObservers();
		
		return(bReturnValue);
	}
	
	protected abstract void saveDAO(final ModelType objModelType);
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		setChanged();
		notifyObservers();
	}
}