package net.draconia.utilities.listeners;

import java.io.Serializable;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import net.draconia.utilities.actions.TextCopy;
import net.draconia.utilities.actions.TextCut;
import net.draconia.utilities.actions.TextDelete;

public class TextComponentCaretListener implements CaretListener, Serializable
{
	private static final long serialVersionUID = 7912432094161467048L;
	
	private TextCopy mActCopy;
	private TextCut mActCut;
	private TextDelete mActDelete;
	
	public TextComponentCaretListener(final TextCopy actCopy, final TextCut actCut, final TextDelete actDelete)
	{
		setCopyAction(actCopy);
		setCutAction(actCut);
		setDeleteAction(actDelete);
	}
	
	public void caretUpdate(final CaretEvent objCaretEvent)
	{
		if(objCaretEvent.getDot() != objCaretEvent.getMark())
			{
			getCopyAction().setEnabled(true);
			getCutAction().setEnabled(true);
			getDeleteAction().setEnabled(true);
			}
		else
			{
			getCopyAction().setEnabled(false);
			getCutAction().setEnabled(false);
			getDeleteAction().setEnabled(false);
			}
	}
	
	protected TextCopy getCopyAction()
	{
		return(mActCopy);
	}
	
	protected TextCut getCutAction()
	{
		return(mActCut);
	}
	
	protected TextDelete getDeleteAction()
	{
		return(mActDelete);
	}
	
	protected void setCopyAction(final TextCopy actCopy)
	{
		mActCopy = actCopy;
	}
	
	protected void setCutAction(final TextCut actCut)
	{
		mActCut = actCut;
	}
	
	protected void setDeleteAction(final TextDelete actDelete)
	{
		mActDelete = actDelete;
	}
}