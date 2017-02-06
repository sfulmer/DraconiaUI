package net.draconia.ui.listdetails;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import java.util.Observable;

import javax.annotation.PostConstruct;

import javax.swing.Action;
import javax.swing.JPanel;

import net.draconia.ui.listdetails.actions.Apply;
import net.draconia.ui.listdetails.actions.Cancel;
import net.draconia.ui.listdetails.actions.Save;
import net.draconia.ui.listdetails.model.DetailsPanelModel;

public abstract class DetailsPanel<ModelType extends Observable> extends EnablablePanel
{
	private static final long serialVersionUID = -9083062930871602527L;
	
	private Apply<ModelType> mActApply;
	private ButtonsPanel mPnlButtons;
	private Cancel<ModelType> mActCancel;
	private DetailsPanelModel<ModelType> mObjModel;
	private Save<ModelType> mActSave;
	
	public DetailsPanel(final LayoutManager objLayoutManager)
	{
		super(objLayoutManager);
	}
	
	protected Apply<ModelType> getApplyAction()
	{
		return(mActApply);
	}
	
	protected Cancel<ModelType> getCancelAction()
	{
		return(mActCancel);
	}
	
	protected ButtonsPanel getButtonsPanel()
	{
		if(mPnlButtons == null)
			setButtonsPanel(new ButtonsPanel());
		
		return(mPnlButtons);
	}
	
	protected abstract JPanel getFieldsPanel();
	
	public DetailsPanelModel<ModelType> getModel()
	{
		return(mObjModel);
	}
	
	protected Save<ModelType> getSaveAction()
	{
		return(mActSave);
	}
	
	@PostConstruct
	protected void initPanel()
	{
		add(getFieldsPanel(), BorderLayout.NORTH);
		add(getButtonsPanel(), BorderLayout.SOUTH);
		
		setEnabled(false);
	}
	
	protected void setApplyAction(final Apply<ModelType> actApply)
	{
		mActApply = actApply;
	}
	
	protected void setButtonsPanel(final ButtonsPanel pnlButtons)
	{
		mPnlButtons = pnlButtons;
		
		mPnlButtons.setButtons(new Action[] {getApplyAction(), getSaveAction(), getCancelAction()});
	}
	
	protected void setCancelAction(final Cancel<ModelType> actCancel)
	{
		mActCancel = actCancel;
	}
	
	public void setModel(final DetailsPanelModel<ModelType> objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setSaveAction(final Save<ModelType> actSave)
	{
		mActSave = actSave;
	}
}