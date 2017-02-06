package net.draconia.ui.subcomponent.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;

import javax.swing.AbstractAction;

import net.draconia.ui.listdetails.ListDetailsDialog;

public class Add<ModelType extends Observable> extends AbstractAction
{
	private static final long serialVersionUID = 7917926034102674787L;
	
	private ListDetailsDialog<ModelType> mDlgNew;
	
	public Add(final ListDetailsDialog<ModelType> dlgNew)
	{
		super("Add...");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		
		setNewDialog(dlgNew);
		
		setEnabled(false);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getNewDialog().setVisible(true);
	}
	
	protected ListDetailsDialog<ModelType> getNewDialog()
	{
		return(mDlgNew);
	}
	
	protected void setNewDialog(final ListDetailsDialog<ModelType> dlgNew)
	{
		mDlgNew = dlgNew;
	}
}