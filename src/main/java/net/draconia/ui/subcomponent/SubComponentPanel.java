package net.draconia.ui.subcomponent;

import java.awt.BorderLayout;
import java.awt.Color;

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

public class SubComponentPanel<ModelType extends Observable, SubcomponentType extends Observable> extends EnablablePanel
{
	private static final long serialVersionUID = 6182235111430180194L;
	
	private Add<SubcomponentType> mActAdd;
	private ButtonsPanel mPnlButtons;
	private Edit<SubcomponentType> mActEdit;
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
		return(mPnlButtons);
	}
	
	protected Edit<SubcomponentType> getEditAction()
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
			mTblList = new JTable(getListTableModel());
		
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
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), getSubComponentName(), TitledBorder.LEFT, TitledBorder.ABOVE_BOTTOM));
		
		add(getListScrollPane(), BorderLayout.NORTH);
		add(getButtonPanel(), BorderLayout.SOUTH);
	}
	
	protected void setAddAction(final Add<SubcomponentType> actAdd)
	{
		mActAdd = actAdd;
	}
	
	protected void setButtonPanel(final ButtonsPanel pnlButtons)
	{
		mPnlButtons = pnlButtons;
		
		mPnlButtons.setButtons(new Action[] {getAddAction(), getEditAction(), getRemoveAction()});
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
	
	protected void setSubComponentName(final String sSubComponentName)
	{
		msSubComponentName = sSubComponentName;
	}
}