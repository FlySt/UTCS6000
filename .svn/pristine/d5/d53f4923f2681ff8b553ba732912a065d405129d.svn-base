package com.ncjk.utcs.modules.logs.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.ServletActionContext;

import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;


@Entity
@Table(name = "UTCS_LOG")
public class UtcsLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//日志ID 主键 唯一 自增长
	private Integer logId;
	//用户账号
	private String userAccount;
	//用户姓名
	private String userName;
	//模块名
	private String moduleName;
	//日志内容
	private String logContent;
	//IP地址
	private String loginIpaddr;
	//时间
	private Date logTime;
	//默认构造器
	public UtcsLog(){
		
	}
	/**
	 * 构造器
	 * @param content 
	 * @param moduleName
	 */
	public UtcsLog(String content,String moduleName) {
		//取提当前用户IP地址    //如果项目在本地取得的IP为 127.0.0.1
        String userIp = ServletActionContext.getRequest().getRemoteAddr();
        this.loginIpaddr =userIp;
        if(this.loginIpaddr==null||"".equals(this.loginIpaddr)){
        	this.loginIpaddr = "127.0.0.1";
        }
        UtcsUser user = (UtcsUser) ServletActionContext.getRequest().getSession()
		.getAttribute("user");
      
        if(user != null){
			this.userAccount = user.getUserAccount();
			this.userName = user.getUserName();
		}
		this.moduleName = moduleName;
		this.logContent = content;
		this.logTime = new Date();
	}
	/** 
	* @return Integer logId
	*/
	@Id
	@Column(name = "LOG_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getLogId() {
		return logId;
	}
	/** 
	* @param Integer logId
	*/
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	/** 
	* @return userAccount 
	*/
	@Column(name = "USER_ACCOUNT")
	public String getUserAccount() {
		return userAccount;
	}
	/** 
	* @param userAccount 
	*/
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	/** 
	* @return userName 
	*/
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}
	/** 
	* @param userName
	*/
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/** 
	* @return moduleName
	*/
	@Column(name = "MODULE_NAME")
	public String getModuleName() {
		return moduleName;
	}
	/** 
	* @param moduleName
	*/
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/** 
	* @return logContent 
	*/
	@Column(name = "LOG_CONTENT")
	public String getLogContent() {
		return logContent;
	}
	/** 
	* @param logContent
	*/
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	/** 
	* @return logIpaddr 
	*/
	@Column(name = "LOG_IPADDR")
	public String getLoginIpaddr() {
		return loginIpaddr;
	}
	/** 
	* @param logIpaddr
	*/
	public void setLoginIpaddr(String loginIpaddr) {
		this.loginIpaddr = loginIpaddr;
	}
	/** 
	* @return logTime 
	*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOG_TIME")
	public Date getLogTime() {
		return logTime;
	}
	/** 
	* @param logTime
	*/
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	
}
