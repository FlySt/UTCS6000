package com.ncjk.utcs.modules.resources.basic.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 部门信息实体类
 */
@Entity
@Table(name = "UTCS_DEPT")
public class UtcsDept {
	//部门ID,主键,唯一,由BASIC_DATA_SEQUENCE序列生成
	private Integer deptId;
	//部门名称,唯一
	private String deptName;
	//部门名称全拼
	private String deptFullSpell;
	//部门名称首拼
	private String deptHeadSpell;
	//部门代码,与公安部对应(12位,程序控制)
	private String deptCode;
	//上级部门ID(-1-无上级部门)
	private Integer parentDeptId;
	//行政区划,与公安部对应(6位,程序控制)
	private String regionCode;
	//部门等级(0为部局,1为总队,2为支队,3为大队,4为中队,5-其他)
	private Integer deptLevel;
	//部门类型(0为部门,1为区域)
	private Integer deptType;
	//处理地址,预留
	private String dealAddress;
	//部门描述
	private String deptExplain;
	//显示标记(0-不显示,1-显示)
	private Integer showSign;
	//更新时间
	private Date updateTime;
	//更新账号
	private String updateAccount;

	/**
	 * 获取部门ID
	 * @return 部门ID
	 */
	@Id
	@Column(name = "DEPT_ID", precision = 20, scale = 0)
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASIC_DATA_SEQUENCE")
	//@SequenceGenerator(name = "BASIC_DATA_SEQUENCE", sequenceName = "BASIC_DATA_SEQUENCE")
	public Integer getDeptId() {
		return deptId;
	}

	/**
	 * 设置部门ID
	 * @param 部门ID
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/**
	 * 获取部门名称
	 * @return 部门名称
	 */
	@Column(name = "DEPT_NAME", length = 50, nullable = false)
	public String getDeptName() {
		return deptName;
	}

	/**
	 * 设置部门名称
	 * @param 部门名称,50位
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 获取部门名称全拼音
	 * @return 部门名称全拼音
	 */
	@Column(name = "DEPT_FULL_SPELL", length = 50, nullable = true)
	public String getDeptFullSpell() {
		return deptFullSpell;
	}

	/**
	 * 设置部门名称全拼音
	 * @param 部门名称全拼音
	 */
	public void setDeptFullSpell(String deptFullSpell) {
		this.deptFullSpell = deptFullSpell;
	}

	/**
	 * 获取部门名称首拼音
	 * @return 部门名称首拼音
	 */
	@Column(name = "DEPT_HEAD_SPELL", length = 50, nullable = true)
	public String getDeptHeadSpell() {
		return deptHeadSpell;
	}

	/**
	 * 设置部门名称首拼音
	 * @param 部门名称首拼音
	 */
	public void setDeptHeadSpell(String deptHeadSpell) {
		this.deptHeadSpell = deptHeadSpell;
	}

	/**
	 * 获取部门代码
	 * @return 部门代码,与公安部对应(12位,程序控制)
	 */
	@Column(name = "DEPT_CODE", length = 24)
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * 设置部门代码
	 * @param 部门代码 ,与公安部对应(12位,程序控制)
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * 获取上级部门ID
	 * @return 上级部门ID(-1-无上级部门)
	 */
	@Column(name = "PARENT_DEPT_ID", length = 20)
	public Integer getParentDeptId() {
		return parentDeptId;
	}

	/**
	 * 设置上级部门ID
	 * @param 上级部门ID (-1-无上级部门)
	 */
	public void setParentDeptId(Integer parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	/**
	 * 获取行政区划
	 * @return 行政区划,与公安部对应(6位,程序控制)
	 */
	@Column(name = "REGION_CODE", length = 16)
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * 设置行政区划
	 * @param 行政区划 ,与公安部对应(6位,程序控制)
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * 获取部门等级
	 * @return 部门等级(0为部局,1为总队,2为支队,3为大队,4为中队,5-其他)
	 */
	@Column(name = "DEPT_LEVEL", precision = 20, scale = 0)
	public Integer getDeptLevel() {
		return deptLevel;
	}

	/**
	 * 设置部门等级
	 * @param 部门等级 (0为部局,1为总队,2为支队,3为大队,4为中队,5-其他)
	 */
	public void setDeptLevel(Integer deptLevel) {
		this.deptLevel = deptLevel;
	}

	/**
	 * 获取部门类型
	 * @return 部门类型(0为部门,1为区域)
	 */
	@Column(name = "DEPT_TYPE", length = 5)
	public Integer getDeptType() {
		return deptType;
	}

	/**
	 * 设置部门类型
	 * @param 部门类型 (0为部门,1为区域)
	 */
	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}

	/**
	 * 获取处理地址
	 * @return 处理地址
	 */
	@Column(name = "DEAL_ADDRESS", precision = 20, scale = 0)
	public String getDealAddress() {
		return dealAddress;
	}

	/**
	 * 设置处理地址
	 * @param 处理地址
	 */
	public void setDealAddress(String dealAddress) {
		this.dealAddress = dealAddress;
	}

	/**
	 * 获取部门描述
	 * @return 部门描述
	 */
	@Column(name = "DEPT_EXPLAIN", length = 255)
	public String getDeptExplain() {
		return deptExplain;
	}

	/**
	 * 设置部门描述
	 * @param 部门描述
	 */
	public void setDeptExplain(String deptExplain) {
		this.deptExplain = deptExplain;
	}
	
	/**
	 * 获取显示标记
	 * @return 显示标记(0-不显示,1-显示)
	 */
	@Column(name = "SHOW_SIGN", length = 2)
	public Integer getShowSign() {
		return showSign;
	}

	/**
	 * 设置显示标记
	 * @param 显示标记(0-不显示,1-显示)
	 */
	public void setShowSign(Integer showSign) {
		this.showSign = showSign;
	}

	/**
	 * 获取最后更新时间
	 * @return 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 30, nullable = true)
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置最后更新时间
	 * @param 更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取最后更新账号
	 * @return 更新账号
	 */
	@Column(name = "UPDATE_ACCOUNT", length = 50)
	public String getUpdateAccount() {
		return updateAccount;
	}

	/**
	 * 设置最后更新账号
	 * @param 更新账号
	 */
	public void setUpdateAccount(String updateAccount) {
		this.updateAccount = updateAccount;
	}
}