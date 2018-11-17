package net.draconia.utilities.listeners;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.text.JTextComponent;

public class BoundChangeListener implements ChangeListener, Serializable
{
	private static final long serialVersionUID = 4844656446591807892L;
	
	private Class<?> mClsValueType;
	private Object mObjModel;
	private String msField;
	
	public BoundChangeListener(final Object objModel, final String sField)
	{
		this(objModel, sField, String.class);
	}
	
	public BoundChangeListener(final Object objModel, final String sField, final Class<?> clsValueType)
	{
		setModel(objModel);
		setField(sField);
		setValueType(clsValueType);
	}
	
	protected Object castToExpectedType(final Object objValue) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Constructor<?> funcCopyConstructor = getValueType().getConstructor(new Class<?>[] {objValue.getClass()});
		
		if(!funcCopyConstructor.canAccess(null))
			funcCopyConstructor.setAccessible(true);
		
		return(funcCopyConstructor.newInstance(new Object[] {objValue}));
	}
	
	protected String getField()
	{
		return(msField);
	}
	
	protected Object getFieldValue() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Method funcGetter = getGetter();
		Object objValue = funcGetter.invoke(getModel(), new Object[0]);
		
		return(objValue);
	}
	
	protected Method getGetter() throws NoSuchMethodException, SecurityException
	{
		return(getModel().getClass().getMethod("get" + getField().substring(0, 1).toUpperCase() + getField().substring(1), new Class<?>[0]));
	}
	
	protected Object getModel()
	{
		return(mObjModel);
	}
	
	protected Method getSetter() throws NoSuchMethodException, SecurityException
	{
		return(getModel().getClass().getMethod("set" + getField().substring(0, 1).toUpperCase() + getField().substring(1), new Class<?>[] {getValueType()}));
	}
	
	protected Class<?> getValueType()
	{
		return(mClsValueType);
	}
	
	protected void setField(final String sField)
	{
		msField = sField;
	}
	
	protected void setFieldValue(final String sFieldValue) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		try
			{
			getSetter().invoke(getModel(), new Object[] {castToExpectedType(sFieldValue)});
			}
		catch(Exception objException)
			{
			objException.printStackTrace(System.err);
			}
	}
	
	protected void setModel(final Object objModel)
	{
		mObjModel = objModel;
	}
	
	protected void setValueType(final Class<?> clsValueType)
	{
		mClsValueType = clsValueType;
	}
	
	public void stateChanged(final ChangeEvent objChangeEvent)
	{
		JTextComponent txtEventSource = ((JTextComponent)(objChangeEvent.getSource()));
		String sEventValue = txtEventSource.getText();
		
		try
			{
			Object objCastedValue = null;
			Object objFieldValue = getFieldValue();
			
			try
				{
				objCastedValue = castToExpectedType(sEventValue);
				}
			catch(InvocationTargetException objException)
				{
				if(getValueType().equals(String.class))
					objCastedValue = "";
				else if(Number.class.isAssignableFrom(getValueType()))
					objCastedValue = 0;
				}
			
			if(objCastedValue != null)
				{
				if(!objCastedValue.equals(objFieldValue))
					setFieldValue(sEventValue);
				}
			else
				if(objFieldValue != null)
					setFieldValue(sEventValue);
			}
		catch(Exception objException)
			{
			objException.printStackTrace(System.err);
			}
	}
}