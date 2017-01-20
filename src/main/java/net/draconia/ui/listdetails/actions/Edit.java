package net.draconia.ui.listdetails.actions;

import java.awt.event.KeyEvent;

import java.util.Observable;

import net.draconia.ui.listdetails.actions.EditingAction;
import net.draconia.ui.listdetails.model.DetailsPanelModel;

public abstract class Edit<ModelType extends Observable> extends EditingAction<ModelType>
{
	private static final long serialVersionUID = -4483818628539517432L;
	
	public Edit()
	{
		this(null);
	}
	
	public Edit(final DetailsPanelModel<ModelType> objModel)
	{
		super("Edit...", objModel);
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);
		
		setEnabled(false);
	}
}