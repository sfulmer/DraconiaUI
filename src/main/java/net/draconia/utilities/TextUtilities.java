package net.draconia.utilities;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import javax.swing.undo.UndoManager;

import net.draconia.utilities.actions.TextCopy;
import net.draconia.utilities.actions.TextCut;
import net.draconia.utilities.actions.TextDelete;
import net.draconia.utilities.actions.TextPaste;
import net.draconia.utilities.actions.TextRedo;
import net.draconia.utilities.actions.TextSelectAll;
import net.draconia.utilities.actions.TextUndo;
import net.draconia.utilities.listeners.TextComponentCaretListener;

public class TextUtilities implements Serializable
{
	private static final long serialVersionUID = 986707425578607868L;
	
	private MouseUtilities mObjMouseUtilities;
	
	protected MouseUtilities getMouseUtilities()
	{
		if(mObjMouseUtilities == null)
			mObjMouseUtilities = new MouseUtilities();
		
		return(mObjMouseUtilities);
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
		UndoManager objUndoManager = new UndoManager();
		UnRedoablePopupMenu mnuPopup = new UnRedoablePopupMenu(objUndoManager);
		
		Objects.requireNonNull(txtComponent);
		Objects.requireNonNull(objChangeListener);
		
		objDocumentListener = new UnRedoableMenuDocumentListener(txtComponent, objChangeListener);
		
		txtComponent.addPropertyChangeListener("document", new UnRedoablePropertyChangeListener(objDocumentListener));
		
		objUndoableEditListener = new UnRedoableUndoableEditListener(objUndoManager, txtComponent, objChangeListener, mnuPopup.getRedoAction(), mnuPopup.getUndoAction());
		
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
	
	protected class BoundChangeListener implements ChangeListener, Serializable
	{
		private static final long serialVersionUID = 4844656446591807892L;
		
		private Class<?> mClsValueType;
		private Object mObjModel;
		private String msField;
		
		public BoundChangeListener(final Object objModel, final String sField)
		{
			this(objModel, sField, String.class);
		}
		
		public BoundChangeListener(final Object objModel, final String sField, final Class<?> clsValueType)
		{
			setModel(objModel);
			setField(sField);
			setValueType(clsValueType);
		}
		
		protected Object castToExpectedType(final Object objValue) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
		{
			Constructor<?> funcCopyConstructor = getValueType().getConstructor(new Class<?>[] {objValue.getClass()});
			
			if(!funcCopyConstructor.canAccess(null))
				funcCopyConstructor.setAccessible(true);
			
			return(funcCopyConstructor.newInstance(new Object[] {objValue}));
		}
		
		protected String getField()
		{
			return(msField);
		}
		
		protected Object getFieldValue() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
		{
			Method funcGetter = getGetter();
			Object objValue = funcGetter.invoke(getModel(), new Object[0]);
			
			return(objValue);
		}
		
		protected Method getGetter() throws NoSuchMethodException, SecurityException
		{
			return(getModel().getClass().getMethod("get" + getField().substring(0, 1).toUpperCase() + getField().substring(1), new Class<?>[0]));
		}
		
		protected Object getModel()
		{
			return(mObjModel);
		}
		
		protected Method getSetter() throws NoSuchMethodException, SecurityException
		{
			return(getModel().getClass().getMethod("set" + getField().substring(0, 1).toUpperCase() + getField().substring(1), new Class<?>[] {getValueType()}));
		}
		
		protected Class<?> getValueType()
		{
			return(mClsValueType);
		}
		
		protected void setField(final String sField)
		{
			msField = sField;
		}
		
		protected void setFieldValue(final String sFieldValue) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
		{
			try
				{
				getSetter().invoke(getModel(), new Object[] {castToExpectedType(sFieldValue)});
				}
			catch(Exception objException)
				{
				objException.printStackTrace(System.err);
				}
		}
		
		protected void setModel(final Object objModel)
		{
			mObjModel = objModel;
		}
		
		protected void setValueType(final Class<?> clsValueType)
		{
			mClsValueType = clsValueType;
		}
		
		public void stateChanged(final ChangeEvent objChangeEvent)
		{
			JTextComponent txtEventSource = ((JTextComponent)(objChangeEvent.getSource()));
			String sEventValue = txtEventSource.getText();
			
			try
				{
				Object objCastedValue = null;
				Object objFieldValue = getFieldValue();
				
				try
					{
					objCastedValue = castToExpectedType(sEventValue);
					}
				catch(InvocationTargetException objException)
					{
					if(getValueType().equals(String.class))
						objCastedValue = "";
					else if(Number.class.isAssignableFrom(getValueType()))
						objCastedValue = 0;
					}
				
				if(objCastedValue != null)
					{
					if(!objCastedValue.equals(objFieldValue))
						setFieldValue(sEventValue);
					}
				else
					if(objFieldValue != null)
						setFieldValue(sEventValue);
				}
			catch(Exception objException)
				{
				objException.printStackTrace(System.err);
				}
		}
	}
	
	protected class UnRedoableMenuDocumentListener implements DocumentListener, Serializable
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
	
	protected class UnRedoablePropertyChangeListener implements PropertyChangeListener, Serializable
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
	
	protected class UnRedoableUndoableEditListener implements Serializable, UndoableEditListener
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

	protected class UnRedoablePopupMenu extends JPopupMenu implements PopupMenuListener
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
				mActRedo = new TextRedo(getUndoManager());
			
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
}