package net.draconia.ui.subcomponent.listeners;

import java.io.Serializable;

import java.util.Observable;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.draconia.ui.subcomponent.actions.Edit;
import net.draconia.ui.subcomponent.actions.Remove;

public class SubComponentListSelectionListener<ModelType extends Observable, SubcomponentType extends Observable> implements ListSelectionListener, Serializable
{
	private static final long serialVersionUID = 237011529412695132L;
	
	private Edit<ModelType, SubcomponentType> mActEdit;
	private JTable mTblList;
	private Remove<SubcomponentType> mActRemove;
	
	public SubComponentListSelectionListener(final JTable tblList, final Edit<ModelType, SubcomponentType> actEdit, final Remove<SubcomponentType> actRemove)
	{
		setEditAction(actEdit);
		setListTable(tblList);
		setRemoveAction(actRemove);
	}
	
	protected Edit<ModelType, SubcomponentType> getEditAction()
	{
		return(mActEdit);
	}
	
	protected JTable getListTable()
	{
		return(mTblList);
	}
	
	protected Remove<SubcomponentType> getRemoveAction()
	{
		return(mActRemove);
	}
	
	protected void setEditAction(final Edit<ModelType, SubcomponentType> actEdit)
	{
		mActEdit = actEdit;
	}
	
	protected void setListTable(final JTable tblList)
	{
		mTblList = tblList;
	}
	
	protected void setRemoveAction(final Remove<SubcomponentType> actRemove)
	{
		mActRemove = actRemove;
	}
	
	public void valueChanged(final ListSelectionEvent objListSelectionEvent)
	{
		if(!objListSelectionEvent.getValueIsAdjusting())
			{
			getEditAction().setEnabled(getListTable().getSelectedRowCount() == 1);
			getRemoveAction().setEnabled(getListTable().getSelectedRowCount() > 0);
			}
	}
}