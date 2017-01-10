package com.ncjk.utcs.modules.resources.resources.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "UTCS_SIGNAL_LIGHT")
public class UtcsSignalLight {
	//灯组ID,主键,唯一,由BASIC_DATA_SEQUENCE序列生成
	private 	Integer		lightId;
	//信号机ID,外键
	private 	UtcsSignalControler	utcsSignalControler;
	//灯组宽度
	private		Integer		lightWidth;
	//灯组高度
	private		Integer		lightHeight;
	//所属灯驱板板号(1~16)
	private		Integer		lightNum;
	//所在灯驱板输出号(1~4)
	private		Integer		lightOutPut;
	//在路口图片中的横坐标
	private		Integer		lightX;
	//在路口图片中的纵坐标
	private		Integer		lightY;
	//车道类型(0-机动车,1-人行)
	private		Integer		driverWayType;
	//车道方向(0-东,1-南,2-西,3-北,4-东南,5-西南,6-西北,7-东北)
	private		Integer		driverWayDirection;
	//车道方向类型(0-左,1-直,2-右,3-人行)
	private 	Integer		driverWayDirectionType;
	//旋转角度(单位:度,不包括)
	private		Integer		eddyAngle;
	
	

	/**
	 * 获取信号机灯组ID
	 * @return 信号机灯组ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LIGHT_ID")
	public Integer getLightId() {
		return lightId;
	}
	/**
	 * 设置信号机灯组ID
	 * @param
	 */
	public void setLightId(Integer lightId) {
		this.lightId = lightId;
	}

	/**
	 * 获取信号机车道所属信号机设备
	 * @return 信号机车道所属信号机设备
	 */
	@ManyToOne(targetEntity = UtcsSignalControler.class)
	@JoinColumn(name = "SIGNAL_CONTROLER_ID", nullable = false)
	public UtcsSignalControler getUtcsSignalControler() {
		return utcsSignalControler;
	}
	/**
	 * 设置信号机车道所属信号机设备
	 * @param
	 */
	public void setUtcsSignalControler(UtcsSignalControler utcsSignalControler) {
		this.utcsSignalControler = utcsSignalControler;
	}

	/**
	 * 获取灯组宽度
	 * @return 灯组宽度
	 */
	@Column(name = "LIGHT_WIDTH")
	public Integer getLightWidth() {
		return lightWidth;
	}
	/**
	 * 设置灯组宽度
	 * @param
	 */
	public void setLightWidth(Integer lightWidth) {
		this.lightWidth = lightWidth;
	}
	

	/**
	 * 获取灯组高度
	 * @return 灯组高度
	 */
	@Column(name = "LIGHT_HEIGHT")
	public Integer getLightHeight() {
		return lightHeight;
	}
	/**
	 * 设置灯组高度
	 * @param
	 */
	public void setLightHeight(Integer lightHeight) {
		this.lightHeight = lightHeight;
	}
	

	/**
	 * 获取所属灯驱板板号(1~16)
	 * @return 所属灯驱板板号(1~16)
	 */
	@Column(name = "LIGHT_NUM")
	public Integer getLightNum() {
		return lightNum;
	}
	/**
	 * 设置所属灯驱板板号(1~16)
	 * @param
	 */
	public void setLightNum(Integer lightNum) {
		this.lightNum = lightNum;
	}
	

	/**
	 * 获取所在灯驱板输出号(1~4)
	 * @return 所在灯驱板输出号(1~4)
	 */
	@Column(name = "LIGHT_OUTPUT")
	public Integer getLightOutPut() {
		return lightOutPut;
	}
	/**
	 * 设置所在灯驱板输出号(1~4)
	 * @param (
	 */
	public void setLightOutPut(Integer lightOutPut) {
		this.lightOutPut = lightOutPut;
	}
	

	/**
	 * 获取在路口图片中的横坐标
	 * @return 在路口图片中的横坐标
	 */
	@Column(name = "LIGHT_X")
	public Integer getLightX() {
		return lightX;
	}
	/**
	 * 设置在路口图片中的横坐标
	 * @param
	 */
	public void setLightX(Integer lightX) {
		this.lightX = lightX;
	}
	

	/**
	 * 获取在路口图片中的纵坐标
	 * @return 在路口图片中的纵坐标
	 */
	@Column(name = "LIGHT_Y")
	public Integer getLightY() {
		return lightY;
	}
	/**
	 * 设置在路口图片中的纵坐标
	 * @param
	 */
	public void setLightY(Integer lightY) {
		this.lightY = lightY;
	}

	/**
	 * 获取车道类型
	 * @return 车道类型
	 */
	@Column(name = "DRIVERWAY_TYPE")
	public Integer getDriverWayType() {
		return driverWayType;
	}
	/**
	 * 设置车道类型
	 * @param
	 */
	public void setDriverWayType(Integer driverWayType) {
		this.driverWayType = driverWayType;
	}
	

	/**
	 * 获取车道方向
	 * @return 车道方向
	 */
	@Column(name = "DRIVERWAY_DIRECTION")
	public Integer getDriverWayDirection() {
		return driverWayDirection;
	}
	/**
	 * 设置车道方向
	 * @param
	 */
	public void setDriverWayDirection(Integer driverWayDirection) {
		this.driverWayDirection = driverWayDirection;
	}
	

	/**
	 * 获取车道方向类型
	 * @return 车道方向类型
	 */
	@Column(name = "DRIVERWAY_DIRECTION_TYPE")
	public Integer getDriverWayDirectionType() {
		return driverWayDirectionType;
	}
	/**
	 * 设置车道方向类型
	 * @param
	 */
	public void setDriverWayDirectionType(Integer driverWayDirectionType) {
		this.driverWayDirectionType = driverWayDirectionType;
	}
	

	/**
	 * 获取旋转角度
	 * @return 旋转角度
	 */
	@Column(name = "EDDY_ANGLE")
	public Integer getEddyAngle() {
		return eddyAngle;
	}
	/**
	 * 设置旋转角度
	 * @param
	 */
	public void setEddyAngle(Integer eddyAngle) {
		this.eddyAngle = eddyAngle;
	}
	
}
