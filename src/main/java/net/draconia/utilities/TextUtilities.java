package net.draconia.utilities;

import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import java.io.Serializable;

import java.util.Objects;

import javax.swing.KeyStroke;

import javax.swing.event.CaretListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;

import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import javax.swing.undo.UndoManager;

import net.draconia.utilities.listeners.BoundChangeListener;
import net.draconia.utilities.listeners.BoundPropertyChangeListener;
import net.draconia.utilities.listeners.TextComponentCaretListener;
import net.draconia.utilities.listeners.UnRedoableMenuDocumentListener;
import net.draconia.utilities.listeners.UnRedoablePopupMenu;
import net.draconia.utilities.listeners.UnRedoablePropertyChangeListener;
import net.draconia.utilities.listeners.UnRedoableUndoableEditListener;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class TextUtilities implements Serializable
{
	private static final long serialVersionUID = 986707425578607868L;
		
	@Autowired
	private MouseUtilities mObjMouseUtilities;
	private UndoManager mObjUndoManager;
	
	protected MouseUtilities getMouseUtilities()
	{
		return(mObjMouseUtilities);
	}
	
	public UndoManager getUndoManager()
	{
		if(mObjUndoManager == null)
			mObjUndoManager = new UndoManager();
		
		return(mObjUndoManager);
	}
	
	protected void installPopupMenu(final JTextComponent txtComponent, final UnRedoablePopupMenu mnuPopup)
	{
		new MouseUtilities().installPopupListener(txtComponent, mnuPopup);
	}
	
	public void resetTextComponent(final JTextComponent txtComponent)
	{
		Document objDocument = txtComponent.getDocument();
		
		for(CaretListener objCaretListener : txtComponent.getCaretListeners())
			txtComponent.removeCaretListener(objCaretListener);
		
		for(DocumentListener objDocumentListener : ((AbstractDocument)(objDocument)).getDocumentListeners())
			objDocument.removeDocumentListener(objDocumentListener);
		
		for(UndoableEditListener objUndoableEditListener : ((AbstractDocument)(objDocument)).getUndoableEditListeners())
			objDocument.removeUndoableEditListener(objUndoableEditListener);
		
		for(MouseListener objMouseListener : txtComponent.getMouseListeners())
			txtComponent.removeMouseListener(objMouseListener);
	}
	
	public JTextComponent setupUnRedoableBiDirectionalBoundTextComponent(final JTextComponent txtComponent, final PropertyChangeable objModel, final String sField, final Class<?> clsValueType)
	{
		JTextComponent txtReturn = setupUnRedoableMenuTextComponent(txtComponent, new BoundChangeListener(objModel, sField, clsValueType));
		
		objModel.addPropertyChangeListener(new BoundPropertyChangeListener(txtReturn, sField, clsValueType));
		
		return(txtReturn);
	}
	
	public JTextComponent setupUnRedoableBoundTextComponent(final JTextComponent txtComponent, final Object objModel, final String sField)
	{
		return(setupUnRedoableMenuTextComponent(txtComponent, new BoundChangeListener(objModel, sField)));
	}
	
	public JTextComponent setupUnRedoableBoundTextComponent(final JTextComponent txtComponent, final Object objModel, final String sField, final Class<?> clsValueType)
	{
		return(setupUnRedoableMenuTextComponent(txtComponent, new BoundChangeListener(objModel, sField, clsValueType)));
	}
	
	public JTextComponent setupUnRedoableMenuTextComponent(final JTextComponent txtComponent)
	{
		UnRedoablePopupMenu mnuPopup = new UnRedoablePopupMenu();
		
		Objects.requireNonNull(txtComponent);
		
		txtComponent.addCaretListener(new TextComponentCaretListener(mnuPopup.getCopyAction(), mnuPopup.getCutAction(), mnuPopup.getDeleteAction()));
		
		installPopupMenu(txtComponent, mnuPopup);
		
		return(txtComponent);
	}
	
	public JTextComponent setupUnRedoableMenuTextComponent(final JTextComponent txtComponent, final ChangeListener objChangeListener)
	{
		Document objDocument;
		DocumentListener objDocumentListener;
		UndoableEditListener objUndoableEditListener;
		UnRedoablePopupMenu mnuPopup = new UnRedoablePopupMenu(getUndoManager());
		
		Objects.requireNonNull(txtComponent);
		Objects.requireNonNull(objChangeListener);
		
		objDocumentListener = new UnRedoableMenuDocumentListener(txtComponent, objChangeListener);
		
		txtComponent.addPropertyChangeListener("document", new UnRedoablePropertyChangeListener(objDocumentListener));
		
		objUndoableEditListener = new UnRedoableUndoableEditListener(getUndoManager(), txtComponent, objChangeListener, mnuPopup.getRedoAction(), mnuPopup.getUndoAction());
		
		objDocument = txtComponent.getDocument();
		
		if(objDocument != null)
			{
			objDocument.addDocumentListener(objDocumentListener);
			objDocument.addUndoableEditListener(objUndoableEditListener);

			txtComponent.getActionMap().put("REDO", mnuPopup.getRedoAction());
			txtComponent.getActionMap().put("UNDO", mnuPopup.getUndoAction());
			
			txtComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK), "REDO");
			txtComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "UNDO");
			}
		
		txtComponent.addCaretListener(new TextComponentCaretListener(mnuPopup.getCopyAction(), mnuPopup.getCutAction(), mnuPopup.getDeleteAction()));
		
		installPopupMenu(txtComponent, mnuPopup);
		
		return(txtComponent);
	}
	
	public JTextComponent setupUnRedoableMenuTextComponent(final JTextComponent txtComponent, final ChangeListener[] arrObjChangeListeners)
	{
		Document objDocument;
		DocumentListener objDocumentListener;
		UndoableEditListener objUndoableEditListener;
		UndoManager objUndoManager = new UndoManager();
		UnRedoablePopupMenu mnuPopup = new UnRedoablePopupMenu(objUndoManager);
		
		Objects.requireNonNull(txtComponent);
		Objects.requireNonNull(arrObjChangeListeners);
		
		objDocumentListener = new UnRedoableMenuDocumentListener(txtComponent, arrObjChangeListeners);
		
		txtComponent.addPropertyChangeListener("document", new UnRedoablePropertyChangeListener(objDocumentListener));
		
		objUndoableEditListener = new UnRedoableUndoableEditListener(objUndoManager, txtComponent, arrObjChangeListeners, mnuPopup.getRedoAction(), mnuPopup.getUndoAction());
		
		objDocument = txtComponent.getDocument();
		
		if(objDocument != null)
			{
			objDocument.addDocumentListener(objDocumentListener);
			objDocument.addUndoableEditListener(objUndoableEditListener);

			txtComponent.getActionMap().put("REDO", mnuPopup.getRedoAction());
			txtComponent.getActionMap().put("UNDO", mnuPopup.getUndoAction());
			
			txtComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK), "REDO");
			txtComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "UNDO");
			}
		
		txtComponent.addCaretListener(new TextComponentCaretListener(mnuPopup.getCopyAction(), mnuPopup.getCutAction(), mnuPopup.getDeleteAction()));
		
		installPopupMenu(txtComponent, mnuPopup);
		
		return(txtComponent);
	}	
}