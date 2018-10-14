package net.draconia.utilities;

import java.io.Serializable;

import javax.swing.undo.UndoManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilityBeanConfiguration implements Serializable
{
	private static final long serialVersionUID = -6700155212301623724L;
	
	private UndoManager mObjUndoManager;
	
	@Bean("undoManager")
	public UndoManager getUndoManager()
	{
		if(mObjUndoManager == null)
			mObjUndoManager = new UndoManager();
		
		return(mObjUndoManager);
	}
}