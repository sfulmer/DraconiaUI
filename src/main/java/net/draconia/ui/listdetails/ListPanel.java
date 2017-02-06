package net.draconia.ui.listdetails;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import java.util.Observable;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.draconia.ui.listdetails.actions.Apply;
import net.draconia.ui.listdetails.actions.Delete;
import net.draconia.ui.listdetails.actions.Edit;
import net.draconia.ui.listdetails.actions.New;
import net.draconia.ui.listdetails.actions.Save;
import net.draconia.ui.listdetails.listeners.TableMouseListener;
import net.draconia.ui.listdetails.listeners.TableSelectionListener;
import net.draconia.ui.listdetails.model.ListTableModel;

public class ListPanel<ModelType extends Observable> extends EnablablePanel
{
	private static final long serialVersionUID = -2467526661859795221L;
	
	private Apply<ModelType> mActApply;
	private ButtonsPanel mPnlButtons;
	private Delete<ModelType> mActDelete;
	private DetailsPanel<ModelType> mPnlDetails;
	private Edit<ModelType> mActEdit;
	private JScrollPane mPnlList;
	private JTable mTblList;
	private TableSelectionListener<ModelType> mObjListSelectionListener;
	private ListTableModel<ModelType> mObjListTableModel;
	private New<ModelType> mActNew;
	private Save<ModelType> mActSave;
	private TableMouseListener<ModelType> mObjMouseListener;
	
	public ListPanel(final LayoutManager objLayoutManager, final DetailsPanel<ModelType> pnlDetails, final Apply<ModelType> actApply, final Save<ModelType> actSave)
	{
		super(objLayoutManager);
		
		setApplyAction(actApply);
		setDetailsPanel(pnlDetails);
		setSaveAction(actSave);
	}
	
	public ListPanel(final LayoutManager objLayoutManager)
	{
		super(objLayoutManager);
	}
	
	protected Apply<ModelType> getApplyAction()
	{
		return(mActApply); 
	}
	
	protected ButtonsPanel getButtonsPanel()
	{
		if(mPnlButtons == null)
			setButtonsPanel(new ButtonsPanel());
		
		return(mPnlButtons);
	}
	
	protected Delete<ModelType> getDeleteAction()
	{
		return(mActDelete);
	}
	
	protected DetailsPanel<ModelType> getDetailsPanel()
	{
		return(mPnlDetails);
	}
	
	protected Edit<ModelType> getEditAction()
	{
		return(mActEdit);
	}
	
	protected JScrollPane getListScrollPane()
	{
		if(mPnlList == null)
			setListScrollPane(new JScrollPane());
		
		return(mPnlList);
	}
	
	protected JTable getListTable()
	{
		return(mTblList);
	}
	
	protected TableSelectionListener<ModelType> getListSelectionListener()
	{
		return(mObjListSelectionListener);
	}
	
	protected ListTableModel<ModelType> getListTableModel()
	{
		return(mObjListTableModel);
	}
	
	protected TableMouseListener<ModelType> getMouseListener()
	{
		if(mObjMouseListener == null)
			mObjMouseListener = new TableMouseListener<ModelType>(getDetailsPanel(), getApplyAction(), getEditAction(), getSaveAction());
		
		return(mObjMouseListener);
	}
	
	protected New<ModelType> getNewAction()
	{
		return(mActNew);
	}
	
	protected Save<ModelType> getSaveAction()
	{
		return(mActSave);
	}
	
	protected void initPanel()
	{
		add(getListScrollPane(), BorderLayout.NORTH);
		add(getButtonsPanel(), BorderLayout.SOUTH);
	}
	
	protected void setApplyAction(final Apply<ModelType> actApply)
	{
		mActApply = actApply;
	}
	
	protected void setButtonsPanel(final ButtonsPanel pnlButtons)
	{
		if(pnlButtons == null)
			mPnlButtons = new ButtonsPanel();
		else
			mPnlButtons = pnlButtons;
		
		mPnlButtons.setButtons(new Action[] {getNewAction(), getEditAction(), getDeleteAction()});
	}
	
	protected void setDeleteAction(final Delete<ModelType> actDelete)
	{
		mActDelete = actDelete;
	}
	
	protected void setDetailsPanel(final DetailsPanel<ModelType> pnlDetails)
	{
		mPnlDetails = pnlDetails;
	}
	
	protected void setEditAction(final Edit<ModelType> actEdit)
	{
		mActEdit = actEdit;
	}
	
	protected void setListScrollPane(final JScrollPane pnlList)
	{
		if(pnlList == null)
			mPnlList = new JScrollPane();
		else
			mPnlList = pnlList;
		
		mPnlList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mPnlList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mPnlList.setViewportView(getListTable());
	}
	
	protected void setListSelectionListener(final TableSelectionListener<ModelType> objListSelectionListener)
	{
		mObjListSelectionListener = objListSelectionListener;
	}
	
	protected void setListTable(final JTable tblList)
	{
		mTblList = tblList;
		
		mTblList.setBorder(BorderFactory.createLoweredBevelBorder());
		mTblList.setFont(getFont());
		mTblList.setModel(getListTableModel());
		
		mTblList.addMouseListener(getMouseListener());
		mTblList.getSelectionModel().addListSelectionListener(getListSelectionListener());
	}
	
	protected void setListTableModel(final ListTableModel<ModelType> objTableModel)
	{
		mObjListTableModel = objTableModel;
	}
	
	protected void setMouseListener(final TableMouseListener<ModelType> objMouseListener)
	{
		mObjMouseListener = objMouseListener;
	}
	
	protected void setNewAction(final New<ModelType> actNew)
	{
		mActNew = actNew;
	}
	
	protected void setSaveAction(final Save<ModelType> actSave)
	{
		mActSave = actSave;
	}
}