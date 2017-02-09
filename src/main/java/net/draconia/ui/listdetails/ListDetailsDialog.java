package net.draconia.ui.listdetails;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import java.util.Observable;

import javax.annotation.PostConstruct;

import javax.swing.JDialog;
import javax.swing.JPanel;

import net.draconia.ui.listdetails.model.DialogModel;

public class ListDetailsDialog<ModelType extends Observable> extends JDialog
{
	private static final long serialVersionUID = -5982167625864987317L;
	
	private Boolean mbCentered;
	private DialogModel<ModelType> mObjModel;
	private ListDetailsPanel<ModelType> mPnlListDetails;
	
	public ListDetailsDialog(final String sTitle, final Window wndOwner)
	{
		super(wndOwner, sTitle.trim() + " Details");
	}
	
	public ListDetailsDialog(final String sTitle, final Window wndOwner, final ModalityType eModalityType)
	{
		super(wndOwner, sTitle.trim() + " Details", eModalityType);
	}
	
	protected ListDetailsPanel<ModelType> getListDetailsPanel()
	{
		if(mPnlListDetails == null)
			mPnlListDetails = new ListDetailsPanel<ModelType>();
		
		return(mPnlListDetails);
	}
	
	public DialogModel<ModelType> getModel()
	{
		return(mObjModel);
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
	protected void initDialog()
	{
		Dimension szScreen = getToolkit().getScreenSize();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));
		
		initControls();
		
		pack();
		
		setLocation(new Point((szScreen.width - getWidth()) / 2, (szScreen.height - getHeight()) / 2));
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
	
	protected void setModel(final DialogModel<ModelType> objModel)
	{
		mObjModel = objModel;
	}
}