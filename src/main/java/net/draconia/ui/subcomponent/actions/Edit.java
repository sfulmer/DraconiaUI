package net.draconia.ui.subcomponent.actions;

import java.awt.event.ActionEvent;

import java.util.Observable;

import javax.swing.AbstractAction;

import net.draconia.ui.listdetails.ListDetailsDialog;

public abstract class Edit<ModelType extends Observable> extends AbstractAction
{
	private static final long serialVersionUID = 1969244598163071306L;
	
	private ListDetailsDialog<ModelType> mDlgDetails;
	
	public Edit(final ListDetailsDialog<ModelType> dlgDetails)
	{
		setDetailsDialog(dlgDetails);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getDetailsDialog().setVisible(true);
	}
	
	protected ListDetailsDialog<ModelType> getDetailsDialog()
	{
		return(mDlgDetails);
	}
	
	protected abstract ModelType getModel();
	
	protected void setDetailsDialog(final ListDetailsDialog<ModelType> dlgDetails)
	{
		mDlgDetails = dlgDetails;
	}
}