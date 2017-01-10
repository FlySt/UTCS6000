package com.ncjk.utcs.modules.access.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ncjk.utcs.modules.access.services.interfaces.IUserLoginService;
import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;

import net.sf.json.JSONObject;

/** 
* @ClassName: UserLoginAction 
* @Description: 用户登录Action
* @author 石望来
* @date 2016年8月23日 下午5:27:53 
* 
* tags 
*/

@Controller("userLoginAction")
@Scope("prototype")
public class UserLoginAction {
	
	@Resource(name = "userLoginService")
	IUserLoginService userLoginService;
	
	@Resource(name = "logService")
	ILogService logService;
	
	private String userAccount;
	private String userPassword;
	/**  
	* @Description: 登录
	* @param     
	* @return return_type   
	* @author shiwanglai
	* @date 2016年8月23日 下午6:59:06  
	*/

	public void userLogin(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		System.out.println("userAccount:"+userAccount+",userPassword:"+userPassword);
		JSONObject jsonObject = userLoginService.userLogin(userAccount, userPassword);		
		try {
			String msg = jsonObject.get("msg").toString();
			if(msg=="" || msg.equals("")){
				logService.saveOrUpdateLog("用户【登录】成功", "用户登录");
			}else {
				logService.saveOrUpdateLog("用户【登录】失败"+jsonObject.get("msg"), "用户登录");
			}
			response.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**  
	* @Description: 用户退出系统
	* @param     
	* @return void   
	* @author shiwanglai
	* @date 2016年8月25日 下午5:01:09  
	*/
	public void userQuit(){
		String msg = "";
		JSONObject js = new JSONObject();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = ServletActionContext.getRequest().getSession();
		UtcsUser user = (UtcsUser)session.getAttribute("user");
		response.setContentType("text/json;charset=utf-8");
		if(session!=null){
			if(user!=null){
				logService.saveOrUpdateLog("用户【退出】系统", "退出系统");
				session.invalidate();
				msg = "退出成功";
			}else{
				
			}
		}
		js.put("msg", msg);
		try {
			response.getWriter().write(js.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
}
