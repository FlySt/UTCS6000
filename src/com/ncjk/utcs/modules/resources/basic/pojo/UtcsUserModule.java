package com.ncjk.utcs.modules.resources.basic.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "UTCS_USER_MODULE")
public class UtcsUserModule {

	 //用户模块权限ID,主键,唯一
	private Integer userModuleId;
	//用户信息 根据用户ID（外键）获取
	private UtcsUser utcsUser;
	//模块信息 根据模块ID(外键)获取
	private UtcsModule utcsModule;
	//模块权限(8位表示,第1位-查看,第2位-增加,第3位-修改,第4位-删除,后4位预留;1-有权限,0-无权限)
	private String userPower;
	
	/** 
	* @return userModuleId 
	*/
	@Id
	@Column(name = "USER_MODULE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)//自增
	public Integer getUserModuleId() {
		return userModuleId;
	}
	/** 
	* @param userModuleId
	*/
	public void setUserModuleId(Integer userModuleId) {
		this.userModuleId = userModuleId;
	}
	/** 
	* @return utcsUser
	*/
	@ManyToOne(targetEntity=UtcsUser.class,cascade=CascadeType.ALL)
	@JoinColumn(name="USER_ID",nullable=false)
	//外键为USER_ID，与UtcsUser中的USER_ID关联
	public UtcsUser getUtcsUser() {
		return utcsUser;
	}
	public void setUtcsUser(UtcsUser utcsUser) {
		this.utcsUser = utcsUser;
	}
	/** 
	* @return utcsModule 
	*/
	@ManyToOne(targetEntity=UtcsModule.class,cascade=CascadeType.ALL)
	@JoinColumn(name = "MODULE_ID",nullable=false)
	public UtcsModule getUtcsModule() {
		return utcsModule;
	}
	/** 
	* @param utcsModule
	*/
	public void setUtcsModule(UtcsModule utcsModule) {
		this.utcsModule = utcsModule;
	}
	/** 
	* @return userPower 
	*/
	@Column(name = "USER_POWER")
	public String getUserPower() {
		return userPower;
	}
	/** 
	* @param userPower
	*/
	public void setUserPower(String userPower) {
		this.userPower = userPower;
	}
	
	
}
