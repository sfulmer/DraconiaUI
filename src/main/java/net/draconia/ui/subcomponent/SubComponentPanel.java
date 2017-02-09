package net.draconia.ui.subcomponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;

import javax.annotation.PostConstruct;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.border.TitledBorder;

import net.draconia.ui.listdetails.ButtonsPanel;
import net.draconia.ui.listdetails.EnablablePanel;

import net.draconia.ui.subcomponent.actions.Add;
import net.draconia.ui.subcomponent.actions.Edit;
import net.draconia.ui.subcomponent.actions.Remove;
import net.draconia.ui.subcomponent.listeners.SubComponentListSelectionListener;

public class SubComponentPanel<ModelType extends Observable, SubcomponentType extends Observable> extends EnablablePanel
{
	private static final long serialVersionUID = 6182235111430180194L;
	
	private Add<ModelType, SubcomponentType> mActAdd;
	private ButtonsPanel mPnlButtons;
	private Edit<ModelType, SubcomponentType> mActEdit;
	private JScrollPane mPnlList;
	private JTable mTblList;
	private Remove<SubcomponentType> mActRemove;
	private String msSubComponentName;
	private SubComponentPanelModel<ModelType> mObjModel;
	private SubComponentPanelObserver<ModelType, SubcomponentType> mObjPanelObserver;
	private SubComponentTableModel<ModelType, SubcomponentType> mObjTableModel;
	
	public SubComponentPanel()
	{
		super(new BorderLayout(5, 5));
	}
	
	public SubComponentPanel(final String sSubComponentName)
	{
		super(new BorderLayout(5, 5));
		
		setSubComponentName(sSubComponentName);
	}
	
	protected Add<ModelType, SubcomponentType> getAddAction()
	{
		return(mActAdd);
	}
	
	protected ButtonsPanel getButtonPanel()
	{
		if(mPnlButtons == null)
			{
			mPnlButtons = new ButtonsPanel(new Action[] {getAddAction(), getEditAction(), getRemoveAction()});
			
			mPnlButtons.setEnabled(false);
			}
		
		return(mPnlButtons);
	}
	
	protected Edit<ModelType, SubcomponentType> getEditAction()
	{
		return(mActEdit);
	}
	
	protected JScrollPane getListScrollPane()
	{
		if(mPnlList == null)
			setListScrollPane(new JScrollPane(getListTable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		return(mPnlList);
	}
	
	protected JTable getListTable()
	{
		if(mTblList == null)
			{
			mTblList = new JTable(getListTableModel());
			
			mTblList.getSelectionModel().addListSelectionListener(new SubComponentListSelectionListener<>(getListTable(), getEditAction(), getRemoveAction()));
			}
		
		return(mTblList);
	}
	
	protected SubComponentTableModel<ModelType, SubcomponentType>  getListTableModel()
	{
		return(mObjTableModel);
	}
	
	public SubComponentPanelModel<ModelType> getModel()
	{
		if(mObjModel == null)
			setModel(new SubComponentPanelModel<ModelType>());
		
		return(mObjModel);
	}
	
	protected SubComponentPanelObserver<ModelType, SubcomponentType> getPanelObserver()
	{
		return(mObjPanelObserver);
	}
	
	protected Remove<SubcomponentType> getRemoveAction()
	{
		return(mActRemove);
	}
	
	protected String getSubComponentName()
	{
		return(msSubComponentName);
	}
	
	@PostConstruct
	protected void initPanel()
	{
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), getSubComponentName(), TitledBorder.LEFT, TitledBorder.BELOW_TOP));
		
		add(getListScrollPane(), BorderLayout.NORTH);
		add(getButtonPanel(), BorderLayout.SOUTH);
		
		setEnabled(false);
		
		for(PropertyChangeListener objPropertyChangeListener : getButtonPanel().getPropertyChangeListeners("enabled"))
			getButtonPanel().removePropertyChangeListener("enabled",  objPropertyChangeListener);
		
		getButtonPanel().addPropertyChangeListener("enabled", new PropertyChangeListener()
		{
			public void propertyChange(final PropertyChangeEvent objPropertyChangeEvent)
			{
				if(objPropertyChangeEvent.getNewValue().equals(true))
					{
					getAddAction().setEnabled(true);
					
					if(getListTable().getRowCount() > 0)
						{
						getEditAction().setEnabled(getListTable().getSelectedRowCount() == 1);
						getRemoveAction().setEnabled(getListTable().getSelectedRowCount() > 0);
						}
					else
						{
						getEditAction().setEnabled(false);
						getRemoveAction().setEnabled(false);
						}
					}
				else
					{
					getAddAction().setEnabled(false);
					getEditAction().setEnabled(false);
					getRemoveAction().setEnabled(false);
					}
			}
		});
	}
	
	protected void setAddAction(final Add<ModelType, SubcomponentType> actAdd)
	{
		mActAdd = actAdd;
	}
	
	protected void setButtonPanel(final ButtonsPanel pnlButtons)
	{
		mPnlButtons = pnlButtons;
		
		mPnlButtons.setButtons(new Action[] {getAddAction(), getEditAction(), getRemoveAction()});
		
		mPnlButtons.setEnabled(false);
		
	}
	
	protected void setEditAction(final Edit<ModelType, SubcomponentType> actEdit)
	{
		mActEdit = actEdit;
		
		mActEdit.setSubcomponentTable(getListTable());
	}
	
	protected void setListScrollPane(final JScrollPane pnlList)
	{
		if(pnlList == null)
			mPnlList = new JScrollPane(getListTable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		else
			mPnlList = pnlList;
		
		mPnlList.setPreferredSize(new Dimension(mPnlList.getPreferredSize().width, (int)(mPnlList.getPreferredSize().height * .6)));
	}
	
	protected void setListTable(final JTable tblList)
	{
		if(tblList == null)
			mTblList = new JTable(getListTableModel());
		else
			mTblList = tblList;
	}
	
	protected void setListTableModel(final SubComponentTableModel<ModelType, SubcomponentType> objTableModel)
	{
		mObjTableModel = objTableModel;
	}
	
	protected void setModel(final SubComponentPanelModel<ModelType> objModel)
	{
		if(mObjModel != null)
			mObjModel.deleteObservers();
		
		if(objModel == null)
			mObjModel = new SubComponentPanelModel<ModelType>();
		else
			mObjModel = objModel;
		
		mObjModel.addObserver(getPanelObserver());
	}
	
	protected void setPanelObserver(final SubComponentPanelObserver<ModelType, SubcomponentType> objPanelObserver)
	{
		mObjPanelObserver = objPanelObserver;
	}
	
	protected void setRemoveAction(final Remove<SubcomponentType> actRemove)
	{
		mActRemove = actRemove;
		
		mActRemove.setListTable(getListTable());
	}
	
	protected void setSubComponentName(final String sSubComponentName)
	{
		msSubComponentName = sSubComponentName;
	}
}