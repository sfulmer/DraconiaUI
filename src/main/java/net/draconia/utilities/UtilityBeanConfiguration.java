package net.draconia.utilities;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("net.draconia.utilities")
public class UtilityBeanConfiguration implements Serializable
{
	private static final long serialVersionUID = -6700155212301623724L;
	
	private MouseUtilities mObjMouseUtilities;
	
	@Bean("mouseUtilities")
	public MouseUtilities getMouseUtilities()
	{
		if(mObjMouseUtilities == null)
			mObjMouseUtilities = new MouseUtilities();
		
		return(mObjMouseUtilities);
	}
}