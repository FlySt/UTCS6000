package com.ncjk.utcs.modules.resources.basic.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/** 
* @ClassName: UtcsModule 
* @Description: 模块实体类
* @author 石望来
* @date 2016年8月18日 下午3:18:58 
* 
* tags 
*/
@Entity
@Table(name = "UTCS_MODULE")
public class UtcsModule {
   
	//模块ID 主键唯一 自增  非空
	private Integer moduleId ;
	//模块名称   非空
	private String moduleName ;
	//模块路径
	private String modulePath;
	//模块类型 预留
	private Integer  moduleType;
	//父级模块ID (-1-无父级模块)
	private Integer parentModuleId;
	//模块排序
	private Integer moduleOrder;
	//模块权限 (8位表示，前4位分别为查看、增加、修改、删除，后4位预留；1-有权限、0-无权限)
	private String modulePower;
	//模块描述
	private String moduleDesc;
	// 使用状态 (0-启用、2-禁用) 默认为0  非空
	private Integer useStatus;
	
	//ICON路径
	private String iconPath;
	/** 
	* @return mouduleId 
	*/
	@Id
	@Column(name="MODULE_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getModuleId() {
		return moduleId;
	}
	/** 
	* @param mouduleId
	*/
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	/** 
	* @return moduleName
	*/
	@Column(name = "MODULE_NAME", length=24 ,nullable=false )
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
	* @return modulePath 
	*/
	@Column(name = "MODULE_PATH")
	public String getModulePath() {
		return modulePath;
	}
	/** 
	* @param modulePath 
	*/
	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}
	/** 
	* @return moduleType 
	*/
	@Column(name = "MODULE_TYPE")
	public Integer getModuleType() {
		return moduleType;
	}
	/** 
	* @param moduleType
	*/
	public void setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
	}
	/** 
	* @return parentModuleId 
	*/
	@Column(name = "PARENT_MODULE_ID")
	public Integer getParentModuleId() {
		return parentModuleId;
	}
	/** 
	* @param parentModuleId
	*/
	public void setParentModuleId(Integer parentModuleId) {
		this.parentModuleId = parentModuleId;
	}
	/** 
	* @return moduleOrder 
	*/
	@Column(name = "MODULE_ORDER")
	public Integer getModuleOrder() {
		return moduleOrder;
	}
	/** 
	* @param moduleOrder
	*/
	public void setModuleOrder(Integer moduleOrder) {
		this.moduleOrder = moduleOrder;
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
	* @return moduleDesc 
	*/
	@Column(name = "MODULE_DESC")
	public String getModuleDesc() {
		return moduleDesc;
	}
	/** 
	* @param moduleDesc
	*/
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}
	
	/** 
	* @return useStatus 
	*/
	@Column(name = "USE_STATUS")
	public Integer getUseStatus() {
		return useStatus;
	}
	/** 
	* @param useStatus
	*/
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	/** 
	* @return iconPath 
	*/
	@Column(name = "ICON_PATH")
	public String getIconPath() {
		return iconPath;
	}
	/** 
	* @param iconPath
	*/
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	
	
	
}
