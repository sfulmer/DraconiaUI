package net.draconia.ui.listdetails.observers;

import java.io.Serializable;

import java.util.Observable;
import java.util.Observer;

import net.draconia.ui.listdetails.actions.Apply;
import net.draconia.ui.listdetails.actions.Save;
import net.draconia.ui.listdetails.model.DetailsPanelModel;

public class DirtyObserver<ModelType extends Observable> implements Observer, Serializable
{
	private static final long serialVersionUID = 3756637440515641205L;
	
	private Apply<ModelType> mActApply;
	private Save<ModelType> mActSave;
	
	public DirtyObserver(final Apply<ModelType> actApply, final Save<ModelType> actSave)
	{
		setApplyAction(actApply);
		setSaveAction(actSave);
	}
	
	protected Apply<ModelType> getApplyAction()
	{
		return(mActApply);
	}
	
	protected Save<ModelType> getSaveAction()
	{
		return(mActSave);
	}
	
	protected void setApplyAction(final Apply<ModelType> actApply)
	{
		mActApply = actApply;
	}
	
	protected void setSaveAction(final Save<ModelType> actSave)
	{
		mActSave = actSave;
	}
	
	@SuppressWarnings("unchecked")
	public void update(final Observable objObservable, final Object objArgument)
	{
		boolean bEnabled;
		DetailsPanelModel<ModelType> objModel = ((DetailsPanelModel<ModelType>)(objObservable));
		
		bEnabled = objModel.isDirty();
		
		getApplyAction().setEnabled(bEnabled);
		getSaveAction().setEnabled(bEnabled);
	}
}