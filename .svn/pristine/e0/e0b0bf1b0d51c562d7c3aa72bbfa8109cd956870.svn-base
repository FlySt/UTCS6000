package com.ncjk.utcs.modules.resources.basic.services;



import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IGroupPowerService;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IModuleService;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IUserGroupService;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsGroupPower;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsModule;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUserGroup;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
* @ClassName: UserGroupService 
* @Description: 用户组业务实现
* @author 石望来
* @date 2016年8月19日 上午10:28:11 
* 
* tags 
*/
@Scope("prototype")
@Component("userGroupService")
public class UserGroupService implements IUserGroupService{
	
	private ICommonDAO comDAO;
	private IModuleService moduleService;
	private IGroupPowerService groupPowerService;
	/**  
	* @Description: 查询用户组信息
	* @param userGroupName  page   pageSize
	* @return JSONObject   
	* @author shiwanglai
	* @date 2016年8月19日 上午10:11:35  
	*/
	public JSONObject findUserGroups(String userGroupName, int page, int pageSize){
		String hql = " from UtcsUserGroup t  where t.showSign!=0 ";
		StringBuffer condition = new StringBuffer();
		if (userGroupName != null && !"".equals(userGroupName)) {
			condition.append(" and t.userGroupName like '%" + userGroupName + "%'");
		}
		JSONObject jsonObj = new JSONObject();
		JSONArray invdata = new JSONArray();
		// 分页查询角色信息
		List<UtcsUserGroup> userGroups = findUserGroupsByHql(hql + condition.toString(), page, pageSize);
		if (userGroups != null && !userGroups.isEmpty()) {
			for (UtcsUserGroup userGroup : userGroups) {
				JSONObject js = new JSONObject();
				js.put("userGroupId", userGroup.getUserGroupId()); // 角色ID
				js.put("userGroupName", userGroup.getUserGroupName()); // 角色名称
				js.put("userGroupDesc", userGroup.getUserGroupDesc()); // 角色描述
				String userGroupPower = ""; //roleAuthority
				//用户角色
				for (UtcsGroupPower groupPower : userGroup.getGroupPowers()) {
					String modIdRoleAuthority = groupPower.getUtcsModule().getModuleId() + "_"
							+ groupPower.getGroupPower();
					userGroupPower = userGroupPower + "," + modIdRoleAuthority;
				}
				if (!userGroupPower.equals("") && userGroupPower != null) {
					userGroupPower = userGroupPower.substring(1);
					js.put("userGroupPower", userGroupPower);// 角色模块权限
				}
				invdata.add(js);
			}
		}
		// 查询总条数
		int total = countUserGroups(condition.toString());
		int totalPage = 0;
		if (pageSize != 0) {
			int resultPage = total % pageSize;
			totalPage = (resultPage == 0) ? (total / pageSize) : (total / pageSize + 1);
		}
		jsonObj.put("data", invdata);
		jsonObj.put("page", page);
		jsonObj.put("total", total);
		jsonObj.put("totalPage", totalPage);
		jsonObj.put("objectId", 1);
		return jsonObj;
	}
	
	/**  
	* @Description: 分页查询用户组信息
	* @param hql  page pageSize
	* @return List<UtcsUserGroup>   
	* @author shiwanglai
	* @date 2016年8月19日 上午10:20:38  
	*/
	@SuppressWarnings("unchecked")
	public List<UtcsUserGroup> findUserGroupsByHql(String hql, int page, int pageSize) {
		List<UtcsUserGroup> userGroups = (List<UtcsUserGroup>) comDAO.findByHql(hql, page, pageSize);
		return userGroups;
	}
	/** 
	* @Description: 根据HQL语句查询用户组总条数
	* @param 根据HQL语句查询用户组总条数    
	* @return int   
	* @author shiwanglai
	* @date 2016年8月19日 上午10:20:06  
	*/
	public int countUserGroups(String hql) {
		int count = 0;
		StringBuffer sb = new StringBuffer(" select count(t.id) from  UtcsUserGroup t where t.showSign!=0 ");
		sb.append(hql);
		Object objs = comDAO.findByHql(sb.toString());
		if (objs != null) {
			count = ((Long) objs).intValue();
		}
		return count;
	}

	/**  
	* @Description: 删除用户组信息
	* @param ids    
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月19日 上午11:14:30  
	*/
	public boolean delUserGroups(int[] ids) {
		boolean b = false;
		if (ids != null && ids.length > 0) {
			String idstr = "";
			for (int id : ids) {
				idstr = idstr + "," + id;
			}
			idstr = idstr.substring(1);
			b = comDAO.deleteByHql(" delete UtcsUserGroup t where t.userGroupId in (" + idstr + ")");
		}
		return b;
	}

