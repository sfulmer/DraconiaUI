package net.draconia.ui.listdetails;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import java.util.Observable;

import javax.annotation.PostConstruct;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ListDetailsFrame<ModelType extends Observable> extends JFrame
{
	private static final long serialVersionUID = -5982167625864987317L;
	
	private Boolean mbCentered;
	private ListDetailsPanel<ModelType> mPnlListDetails; 
	
	public ListDetailsFrame(final String sTitle)
	{
		super(sTitle);
	}
	
	protected ListDetailsPanel<ModelType> getListDetailsPanel()
	{
		if(mPnlListDetails == null)
			mPnlListDetails = new ListDetailsPanel<ModelType>();
		
		return(mPnlListDetails);
	}
	
	protected void initControls()
	{
		add(new JPanel(), BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.SOUTH);
		add(getListDetailsPanel(), BorderLayout.CENTER);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
	}
	
	@PostConstruct
	protected void initFrame()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));
		
		initControls();
		
		pack();
	}
	
	public Boolean isCentered()
	{
		if(mbCentered == null)
			setCentered(null);
		
		return(mbCentered);
	}
	
	public void setCentered(final Boolean bCentered)
	{
		if(bCentered == null)
			mbCentered = false;
		else
			mbCentered = bCentered;
		
		if(mbCentered)
			{
			Dimension szScreen = getToolkit().getScreenSize();
			
			setLocation(new Point((szScreen.width - getWidth())/2, (szScreen.height - getHeight()) / 2));
			}
	}
	
	protected void setListDetailsPanel(final ListDetailsPanel<ModelType> pnlListDetails)
	{
		if(pnlListDetails == null)
			mPnlListDetails = new ListDetailsPanel<ModelType>();
		else
			mPnlListDetails = pnlListDetails;
	}
}