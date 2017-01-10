package com.ncjk.utcs.modules.resources.basic.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.ncjk.utcs.common.util.DesUtil;
import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsDept;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IDeptService;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IUserGroupService;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IUserService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户Action
 */
@Controller
@Scope("prototype")
@Component("userAction")
public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Resource(name = "logService")
	private ILogService logService;	
	@Resource(name = "userGroupService")
	private IUserGroupService userGroupService;	
	private IUserService userService;
	private IDeptService deptService;
	//部门列表
	private List<UtcsDept> deptList;
	//当前页面数
	private int page;
	//页面显示数
	private int limit;
	//模块id数组
	private int[] ids;
	//用户ID
	private int userId;
	//用户对象
	private UtcsUser user = new UtcsUser();
	//用户帐号
	private String userAccount;
	//用户名称
	private String searchName;
	//部门ID
	private int deptId;
	//用户模块
	private String[] powerIds;
	//模块列表集
//	private List<UtcsUserModule> userModules;
	//用户组ids数据
	private String[] userGroupIds;
	//数组
	private String[] ips;
	//文件上传
	private File fileupload;
	//上传文件的名字
	private String fileuploadFileName;
	//上传文件流
	private InputStream inputStream;
	//模块名称
	private String[] names;
	String operate = "失败";
	private String oldUserPassword;

	/**
	 * 查询用户信息
	 */
	public void queryAllUsers() {
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject jsonObj = userService.findUsers(searchName, deptId, page, limit);
		response.setContentType("text/json; charset=utf-8");
		try {
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**  
	* @Description: 获取当前用户权限信息
	* @param    
	* @return void   
	* @author shiwanglai
	* @date 2016年8月24日 下午2:14:27  
	*/
	public void getCurrentUserPowerInfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		UtcsUser us = (UtcsUser) ServletActionContext.getRequest().getSession().getAttribute("user");
		JSONObject jsonObj = userGroupService.getUserPowerInfo(us);
		response.setContentType("text/json;charset=utf-8");		
		try {
			response.getWriter().write(jsonObj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改用户
	 */
	public String modifyUser() {
		deptList = deptService.deptList(0);
		if (userId != 0) {
			user = userService.findUserById(userId);
			//用户组ID
			if (user.getUserGroupIds() != null && !user.getUserGroupIds().equals("")) {
				userGroupIds = user.getUserGroupIds().split(";");
			}
		}
		return "modifyUser";
	}

	/**
	 * 新增或修改用户
	 */
	public void saveUser() {
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject jsonObj = new JSONObject();
		JSONArray invdata = new JSONArray();
		JSONObject js = new JSONObject();
        System.out.println("userAccount:"+user.getUserAccount());
		//判断图片大小
		if (fileupload != null && fileupload.length() > 1024 * 1024) {
			jsonObj.put("result", "2");
		}
		else {
			String ip = ""; //IP地址
			for (int i = 0; i < ips.length; i++) {
				if (ips[i] != null && !ips[i].equals("")) {
					ip = ip + ";" + ips[i];
				}
			}
			if (!ip.equals("")) {
				ip = ip.substring(1);
			}
			user.setLimitIpaddrs(ip); //限制IP

			if (userGroupIds != null) {
				String userGroupId = ""; //用户组ID
				for (int i = 0; i < userGroupIds.length; i++) {
					if (userGroupIds[i] != null && !userGroupIds[i].equals("")) {
						userGroupId = userGroupId + ";" + userGroupIds[i];
					}
				}
				if (!userGroupId.equals("")) {
					userGroupId = userGroupId.substring(1);
				}
				user.setUserGroupIds(userGroupId); //用户组ID
			} 

/*			if (fileupload != null && !fileupload.equals("")) {
				//图片后缀名
				String photoPostfix = fileuploadFileName.substring(fileuploadFileName.lastIndexOf("."));
		//		user.setPhotoPostfix(photoPostfix);
		//		user.setUserPhoto(FileUtil.toByteArray(fileupload));
			}*/
			//添加或修改监控点信息
			boolean b = userService.saveOrUpdateUser(user);
			if (b) {
				operate = "成功";
				jsonObj.put("result", true);
				jsonObj.put("msg", "ok");
			}
			else {
				jsonObj.put("result", false);
			}
		if (user.getUserId() == null) {
				logService.saveOrUpdateLog("新增用户【" + user.getUserName() + "】" + operate, "用户管理");
			}
			else {
				logService.saveOrUpdateLog("修改用户【" + user.getUserName() + "】" + operate, "用户管理");
			} 
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
	 * 根据ID查询照片
	 * @return
	 */
	public String photoshow() {
		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream ops = null;
		user = userService.findUserById(userId);
		try {
		//	response.getOutputStream().write(user.getUserPhoto());
			ops = response.getOutputStream();
			ops.flush();
			ops.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除用户
	 */
	public void delUsers() {
		System.out.println("ids："+ids[0]);
		boolean b = userService.delUsers(ids);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", b);
		if (b) {
			operate = "成功";
		}
		String name = "";
		for (int i = 0; i < names.length; i++) {
			name = name + "，" + names[i];
		}
		name = name.substring(1);
	//	logService.saveOrUpdateLog("删除用户【" + name + "】" + operate, "用户管理");
		try {
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户帐号不能重复
	 */
	public void validatorUserAccount() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		Boolean b = userService.isExitUserAccount(userAccount, userId);
		try {
			response.getWriter().write(b.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证原始密码是否正确
	 */
	public void validatorUserPassword() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		Boolean b = false;
		try {
			UtcsUser utcsUser = (UtcsUser) ServletActionContext.getRequest().getSession().getAttribute("user");
			if (utcsUser != null && user != null ) {
				String password = ""; 
				try {
					DesUtil des = new DesUtil("RJ@NCJK");// 自定义密钥
					password = des.encrypt(utcsUser.getUserAccount()+"_"+oldUserPassword);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(password.equals(utcsUser.getUserPassword())){
					b = true;
				}
				
			}
			response.getWriter().write(b.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改密码
	 */
	public void modifyPassword() {
		UtcsUser utmsUser = (UtcsUser) ServletActionContext.getRequest().getSession().getAttribute("user");
		utmsUser.setUserPassword(user.getUserPassword());
		utmsUser.setUpdateAccount(utmsUser.getUserAccount());
		utmsUser.setUpdateTime(new Date());
		//添加或修改监控点信息
		boolean b = userService.modifyPassword(utmsUser);
		String msg = "修改用户密码失败";
		if (b) {
			msg = "ok";
			operate = "成功";
		}
		String name = utmsUser.getUserAccount();
		logService.saveOrUpdateLog("修改用户【" + name + "】密码" + operate, "用户管理");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msg", msg);
		jsonObj.put("result", true);
		try {
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重置密码
	 */
	public void resetPassword() {
		boolean b = userService.resetPassword(ids);
		String msg = "重置用户密码失败";
		if (b) {
			msg = "ok";
			operate = "成功";
		}
		String name = "";
		for (int i = 0; i < names.length; i++) {
			name = name + "，" + names[i];
		}
		name = name.substring(1);
	//	logService.saveOrUpdateLog("重置用户【" + name + "】密码" + operate, "用户管理");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=utf-8");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("msg", msg);
		jsonObj.put("result", true);
		try {
			response.getWriter().write(jsonObj.toString());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

/*	public ILogService getLogService() {
		return logService;
	}

	@Resource(name = "logService")
	public void setLogService(ILogService logService) {
		this.logService = logService;
	} */

	public IUserService getUserService() {
		return userService;
	}

	@Resource(name = "userService")
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IDeptService getDeptService() {
		return deptService;
	}

	@Resource(name = "deptService")
	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}

	public List<UtcsDept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<UtcsDept> deptList) {
		this.deptList = deptList;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public UtcsUser getUser() {
		return user;
	}

	public void setUser(UtcsUser user) {
		this.user = user;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String[] getPowerIds() {
		return powerIds;
	}

	public void setPowerIds(String[] powerIds) {
		this.powerIds = powerIds;
	}

/*	public List<UtmsUserModule> getUserModules() {
		return userModules;
	}

	public void setUserModules(List<UtmsUserModule> userModules) {
		this.userModules = userModules;
	} */

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String[] getIps() {
		return ips;
	}

	public void setIps(String[] ips) {
		this.ips = ips;
	}

	public File getFileupload() {
		return fileupload;
	}

	public void setFileupload(File fileupload) {
		this.fileupload = fileupload;
	}

	public String getFileuploadFileName() {
		return fileuploadFileName;
	}

	public void setFileuploadFileName(String fileuploadFileName) {
		this.fileuploadFileName = fileuploadFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setServletResponse(HttpServletResponse arg0) {
	}

	public String[] getUserGroupIds() {
		return userGroupIds;
	}

	public void setUserGroupIds(String[] userGroupIds) {
		this.userGroupIds = userGroupIds;
	}

	public String getOldUserPassword() {
		return oldUserPassword;
	}

	public void setOldUserPassword(String oldUserPassword) {
		this.oldUserPassword = oldUserPassword;
	}
}