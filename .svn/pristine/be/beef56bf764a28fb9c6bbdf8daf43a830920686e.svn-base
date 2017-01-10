package com.ncjk.utcs.modules.system.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ncjk.utcs.modules.system.pojo.UtcsNetWorkParam;
import com.ncjk.utcs.modules.system.pojo.UtcsPluginParam;
import com.ncjk.utcs.modules.system.services.interfaces.INetWorkParamService;
import com.ncjk.utcs.modules.system.services.interfaces.IPluginParamService;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ncjk.utcs.modules.system.pojo.UtcsSystemParam;
import com.ncjk.utcs.modules.system.services.interfaces.ISystemParaService;

import net.sf.json.JSONObject;

/** 
* @ClassName: SystemParamAction 
* @Description: 系统参数Action 
* @author 石望来
* @date 2016年8月26日 下午4:18:58 
* 
* tags 
*/
@Scope("prototype")
@Controller("systemParamAction")
public class SystemParamAction {
	@Resource
	ISystemParaService systemParaService;
	@Resource
	INetWorkParamService netWorkParamService;
	@Resource
	IPluginParamService pluginParamService;
	//参数类型 0 系统常规参数 1服务器通信参数
	private int paramType;
	private UtcsSystemParam utcsSystemParam =new UtcsSystemParam();
	private UtcsNetWorkParam utcsNetWorkParam = new UtcsNetWorkParam();
	private UtcsPluginParam utcsPluginParam = new UtcsPluginParam();
	public String getFirstParam(){
		utcsSystemParam = systemParaService.findSystemParam();
		utcsNetWorkParam = netWorkParamService.findNetWorkParam();
		utcsPluginParam = pluginParamService.findPluginParam();
		return "success";
	}
	
	public void saveParam(){
		JSONObject jsonObject = new JSONObject();
		HttpServletResponse response = ServletActionContext.getResponse();
		System.out.println(utcsSystemParam.getSystemName());
		System.out.println(utcsSystemParam.getSystemVersion());
		boolean isSave = systemParaService.saveParam(utcsSystemParam);
		response.setContentType("text/json;charset=utf-8");
		String msg = "";
		if(isSave){
			msg = "ok";
		}else {
		}
		jsonObject.put("msg", msg);
		try {
			response.getWriter().write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/** 
	* @return utcsSystemParam 
	*/
	public UtcsSystemParam getUtcsSystemParam() {
		return utcsSystemParam;
	}

	/** 
	* @param utcsSystemParam
	*/
	public void setUtcsSystemParam(UtcsSystemParam utcsSystemParam) {
		this.utcsSystemParam = utcsSystemParam;
	}

	public UtcsNetWorkParam getUtcsNetWorkParam() {
		return utcsNetWorkParam;
	}

	public void setUtcsNetWorkParam(UtcsNetWorkParam utcsNetWorkParam) {
		this.utcsNetWorkParam = utcsNetWorkParam;
	}

	public int getParamType() {
		return paramType;
	}

	public void setParamType(int paramType) {
		this.paramType = paramType;
	}
	
	
}
