package com.ncjk.utcs.modules.access.services.interfaces;

import net.sf.json.JSONObject;

/** 
* @ClassName: IUserLoginService 
* @Description: 用户登录接口类 
* @author 石望来
* @date 2016年8月23日 下午5:21:46 
* 
* tags 
*/
public interface IUserLoginService {
	
	public JSONObject userLogin(String userAccount,String userPassword);
}
