package net.draconia.ui.listdetails.listeners;

import java.io.Serializable;
import java.util.Observable;

import javax.swing.JTable;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.draconia.ui.listdetails.DetailsPanel;

import net.draconia.ui.listdetails.actions.Delete;
import net.draconia.ui.listdetails.actions.Edit;

import net.draconia.ui.listdetails.model.ListTableModel;

public abstract class TableSelectionListener<ModelType extends Observable> implements ListSelectionListener, Serializable
{
	private static final long serialVersionUID = -4969287280304914269L;

	private Delete<ModelType> mActDelete;
	private DetailsPanel<ModelType> mPnlDetails;
	private Edit<ModelType> mActEdit;
	private JTable mTblList;
	
	protected Delete<ModelType> getDeleteAction()
	{
		return(mActDelete);
	}
	
	protected DetailsPanel<ModelType> getDetailsPanel()
	{
		return(mPnlDetails);
	}
	
	protected Edit<ModelType> getEditAction()
	{
		return(mActEdit);
	}
	
	protected JTable getListTable()
	{
		return(mTblList);
	}
	
	@SuppressWarnings("unchecked")
	protected ListTableModel<ModelType> getTableModel()
	{
		return(((ListTableModel<ModelType>)(getListTable().getModel())));
	}
	
	protected void setDeleteAction(final Delete<ModelType> actDelete)
	{
		mActDelete = actDelete;
	}
	
	protected void setDetailsPanel(final DetailsPanel<ModelType> pnlDetails)
	{
		mPnlDetails = pnlDetails;
	}
	
	protected void setEditAction(final Edit<ModelType> actEdit)
	{
		mActEdit = actEdit;
	}
	
	protected void setListTable(final JTable tblList)
	{
		mTblList = tblList;
	}
	
	public void valueChanged(final ListSelectionEvent objListSelectionEvent)
	{
		if(!objListSelectionEvent.getValueIsAdjusting())
			{
			ListTableModel<ModelType> objTableModel = getTableModel();
			
			getEditAction().setEnabled(getListTable().getSelectedRowCount() == 1);
			getDeleteAction().setEnabled(getListTable().getSelectedRowCount() >= 1);
			
			if(getListTable().getSelectedRowCount() == 1)
				{
				int iSelectedRow = getListTable().getSelectedRow();
				ModelType objSelectedModel = objTableModel.getRow(iSelectedRow);
				
				getDetailsPanel().getModel().setModel(objSelectedModel);
				}
			else
				getDetailsPanel().getModel().setModel(null);
			}
	}
}