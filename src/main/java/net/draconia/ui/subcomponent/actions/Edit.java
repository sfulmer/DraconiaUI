package net.draconia.ui.subcomponent.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import net.draconia.ui.listdetails.ListDetailsDialog;
import net.draconia.ui.subcomponent.SubComponentTableModel;

public abstract class Edit<ModelType extends Observable, SubcomponentType extends Observable> extends AbstractAction
{
	private static final long serialVersionUID = 1969244598163071306L;
	
	private ListDetailsDialog<SubcomponentType> mDlgDetails;
	private JTable mTblSubcomponent;
	
	public Edit(final ListDetailsDialog<SubcomponentType> dlgDetails)
	{
		super("Edit...");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);
		
		setDetailsDialog(dlgDetails);
		setEnabled(false);
	}
	
	public Edit(final ListDetailsDialog<SubcomponentType> dlgDetails, final JTable tblSubcomponent)
	{
		super("Edit...");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);
		
		setDetailsDialog(dlgDetails);
		setEnabled(false);
		setSubcomponentTable(tblSubcomponent);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getDetailsDialog().setVisible(true);
	}
	
	protected ListDetailsDialog<SubcomponentType> getDetailsDialog()
	{
		return(mDlgDetails);
	}
	
	@SuppressWarnings("unchecked")
	protected SubcomponentType getModel()
	{
		SubComponentTableModel<ModelType, SubcomponentType> objTableModel = ((SubComponentTableModel<ModelType, SubcomponentType>)(getSubcomponentTable().getModel()));
		
		try
			{
			return(objTableModel.getRows().get(getSubcomponentTable().getSelectedRow()));
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			
			return(null);
			}
	}
	
	public JTable getSubcomponentTable()
	{
		return(mTblSubcomponent);
	}
	
	protected void setDetailsDialog(final ListDetailsDialog<SubcomponentType> dlgDetails)
	{
		mDlgDetails = dlgDetails;
	}
	
	public void setSubcomponentTable(final JTable tblSubcomponent)
	{
		mTblSubcomponent = tblSubcomponent;
	}
}