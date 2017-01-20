package net.draconia.ui.listdetails;

import java.awt.FlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.swing.Action;
import javax.swing.JButton;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import net.draconia.ui.listdetails.EnablablePanel;

public class ButtonsPanel extends EnablablePanel
{
	private static final long serialVersionUID = 5835100874472074511L;
	
	private List<JButton> mLstButtons;
	
	public ButtonsPanel()
	{
		super(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
	
	public ButtonsPanel(final Action[] arrActions)
	{
		super(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		addAncestorListener(new AncestorListener()
		{
			public void ancestorRemoved(final AncestorEvent objAncestorEvent)
			{ }
			
			public void ancestorMoved(final AncestorEvent objAncestorEvent)
			{
				setFont(objAncestorEvent.getComponent().getFont());
				
				initPanel();
			}
			
			public void ancestorAdded(final AncestorEvent objAncestorEvent)
			{
				setFont(objAncestorEvent.getComponent().getFont());
				
				initPanel();
			}
		});
		
		initializeButtons(arrActions);
	}
	
	public boolean addButton(final JButton btn)
	{
		boolean bReturn = getButtonsInternal().add(btn);
		
		return(bReturn);
	}
	
	public List<JButton> getButtons()
	{
		return(Collections.unmodifiableList(getButtonsInternal()));
	}
	
	protected List<JButton> getButtonsInternal()
	{
		if(mLstButtons == null)
			mLstButtons = Collections.synchronizedList(new ArrayList<JButton>());
		
		return(mLstButtons);
	}
	
	protected void initializeButtons(final Action[] arrActions)
	{
		for(Action actButton : arrActions)
			addButton(new JButton(actButton));
	}
	
	@PostConstruct
	protected void initPanel()
	{
		for(JButton btn : getButtons())
			{
			btn.setFont(getFont());
			
			add(btn);
			}
	}
	
	public void setButtons(final Action[] arrActions)
	{
		getButtonsInternal().clear();
		
		initializeButtons(arrActions);
		
		removeAll();
		
		initPanel();
	}
}