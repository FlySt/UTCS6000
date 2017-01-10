package com.ncjk.utcs.modules.resources.basic.services.interfaces;

import java.util.List;

import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户接口
 */
public interface IUserService {
	/**
	 * 查询所有用户信息
	 */
	public JSONObject findUsers(String userName, int deptId, int page, int pageSize);

	/**
	 * 分页查询用户信息
	 */
	@SuppressWarnings("unchecked")
	public List<UtcsUser> findUsersByHql(String hql, int page, int pageSize);
	
	/**
	 * 删除角色信息
	 * @param ids 角色ID数组
	 * @return
	 */
	public boolean delUsers(int[] ids);

	/**
	 * 查询用户帐号是否有重复
	 */
	public boolean isExitUserAccount(String userAccount, int userId);

	/**
	 * 添加或修改用户信息
	 */
	//public boolean saveOrUpdateUser(UtcsUser userInfo, String[] moodulePowerIds);
	public boolean saveOrUpdateUser(UtcsUser userInfo);
	/**
	 * 根据ID查询用户信息
	 */
	public UtcsUser findUserById(int UserId);

	/**
	 * 重置密码
	 */
	public boolean resetPassword(int[] ids);

	/**
	 * 修改密码
	 */
	public boolean modifyPassword(UtcsUser userInfo);
	
	/**
	 * 创建用户树
	 * */
	public JSONArray bulidUserTree();
	
	/**
	 * 创建用户树
	 * */
	public JSONArray bulidUserAccountTree();
}
