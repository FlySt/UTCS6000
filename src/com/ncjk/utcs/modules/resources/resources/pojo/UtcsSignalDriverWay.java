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
@Table(name = "UTCS_SIGNAL_DRIVERWAY")
public class UtcsSignalDriverWay {
	//车道ID,主键,唯一
	private		Integer		driverWayId;
	//信号机ID,外键
	private 	UtcsSignalControler	utcsSignalControler;
	//车道名称
	private		String		driverWayName;		
	//车道在图片中的横坐标
	private		Integer		driverWayX;
	//车道在图片中的纵坐标
	private		Integer		driverWayY;
	//旋转角度
	private		Integer		eddyAngle;
	//字体颜色
	private		String		fontColor;			
	//字体大小
	private		Integer		fontSize;
	

	/**
	 * 获取信号机车道ID
	 * @return 信号机车道ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DRIVERWAY_ID")
	public Integer getDriverWayId() {
		return driverWayId;
	}
	/**
	 * 设置信号机车道ID
	 * @param
	 */
	public void setDriverWayId(Integer driverWayId) {
		this.driverWayId = driverWayId;
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
	 * 获取信号机车道名称
	 * @return 信号机车道名称
	 */
	@Column(name = "DRIVERWAY_NAME")
	public String getDriverWayName() {
		return driverWayName;
	}
	/**
	 * 设置信号机车道名称
	 * @param
	 */
	public void setDriverWayName(String driverWayName) {
		this.driverWayName = driverWayName;
	}

	/**
	 * 获取车道在图片中的横坐标
	 * @return 车道在图片中的横坐标
	 */
	@Column(name = "DRIVERWAY_X")
	public Integer getDriverWayX() {
		return driverWayX;
	}
	/**
	 * 设置车道在图片中的横坐标
	 * @param
	 */
	public void setDriverWayX(Integer driverWayX) {
		this.driverWayX = driverWayX;
	}
	
	@Column(name = "DRIVERWAY_Y", precision = 20, scale = 0)
	/**
	 * 获取车道在图片中的纵坐标
	 * @return 车道在图片中的纵坐标
	 */
	public Integer getDriverWayY() {
		return driverWayY;
	}
	/**
	 * 设置车道在图片中的纵坐标
	 * @param
	 */
	public void setDriverWayY(Integer driverWayY) {
		this.driverWayY = driverWayY;
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

	/**
	 * 获取字体颜色
	 * @return 字体颜色
	 */
	@Column(name = "FONT_COLOR")
	public String getFontColor() {
		return fontColor;
	}
	/**
	 * 设置字体颜色
	 * @param
	 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * 获取字体大小
	 * @return 字体大小
	 */
	@Column(name = "FONT_SIZE")
	public Integer getFontSize() {
		return fontSize;
	}
	/**
	 * 设置字体大小
	 * @param
	 */
	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}
	
	
}
