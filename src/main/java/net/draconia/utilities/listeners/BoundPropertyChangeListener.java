package net.draconia.utilities.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.text.JTextComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.draconia.utilities.PropertyChangeable;

public class BoundPropertyChangeListener implements PropertyChangeListener, Serializable
{
	private static final long serialVersionUID = -7580088821281622795L;
	private static final Logger msObjLogger = LoggerFactory.getLogger(BoundPropertyChangeListener.class);
	
	private Class<?> mClsValueType;
	private JTextComponent mTxtComponent;
	private String msField;
	
	public BoundPropertyChangeListener(final JTextComponent txtComponent, final String sField, final Class<?> clsValueType)
	{
		setTextComponent(txtComponent);
		setField(sField);
		setValueType(clsValueType);
	}
	
	protected String getField()
	{
		return(msField);
	}
	
	protected JTextComponent getTextComponent()
	{
		return(mTxtComponent);
	}
	
	protected Class<?> getValueType()
	{
		return(mClsValueType);
	}
	
	public void propertyChange(final PropertyChangeEvent objPropertyChangeEvent)
	{
		if(objPropertyChangeEvent.getPropertyName().equalsIgnoreCase(getField()))
			try
				{
				Method funcGetter, funcSetter;
				Object objValue;
				PropertyChangeable objModel = ((PropertyChangeable)(objPropertyChangeEvent.getSource()));
				String sField = getField().substring(0, 1).toUpperCase() + getField().substring(1);
				String sValue;
				
				funcGetter = objModel.getClass().getMethod("get" + sField, new Class<?>[0]);
				funcSetter = objModel.getClass().getMethod("set" + sField, new Class<?>[] {String.class});
				
				if(!funcGetter.canAccess(objModel))
					funcGetter.setAccessible(true);
				
				if(!funcSetter.canAccess(objModel))
					funcSetter.setAccessible(true);
				
				objValue = funcGetter.invoke(objModel, new Object[0]);
				sValue = String.valueOf(objValue);
				
				if(!getTextComponent().getText().equals(sValue))
					{
					sValue = getTextComponent().getText();
					
					if(!getValueType().equals(String.class))
						{
						Method funcCasting = getValueType().getMethod("valueOf", new Class<?>[] {String.class});
						
						if(!funcCasting.canAccess(objModel))
							funcCasting.setAccessible(true);
						
						objValue = funcCasting.invoke(null, new Object[] {sValue});
						}
					else
						objValue = sValue;
					
					funcSetter.invoke(objModel, new Object[] {sValue});
					}
				}
			catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException objException)
				{
				msObjLogger.error("Error setting TextComponent's value from model...", objException);
				}
	}
	
	protected void setField(final String sField)
	{
		msField = sField;
	}
	
	protected void setTextComponent(final JTextComponent txtComponent)
	{
		mTxtComponent = txtComponent;
	}
	
	protected void setValueType(final Class<?> clsValueType)
	{
		mClsValueType = clsValueType;
	}
}