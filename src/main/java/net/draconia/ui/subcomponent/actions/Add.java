package net.draconia.ui.subcomponent.actions;

import java.awt.event.ActionEvent;

import java.util.Observable;

import javax.swing.AbstractAction;

import net.draconia.ui.listdetails.ListDetailsDialog;

public class Add<ModelType extends Observable> extends AbstractAction
{
	private static final long serialVersionUID = 7917926034102674787L;
	
	private ListDetailsDialog<ModelType> mDlgNew;
	
	public Add(final ListDetailsDialog<ModelType> dlgNew)
	{
		setNewDialog(dlgNew);
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