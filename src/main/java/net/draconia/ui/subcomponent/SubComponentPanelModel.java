package net.draconia.ui.subcomponent;

import java.io.Serializable;

import java.util.Observable;

public class SubComponentPanelModel<ModelType extends Observable> extends Observable implements Serializable
{
	private static final long serialVersionUID = 181889833469032428L;
	
	private ModelType mObjModel;
	
	public ModelType getModel()
	{
		return(mObjModel);
	}
	
	public void setModel(final ModelType objModel)
	{
		mObjModel = objModel;
		
		setChanged();
		notifyObservers();
	}
}