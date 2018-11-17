package net.draconia.utilities.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.Serializable;

import javax.swing.event.DocumentListener;

import javax.swing.text.Document;

public class UnRedoablePropertyChangeListener implements PropertyChangeListener, Serializable
{
	private static final long serialVersionUID = -1147976152927530833L;
	
	private DocumentListener mObjDocumentListener;
	
	public UnRedoablePropertyChangeListener(final DocumentListener objDocumentListener)
	{
		setDocumentListener(objDocumentListener);
	}
	
	protected DocumentListener getDocumentListener()
	{
		return(mObjDocumentListener);
	}
	
	public void propertyChange(final PropertyChangeEvent objPropertyChangeEvent)
	{
		Document objDocumentOld = ((Document)(objPropertyChangeEvent.getOldValue()));
		Document objDocumentNew = ((Document)(objPropertyChangeEvent.getNewValue()));
		
		if(objDocumentOld != null)
			objDocumentOld.removeDocumentListener(getDocumentListener());
		if(objDocumentNew != null)
			objDocumentNew.addDocumentListener(getDocumentListener());
		
		getDocumentListener().changedUpdate(null);
	}
	
	protected void setDocumentListener(final DocumentListener objDocumentListener)
	{
		mObjDocumentListener = objDocumentListener;
	}
}