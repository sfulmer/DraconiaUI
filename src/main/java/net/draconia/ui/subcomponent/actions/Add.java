package net.draconia.ui.subcomponent.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.Observable;

import javax.swing.AbstractAction;

import net.draconia.ui.listdetails.ListDetailsDialog;
import net.draconia.ui.listdetails.model.DetailsPanelModel;

public abstract class Add<ModelType extends Observable, SubComponentType extends Observable> extends AbstractAction
{
	private static final long serialVersionUID = 7917926034102674787L;
	
	private DetailsPanelModel<ModelType> mObjDetailsModel;
	private ListDetailsDialog<SubComponentType> mDlgNew;
	
	public Add(final DetailsPanelModel<ModelType> objDetailsModel, final ListDetailsDialog<SubComponentType> dlgNew)
	{
		super("Add...");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		
		setDetailsModel(objDetailsModel);
		setNewDialog(dlgNew);
		
		setEnabled(false);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		openNewDialog();
	}
	
	protected DetailsPanelModel<ModelType> getDetailsModel()
	{
		return(mObjDetailsModel);
	}
	
	protected ListDetailsDialog<SubComponentType> getNewDialog()
	{
		return(mDlgNew);
	}
	
	protected abstract void openNewDialog();
	
	protected void setDetailsModel(final DetailsPanelModel<ModelType> objDetailsModel)
	{
		mObjDetailsModel = objDetailsModel;
	}
	
	protected void setNewDialog(final ListDetailsDialog<SubComponentType> dlgNew)
	{
		mDlgNew = dlgNew;
	}
}