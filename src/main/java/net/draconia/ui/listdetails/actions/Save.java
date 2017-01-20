package net.draconia.ui.listdetails.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.Observable;

import javax.swing.AbstractAction;

public class Save<ModelType extends Observable> extends AbstractAction
{
	private static final long serialVersionUID = 1848240552282478551L;
	
	private Apply<ModelType> mActApply;
	
	public Save()
	{
		super("Save");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
		
		setEnabled(false);
	}
	
	public Save(final Apply<ModelType> actApply)
	{
		super("Save");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
		
		setApplyAction(actApply);
		
		setEnabled(false);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		getApplyAction().actionPerformed(objActionEvent);
	}
	
	protected Apply<ModelType> getApplyAction()
	{
		return(mActApply);
	}
	
	protected void setApplyAction(final Apply<ModelType> actApply)
	{
		mActApply = actApply;
	}
}