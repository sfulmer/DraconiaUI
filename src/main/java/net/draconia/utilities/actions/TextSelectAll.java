package net.draconia.utilities.actions;

import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import javax.swing.text.JTextComponent;

import org.springframework.stereotype.Component;

@Component
public class TextSelectAll extends AbstractAction
{
	private static final long serialVersionUID = 3965383888469417477L;
	
	public TextSelectAll()
	{
		super("Select All");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		JPopupMenu mnuPopup = (JPopupMenu)(((Container)(objActionEvent.getSource())).getParent());
		JTextComponent txtComponent = ((JTextComponent)(mnuPopup.getInvoker()));
		
		txtComponent.requestFocusInWindow();
		txtComponent.selectAll();
	}
}