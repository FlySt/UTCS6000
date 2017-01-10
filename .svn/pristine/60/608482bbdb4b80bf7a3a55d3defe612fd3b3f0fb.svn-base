package com.ncjk.utcs.modules.resources.resources.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * 信号机参数实体类
 * Created by 石望来 on 2016/9/7.
 */
@Entity
@Table(name = "UTCS_SIGNAL_CONTROLER")
public class UtcsSignalControler {
    //信号机ID
    private Integer signalControlerId ;
    //路口
    private UtcsCrossParam crossParam;
    //信号机编号
    private String signalControlerNum;
    //信号机名称
    private String signalControlerName;
    //所属服务器
    private UtcsServerParam serverParam;
    //供应商
    private Integer supplier;
    //规格型号
    private Integer type;
    //协议号
    private String protocolNum;
    //设备IP地址
    private String deviceIp;
    //设备通信端口号（预留）
    private Integer devicePort;
    //信号机状态
    private Integer signalState;
    //错误值
    private Integer errorId;
    //信号机经度
    private String longitude;
    //信号机纬度
    private String latitude;
    //地图标注 0-未标注 1-已标注
    private Integer mapSign;
    //路口类型(0-十字路口,1-三叉路口,2-五叉路口),预留
    private		Integer			roadType;
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
    private Date updateTime;
    //更新账号
    private 	String			updateAccount;

    //车流量通道配置
    private		String	trafficpicSet;
    //每个特殊灯色表的名称，共60个特殊灯色表(JK-F)
    private		String	specialLightName;
    //信号机类型 0-非国标信号机  1-国标信号机
    private Integer signalType;
    public UtcsSignalControler() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SIGNAL_CONTROLER_ID")
    public Integer getSignalControlerId() {
        return signalControlerId;
    }

    public void setSignalControlerId(Integer signalControlerId) {
        this.signalControlerId = signalControlerId;
    }

    @OneToOne(targetEntity = UtcsCrossParam.class)
    @JoinColumn(name = "CROSS_ID",nullable = false)
    public UtcsCrossParam getCrossParam() {
        return crossParam;
    }

    public void setCrossParam(UtcsCrossParam crossParam) {
        this.crossParam = crossParam;
    }

    @Column(name = "SIGNAL_CONTROLER_NUM",nullable = false)
    public String getSignalControlerNum() {
        return signalControlerNum;
    }

    public void setSignalControlerNum(String signalControlerNum) {
        this.signalControlerNum = signalControlerNum;
    }

    @Column(name = "SIGNAL_CONTROLER_NAME",nullable = false)
    public String getSignalControlerName() {
        return signalControlerName;
    }

    public void setSignalControlerName(String signalControlerName) {
        this.signalControlerName = signalControlerName;
    }

    @ManyToOne(targetEntity = UtcsServerParam.class)
    @JoinColumn(name = "SERVER_ID",nullable = false)
    public UtcsServerParam getServerParam() {
        return serverParam;
    }

    public void setServerParam(UtcsServerParam serverParam) {
        this.serverParam = serverParam;
    }

    @Column(name = "SUPPLIER")
    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "PROTOCOL_NUM")
    public String getProtocolNum() {
        return protocolNum;
    }

    public void setProtocolNum(String protocolNum) {
        this.protocolNum = protocolNum;
    }

    @Column(name = "DEVICE_IP")
    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    @Column(name = "DEVICE_PORT")
    public Integer getDevicePort() {
        return devicePort;
    }

    public void setDevicePort(Integer devicePort) {
        this.devicePort = devicePort;
    }

    @Column(name = "SIGNAL_STATE")
    public Integer getSignalState() {
        return signalState;
    }

    public void setSignalState(Integer signalState) {
        this.signalState = signalState;
    }

    @Column(name = "ERROR_ID",nullable = false)
    public Integer getErrorId() {
        return errorId;
    }

    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    @Column(name = "LONGITUDE")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Column(name = "LATITUDE")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column(name = "MAP_SIGN")
    public Integer getMapSign() {
        return mapSign;
    }

    public void setMapSign(Integer mapSign) {
        this.mapSign = mapSign;
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

    @Column(name = "SIGNAL_TYPE")
    public Integer getSignalType() {
        return signalType;
    }

    public void setSignalType(Integer signalType) {
        this.signalType = signalType;
    }

    @Override
    public String toString() {
        return "UtcsSignalControler{" +
                "signalControlerId=" + signalControlerId +
                ", signalControlerNum='" + signalControlerNum + '\'' +
                ", signalControlerName='" + signalControlerName + '\'' +
                ", supplier='" + supplier + '\'' +
                ", type=" + type +
                ", protocolNum='" + protocolNum + '\'' +
                ", deviceIp='" + deviceIp + '\'' +
                ", devicePort=" + devicePort +
                ", signalState=" + signalState +
                ", errorId=" + errorId +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
