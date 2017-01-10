package com.ncjk.utcs.modules.system.services.interfaces;

import com.ncjk.utcs.modules.system.pojo.UtcsSystemParam;

/** 
* @ClassName: ISystemParaService 
* @Description: 系统参数业务接口类
* @author 石望来
* @date 2016年8月26日 下午4:20:22 
* 
* tags 
*/
public interface ISystemParaService {

	/**  
	* @Description: 查找参数表的第一条数据
	* @param     
	* @return UtcsSystemParam   
	* @author shiwanglai
	* @date 2016年8月26日 下午4:37:34  
	*/
	public UtcsSystemParam findSystemParam();
	
	/**  
	* @Description: 保存参数
	* @param utcsSystemParam
	* @return boolean
	* @author shiwanglai
	* @date 2016年8月29日 上午8:49:51  
	*/
	public boolean saveParam(UtcsSystemParam utcsSystemParam);
}
