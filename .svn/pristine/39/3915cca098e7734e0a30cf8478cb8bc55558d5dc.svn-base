package com.ncjk.utcs.modules.resources.resources.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.persistence.*;

/**
 * 路口参数实体类
 * Created by swl on 2016/9/19.
 */
@Entity
@Table(name = "UTCS_CROSS_PARAM")
public class UtcsCrossParam {
    //路口ID
    private Integer crossId;
    //路口编号
    private String crossNum;
    //路口名称
    private String crossName;
    //所属区域
    private UtcsRegionParam regionParam;
    //路口特征
    private Integer feature;
    //是否是关键路口
    private Integer isKey;
    //路口类型
    private Integer crossType;
    //路口状态
    private Integer crossState;
    //路口描述
    private String crossDesc;
    //经度
    private String longitude;
    //纬度
    private String latitude;

    public UtcsCrossParam() {
    }

    /**
     * 路口ID
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CROSS_ID")
    public Integer getCrossId() {
        return crossId;
    }

    public void setCrossId(Integer crossId) {
        this.crossId = crossId;
    }

    /**
     * 路口编号，全局唯一
     * @return
     */
    @Column(name = "CROSS_NUM",nullable = false)
    public String getCrossNum() {
        return crossNum;
    }

    public void setCrossNum(String crossNum) {
        this.crossNum = crossNum;
    }

    /**
     * 路口名称
     * @return
     */
    @Column(name = "CROSS_NAME",nullable = false)
    public String getCrossName() {
        return crossName;
    }

    public void setCrossName(String crossName) {
        this.crossName = crossName;
    }

    /**
     * 所属区域
     * @return
     */
    @ManyToOne(targetEntity = UtcsRegionParam.class)
    @JoinColumn(name = "REGION_ID",nullable = false)
    public UtcsRegionParam getRegionParam() {
        return regionParam;
    }

    public void setRegionParam(UtcsRegionParam regionParam) {
        this.regionParam = regionParam;
    }

    /**
     * 路口特征
     * @return
     */
    @Column(name = "FEATURE",nullable = false)
    public Integer getFeature() {
        return feature;
    }

    public void setFeature(Integer feature) {
        this.feature = feature;
    }

    /**
     * 是否是关键路口
     * @return
     */
    @Column(name = "ISKEY",nullable = false)
    public Integer getIsKey() {
        return isKey;
    }

    public void setIsKey(Integer isKey) {
        this.isKey = isKey;
    }

    /**
     * 路口类型
     * @return
     */
    @Column(name = "CROSS_TYPE")
    public Integer getCrossType() {
        return crossType;
    }

    public void setCrossType(Integer crossType) {
        this.crossType = crossType;
    }
 /*   *//**
     * 信号机设备ID
     * @return
     *//*
    @Column(name = "SIGNAL_CONTROLER_ID")
    public Integer getSignalControlerId() {
        return signalControlerId;
    }

    public void setSignalControlerId(Integer signalControlerId) {
        this.signalControlerId = signalControlerId;
    }

    /**
     * 路口状态
     * @return
     */
    @Column(name = "CROSS_STATE")
    public Integer getCrossState() {
        return crossState;
    }

    public void setCrossState(Integer crossState) {
        this.crossState = crossState;
    }

    /**
     * 路口描述
     * @return
     */
    @Column(name = "CROSS_DESC")
    public String getCrossDesc() {
        return crossDesc;
    }

    public void setCrossDesc(String crossDesc) {
        this.crossDesc = crossDesc;
    }

    /**
     * 经度
     * @return
     */
    @Column(name = "LONGITUDE")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 纬度
     * @return
     */
    @Column(name = "LATITUDE")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
