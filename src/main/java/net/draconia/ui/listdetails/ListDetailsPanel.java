package net.draconia.ui.listdetails;

import java.awt.BorderLayout;
import java.awt.Window;

import java.util.Observable;

import javax.annotation.PostConstruct;
import javax.swing.Action;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import net.draconia.ui.listdetails.actions.Close;

public class ListDetailsPanel<ModelType extends Observable> extends EnablablePanel
{
	private static final long serialVersionUID = 3494005354276086702L;
	
	private ButtonsPanel mPnlButtons;
	private Close mActClose;
	private DetailsPanel<ModelType> mPnlDetails;
	private ListPanel<ModelType> mPnlList;
	
	public ListDetailsPanel()
	{
		super(new BorderLayout(5, 5));
		
		addAncestorListener(new AncestorListener()
		{
			public void ancestorAdded(final AncestorEvent objAncestorEvent)
			{
				getButtonsPanel().setButtons(new Action[] {getCloseAction()});
				
				((Window)(objAncestorEvent.getAncestor())).pack();
				((Window)(objAncestorEvent.getAncestor())).repaint();
			}
			
			public void ancestorRemoved(final AncestorEvent objAncestorEvent)
			{ }
			
			public void ancestorMoved(final AncestorEvent objAncestorEvent)
			{ }
		});
	}
	
	protected ButtonsPanel getButtonsPanel()
	{
		if(mPnlButtons == null)
			setButtonsPanel(new ButtonsPanel());
		
		return(mPnlButtons);
	}
	
	protected Close getCloseAction()
	{
		if(mActClose == null)
			setCloseAction(null);
		
		return(mActClose);
	}
	
	protected DetailsPanel<ModelType> getDetailsPanel()
	{
		return(mPnlDetails);
	}
	
	protected ListPanel<ModelType> getListPanel()
	{
		return(mPnlList);
	}
	
	protected void initControls()
	{
		add(getListPanel(), BorderLayout.WEST);
		add(getDetailsPanel(), BorderLayout.EAST);
		add(getButtonsPanel(), BorderLayout.SOUTH);
	}
	
	@PostConstruct
	protected void initPanel()
	{
		initControls();
	}
	
	protected void setButtonsPanel(final ButtonsPanel pnlButtons)
	{
		mPnlButtons = pnlButtons;
		
		mPnlButtons.setButtons(new Action[] {getCloseAction()});
	}
	
	protected void setCloseAction(final Close actClose)
	{
		if(actClose == null)
			mActClose = new Close((Window)(getTopLevelAncestor()));
		else
			mActClose = actClose;
	}
	
	protected void setDetailsPanel(final DetailsPanel<ModelType> pnlDetails)
	{
		mPnlDetails = pnlDetails;
	}
	
	protected void setListPanel(final ListPanel<ModelType> pnlList)
	{
		mPnlList = pnlList;
	}
}