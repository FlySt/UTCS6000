package com.ncjk.utcs.modules.access.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.common.util.DesUtil;
import com.ncjk.utcs.modules.access.services.interfaces.IUserLoginService;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;


import net.sf.json.JSONObject;

/** 
* @ClassName: UserLoginService 
* @Description: 用户登录业务实现类
* @author 石望来
* @date 2016年8月25日 下午4:48:31 
* 
* tags 
*/
@Service("userLoginService")
public class UserLoginService implements IUserLoginService{

	@Resource(name = "commonDAO")
	ICommonDAO commonDAO;

	
	public JSONObject userLogin(String userAccount,String userPassword){
		
		String ipaddrs =  ServletActionContext.getRequest().getRemoteAddr();
		String msg = "";
		String hql = "from UtcsUser t where t.useStatus=0 and t.userAccount='"+userAccount+"'";
		JSONObject jsonObj = new JSONObject();
		@SuppressWarnings("unchecked")
		List<UtcsUser> utcsUsers = (List<UtcsUser>)commonDAO.findByHql(hql,0,0);
		
		if(utcsUsers!=null && !utcsUsers.isEmpty()){
			UtcsUser user = utcsUsers.get(0);
			String password = "";
			try {
				DesUtil des = new DesUtil("RJ@NCJK");// 自定义密钥
				password = des.encrypt(userAccount+"_"+userPassword);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(user.getUserPassword().equals(password)){
				//判断限制IP集合
				if(user.getLimitIpaddrs()==null || user.getLimitIpaddrs().equals("") || user.getLimitIpaddrs().indexOf(ipaddrs)==-1){
					Date date = new Date();
					Date startDate = user.getValidStartDate();
					Date endDate = user.getValidEndDate();
					if(date.after(startDate) && date.before(endDate)){//判断日期
						//登录成功
						ServletActionContext.getRequest().getSession().setAttribute("user", user);
						user.setLastLoginIpaddr(ipaddrs);
						user.setLastLoginTime(new Date());
						user.setLoginTimes(user.getLoginTimes()+1);
						commonDAO.saveOrUpdate(user);
					}else {
						msg = "不在有效期内";
					}
				}else{
					msg = "IP不允许访问";
				}
			}else {
				msg = "密码错误";
			}
		}else {
			msg = "帐号不存在";
		}
		jsonObj.put("msg", msg);
		return jsonObj;
	}
}
