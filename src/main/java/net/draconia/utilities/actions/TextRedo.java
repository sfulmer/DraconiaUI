package net.draconia.utilities.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import javax.swing.undo.UndoManager;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class TextRedo extends AbstractAction
{
	private static final long serialVersionUID = 3965383888469417477L;
	
	@Autowired
	private TextUndo mActUndo;
	
	private UndoManager mObjUndoManager;
	
	public TextRedo()
	{
		super("Redo");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
	}
	
	public TextRedo(final UndoManager objUndoManager)
	{
		super("Redo");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));
		
		setUndoManager(objUndoManager);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		if(getUndoManager().canRedo())
			getUndoManager().redo();
		
		if(getUndoAction() != null)
			getUndoAction().setEnabled(getUndoManager().canUndo());
	}
	
	public Action getUndoAction()
	{
		return(mActUndo);
	}
	
	protected UndoManager getUndoManager()
	{
		return(mObjUndoManager);
	}
	
	public void setUndoAction(final TextUndo actUndo)
	{
		mActUndo = actUndo;
	}
	
	@Autowired
	protected void setUndoManager(final UndoManager objUndoManager)
	{
		mObjUndoManager = objUndoManager;
		
		if(objUndoManager != null)
			setEnabled(mObjUndoManager.canRedo());
	}
}