	/**  
	* @Description: 查询组名称是否有重复
	* @param groupName   userGroupId  
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月19日 上午11:15:06  
	*/
	public boolean isExitGroupName(String userGroupName, int userGroupId) {
		userGroupName = userGroupName.trim();
		boolean b = true;
		if (userGroupId != 0) {
			UtcsUserGroup userGroup = findUserGroupById(userGroupId);
			if (userGroup != null && userGroup.getUserGroupName().equals(userGroupName)) {
				return b;
			}
		}
		if (userGroupName != null && !"".equals(userGroupName)) {
			String hql = "from UtcsUserGroup t where t.userGroupName = '" + userGroupName + "'";
			Object obj = comDAO.findByHql(hql);
			if (obj != null) {
				b = false;
			}
		}
		return b;
	}
	/**  
	* @Description: 添加或修改用户组信息
	* @param UserGroup  powerId   
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月19日 上午10:40:05  
	*/
	public boolean saveOrUpdateUserGroup(UtcsUserGroup UserGroup, String[] powerId) {
		UtcsUserGroup addUserGroup = new UtcsUserGroup();
		if (UserGroup.getUserGroupId() == null) {// 新增
			//addUserGroup.setGroupName(UserGroup.getGroupName()); // 用户组名称
		}
		else {// 修改
			// 根据用户组ID查询用户组信息
			addUserGroup = findUserGroupById(UserGroup.getUserGroupId());
		}
		addUserGroup.setUserGroupName(UserGroup.getUserGroupName()); // 用户组名称
		addUserGroup.setUpdateTime(new Date()); // 更新时间
		addUserGroup.setUserGroupDesc(UserGroup.getUserGroupDesc()); // 用户组描述
		addUserGroup.setShowSign(1);

		UtcsUser user = (UtcsUser) ServletActionContext.getRequest().getSession().getAttribute("user");
		addUserGroup.setUpdateAccount(user.getUserAccount()); // 更新用户帐号
		// 添加或修改用户组信息
		boolean b = comDAO.saveOrUpdate(addUserGroup);
		return b;
	}
	/**  
	* @Description: 添加或修改用户组权限
	* @param UserGroup  powerId   
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月19日 上午10:40:05  
	*/
	public boolean saveOrUpdateUserGroupPower(int UserGroupId, String[] powerId) {
		UtcsUserGroup  editUserGroup = findUserGroupById(UserGroupId);		
		editUserGroup.setUpdateTime(new Date()); // 更新时间

		//删除用户组模块	得到用户组已有模块	
		if (editUserGroup.getGroupPowers() != null) {
			//删除用户组模块ID数组
			int[] groupPowerIds = new int[editUserGroup.getGroupPowers().size()];
			int j = 0;
			for (UtcsGroupPower groupPower : editUserGroup.getGroupPowers()) {
				groupPowerIds[j] = groupPower.getGroupPowerId().intValue();
				j++;
			}
			groupPowerService.delGroupPowers(groupPowerIds);
		}
		//给用户组中Set用户组模块表
		if (powerId != null) {
			for (int i = 0; i < powerId.length; i++) {
				String[] moduleIdPower = powerId[i].split("_");
				int moduleId = Integer.parseInt(moduleIdPower[0]);
				String power = moduleIdPower[1];

				//添加用户组模块	   模块对象 
				UtcsModule module = moduleService.findModuleById(moduleId);
				//组权限信息
				UtcsGroupPower groupPower = new UtcsGroupPower();
				groupPower.setUtcsUserGroup(editUserGroup);
				groupPower.setUtcsModule(module);
				groupPower.setModulePower(module.getModulePower());
				groupPower.setGroupPower(power);
				//赋值
				editUserGroup.getGroupPowers().add(groupPower);
			}
		}
		// 添加或修改用户组信息
		boolean b = comDAO.saveOrUpdate(editUserGroup);
		return b;
	}
	/**  
	* @Description: 获取用户权限信息以及模块信息
	* @param  UtcsUser  
	* @return JSONObject   
	* @author shiwanglai
	* @date 2016年8月24日 下午2:09:28  
	*/
	public JSONObject getUserPowerInfo(UtcsUser user){
		JSONObject jsonObject = new JSONObject();
		JSONArray buttonArray = new JSONArray();
		JSONArray rootArray = new JSONArray();
		JSONArray leftArray = new JSONArray();
		JSONArray leftSonArray = new JSONArray();
		String[] userGroupIds = user.getUserGroupIds().split(";");
		for(int i=0;i<userGroupIds.length;i++){
			JSONObject js = new JSONObject();
			UtcsUserGroup userGroup = findUserGroupById(Integer.parseInt(userGroupIds[i]));
			//UtcsGroupPower utcsGroupPower = userGroup.getGroupPowers();
			for(UtcsGroupPower groupPower:userGroup.getGroupPowers()){
				js.put("id", "module_"+groupPower.getUtcsModule().getModuleId());
				js.put("pId", "module_"+groupPower.getUtcsModule().getParentModuleId());
				js.put("name", groupPower.getUtcsModule().getModuleName());
				js.put("moduleId", groupPower.getUtcsModule().getModuleId());
				js.put("modulePath", groupPower.getUtcsModule().getModulePath());
				js.put("moduleOrder",groupPower.getUtcsModule().getModuleOrder());
				js.put("moduleName", groupPower.getUtcsModule().getModuleName());
				js.put("moduleParentId", groupPower.getUtcsModule().getParentModuleId());
				js.put("iconPath", groupPower.getUtcsModule().getIconPath());
				if(groupPower.getUtcsModule().getParentModuleId()==-1){//根菜单
					if(!rootArray.contains(js)){
						rootArray.add(js);
					}
				}else if(groupPower.getUtcsModule().getModulePower().equals("00000000")){//二级菜单
					if(!leftArray.contains(js)){
						leftArray.add(js);
					}					
				}else{//三级菜单
					if(!leftSonArray.contains(js)){
						leftSonArray.add(js);
					}	
				}		
				String power = groupPower.getGroupPower();
				UtcsModule module = groupPower.getUtcsModule();
				if(!power.equals("00000000")){
					if(power.charAt(0)=='1'){
						JSONObject add = new JSONObject();
						add.put("id", "watch_"+module.getModuleId());
						add.put("pId", "module_"+module.getModuleId());
						add.put("name", "查看");
						if(!buttonArray.contains(add)){
							buttonArray.add(add);
						}						
					}
					if(power.charAt(1)=='1'){
						JSONObject watch = new JSONObject();
						watch.put("id", "add_"+module.getModuleId());
						watch.put("pId", "module_"+module.getModuleId());
						watch.put("name", "增加");
						if(!buttonArray.contains(watch)){
							buttonArray.add(watch);
						}	
					}
					if(power.charAt(2)=='1'){
						JSONObject modify = new JSONObject();
						modify.put("id", "modify_"+module.getModuleId());
						modify.put("pId", "module_"+module.getModuleId());
						modify.put("name", "修改");
						if(!buttonArray.contains(modify)){
							buttonArray.add(modify);
						}	
					}
					if(power.charAt(3)=='1'){
						JSONObject del = new JSONObject();
						del.put("id", "del_"+module.getModuleId());
						del.put("pId", "module_"+module.getModuleId());
						del.put("name", "删除");
						if(!buttonArray.contains(del)){
							buttonArray.add(del);
						}	
					}
				}
			}
		}
		jsonObject.put("root", rootArray);//根菜单
		jsonObject.put("left", leftArray);//二级菜单
		jsonObject.put("leftSon", leftSonArray);//三级菜单		
		jsonObject.put("button", buttonArray);//按钮
		return jsonObject;
	}
	/**  
	* @Description: 根据ID查询用户组信息
	* @param userGroupId    
	* @return UtcsUserGroup   
	* @author shiwanglai
	* @date 2016年8月19日 上午10:38:04  
	*/
	public UtcsUserGroup findUserGroupById(int userGroupId) {
		UtcsUserGroup userGroup = (UtcsUserGroup) comDAO.findObjectById(UtcsUserGroup.class, userGroupId);
		return userGroup;
	}
	
	
	public ICommonDAO getComDAO() {
		return comDAO;
	}

	@Resource(name = "commonDAO")
	public void setComDAO(ICommonDAO comDAO) {
		this.comDAO = comDAO;
	}

	public IModuleService getModuleService() {
		return moduleService;
	}

	@Resource(name = "moduleService")
	public void setModuleService(IModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public IGroupPowerService getGroupPowerService() {
		return groupPowerService;
	}

	@Resource(name = "groupPowerService")
	public void setGroupPowerService(IGroupPowerService groupPowerService) {
		this.groupPowerService = groupPowerService;
	}
}
