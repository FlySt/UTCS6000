package com.ncjk.utcs.modules.resources.basic.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsGroupPower;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsModule;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUserGroup;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IUserGroupService;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
* @ClassName: UserGroupAction 
* @Description: 用户组Action 
* @author 石望来
* @date 2016年8月19日 上午10:30:28 
* 
* tags 
*/
@Controller
@Scope("prototype")
@Component("userGroupAction")
public class UserGroupAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "logService")
	ILogService logService;
	
	private IUserGroupService userGroupService;
	//当前页面数
	private int page;
	//页面显示数
	private int limit;
	//用户组id数组
	private int[] ids;
	//用户组ID
	private int userGroupId;
	//用户组对象
	private UtcsUserGroup userGroup = new UtcsUserGroup();
	//用户组名称
	private String userGroupName;
	//用户组名称
	private String searchName;
	//权限
	private String[] powerId;
	private List<UtcsGroupPower> groupPowers;
	//删除名称数据
	private String[] names;
	String operate = "失败";

	/**
	 * 查询用户组信息
	 */
	public void queryAllUserGroups() {
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject jsonObj = userGroupService.findUserGroups(searchName, page, limit);
		response.setContentType("text/json; charset=utf-8");
		try {
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改用户组
	 */
	public String modifyUserGroup() {
		if (userGroupId != 0) {
			userGroup = userGroupService.findUserGroupById(userGroupId);
			int i = 0;
			for (UtcsGroupPower groupPower : userGroup.getGroupPowers()) {
				if (i == 0) {
					groupPowers = new ArrayList<UtcsGroupPower>();
				}
				groupPowers.add(groupPower);
				i++;
			}
		}
		return "modifyUserGroup";
	}

	/**
	 * 新增或修改用户组
	 */
	public void saveUserGroup() {
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject jsonObj = new JSONObject();
		JSONArray invdata = new JSONArray();
		JSONObject js = new JSONObject();

		//添加或修改监控点信息
		boolean b = userGroupService.saveOrUpdateUserGroup(userGroup, powerId);
		if (b) {
			operate = "成功";
			jsonObj.put("result", true);
			jsonObj.put("msg", "ok");
		}
		else {
			jsonObj.put("result", false);
		}

		if (userGroup.getUserGroupId() == null) {
			logService.saveOrUpdateLog("新增用户组信息【" + userGroup.getUserGroupName() + "】" + operate, "用户组管理");
		}
		else {
			logService.saveOrUpdateLog("修改用户组信息【" + userGroup.getUserGroupName() + "】" + operate, "用户组管理");
		}
		try {
			invdata.add(js);
			jsonObj.put("rosultList", invdata);
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存用户组权限
	 */
	public void saveUserGroupPower() {
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject jsonObj = new JSONObject();
		JSONArray invdata = new JSONArray();
		JSONObject js = new JSONObject();
		//添加或修改监控点信息
		boolean b = userGroupService.saveOrUpdateUserGroupPower(userGroupId, powerId);
		if (b) {
			operate = "成功";
			jsonObj.put("result", true);
			jsonObj.put("msg", "ok");
		}
		else {
			jsonObj.put("result", false);
		}

		if (userGroup.getUserGroupId() == null) {
			logService.saveOrUpdateLog("新增用户组权限" + operate, "用户组管理");
		}
		else {
			logService.saveOrUpdateLog("修改用户组权限" + operate, "用户组管理");
		}
		try {
			invdata.add(js);
			jsonObj.put("rosultList", invdata);
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除用户组
	 */
	public void delUserGroups() {
		boolean b = userGroupService.delUserGroups(ids);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", b);
		if (b) {
			operate = "成功";
		}
		String name = "";
		if (names != null && !names.equals("")) {
			for (int i = 0; i < names.length; i++) {
				name = name + "，" + names[i];
			}
			name = name.substring(1);
		}
		logService.saveOrUpdateLog("删除用户组信息【" + name + "】" + operate, "用户组管理");
		try {
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 组名称不能重复
	 */
	public void validatorGroupName() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		Boolean b = userGroupService.isExitGroupName(userGroupName, userGroupId);
		try {
			response.getWriter().write(b.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**  
	* @Description: 获取用户组权限
	* @param     
	* @return void   
	* @author shiwanglai
	* @date 2016年8月22日 下午7:06:57  
	*/
	public void getUserGroupPower(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		UtcsUserGroup  editUserGroup = userGroupService.findUserGroupById(userGroupId);		
		JSONObject jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		//	得到用户组已有的模块树	
		if (editUserGroup.getGroupPowers() != null) {

			for (UtcsGroupPower groupPower : editUserGroup.getGroupPowers()) {
				JSONObject js = new JSONObject();
				js.put("id", "module_"+groupPower.getUtcsModule().getModuleId());
				js.put("pId", "module_"+groupPower.getUtcsModule().getParentModuleId());
				js.put("name", groupPower.getUtcsModule().getModuleName());
				array.add(js);
				String power = groupPower.getGroupPower();
				UtcsModule module = groupPower.getUtcsModule();
				if(!power.equals("00000000")){
					if(power.charAt(0)=='1'){
						JSONObject add = new JSONObject();
						add.put("id", "watch_"+module.getModuleId());
						add.put("pId", "module_"+module.getModuleId());
						add.put("name", "查看");
						array.add(add);
					}
					if(power.charAt(1)=='1'){
						JSONObject watch = new JSONObject();
						watch.put("id", "add_"+module.getModuleId());
						watch.put("pId", "module_"+module.getModuleId());
						watch.put("name", "增加");
						array.add(watch);
					}
					if(power.charAt(2)=='1'){
						JSONObject modify = new JSONObject();
						modify.put("id", "modify_"+module.getModuleId());
						modify.put("pId", "module_"+module.getModuleId());
						modify.put("name", "修改");
						array.add(modify);
					}
					if(power.charAt(3)=='1'){
						JSONObject del = new JSONObject();
						del.put("id", "del_"+module.getModuleId());
						del.put("pId", "module_"+module.getModuleId());
						del.put("name", "删除");
						array.add(del);
					}
				}
			}
		}
		jsonObject.put("data",array);
		try {
			response.getWriter().write(jsonObject.toString());
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public IUserGroupService getUserGroupService() {
		return userGroupService;
	}

	@Resource(name = "userGroupService")
	public void setUserGroupService(IUserGroupService userGroupService) {
		this.userGroupService = userGroupService;
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

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public UtcsUserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UtcsUserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public List<UtcsGroupPower> getGroupPowers() {
		return groupPowers;
	}

	public void setGroupPowers(List<UtcsGroupPower> groupPowers) {
		this.groupPowers = groupPowers;
	}

	public String[] getPowerId() {
		return powerId;
	}

	public void setPowerId(String[] powerId) {
		this.powerId = powerId;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
	
}
