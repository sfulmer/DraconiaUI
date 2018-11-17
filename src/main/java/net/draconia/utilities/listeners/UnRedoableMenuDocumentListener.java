package net.draconia.utilities.listeners;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.text.JTextComponent;

public class UnRedoableMenuDocumentListener implements DocumentListener, Serializable
{
	private static final long serialVersionUID = -573332939747855497L;
	
	private List<ChangeListener> mLstChangeListeners;
	private int miLastChange = 0, miLastNotifiedChange = 0; 
	private JTextComponent mTxtComponent;
	
	public UnRedoableMenuDocumentListener(final JTextComponent txtComponent, final ChangeListener objChangeListener)
	{
		setChangeListener(objChangeListener);
		setTextComponent(txtComponent);
	}
	
	public UnRedoableMenuDocumentListener(final JTextComponent txtComponent, final ChangeListener[] arrObjChangeListeners)
	{
		setChangeListeners(Arrays.asList(arrObjChangeListeners));
		setTextComponent(txtComponent);
	}
	
	public UnRedoableMenuDocumentListener(final JTextComponent txtComponent, final List<ChangeListener> lstChangeListeners)
	{
		setChangeListeners(lstChangeListeners);
		setTextComponent(txtComponent);
	}
	
	protected boolean addChangeListener(final ChangeListener objChangeListener)
	{
		return(getChangeListeners().add(objChangeListener));
	}
	
	public void changedUpdate(final DocumentEvent objDocumentEvent)
	{
		incrementLastChange();
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				if(getLastNotifiedChange() != getLastChange())
					{
					setLastNotifiedChange(getLastChange());
					
					for(ChangeListener objChangeListener : getChangeListeners())
						objChangeListener.stateChanged(new ChangeEvent(getTextComponent()));
					}
				
				if((getTextComponent().getSelectionStart() + objDocumentEvent.getLength()) > getTextComponent().getText().length())
					getTextComponent().setCaretPosition(getTextComponent().getText().length());
				else
					getTextComponent().setCaretPosition(getTextComponent().getText().substring(0, getTextComponent().getSelectionStart()).length() + objDocumentEvent.getLength());
				
				getTextComponent().repaint();
			}
		});
	}
	
	protected List<ChangeListener> getChangeListeners()
	{
		if(mLstChangeListeners == null)
			mLstChangeListeners = new ArrayList<ChangeListener>();
		
		return(mLstChangeListeners);
	}
	
	protected int getLastChange()
	{
		return(miLastChange);
	}
	
	protected int getLastNotifiedChange()
	{
		return(miLastNotifiedChange);
	}
	
	protected JTextComponent getTextComponent()
	{
		return(mTxtComponent);
	}
	
	protected void incrementLastChange()
	{
		setLastChange(getLastChange() + 1);
	}
	
	public void insertUpdate(final DocumentEvent objDocumentEvent)
	{
		changedUpdate(objDocumentEvent);
	}
	
	protected boolean removeChangeListener(final ChangeListener objChangeListener)
	{
		return(getChangeListeners().remove(objChangeListener));
	}
	
	public void removeUpdate(final DocumentEvent objDocumentEvent)
	{
		changedUpdate(objDocumentEvent);
	}
	
	protected void setChangeListener(final ChangeListener objChangeListener)
	{
		getChangeListeners().clear();
		getChangeListeners().add(objChangeListener);
	}
	
	protected void setChangeListeners(final List<ChangeListener> lstChangeListeners)
	{
		getChangeListeners().clear();
		getChangeListeners().addAll(lstChangeListeners);
	}
	
	protected void setLastChange(final int iLastChange)
	{
		miLastChange = iLastChange;
	}
	
	protected void setLastNotifiedChange(final int iLastNotifiedChange)
	{
		miLastNotifiedChange = iLastNotifiedChange;
	}
	
	protected void setTextComponent(final JTextComponent txtComponent)
	{
		mTxtComponent = txtComponent;
	}
}