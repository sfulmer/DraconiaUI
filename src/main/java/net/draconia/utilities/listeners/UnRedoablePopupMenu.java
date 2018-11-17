package net.draconia.utilities.listeners;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.Action;
import javax.swing.JPopupMenu;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import javax.swing.text.JTextComponent;

import javax.swing.undo.UndoManager;

import net.draconia.utilities.actions.TextCopy;
import net.draconia.utilities.actions.TextCut;
import net.draconia.utilities.actions.TextDelete;
import net.draconia.utilities.actions.TextPaste;
import net.draconia.utilities.actions.TextRedo;
import net.draconia.utilities.actions.TextSelectAll;
import net.draconia.utilities.actions.TextUndo;

public class UnRedoablePopupMenu extends JPopupMenu implements PopupMenuListener
{
	private static final long serialVersionUID = -358551677702431214L;
	
	private TextCopy mActCopy;
	private TextCut mActCut;
	private TextDelete mActDelete;
	private TextPaste mActPaste;
	private TextRedo mActRedo;
	private TextSelectAll mActSelectAll;
	private TextUndo mActUndo;
	private UndoManager mObjUndoManager;
	
	public UnRedoablePopupMenu()
	{
		this(null);
	}
	
	public UnRedoablePopupMenu(final UndoManager objUndoManager)
	{
		setUndoManager(objUndoManager);
		
		addPopupMenuListener(this);
		
		add(getUndoAction());
		add(getRedoAction());
		
		((TextRedo)(getRedoAction())).setUndoAction(getUndoAction());
		((TextUndo)(getUndoAction())).setRedoAction(getRedoAction());
		
		addSeparator();
		add(getCutAction());
		add(getCopyAction());
		add(getPasteAction());
		add(getDeleteAction());
		addSeparator();
		add(getSelectAllAction());
	}
	
	public TextCopy getCopyAction()
	{
		if(mActCopy == null)
			mActCopy = new TextCopy();
		
		return(mActCopy);
	}
	
	public TextCut getCutAction()
	{
		if(mActCut == null)
			mActCut = new TextCut();
		
		return(mActCut);
	}
	
	public TextDelete getDeleteAction()
	{
		if(mActDelete == null)
			mActDelete = new TextDelete();
		
		return(mActDelete);
	}
	
	public Action getPasteAction()
	{
		if(mActPaste == null)
			mActPaste = new TextPaste();
		
		return(mActPaste);
	}
	
	public TextRedo getRedoAction()
	{
		if(mActRedo == null)
			mActRedo = new TextRedo();
		
		return(mActRedo);
	}
	
	public TextSelectAll getSelectAllAction()
	{
		if(mActSelectAll == null)
			mActSelectAll = new TextSelectAll();
		
		return(mActSelectAll);
	}
	
	public TextUndo getUndoAction()
	{
		if(mActUndo == null)
			mActUndo = new TextUndo(getUndoManager());
		
		return(mActUndo);
	}
	
	protected UndoManager getUndoManager()
	{
		return(mObjUndoManager);
	}
	
	public void popupMenuWillBecomeVisible(final PopupMenuEvent objPopupMenuEvent)
	{
		JPopupMenu mnuPopup = ((JPopupMenu)(objPopupMenuEvent.getSource()));
		JTextComponent txt = ((JTextComponent)(mnuPopup.getInvoker()));
		
		if(txt.isEditable())
			{
			Clipboard objClipboard = getToolkit().getSystemClipboard();
			Transferable objContents = objClipboard.getContents(null);
			
			if(objContents.isDataFlavorSupported(DataFlavor.stringFlavor))
				getPasteAction().setEnabled(true);
			else
				getPasteAction().setEnabled(false);
			
			if(getUndoManager() != null)
				{
				getUndoAction().setEnabled(getUndoManager().canUndo());
				getRedoAction().setEnabled(getUndoManager().canRedo());
				}
			}
		else
			{
			getCutAction().setEnabled(false);
			getDeleteAction().setEnabled(false);
			getPasteAction().setEnabled(false);
			
			getUndoAction().setEnabled(false);
			getRedoAction().setEnabled(false);
			}
	}
	
	public void popupMenuWillBecomeInvisible(final PopupMenuEvent objPopupMenuEvent)
	{ }
	
	public void popupMenuCanceled(final PopupMenuEvent objPopupMenuEvent)
	{ }
	
	protected void setUndoManager(final UndoManager objUndoManager)
	{
		mObjUndoManager = objUndoManager;
	}
}