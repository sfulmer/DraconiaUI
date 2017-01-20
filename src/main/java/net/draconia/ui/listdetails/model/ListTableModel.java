package net.draconia.ui.listdetails.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import javax.swing.table.TableModel;

public abstract class ListTableModel<ModelType extends Observable> implements Serializable, TableModel
{
	private static final long serialVersionUID = -4131794173061229934L;
	
	private DialogModel<ModelType> mObjDialogModel;
	private List<TableModelListener> mLstListeners;
	
	public ListTableModel()
	{ }
	
	public ListTableModel(final DialogModel<ModelType> objDialogModel)
	{
		setDialogModel(objDialogModel);
	}
	
	public void addTableModelListener(final TableModelListener objTableModelListener)
	{
		getListeners().add(objTableModelListener);
	}
	
	protected List<TableModelListener> getListeners()
	{
		if(mLstListeners == null)
			mLstListeners = new ArrayList<TableModelListener>();
		
		return(mLstListeners);
	}
	
	public void fireTableChanged()
	{
		for(TableModelListener objListener : getListeners())
			objListener.tableChanged(new TableModelEvent(this, 0, getRowCount(), TableModelEvent.ALL_COLUMNS));
	}
	
	public DialogModel<ModelType> getDialogModel()
	{
		return(mObjDialogModel);
	}
	
	public ModelType getRow(final int iRowIndex)
	{
		if(iRowIndex >= 0)
			return(getDialogModel().getModelList().get(iRowIndex));
		else
			return(null);
	}
	
	public int getRowCount()
	{
		return(getDialogModel().getModelList().size());
	}
	
	public void removeTableModelListener(final TableModelListener objTableModelListener)
	{
		getListeners().remove(objTableModelListener);
	}
	
	public void setDialogModel(final DialogModel<ModelType> objDialogModel)
	{
		mObjDialogModel = objDialogModel;
		
		fireTableChanged();
	}
}