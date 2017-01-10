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
@Table(name = "UTCS_SIGNAL_TRAFFICPIC")
public class UtcsSignalTrafficPic {
	//主键
	private	Integer		trafficpicId;
	//信号机设备ID，外键(UTMS_SIGNAL)
	private 	UtcsSignalControler	utcsSignalControler;
	//方向(0-东左，1-东直，2-东右...........9-北左，10-北直，11-北右)
	private	Integer		trafficpicDirection;
	//横坐标
	private	Integer		trafficpicX;
	//纵坐标
	private Integer		trafficpicY;
	//宽度
	private	Integer		trafficpicWidth;
	//高度
	private	Integer		trafficpicHeight;
	//角度
	private	Double		trafficpicAngle;
	//字体颜色
	private	String		trafficpicFontColor;
	//字体大小
	private	Integer		trafficpicFontSize;
	//交通状态阀值(黄色状态)
	private	Integer		trafficpicYellow;
	//交通状态阀值(红色状态)
	private	Integer		trafficpicRed;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRAFFICPIC_ID")
	public Integer getTrafficpicId() {
		return trafficpicId;
	}
	public void setTrafficpicId(Integer trafficpicId) {
		this.trafficpicId = trafficpicId;
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
	
	@Column(name = "TRAFFICPIC_DIRECTION")
	public Integer getTrafficpicDirection() {
		return trafficpicDirection;
	}
	public void setTrafficpicDirection(Integer trafficpicDirection) {
		this.trafficpicDirection = trafficpicDirection;
	}
	
	@Column(name = "TRAFFICPIC_X")
	public Integer getTrafficpicX() {
		return trafficpicX;
	}
	public void setTrafficpicX(Integer trafficpicX) {
		this.trafficpicX = trafficpicX;
	}
	
	@Column(name = "TRAFFICPIC_Y")
	public Integer getTrafficpicY() {
		return trafficpicY;
	}
	public void setTrafficpicY(Integer trafficpicY) {
		this.trafficpicY = trafficpicY;
	}
	
	@Column(name = "TRAFFICPIC_WIDTH")
	public Integer getTrafficpicWidth() {
		return trafficpicWidth;
	}
	public void setTrafficpicWidth(Integer trafficpicWidth) {
		this.trafficpicWidth = trafficpicWidth;
	}
	
	@Column(name = "TRAFFICPIC_HEIGHT")
	public Integer getTrafficpicHeight() {
		return trafficpicHeight;
	}
	public void setTrafficpicHeight(Integer trafficpicHeight) {
		this.trafficpicHeight = trafficpicHeight;
	}
	
	@Column(name = "TRAFFICPIC_ANGLE", precision = 20)
	public Double getTrafficpicAngle() {
		return trafficpicAngle;
	}
	public void setTrafficpicAngle(Double trafficpicAngle) {
		this.trafficpicAngle = trafficpicAngle;
	}
	
	@Column(name = "TRAFFICPIC_FONTCOLOR")
	public String getTrafficpicFontColor() {
		return trafficpicFontColor;
	}
	public void setTrafficpicFontColor(String trafficpicFontColor) {
		this.trafficpicFontColor = trafficpicFontColor;
	}
	
	@Column(name = "TRAFFICPIC_FONTSIZE")
	public Integer getTrafficpicFontSize() {
		return trafficpicFontSize;
	}
	public void setTrafficpicFontSize(Integer trafficpicFontSize) {
		this.trafficpicFontSize = trafficpicFontSize;
	}
	
	@Column(name = "TRAFFICPIC_YELLOW")
	public Integer getTrafficpicYellow() {
		return trafficpicYellow;
	}
	public void setTrafficpicYellow(Integer trafficpicYellow) {
		this.trafficpicYellow = trafficpicYellow;
	}
	
	@Column(name = "TRAFFICPIC_RED")
	public Integer getTrafficpicRed() {
		return trafficpicRed;
	}
	public void setTrafficpicRed(Integer trafficpicRed) {
		this.trafficpicRed = trafficpicRed;
	}
	
	
}
