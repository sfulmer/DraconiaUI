package net.draconia.ui.listdetails;

import java.awt.Component;
import java.awt.LayoutManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class EnablablePanel extends JPanel
{
	private static final long serialVersionUID = -2876518482903421521L;
	
	public EnablablePanel(final LayoutManager objLayoutManager)
	{
		super(objLayoutManager);
		
		addPropertyChangeListener("enabled", new EnabledListener(this));
	}
}

class EnabledListener implements PropertyChangeListener, Serializable
{
	private static final long serialVersionUID = 818909515276514539L;
	
	private JComponent mObjHookedUp;
	
	public EnabledListener(final JComponent objHookedUp)
	{
		super();
		
		setHookedUp(objHookedUp);
	}
	
	protected JComponent getHookedUp()
	{
		return(mObjHookedUp);
	}
	
	public void propertyChange(final PropertyChangeEvent objPropertyChangeEvent)
	{
		boolean bNewValue = ((Boolean)(objPropertyChangeEvent.getNewValue()));
		
		for(int iLength = getHookedUp().getComponentCount(), iLoop = 0; iLoop < iLength; iLoop++)
			{
			Component objComponent = getHookedUp().getComponent(iLoop);
			String sComponentName = objComponent.getName();
			
			if(sComponentName == null)
				sComponentName = "";
			
			objComponent.setEnabled(bNewValue);
			}
	}
	
	protected void setHookedUp(final JComponent objHookedUp)
	{
		mObjHookedUp = objHookedUp;
	}
}