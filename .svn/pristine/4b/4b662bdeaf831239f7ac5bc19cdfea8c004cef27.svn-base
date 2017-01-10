package com.ncjk.utcs.modules.resources.resources.pojo;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "UTCS_SIGNAL")
public class UtcsSignal {
	//信号机ID
	private   	Integer 			signalId;
	//卡口/路口ID,外键
	private   	UtcsCrossParam	crossParam;
	//信号机名称
	private		String			signalName;
	//资产编码
	private		String			assetCode;		
	//信号机类型(0-南昌金科C3型,1-南昌金科D型,2-南昌金科E型,3-南昌金科C6型,4-南昌金科F型)
	private 	Integer			signalType;
	//路口类型(0-十字路口,1-三叉路口,2-五叉路口),预留
	private		Integer			roadType;
	//信号机IP地址
	private		String			deviceIpAddr;	
	//通信端口
	private		Integer			commPort;
	//通信协议号,为后续多协议版号预留
	private		String			commProtoclNum;	
	//信号机接入服务器ID,外键
	private		UtcsServerParam		serverParam;
	//路口背景图,仅*.JPG格式
	private		byte[]			roadBackgroundMap;
	//16个灯驱板配置(F型方可配)
	private		String			lightSet;		
	//背景图宽度
	private		Integer			backgroundMapWidth;
	//背景图高度
	private		Integer			backgroundMapHeight;
	//使用状态(0-启用,2-维护)
	private		Integer			useStatus;
	//更新时间
	private		Date			updateTime;		
	//更新账号
	private 	String			updateAccount;	
	//经度
	private 	String  longitude;		
	//纬度
	private 	String  latitude;	
	//地图标注(0-未标注,1-已标注)
	private 	Integer 	mapSign;
	//车流量通道配置
	private		String	trafficpicSet;	
	//每个特殊灯色表的名称，共60个特殊灯色表(JK-F)
	private		String	specialLightName;


	/**
	 * 信号机ID
	 * @return 信号机ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SIGNAL_ID")
	public Integer getSignalId() {
		return signalId;
	}
	/**
	 * 信号机ID
	 * @param
	 */
	public void setSignalId(Integer signalId) {
		this.signalId = signalId;
	}
	

	/**
	 * 信号机路口
	 * @return 信号机路口
	 */
	@ManyToOne(targetEntity = UtcsCrossParam.class)
	@JoinColumn(name = "CROSS_ID", nullable = false)
	public UtcsCrossParam getCrossParam() {
		return crossParam;
	}
	/**
	 * 信号机路口
	 * @param
	 */
	public void setCrossParam(UtcsCrossParam crossParam) {
		this.crossParam = crossParam;
	}
	/**
	 * 信号机名称
	 * @return 信号机名称
	 */
	@Column(name = "SIGNAL_NAME")
	public String getSignalName() {
		return signalName;
	}
	/**
	 * 信号机名称
	 * @param
	 */
	public void setSignalName(String signalName) {
		this.signalName = signalName;
	}

	/**
	 * 信号机资产编码
	 * @return 信号机资产编码
	 */
	@Column(name = "ASSET_CODE", length = 50)
	public String getAssetCode() {
		return assetCode;
	}
	/**
	 * 信号机资产编码
	 * @param assetCode
	 */
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	/**
	 * 信号机类型
	 * @return 信号机类型
	 */
	@Column(name = "SIGNAL_TYPE")
	public Integer getSignalType() {
		return signalType;
	}
	/**
	 * 信号机类型
	 * @param
	 */
	public void setSignalType(Integer signalType) {
		this.signalType = signalType;
	}
	/**
	 * 路口类型
	 * @return 路口类型
	 */
	@Column(name = "ROAD_TYPE")
	public Integer getRoadType() {
		return roadType;
	}
	/**
	 * 路口类型
	 * @param
	 */
	public void setRoadType(Integer roadType) {
		this.roadType = roadType;
	}
	/**
	 * 信号机IP地址
	 * @return 信号机IP地址
	 */
	@Column(name = "DEVICE_IPADDR", length = 50)
	public String getDeviceIpAddr() {
		return deviceIpAddr;
	}
	/**
	 * 信号机IP地址
	 * @param deviceIpAddr
	 */
	public void setDeviceIpAddr(String deviceIpAddr) {
		this.deviceIpAddr = deviceIpAddr;
	}
	/**
	 * 信号机通信端口
	 * @return 信号机通信端口
	 */
	@Column(name = "COMM_PORT")
	public Integer getCommPort() {
		return commPort;
	}
	/**
	 * 信号机通信端口
	 * @param
	 */
	public void setCommPort(Integer commPort) {
		this.commPort = commPort;
	}
	/**
	 * 信号机通信协议号
	 * @return 信号机通信协议号
	 */
	@Column(name = "COMM_PROTOCOL_NUM")
	public String getCommProtoclNum() {
		return commProtoclNum;
	}
	/**
	 * 信号机通信协议号
	 * @param
	 */
	public void setCommProtoclNum(String commProtoclNum) {
		this.commProtoclNum = commProtoclNum;
	}

