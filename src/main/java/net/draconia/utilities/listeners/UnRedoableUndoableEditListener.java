package net.draconia.utilities.listeners;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Action;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import javax.swing.text.JTextComponent;

import javax.swing.undo.UndoManager;

public class UnRedoableUndoableEditListener implements Serializable, UndoableEditListener
{
	private static final long serialVersionUID = -8803785009120703430L;
	
	private Action mActRedo, mActUndo;
	private List<ChangeListener> mLstChangeListeners;
	private JTextComponent mTxtComponent;
	private UndoManager mObjUndoManager;
	
	public UnRedoableUndoableEditListener(final UndoManager objUndoManager, final JTextComponent txtComponent, final ChangeListener objChangeListener, final Action actRedo, final Action actUndo)
	{
		setChangeListener(objChangeListener);
		setTextComponent(txtComponent);
		setUndoManager(objUndoManager);
		setUndoAction(actUndo);
		setRedoAction(actRedo);
	}
	
	public UnRedoableUndoableEditListener(final UndoManager objUndoManager, final JTextComponent txtComponent, final ChangeListener[] arrObjChangeListeners, final Action actRedo, final Action actUndo)
	{
		setChangeListeners(arrObjChangeListeners);
		setTextComponent(txtComponent);
		setUndoManager(objUndoManager);
		setUndoAction(actUndo);
		setRedoAction(actRedo);
	}
	
	protected boolean addChangeListener(final ChangeListener objChangeListener)
	{
		return(getChangeListeners().add(objChangeListener));
	}
	
	protected boolean addChangeListeners(final ChangeListener[] arrObjChangeListeners)
	{
		return(getChangeListeners().addAll(Arrays.asList(arrObjChangeListeners)));
	}
	
	protected boolean addChangeListeners(final List<ChangeListener> lstChangeListeners)
	{
		return(getChangeListeners().addAll(lstChangeListeners));
	}
	
	protected List<ChangeListener> getChangeListeners()
	{
		if(mLstChangeListeners == null)
			mLstChangeListeners = new ArrayList<ChangeListener>();
		
		return(mLstChangeListeners);
	}
	
	protected Action getRedoAction()
	{
		return(mActRedo);
	}
	
	protected JTextComponent getTextComponent()
	{
		return(mTxtComponent);
	}
	
	protected Action getUndoAction()
	{
		return(mActUndo);
	}
	
	protected UndoManager getUndoManager()
	{
		return(mObjUndoManager);
	}
	
	protected boolean removeChangeListener(final ChangeListener objChangeListener)
	{
		return(getChangeListeners().remove(objChangeListener));
	}
	
	protected boolean removeChangeListeners(final ChangeListener[] arrObjChangeListeners)
	{
		return(getChangeListeners().removeAll(Arrays.asList(arrObjChangeListeners)));
	}
	
	protected boolean removeChangeListeners(final List<ChangeListener> lstChangeListeners)
	{
		return(getChangeListeners().removeAll(lstChangeListeners));
	}
	
	protected void setChangeListener(final ChangeListener objChangeListener)
	{
		getChangeListeners().clear();
		addChangeListener(objChangeListener);
	}
	
	protected void setChangeListeners(final ChangeListener[] arrObjChangeListeners)
	{
		getChangeListeners().clear();
		addChangeListeners(Arrays.asList(arrObjChangeListeners));
	}
	
	protected void setChangeListeners(final List<ChangeListener> lstChangeListeners)
	{
		getChangeListeners().clear();
		addChangeListeners(lstChangeListeners);
	}
	
	protected void setRedoAction(final Action actRedo)
	{
		mActRedo = actRedo;
	}
	
	protected void setTextComponent(final JTextComponent txtComponent)
	{
		mTxtComponent = txtComponent;
	}
	
	protected void setUndoAction(final Action actUndo)
	{
		mActUndo = actUndo;
	}
	
	protected void setUndoManager(final UndoManager objUndoManager)
	{
		mObjUndoManager = objUndoManager;
	}
	
	public void undoableEditHappened(final UndoableEditEvent objUndoableEditEvent)
	{
		getUndoManager().addEdit(objUndoableEditEvent.getEdit());
					
		getUndoAction().setEnabled(getUndoManager().canUndo());
		getRedoAction().setEnabled(getUndoManager().canRedo());
		
		for(ChangeListener objChangeListener : getChangeListeners())
			objChangeListener.stateChanged(new ChangeEvent(getTextComponent()));
	}
}