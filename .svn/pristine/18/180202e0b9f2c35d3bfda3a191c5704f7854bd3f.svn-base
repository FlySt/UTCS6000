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
@Table(name = "UTCS_GROUP_POWER")
public class UtcsGroupPower {
	//组权限ID 自增
	private Integer groupPowerId;
	//用户组信息 通过外键获取
	private UtcsUserGroup utcsUserGroup;
	//模块信息 外键获取
	private UtcsModule utcsModule;
	//模块本身权限
	private String modulePower;
	//用户组权限
	private String groupPower;
	
	/** 
	* @return groupPowerId 
	*/
	@Id
	@Column(name = "GROUP_POWER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getGroupPowerId() {
		return groupPowerId;
	}
	/** 
	* @param groupPowerId 
	*/
	public void setGroupPowerId(Integer groupPowerId) {
		this.groupPowerId = groupPowerId;
	}
	/** 
	* @return utcsUserGroup 
	*/
	@ManyToOne(targetEntity=UtcsUserGroup.class,cascade=CascadeType.ALL,optional=false)
	@JoinColumn(name = "USER_GROUP_ID", nullable=false)
	public UtcsUserGroup getUtcsUserGroup() {
		return utcsUserGroup;
	}
	/** 
	* @param utcsUserGroup 
	*/
	public void setUtcsUserGroup(UtcsUserGroup utcsUserGroup) {
		this.utcsUserGroup = utcsUserGroup;
	}
	/** 
	* @return utcsModule 
	*/
	@ManyToOne(targetEntity=UtcsModule.class,cascade=CascadeType.ALL)
	@JoinColumn(name="MODULE_ID",nullable=false)
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
	* @return modulePower 
	*/
	@Column(name = "MODULE_POWER")
	public String getModulePower() {
		return modulePower;
	}
	/** 
	* @param modulePower 
	*/
	public void setModulePower(String modulePower) {
		this.modulePower = modulePower;
	}
	/** 
	* @return groupPower 
	*/
	@Column(name = "GROUP_POWER")
	public String getGroupPower() {
		return groupPower;
	}
	/** 
	* @param groupPower
	*/
	public void setGroupPower(String groupPower) {
		this.groupPower = groupPower;
	}
	
	
}
