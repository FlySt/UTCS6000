package com.ncjk.utcs.modules.system.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** 
* @ClassName: UtcsSystemParam 
* @Description: 系统参数实体类
* @author 石望来
* @date 2016年8月26日 下午3:37:15 
* 
* tags 
*/
@Entity
@Table(name = "UTCS_SYSTEM_PARARM")
public class UtcsSystemParam {
	/*系统常规参数*/
	//id 自增长
	private Integer paramId ;
	//系统名称，默认“交通信号控制系统”
	private String systemName ;
	//版本号
	private String systemVersion;
	//供应商
	private String supplier;
	//授权客户名称
	private String accreditCustomer;
	//最后更新时间
	private Date lastUpdateTime = new Date();
	/*与服务器通信参数*/
	//信号接入主服务器IP地址
	private String serverIPAddr;
	//信号接入主服务器通信端口
	private Integer serverPort;
	//控制系统部署服务器IP地址
	private String localIPAddr;
	//控制系统部署服务器端口
	private Integer localPort;
	
	/** 
	* @return paramId 
	*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "PARAM_ID")
	public Integer getParamId() {
		return paramId;
	}
	/** 
	* @param paramId
	*/
	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}
	/** 
	* @return systemName 
	*/
	@Column(name = "SYSTEM_NAME")
	public String getSystemName() {
		return systemName;
	}
	/** 
	* @return systemName 
	*/
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	/** 
	* @return systemVersion 
	*/
	@Column(name = "SYSTEM_VERSION")
	public String getSystemVersion() {
		return systemVersion;
	}
	/** 
	* @param systemVersion
	*/
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	/** 
	* @return supplier 
	*/
	@Column(name = "SUPPLIER")
	public String getSupplier() {
		return supplier;
	}
	/** 
	* @param supplier
	*/
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	/** 
	* @return accreditCustomer 
	*/
	@Column(name = "ACCREDIT_CUSTOMER")
	public String getAccreditCustomer() {
		return accreditCustomer;
	}
	/** 
	* @param accreditCustomer 
	*/
	public void setAccreditCustomer(String accreditCustomer) {
		this.accreditCustomer = accreditCustomer;
	}
	/** 
	* @return lastUpdateTime 
	*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_TIME")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	/** 
	* @param lastUpdateTime
	*/
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/** 
	* @return serverIPAddr 
	*/
	@Column(name = "SERVER_IPADDR" ,nullable = false)
	public String getServerIPAddr() {
		return serverIPAddr;
	}
	/** 
	* @param serverIPAddr
	*/
	public void setServerIPAddr(String serverIPAddr) {
		this.serverIPAddr = serverIPAddr;
	}
	/** 
	* @return serverPort
	*/
	@Column(name = "SERVER_PORT",nullable = false)
	public Integer getServerPort() {
		return serverPort;
	}
	/** 
	* @param serverPort 
	*/
	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}
	/** 
	* @return localIPAddr
	*/
	@Column(name = "LOCAL_IPADDR")
	public String getLocalIPAddr() {
		return localIPAddr;
	}
	/** 
	* @param localIPAddr
	*/
	public void setLocalIPAddr(String localIPAddr) {
		this.localIPAddr = localIPAddr;
	}
	/** 
	* @return localPort 
	*/
	@Column(name = "LOCAL_PORT")
	public Integer getLocalPort() {
		return localPort;
	}
	/** 
	* @param localPort
	*/
	public void setLocalPort(Integer localPort) {
		this.localPort = localPort;
	}
	
	
}
