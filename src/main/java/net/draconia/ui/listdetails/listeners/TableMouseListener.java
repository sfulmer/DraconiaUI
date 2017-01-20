package net.draconia.ui.listdetails.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.Serializable;

import java.util.Observable;

import javax.swing.JTable;

import net.draconia.ui.listdetails.DetailsPanel;
import net.draconia.ui.listdetails.actions.Apply;
import net.draconia.ui.listdetails.actions.Edit;
import net.draconia.ui.listdetails.actions.Save;
import net.draconia.ui.listdetails.model.DetailsPanelModel;
import net.draconia.ui.listdetails.model.ListTableModel;

public class TableMouseListener<ModelType extends Observable> extends MouseAdapter implements Serializable
{
	private static final long serialVersionUID = -75021157210882918L;
	
	private Apply<ModelType> mActApply;
	private DetailsPanel<ModelType> mPnlDetails;
	private Edit<ModelType> mActEdit;
	private Save<ModelType> mActSave;
	
	public TableMouseListener(final DetailsPanel<ModelType> pnlDetails, final Apply<ModelType> actApply, final Edit<ModelType> actEdit, final Save<ModelType> actSave)
	{
		setDetailsPanel(pnlDetails);
		setApplyAction(actApply);
		setEditAction(actEdit);
		setSaveAction(actSave);
	}
	
	protected Apply<ModelType> getApplyAction()
	{
		return(mActApply);
	}
	
	protected DetailsPanel<ModelType> getDetailsPanel()
	{
		return(mPnlDetails);
	}
	
	protected DetailsPanelModel<ModelType> getDetailsPanelModel()
	{
		return(getDetailsPanel().getModel());
	}
	
	protected Edit<ModelType> getEditAction()
	{
		return(mActEdit);
	}
	
	protected Save<ModelType> getSaveAction()
	{
		return(mActSave);
	}
	
	@SuppressWarnings("unchecked")
	public void mouseClicked(final MouseEvent objMouseEvent)
	{
		int iRow;
		JTable tbl = ((JTable)(objMouseEvent.getSource()));
		ModelType objModel;
		ListTableModel<ModelType> objTableModel = ((ListTableModel<ModelType>)(tbl.getModel()));
		
		iRow = tbl.getSelectedRow();
		
		objModel = objTableModel.getRow(iRow);
		
		getDetailsPanelModel().setModel(objModel);
		
		if(objMouseEvent.getClickCount() == 2)
			getEditAction().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
	}
	
	protected void setApplyAction(final Apply<ModelType> actApply)
	{
		mActApply = actApply;
	}
	
	protected void setDetailsPanel(final DetailsPanel<ModelType> pnlDetails)
	{
		mPnlDetails = pnlDetails;
	}
	
	protected void setEditAction(final Edit<ModelType> actEdit)
	{
		mActEdit = actEdit;
	}
	
	protected void setSaveAction(final Save<ModelType> actSave)
	{
		mActSave = actSave;
	}
}