package com.ncjk.utcs.modules.resources.basic.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ncjk.utcs.modules.resources.basic.pojo.UtcsGroupPower;

/** 
* @ClassName: UtcsUserGroup 
* @Description: 用户组实体类 
* @author 石望来
* @date 2016年8月18日 上午9:08:39 
* 
* tags 
*/
@Entity
@Table(name = "UTCS_USER_GROUP")
public class UtcsUserGroup {
	//用户组ID 自增
	private Integer userGroupId ;
	//用户组名称 唯一
	private String userGroupName ;
	//用户组描述
	private String userGroupDesc ;
	//显示标记(0-不显示,1-显示)
	private Integer showSign;
	// 更新时间
	private Date updateTime;
	//更新账号
	private String updateAccount;
	//组权限
	private Set<UtcsGroupPower> groupPowers = new HashSet<UtcsGroupPower>();
	
	/** 
	* @return userGroupId 
	*/
	@Id
	@Column(name = "USER_GROUP_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	public Integer getUserGroupId() {
		return userGroupId;
	}
	/** 
	* @param userGroupId
	*/
	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}
	/** 
	* @return userGroupName 
	*/
	@Column(name = "GROUP_NAME", length = 50, nullable = false)
	public String getUserGroupName() {
		return userGroupName;
	}
	/** 
	* @param userGroupName
	*/
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	
	/** 
	* @return userGroupDesc 
	*/
	@Column(name = "GROUP_DESC", length = 255)
	public String getUserGroupDesc() {
		return userGroupDesc;
	}
	/** 
	* @param userGroupDesc
	*/
	public void setUserGroupDesc(String userGroupDesc) {
		this.userGroupDesc = userGroupDesc;
	}
	/** 
	* @return Integer 
	*/
	@Column(name = "SHOW_SIGN",nullable = false)
	public Integer getShowSign() {
		return showSign;
	}
	/** 
	* @param Integer
	*/
	public void setShowSign(Integer showSign) {
		this.showSign = showSign;
	}
	/** 
	* @return updateTime 
	*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 30)
	public Date getUpdateTime() {
		return updateTime;
	}
	/** 
	* @param updateTime 
	*/
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/** 
	* @return updateAccount
	*/
	@Column(name = "UPDATE_ACCOUNT", length = 50, nullable = true)
	public String getUpdateAccount() {
		return updateAccount;
	}
	/** 
	* @param updateAccount
	*/
	public void setUpdateAccount(String updateAccount) {
		this.updateAccount = updateAccount;
	}
	
	@OneToMany(mappedBy = "utcsUserGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<UtcsGroupPower> getGroupPowers() {
		return groupPowers;
	}

	public void setGroupPowers(Set<UtcsGroupPower> groupPowers) {
		this.groupPowers = groupPowers;
	}
	
}
