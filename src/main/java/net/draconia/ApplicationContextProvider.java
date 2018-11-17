package net.draconia;

import java.io.Serializable;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware, Serializable
{
	private static final long serialVersionUID = 8439086989591256012L;
	
	private static ApplicationContext msObjContext;
	
	public ApplicationContext getApplicationContext()
	{
		return(msObjContext);
	}
	
	public void setApplicationContext(final ApplicationContext objApplicationContext) throws BeansException
	{
		msObjContext = objApplicationContext;
	}
}