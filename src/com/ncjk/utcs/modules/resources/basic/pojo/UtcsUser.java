package com.ncjk.utcs.modules.resources.basic.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 用户表
 */
@Entity
@Table(name = "UTCS_USER")
public class UtcsUser {
	//主键ID
	private Integer userId;
	//用户帐号
	private String userAccount;
	//用户密码
	private String userPassword;
	//用户姓名
	private String userName;
	//姓名全拼
//	private String nameFullSpell;
	//姓名首拼
//	private String nameHeadSpell;
	//用户部门
	private UtcsDept utcsDept;
	//权限用户组
	private String userGroupIds;
	//身份证号
	private String identityCard;
	//照片
	private byte[] userPhoto;
	//图片后缀名
	private String photoPostfix;
	//用户Email
	private String userEmail;
	//联系电话
	private String userTel;
	//限制IP地址集合最多3个
	private String limitIpaddrs;
	//有效开始时间
	private Date validStartDate = new Date();
	//有效结束时间  默认十年
	private Date validEndDate =new Date(System.currentTimeMillis() + 365 * 10 * 24 * 60 * 60 * 1000L);
	//用户说明
	private String userExplain;
	//用户标记
	private Integer userSign;
	//登录次数
	private Integer loginTimes;
	//最后登录IP
	private String lastLoginIpaddr;
	//最后登录时间
	private Date lastLoginTime;
	//使用状态（0-启用、2-禁用、4-废弃）
	private Integer useStatus;
	//更新时间
	private Date updateTime;
	//更新用户账号
	private String updateAccount;
	//用户模块集
	private Set<UtcsUserModule> userModules = new HashSet<UtcsUserModule>();

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASIC_DATA_SEQUENCE")
//	@SequenceGenerator(name = "BASIC_DATA_SEQUENCE", sequenceName = "BASIC_DATA_SEQUENCE")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 用户帐号唯一
	 * @return
	 */
	@Column(name = "USER_ACCOUNT", length = 20, nullable = false)
	public String getUserAccount() {
		return userAccount;
	}

	/**
	 * 用户帐号唯一
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	/**
	 * 用户密码
	 * @return
	 */
	@Column(name = "USER_PASSWORD", length = 255, nullable = false)
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * 用户密码
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * 用户姓名
	 * @return
	 */
	@Column(name = "USER_NAME", length = 50, nullable = false)
	public String getUserName() {
		return userName;
	}

	/**
	 * 用户姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 姓名全拼
	 * @return
	 */
//	@Column(name = "NAME_FULL_SPELL", length = 200)
//	public String getNameFullSpell() {
//		return nameFullSpell;
//	}

	/**
	 * 姓名全拼
	 */
//	public void setNameFullSpell(String nameFullSpell) {
//		this.nameFullSpell = nameFullSpell;
//	}

	/**
	 * 姓名首拼
	 * @return
	 */
//	@Column(name = "NAME_HEAD_SPELL", length = 50)
//	public String getNameHeadSpell() {
//		return nameHeadSpell;
//	}

	/**
	 * 姓名首拼
	 */
//	public void setNameHeadSpell(String nameHeadSpell) {
//		this.nameHeadSpell = nameHeadSpell;
//	}

	@ManyToOne(targetEntity = UtcsDept.class)
	@JoinColumn(name = "DEPT_ID", nullable = false)
	public UtcsDept getUtcsDept() {
		return utcsDept;
	}

	public void setUtcsDept(UtcsDept utcsDept) {
		this.utcsDept = utcsDept;
	}

	//	@ManyToOne(targetEntity = UtmsDuties.class)
	//	@JoinColumn(name = "JOB_DUTIES_ID", nullable = true)
	//	public UtmsDuties getUtmsDuties() {
	//		return utmsDuties;
	//	}
	//	public void setUtmsDuties(UtmsDuties utmsDuties) {
	//		this.utmsDuties = utmsDuties;
	//	}
	//	
	//	@Column(name="POLICE_NUMBER",length=30,nullable=true)
	//	public String getPoliceNumber() {
	//		return policeNumber;
	//	}
	//	public void setPoliceNumber(String policeNumber) {
	//		this.policeNumber = policeNumber;
	//	}
	//	
	//	@ManyToOne(targetEntity = UtmsRank.class)
	//	@JoinColumn(name = "USER_RANK_ID", nullable = true)
	//	public UtmsRank getUtmsRank() {
	//		return utmsRank;
	//	}
	//	public void setUtmsRank(UtmsRank utmsRank) {
	//		this.utmsRank = utmsRank;
	//	}

	@Column(name = "USER_GROUP_IDS", length = 255 ,nullable = false)
	public String getUserGroupIds() {
		return userGroupIds;
	}

	public void setUserGroupIds(String userGroupIds) {
		this.userGroupIds = userGroupIds;
	}

	@Column(name = "IDENTITY_CARD", length = 30)
	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "USER_PHOTO", columnDefinition = "BLOB", nullable = true)
	public byte[] getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(byte[] userPhoto) {
		this.userPhoto = userPhoto;
	}

	@Column(name = "PHOTO_POSTFIX", length = 10)
	public String getPhotoPostfix() {
		return photoPostfix;
	}

	public void setPhotoPostfix(String photoPostfix) {
		this.photoPostfix = photoPostfix;
	} 

	@Column(name = "USER_EMAIL", length = 50)
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Column(name = "USER_TEL", length = 30)
	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	@Column(name = "LIMIT_IPADDRS", length = 120)
	public String getLimitIpaddrs() {
		return limitIpaddrs;
	}

	public void setLimitIpaddrs(String limitIpaddrs) {
		this.limitIpaddrs = limitIpaddrs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VALID_START_DATE", length = 30, nullable = false)
	public Date getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VALID_END_DATE", length = 30, nullable = false)
	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	@Column(name = "USER_EXPLAIN", length = 255)
	public String getUserExplain() {
		return userExplain;
	}

	public void setUserExplain(String userExplain) {
		this.userExplain = userExplain;
	}

	@Column(name = "USER_SIGN", precision = 2, scale = 0)
	public Integer getUserSign() {
		return userSign;
	}

	public void setUserSign(Integer userSign) {
		this.userSign = userSign;
	}

	@Column(name = "LOGIN_TIMES", precision = 20, scale = 0)
	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	@Column(name = "LAST_LOGIN_IPADDR", length = 50)
	public String getLastLoginIpaddr() {
		return lastLoginIpaddr;
	}

	public void setLastLoginIpaddr(String lastLoginIpaddr) {
		this.lastLoginIpaddr = lastLoginIpaddr;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_LOGIN_TIME", length = 30)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * 使用状态(0-启用,2-禁用)
	 * @return
	 */
	@Column(name = "USE_STATUS", precision = 5, scale = 0)
	public Integer getUseStatus() {
		return useStatus;
	}

	/**
	 * 使用状态(0-启用,2-禁用)
	 */
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	/**
	 * 更新时间
	 * @return
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 30)
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 更新帐号
	 * @return
	 */
	@Column(name = "UPDATE_ACCOUNT", length = 50, nullable = true)
	public String getUpdateAccount() {
		return updateAccount;
	}

	/**
	 * 更新帐号
	 */
	public void setUpdateAccount(String updateAccount) {
		this.updateAccount = updateAccount;
	}

	@OneToMany(mappedBy = "utcsUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = UtcsUserModule.class)
	public Set<UtcsUserModule> getUserModules() {
		return userModules;
	}

	public void setUserModules(Set<UtcsUserModule> userModules) {
		this.userModules = userModules;
	}

}
