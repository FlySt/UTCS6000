package com.ncjk.utcs.common.servlet;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


public class SpringUtil implements ApplicationContextAware{

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
		SpringUtil.applicationContext = applicationContext;
	}
	
	
}