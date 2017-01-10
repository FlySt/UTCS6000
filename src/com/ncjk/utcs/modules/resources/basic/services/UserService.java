package com.ncjk.utcs.modules.resources.basic.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.common.util.DesUtil;
//import com.ncjk.utcs.common.util.Pinyin;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsDept;
//import com.ncjk.utcs.modules.basic.pojo.UtcsModule;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;
//import com.ncjk.utcs.modules.basic.pojo.UtcsUserModule;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IDeptService;
//import com.ncjk.utcs.modules.basic.services.interfaces.IModuleService;
//import com.ncjk.utcs.modules.basic.services.interfaces.IUserGroupService;
//import com.ncjk.utcs.modules.basic.services.interfaces.IUserModuleService;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IUserService;

/** 
* @ClassName: UserService 
* @Description: 用户业务实现 
* @author 石望来
* @date 2016年8月19日 上午10:29:30 
* 
* tags 
*/
@Scope("prototype")
@Component("userService")
public class UserService implements IUserService {
	private ICommonDAO comDAO;
	private IDeptService deptService;

	/**
	 * 查询所有用户信息
	 */
	public JSONObject findUsers(String userName, int deptId, int page, int pageSize) {
		System.out.println("userName:"+userName);
		String hql = " from UtcsUser t  where (t.userSign=8) and t.userAccount!='Admin'";
		StringBuffer condition = new StringBuffer();
		if (userName != null && !"".equals(userName)) {
			condition.append(" and  t.userName like '%" + userName + "%'");
		}
		if (deptId != -1 && deptId != 0) {
			String resultIds = deptService.returnDeptIdString(deptId);
			condition.append("and t.utcsDept.deptId in (" + resultIds + ")");
		}
		JSONObject jsonObj = new JSONObject();
		JSONArray invdata = new JSONArray();
		// 分页查询用户信息
		List<UtcsUser> utcsUsers = findUsersByHql(hql + condition.toString(), page, pageSize);
		if (utcsUsers != null && !utcsUsers.isEmpty()) {
			for (UtcsUser utcsUser : utcsUsers) {
				JSONObject js = new JSONObject();
				js.put("userId", utcsUser.getUserId());
				js.put("userAccount", utcsUser.getUserAccount());
				js.put("userName", utcsUser.getUserName());

				js.put("status", utcsUser.getUseStatus());
				js.put("deptName", utcsUser.getUtcsDept().getDeptName());
				js.put("userTel", utcsUser.getUserTel());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				js.put("validStartDate", sdf.format(utcsUser.getValidStartDate()));
				js.put("validEndDate", sdf.format(utcsUser.getValidEndDate()));
				js.put("userExplain", utcsUser.getUserExplain());
				invdata.add(js);
			}
		}
		// 查询总条数
		int total = countUsers(condition.toString());
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
	 * 分页查询用户信息
	 */

	public List<UtcsUser> findUsersByHql(String hql, int page, int pageSize) {
		@SuppressWarnings("unchecked")
		List<UtcsUser> utcsUsers = (List<UtcsUser>) comDAO.findByHql(hql, page, pageSize);
		return utcsUsers;
	}

	/**
	 * 根据HQL语句查询用户总条数
	 */
	public int countUsers(String hql) {
		int count = 0;
		StringBuffer sb = new StringBuffer(" select count(t.id) from  UtcsUser t where 1=1 and t.userAccount!='Admin' ");
		sb.append(hql);
		Object objs = comDAO.findByHql(sb.toString());
		if (objs != null) {
			count = ((Long) objs).intValue();
		}
		return count;
	}

	/**
	 * 删除用户信息
	 * @param ids 用户ID数组
	 * @return
	 */
	public boolean delUsers(int[] ids) {
		boolean b = false;
		if (ids != null && ids.length > 0) {
			String idstr = "";
			for (int id : ids) {
				idstr = idstr + "," + id;
			}
			idstr = idstr.substring(1);
			b = comDAO.deleteByHql(" delete UtcsUser t where t.userId in (" + idstr + ")");
		}
		return b;
	}

	/**
	 * 查询用户帐号是否有重复
	 */
	public boolean isExitUserAccount(String userAccount, int userId) {
		userAccount.trim();
		boolean b = true;
		if (userId != 0) {
			UtcsUser user = findUserById(userId);
			if (user != null && user.getUserAccount().equals(userAccount)) {
				return b;
			}
		}
		if (userAccount != null && !"".equals(userAccount)) {
			String hql = "from UtcsUser t where t.userAccount = '" + userAccount + "'";
			Object obj = comDAO.findByHql(hql);
			if (obj != null) {
				b = false;
			}
		}
		return b;
	}

	/**
	 * 添加或修改用户信息
	 */
//	public boolean saveOrUpdateUser(UtcsUser utmUser, String[] moodulePowerIds) {
	public boolean saveOrUpdateUser(UtcsUser utmUser) {
		UtcsUser user = new UtcsUser();
		if (utmUser.getUserId() == null) {// 新增
			user.setLoginTimes(0);
			user.setUserAccount(utmUser.getUserAccount()); // 用户帐号
		}
		else {// 修改
			// 根据用户ID查询用户信息
			user = findUserById(utmUser.getUserId());
		}


		user.setUserSign(8);
		user.setUpdateTime(new Date()); // 更新时间
		if(!utmUser.getUserPassword().equals(user.getUserPassword())){
			String password = ""; 
			try {
				DesUtil des = new DesUtil("RJ@NCJK");// 自定义密钥
				password = des.encrypt(utmUser.getUserAccount()+"_"+utmUser.getUserPassword());
			} catch (Exception e) {
				e.printStackTrace();
			}
			user.setUserPassword(password); // 用户密码
		}
		user.setUserName(utmUser.getUserName()); // 用户名称

		// 根据部门ID查询部门信息
		UtcsDept dept = deptService.findDeptById(utmUser.getUtcsDept().getDeptId());
		if (dept != null) {
			user.setUtcsDept(dept); // 用户所属部门
		}
		user.setUserEmail(utmUser.getUserEmail()); // 用户Email
		user.setUserTel(utmUser.getUserTel()); // 用户电话号码
		user.setUseStatus(utmUser.getUseStatus()); // 使用状态
		user.setIdentityCard(utmUser.getIdentityCard());
		user.setUserGroupIds(utmUser.getUserGroupIds());
	//	if (utmUser.getUserPhoto() != null) {
	//		user.setUserPhoto(utmUser.getUserPhoto());
	//	}
	//	user.setPhotoPostfix(utmUser.getPhotoPostfix());
		user.setLimitIpaddrs(utmUser.getLimitIpaddrs());
		user.setValidStartDate(utmUser.getValidStartDate());
		user.setValidEndDate(utmUser.getValidEndDate());
		user.setUserExplain(utmUser.getUserExplain());


		UtcsUser us = (UtcsUser) ServletActionContext.getRequest().getSession().getAttribute("user");
		user.setUpdateAccount(us.getUserAccount());
		// 添加或修改模块信息
		boolean b = comDAO.saveOrUpdate(user);
		return b;
	}

	/**
	 * 根据ID查询用户信息
	 */
	public UtcsUser findUserById(int userId) {
		UtcsUser user = (UtcsUser) comDAO.findObjectById(UtcsUser.class, userId);
		return user;
	}

	/**
	 * 重置密码
	 * @param utmUser
	 * @return
	 */
	public boolean resetPassword(int[] ids) {
		UtcsUser user = (UtcsUser) ServletActionContext.getRequest().getSession().getAttribute("user");
		boolean b = false;
		if(user==null){
			return b;
		}
		if (ids != null && ids.length > 0) {
			String idstr = "";
			for (int id : ids) {
				idstr = idstr + "," + id;
			}
			idstr = idstr.substring(1);
			@SuppressWarnings("unchecked")
			List<UtcsUser> userList = comDAO.findByHql(" from UtcsUser t  where t.userId in (" + idstr + ")", 0, 0);
			String operateUser = user.getUserAccount();
			Date operateTime = new Date();
			if(userList!=null&&!userList.isEmpty()){
				for(UtcsUser us:userList){
					String password = ""; 
					try {
						DesUtil des = new DesUtil("RJ@NCJK");// 自定义密钥
						password = des.encrypt(us.getUserAccount()+"_123456");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					us.setUserPassword(password);
					us.setUpdateAccount(operateUser);
					us.setUpdateTime(operateTime);
					b = comDAO.saveOrUpdate(us);
				}
			}
			//String password = "123456";
			//b = comDAO.updateByHql(" update UtcsUser t set t.userPassword='" + password + "', t.updateAccount='"
			//		+ user.getUserAccount() + "' where t.userId in (" + idstr + ")");
		}
		return b;
	}

	/**
	 * 修改密码
	 */
	public boolean modifyPassword(UtcsUser userInfo) {
		UtcsUser user = (UtcsUser) ServletActionContext.getRequest().getSession().getAttribute("user");
		String password = ""; 
		try {
			DesUtil des = new DesUtil("RJ@NCJK");// 自定义密钥
			password = des.encrypt(userInfo.getUserAccount()+"_"+userInfo.getUserPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userInfo.setUserPassword(password);
		userInfo.setUpdateAccount(user.getUserAccount());
		// 修改密码
		boolean b = comDAO.saveOrUpdate(userInfo);
		return b;
	}
	
	/**
	 * 创建用户树
	 * */
	public JSONArray bulidUserTree(){
		JSONArray invdata = new JSONArray();
		String hql = "from UtcsUser t where t.userSign=8 or t.userAccount ='Admin' ";
		List<UtcsUser> users = findUsersByHql(hql, 0, 0);
		if (users != null && !users.isEmpty()) {
			for (UtcsUser user : users) {
				JSONObject js = new JSONObject();
				js.put("id", "user_" + user.getUserId());
				js.put("pId", "dept_" + user.getUtcsDept().getDeptId());
				js.put("name", user.getUserName()+"["+user.getUserAccount()+"]");
				invdata.add(js);
			}
		}
		return invdata;
	}

	/**
	 * 创建用户树
	 * */
	public JSONArray bulidUserAccountTree(){
		JSONArray invdata = new JSONArray();
		String hql = "from UtcsUser t where t.userSign=8   or t.userAccount ='Admin' ";
		List<UtcsUser> users = findUsersByHql(hql, 0, 0);
		if (users != null && !users.isEmpty()) {
			for (UtcsUser user : users) {
				JSONObject js = new JSONObject();
				js.put("id", "user_" + user.getUserAccount());
				js.put("pId", "dept_" + user.getUtcsDept().getDeptId());
				js.put("name", user.getUserName()+"["+user.getUserAccount()+"]");
				invdata.add(js);
			}
		}
		return invdata;
	}
	
	public ICommonDAO getComDAO() {
		return comDAO;
	}

	@Resource(name = "commonDAO")
	public void setComDAO(ICommonDAO comDAO) {
		this.comDAO = comDAO;
	}

	public IDeptService getDeptService() {
		return deptService;
	}

	@Resource(name = "deptService")
	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}

}