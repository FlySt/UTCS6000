package com.ncjk.utcs.common.util;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ncjk.utcs.common.dao.CommonDAO;

public class PrintLog {
	private static Log logger ;
	
	public static void printErrorLog(Class obj,Exception e){
		logger = LogFactory.getLog(obj);
		Throwable th = e.getCause();
		String cause = " ";
		while(th !=null){
			cause = th.toString();
			th = e.getCause();
		}
		
	//	cause = new Date()+"@"+ cause; 
		logger.error(cause);
	}
	
	public static void printInfoLog(Class obj,String message){
		logger = LogFactory.getLog(obj);
		String cause  = new Date()+":"+ message; 
		logger.error(cause);
	}
	
	
}
