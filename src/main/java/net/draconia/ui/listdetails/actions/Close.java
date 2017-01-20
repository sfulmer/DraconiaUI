package net.draconia.ui.listdetails.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

public class Close extends AbstractAction
{
	private static final long serialVersionUID = 9006939544295952505L;
	
	private Window mWndToClose;
	
	public Close()
	{
		super("Close");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_L);
	}
	
	public Close(final Window wndToClose)
	{
		this();
		
		setToClose(wndToClose);
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		getToClose().dispose();
	}
	
	protected Window getToClose()
	{
		return(mWndToClose);
	}
	
	protected void setToClose(final Window wndToClose)
	{
		mWndToClose = wndToClose;
	}
}