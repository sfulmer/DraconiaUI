package net.draconia.ui.subcomponent.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public abstract class Remove<ModelType extends Observable> extends AbstractAction
{
	private static final long serialVersionUID = -5556374872156359866L;
	
	private JTable mTblList;
	
	public Remove()
	{
		super("Remove");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		
		setEnabled(false);
	}
	
	public Remove(final JTable tblList)
	{
		super("Remove");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		
		setListTable(tblList);
		
		setEnabled(false);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		try
			{
			removeSubcomponent();
			}
		catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
			{
			objException.printStackTrace(System.err);
			}
	}
	
	public JTable getListTable()
	{
		return(mTblList);
	}
	
	protected abstract void removeSubcomponent() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	public void setListTable(final JTable tblList)
	{
		mTblList = tblList;
	}
}