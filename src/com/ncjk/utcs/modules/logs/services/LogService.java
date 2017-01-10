package com.ncjk.utcs.modules.logs.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.logs.pojo.UtcsLog;
import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/** 
* @ClassName: LogService 
* @Description: 系统日志业务实现类 
* @author 石望来
* @date 2016年8月23日 下午4:47:24 
* 
* tags 
*/
@Scope("prototype")
@Service("logService")
public class LogService implements ILogService {
	
	@Resource(name = "commonDAO")
	ICommonDAO commonDAO;
	/**  
	* @Description: 查找操作日志
	* @param    
	* @return JSONObject   
	* @author shiwanglai
	* @date 2016年8月30日 下午6:22:35  
	*/
	public JSONObject findLogs(String userAccount,String loginIpaddr,String startDate,String endDate,
							   Integer start,Integer length,Integer draw){
		JSONObject js = new JSONObject();
		JSONArray array = new JSONArray();
		String hql = "from UtcsLog t where 1=1";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer condition = new StringBuffer();
		if(userAccount!=null && !"".equals(userAccount)){
			String[] accounts = userAccount.split(",");
			String conditionuserAccount = "";
			for(String account:accounts){
				conditionuserAccount = conditionuserAccount+",'"+account+"'";
			}
			conditionuserAccount = conditionuserAccount.substring(1);
			condition.append(" and t.userAccount in ("+conditionuserAccount+")");
		}
		if(loginIpaddr!=null && !"".equals(loginIpaddr)){
			condition.append(" and t.loginIpaddr like '%"+loginIpaddr+"%'");
		}
		if(startDate!=null && !"".equals(startDate)){
			condition.append(" and t.logTime>'"+startDate+"'");
		}
		if(endDate!=null && !"".equals(endDate)){
			condition.append(" and t.logTime<'"+endDate+"'");
		}
		condition.append(" order by t.logTime desc");
		hql = hql + condition.toString();
		@SuppressWarnings("unchecked")
		List<UtcsLog> utcsLogs = (List<UtcsLog>)commonDAO.findByHql(hql,start/length+1,length);
		if(utcsLogs!=null && !utcsLogs.isEmpty()){	
			JSONObject jsonObject = new JSONObject();
			for(UtcsLog utcsLog:utcsLogs){
				jsonObject.put("logId",utcsLog.getLogId());
				jsonObject.put("userAccount", utcsLog.getUserAccount());
				jsonObject.put("userName", utcsLog.getUserName());
				jsonObject.put("moduleName", utcsLog.getModuleName());
				jsonObject.put("logContent", utcsLog.getLogContent());
				jsonObject.put("loginIpaddr", utcsLog.getLoginIpaddr());	
				String logTime = "";
				if (utcsLog.getLogTime() != null) {
					logTime = format.format(utcsLog.getLogTime());
				}
				jsonObject.put("logTime", logTime);
				array.add(jsonObject);
			}			
		}
		// 查询总条数
		int recordsTotal = countLogs(condition.toString());
		js.put("draw",draw);
		js.put("recordsTotal",recordsTotal);
		js.put("recordsFiltered",recordsTotal);
		js.put("data", array);
		return js;
	}
	/**
	 * 根据HQL语句查询日志总条数
	 */
	public int countLogs(String hql) {
		int count = 0;
		StringBuffer sb = new StringBuffer(" select count(t.id) from  UtcsLog t where 1=1  ");
		sb.append(hql);
		Object objs = commonDAO.findByHql(sb.toString());
		if (objs != null) {
			count = ((Long) objs).intValue();
		}
		return count;
	}
	/**  
	* @Description: 保存修改日志信息
	* @param content     moduleName
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月23日 下午5:22:18  
	*/
	public boolean saveOrUpdateLog(String content,String moduleName){
		
		UtcsLog utcsLog = new UtcsLog(content, moduleName);
		boolean b = commonDAO.saveOrUpdate(utcsLog);
		return b;
	}

	public boolean signalSaveLog(String content, String moduleName, String userAccount, String ip) {
		UtcsLog log = new UtcsLog();
		UtcsUser user = (UtcsUser) commonDAO.findByHql(" from UtcsUser t where t.userAccount ='" + userAccount + "'");
		String userName = "未找到用户名";
		if (user != null) {
			userName = user.getUserName();
		}
		log.setLogContent(content);
		log.setLogTime(new Date());
		log.setUserAccount(userAccount);
		log.setModuleName(moduleName);
		log.setUserName(userName);
		log.setLoginIpaddr(ip);
		boolean b = commonDAO.saveOrUpdate(log);
		return b;
	}
}
