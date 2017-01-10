package com.ncjk.utcs.modules.logs.services.interfaces;

import net.sf.json.JSONObject;

/** 
* @ClassName: ILogService
* @Description: 系统日志接口类 
* @author 石望来
* @date 2016年8月23日 下午4:46:32 
* 
* tags 
*/
public interface ILogService {
	/**  
	* @Description: 查找所有操作日志
	* @param    
	* @return JSONObject   
	* @author shiwanglai
	* @date 2016年8月30日 下午6:22:35  
	*/
	 JSONObject findLogs(String userAccount,String loginIpaddr,String startDate,String endDate,
								Integer start,Integer length,Integer draw);
	/**  
	* @Description: 保存修改日志信息
	* @param content     moduleName
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月23日 下午5:22:18  
	*/
	 boolean saveOrUpdateLog(String content,String moduleName);

	 boolean signalSaveLog(String content, String moduleName, String userAccount, String ip);
}