	/**
	 * 信号机接入服务器
	 * @return 信号机接入服务器
	 */
	@ManyToOne(targetEntity = UtcsServerParam.class)
	@JoinColumn(name = "SERVER_ID", nullable = false)
	public UtcsServerParam getServerParam() {
		return serverParam;
	}

	public void setServerParam(UtcsServerParam serverParam) {
		this.serverParam = serverParam;
	}

	/**
	 * 路口背景图
	 * @return 路口背景图
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "ROAD_BACKGROUND_MAP", columnDefinition = "BLOB")
	public byte[] getRoadBackgroundMap() {
		return roadBackgroundMap;
	}
	/**
	 * 路口背景图
	 * @param
	 */
	public void setRoadBackgroundMap(byte[] roadBackgroundMap) {
		this.roadBackgroundMap = roadBackgroundMap;
	}
	/**
	 * 16个灯驱板配置
	 * @return 16个灯驱板配置
	 */
	@Column(name = "LIGHT_SET")
	public String getLightSet() {
		return lightSet;
	}
	/**
	 * 16个灯驱板配置
	 * @param
	 */
	public void setLightSet(String lightSet) {
		this.lightSet = lightSet;
	}
	/**
	 * 背景图宽度
	 * @return 背景图宽度
	 */
	@Column(name = "BACKGROUND_MAP_WIDTH")
	public Integer getBackgroundMapWidth() {
		return backgroundMapWidth;
	}
	/**
	 * 背景图宽度
	 * @param
	 */
	public void setBackgroundMapWidth(Integer backgroundMapWidth) {
		this.backgroundMapWidth = backgroundMapWidth;
	}
	/**
	 * 背景图高度
	 * @return 背景图高度
	 */
	@Column(name = "BACKGROUND_MAP_HEIGHT")
	public Integer getBackgroundMapHeight() {
		return backgroundMapHeight;
	}
	/**
	 * 背景图高度
	 * @param
	 */
	public void setBackgroundMapHeight(Integer backgroundMapHeight) {
		this.backgroundMapHeight = backgroundMapHeight;
	}
	/**
	 * 使用状态
	 * @return 使用状态
	 */
	@Column(name = "USE_STATUS")
	public Integer getUseStatus() {
		return useStatus;
	}
	/**
	 * 使用状态
	 * @param
	 */
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	/**
	 * 更新时间
	 * @return 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 更新时间
	 * @param
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 更新账号
	 * @return 更新账号
	 */
	@Column(name = "UPDATE_ACCOUNT")
	public String getUpdateAccount() {
		return updateAccount;
	}
	/**
	 * 更新账号
	 * @param
	 */
	public void setUpdateAccount(String updateAccount) {
		this.updateAccount = updateAccount;
	}
	/**
	 * 经度
	 * @return
	 */
	@Column(name="LONGITUDE")
	public String getLongitude() {
		return longitude;
	}
	/**
	 * 经度
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * 纬度
	 * @return
	 */
	@Column(name="LATITUDE")
	public String getLatitude() {
		return latitude;
	}
	/**
	 * 纬度
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * 地图标注(0-未标注,1-已标注)
	 * @return
	 */
	@Column(name = "MAP_SIGN")
	public Integer getMapSign() {
		return mapSign;
	}
	/**
	 * 地图标注(0-未标注,1-已标注)
	 */
	public void setMapSign(Integer mapSign) {
		this.mapSign = mapSign;
	}
	
	@Column(name="TRAFFICPIC_SET")
	public String getTrafficpicSet() {
		return trafficpicSet;
	}
	
	public void setTrafficpicSet(String trafficpicSet) {
		this.trafficpicSet = trafficpicSet;
	}
	@Column(name="SPECIALLIGHTNAME")
	public String getSpecialLightName() {
		return specialLightName;
	}
	public void setSpecialLightName(String specialLightName) {
		this.specialLightName = specialLightName;
	}
	
	
	
}
