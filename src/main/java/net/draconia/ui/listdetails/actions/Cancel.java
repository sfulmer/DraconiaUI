package net.draconia.ui.listdetails.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.swing.AbstractAction;

import net.draconia.ui.listdetails.DetailsPanel;

public abstract class Cancel<ModelType extends Observable> extends AbstractAction
{
	private static final long serialVersionUID = 8145583039803633458L;
	
	private DetailsPanel<ModelType> mPnlDetails;
	
	public Cancel()
	{
		this(null);
	}
	
	public Cancel(final DetailsPanel<ModelType> pnlDetails)
	{
		super("Cancel");
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);
		
		setDetailsPanel(pnlDetails);
	}
	
	public void actionPerformed(final ActionEvent objActionEvent)
	{
		ModelType objModel = getDetailsPanel().getModel().getModel();
		ModelType objWorking = getDetailsPanel().getModel().getWorkingModel();
		
		Class<?> clsModel = objModel.getClass();
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
				
				Object objValue = objGetter.invoke(objWorking, new Object[0]);
				
				objSetter.invoke(objModel, new Object[] {objValue});
				}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException objException)
				{ }
		
		getDetailsPanel().getModel().setEditing(true);
		getDetailsPanel().getModel().setEditing(false);
	}
	
	protected DetailsPanel<ModelType> getDetailsPanel()
	{
		return(mPnlDetails);
	}
	
	protected void setDetailsPanel(final DetailsPanel<ModelType> pnlDetails)
	{
		mPnlDetails = pnlDetails;
	}
}