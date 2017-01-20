package net.draconia.ui.listdetails.actions;

import java.util.Observable;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.draconia.ui.listdetails.model.DetailsPanelModel;

public abstract class EditingAction<ModelType extends Observable> extends AbstractAction
{
	private static final long serialVersionUID = 527615569577536727L;
	
	private DetailsPanelModel<ModelType> mObjModel;
	
	public EditingAction()
	{
		super();
	}
	
	public EditingAction(final String sName)
	{
		super(sName);
	}
	
	public EditingAction(final String sName, final Icon objIcon)
	{
		super(sName, objIcon);
	}
	
	public EditingAction(final DetailsPanelModel<ModelType> objModel)
	{
		super();
		
		setModel(objModel);
	}
	
	public EditingAction(final String sName, final DetailsPanelModel<ModelType> objModel)
	{
		this(sName);
		
		setModel(objModel);
	}
	
	public EditingAction(final String sName, final Icon objIcon, final DetailsPanelModel<ModelType> objModel)
	{
		this(sName, objIcon);
		
		setModel(objModel);
	}
	
	protected DetailsPanelModel<ModelType> getModel()
	{
		return(mObjModel);
	}
	
	protected void setModel(final DetailsPanelModel<ModelType> objModel)
	{
		mObjModel = objModel;
	}
}