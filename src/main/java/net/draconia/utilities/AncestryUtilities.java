package net.draconia.utilities;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;

import java.io.Serializable;

public class AncestryUtilities implements Serializable
{
	private static final long serialVersionUID = 3568598829161419478L;
	
	public Window findWindowAncestorOfComponent(final Component objComponent)
	{
		if(objComponent instanceof Container)
			{
			Container objAncestor = ((Container)(objComponent));
			
			while(!(objAncestor instanceof Window))
				objAncestor = ((Component)(objAncestor)).getParent();
			
			return((Window)(objAncestor));
			}
		
		return(null);
	}
}