package com.ncjk.utcs.modules.common.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ncjk.utcs.modules.common.services.interfaces.ITreeResourceService;


import net.sf.json.JSONArray;

@Controller("treeResourceAction")
@Scope("prototype")
public class TreeResourceAction {	
	@Resource(name = "treeResourceService")
	private  ITreeResourceService  treeResourceService;
	// 部门级别
	private String deptLevel;
	// 显示类型
	private String type;
	
	/**
	* @Description: 获取部门树
	* @param     
	* @return void    
	* @date 2016年8月20日
	*/
	public void getDeptTrees(){
		HttpServletResponse  response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		JSONArray array = treeResourceService.buildDeptsTree(deptLevel, type);		
		try {
			response.getWriter().write(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* @Description: 获取组织结构树
	* @param
	* @return return_type    
	* @date 2016年8月21日
	*/
	public void getOrganizationTrees(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		JSONArray array = treeResourceService.buildOrganizationTrees(deptLevel, type);
		try {
			response.getWriter().write(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**  
	* @Description: 获取模块树
	* @param
	* @return void   
	* @author shiwanglai
	* @date 2016年8月22日 上午9:34:44  
	*/
	public void getModuleTrees(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		JSONArray array = treeResourceService.buildModuleTrees();		
		try {
			response.getWriter().write(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取信号机树
	 * @author shiwanglai
	 */
	public void getSignalControlerTrees(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		JSONArray array = treeResourceService.buildSignalControlerTrees(2);
		try {
			response.getWriter().write(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取地图上已有的信号机树
	 * @author shiwanglai
	 */
	public void getOnMapSignalControlerTrees(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		JSONArray array = treeResourceService.buildSignalControlerTrees(1);
		try {
			response.getWriter().write(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取区域树
	 */
	public void getRegionParamTrees(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		JSONArray array = treeResourceService.buildRegionTrees();
		try {
			response.getWriter().write(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取路口树
	 */
	public void getCrossParamTrees(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		JSONArray array = treeResourceService.buildCrossTrees();
		try {
			response.getWriter().write(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
