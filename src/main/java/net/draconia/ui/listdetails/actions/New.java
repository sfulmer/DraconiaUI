package net.draconia.ui.listdetails.actions;

import java.awt.event.KeyEvent;

import java.util.Observable;

import net.draconia.ui.listdetails.actions.EditingAction;
import net.draconia.ui.listdetails.model.DetailsPanelModel;

public abstract class New<ModelType extends Observable> extends EditingAction<ModelType>
{
	private static final long serialVersionUID = -4483818628539517432L;
	
	public New()
	{
		super("New");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_N);
	}
	
	public New(final DetailsPanelModel<ModelType> objModel)
	{
		super("New", objModel);
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_N);
	}
}