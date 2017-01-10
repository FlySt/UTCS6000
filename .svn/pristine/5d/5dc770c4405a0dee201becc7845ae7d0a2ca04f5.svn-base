package com.ncjk.utcs.modules.resources.basic.action;

import java.io.IOException;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
//import com.ncjk.utcs.common.util.ExcelUtil;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsDept;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IDeptService;
//import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
@Component("deptAction")
public class DeptAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IDeptService deptService;
	//日志service
	@Resource(name = "logService")
	private ILogService logService;
	//当前页面数
	private int page;
	//页面显示数
	private int limit;
	//部门id数组
	private int[] ids;
	private UtcsDept deptInfo = new UtcsDept();
	//部门ID
	private int deptId;
	//部门查询名称
	private String searchName;
	//部门名称
	private String deptName;
	//选中的部门名称
	private String names;

	/**
	 * 根据部门名称查询部门
	 */
	public void queryAllDepts() {
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject jsonObj = deptService.findDepts(searchName, page, limit);
		response.setContentType("text/json; charset=utf-8");
		try {
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 根据部门ID修改部门
	 */
	public String modifyDept() {
		deptInfo = deptService.findDeptById(deptId);
		return "modifyDept";
	}
	/**
	 * 保存部门
	 */
	public void saveDept() {
		boolean b = deptService.saveOrUpdateDept(deptInfo);
		String msg = "部门更新失败";
		String logMsg = "失败";
		if (b) {
			msg = "ok";
			logMsg = "成功";
		}
		if (deptInfo.getDeptId() == null) {
			logService.saveOrUpdateLog("新增部门【" + deptInfo.getDeptName() + "】信息" + logMsg, "部门管理");
		}
		else {
			logService.saveOrUpdateLog("修改部门【" + deptInfo.getDeptName() + "】信息" + logMsg, "部门管理");
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msg", msg);
	//	jsonObj.put("result", true);
		try {
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据部门所有ID删除部门
	 */
	public void delDepts() {
		boolean b = deptService.delDpets(ids);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", b);
		String logMsg = "失败";
		if (b) {
			logMsg = "成功";
		}
		logService.saveOrUpdateLog("删除部门信息【" + names + "】" + logMsg, "部门管理");
		try {
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 部门名称不能重复
	 */
	public void validatorDeptName() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		Boolean b = deptService.isExitDeptName(deptName, deptId);
		try {
			response.getWriter().write(b.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public IDeptService getDeptService() {
		return deptService;
	}

	@Resource(name = "deptService")
	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
	}

	public UtcsDept getDeptInfo() {
		return deptInfo;
	}

	public void setDeptInfo(UtcsDept deptInfo) {
		this.deptInfo = deptInfo;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}
}