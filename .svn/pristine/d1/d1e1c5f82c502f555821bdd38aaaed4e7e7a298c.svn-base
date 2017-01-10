package com.ncjk.utcs.modules.logs.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;

import net.sf.json.JSONObject;


@Controller("logAction") 
@Scope("prototype")
public class LogAction {

	@Resource(name = "logService")
	private ILogService logService;
	
	private String userAccount;
	private String userName;
	private String loginIpaddr;
	private String startDate;
	private String endDate;
	//以下为分页查询参数
	private Integer draw;
	//起始位置
	private Integer start;
	//查询长度
	private Integer length;
	/**  
	* @Description: 查询所有操作日志
	* @param     
	* @return void   
	* @author shiwanglai
	* @date 2016年8月30日 下午6:41:35  
	*/
	public void queryAllLogs(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		JSONObject jsonObject = logService.findLogs(userAccount,loginIpaddr,startDate,endDate,
				start,length,draw);
		try {
			response.getWriter().write(jsonObject.toString());
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


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginIpaddr() {
		return loginIpaddr;
	}

	public void setLoginIpaddr(String loginIpaddr) {
		this.loginIpaddr = loginIpaddr;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
}
