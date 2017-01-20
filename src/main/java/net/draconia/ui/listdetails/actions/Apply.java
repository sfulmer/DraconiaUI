package net.draconia.ui.listdetails.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.swing.AbstractAction;

import net.draconia.ui.listdetails.model.DetailsPanelModel;

public class Apply<ModelType extends Observable> extends AbstractAction
{
	private static final long serialVersionUID = -3955180561443767329L;
	
	private DetailsPanelModel<ModelType> mObjModel;
	
	public Apply()
	{
		this(null);
	}
	
	public Apply(final DetailsPanelModel<ModelType> objModel)
	{
		super("Apply");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		
		setModel(objModel);
		
		setEnabled(false);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		Class<?> clsModel = getModel().getModel().getClass();
		Map<String, Method> mapFieldsToSet = new HashMap<String, Method>();
		
		for(Method objMethod : clsModel.getDeclaredMethods())
			{
			String sMethod = objMethod.getName();
			
			if(sMethod.startsWith("set"))
				{
				String sField = sMethod.substring(3);
				
				if(sMethod.startsWith("set"))
					{
					Method objSetter = objMethod;
					
					mapFieldsToSet.put(sField, objSetter);
					}
				}
			}
		
		
		for(String sField : mapFieldsToSet.keySet())
			try
				{
				Method objGetter = clsModel.getDeclaredMethod("get" + sField, new Class<?>[0]);
				Method objSetter = mapFieldsToSet.get(sField);
				
				if(!objGetter.isAccessible())
					objGetter.setAccessible(true);
				
				if(!objSetter.isAccessible())
					objSetter.setAccessible(true);
				
				Object objValue = objGetter.invoke(getModel().getWorkingModel(), new Object[0]);
				
				objSetter.invoke(getModel().getModel(), new Object[] {objValue});
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{ }
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