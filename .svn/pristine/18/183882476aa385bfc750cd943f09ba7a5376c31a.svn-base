package com.ncjk.utcs.modules.resources.basic.services.interfaces;

import java.util.List;

import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUserGroup;

import net.sf.json.JSONObject;

public interface IUserGroupService {

	/**  
	* @Description: 用户
	* @param groupName page  pageSize 
	* @return JSONObject   
	* @author shiwanglai
	* @date 2016年8月19日 上午10:25:37  
	*/
	public JSONObject findUserGroups(String groupName, int page, int pageSize);
	
	/**  
	* @Description: 分页查询用户组信息
	* @param hql page   pageSize 
	* @return List<UtcsUserGroup>    
	* @author shiwanglai
	* @date 2016年8月19日 上午10:26:35  
	*/
	public List<UtcsUserGroup> findUserGroupsByHql(String hql, int page, int pageSize);
	/**  
	* @Description: 删除用户组信息
	* @param ids    
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月19日 上午11:14:30  
	*/
	public boolean delUserGroups(int[] ids);
	/**  
	* @Description: 查询组名称是否有重复
	* @param groupName   userGroupId  
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月19日 上午11:15:06  
	*/
	public boolean isExitGroupName(String groupName, int userGroupId);
	/**  
	* @Description: 添加或修改用户组信息
	* @param UserGroup  powerId   
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月19日 上午10:40:05  
	*/
	public boolean saveOrUpdateUserGroup(UtcsUserGroup UserGroup, String[] powerId);
	/**  
	* @Description: 添加或修改用户组权限
	* @param UserGroup  powerId   
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月19日 上午10:40:05  
	*/
	public boolean saveOrUpdateUserGroupPower(int UserGroupId, String[] powerId) ;
	
	/**  
	* @Description: 获取用户权限信息
	* @param  UtcsUser  
	* @return JSONObject   
	* @author shiwanglai
	* @date 2016年8月24日 下午2:09:28  
	*/
	public JSONObject getUserPowerInfo(UtcsUser user);
	/**  
	* @Description: 根据ID查询用户组信息
	* @param userGroupId    
	* @return UtcsUserGroup   
	* @author shiwanglai
	* @date 2016年8月19日 上午10:38:04  
	*/
	public UtcsUserGroup findUserGroupById(int userGroupId);
}
