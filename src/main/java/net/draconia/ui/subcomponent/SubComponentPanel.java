package net.draconia.ui.subcomponent;

import java.awt.BorderLayout;
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
	
	private Add<SubcomponentType> mActAdd;
	private ButtonsPanel mPnlButtons;
	private Edit<ModelType, SubcomponentType> mActEdit;
	private JScrollPane mPnlList;
	private JTable mTblList;
	private Remove<SubcomponentType> mActRemove;
	private String msSubComponentName;
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
	
	protected Add<SubcomponentType> getAddAction()
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
			mPnlList = new JScrollPane(getListTable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
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
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), getSubComponentName(), TitledBorder.LEFT, TitledBorder.ABOVE_TOP));
		
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
	
	protected void setAddAction(final Add<SubcomponentType> actAdd)
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