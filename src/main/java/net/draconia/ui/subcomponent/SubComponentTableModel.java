package net.draconia.ui.subcomponent;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public abstract class SubComponentTableModel<ModelType extends Observable, SubcomponentType extends Observable> implements Serializable, TableModel
{
	private static final long serialVersionUID = -6032562071225834425L;
	
	private List<TableModelListener> mLstListeners;
	private ModelType mObjModel;
	private String msSubcomponentFieldName;
	
	public SubComponentTableModel(final ModelType objModel, final String sSubcomponentFieldName)
	{
		setModel(objModel);
		setSubcomponentFieldName(sSubcomponentFieldName);
	}

	public void addTableModelListener(final TableModelListener objTableModelListener)
	{
		getListeners().add(objTableModelListener);
	}
	
	public void fireTableChanged()
	{
		for(TableModelListener objListener : getListeners())
			objListener.tableChanged(new TableModelEvent(this));
	}
	
	protected List<TableModelListener> getListeners()
	{
		if(mLstListeners == null)
			mLstListeners = new ArrayList<TableModelListener>();
		
		return(mLstListeners);
	}
	
	protected ModelType getModel()
	{
		return(mObjModel);
	}

	public int getRowCount()
	{
		try
			{
			return(getRows().size());
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			return(0);
			}
	}
	
	@SuppressWarnings("unchecked")
	public List<SubcomponentType> getRows() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		List<SubcomponentType> lstSubComponents;
		
		if(getModel() == null)
			lstSubComponents = new ArrayList<SubcomponentType>();
		else
			{
			Method funcGetter = getModel().getClass().getMethod("get" + getSubcomponentFieldName(), new Class<?>[0]);
			
			lstSubComponents = (List<SubcomponentType>)(funcGetter.invoke(getModel(), new Object[0]));
			}
		
		return(lstSubComponents);
	}
	
	protected String getSubcomponentFieldName()
	{
		return(msSubcomponentFieldName);
	}

	public boolean isCellEditable(final int iRowIndex, final int iColumnIndex)
	{
		return(false);
	}
	
	public void removeTableModelListener(final TableModelListener objTableModelListener)
	{
		getListeners().remove(objTableModelListener);
	}
	
	protected void setModel(final ModelType objModel)
	{
		mObjModel = objModel;
		
		fireTableChanged();
	}
	
	protected void setSubcomponentFieldName(final String sSubcomponentFieldName)
	{
		msSubcomponentFieldName = sSubcomponentFieldName;
		
		fireTableChanged();
	}
}