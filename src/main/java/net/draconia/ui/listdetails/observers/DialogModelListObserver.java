package net.draconia.ui.listdetails.observers;

import java.io.Serializable;

import java.util.Observable;
import java.util.Observer;

import net.draconia.ui.listdetails.model.ListTableModel;

public class DialogModelListObserver<ModelType extends Observable> implements Observer, Serializable
{
	private static final long serialVersionUID = -7683402137401155939L;
	
	private ListTableModel<ModelType> mObjListTableModel;
	
	public DialogModelListObserver(final ListTableModel<ModelType> objListTableModel)
	{
		setListTableModel(objListTableModel);
	}
	
	protected ListTableModel<ModelType> getListTableModel()
	{
		return(mObjListTableModel);
	}
	
	protected void setListTableModel(final ListTableModel<ModelType> objListTableModel)
	{
		mObjListTableModel = objListTableModel;
	}
	
	public void update(final Observable objObservable, final Object objArgument)
	{
		getListTableModel().fireTableChanged();
	}
}