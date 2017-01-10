package com.ncjk.utcs.modules.query.pojo;

import com.ncjk.utcs.modules.resources.resources.pojo.UtcsCrossParam;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by swl on 2016/12/27.
 * 交通流量实体类
 */
@Entity
@Table(name = "UTCS_TRAFFIC_DATA")
public class UtcsTrafficData {

    private Integer trafficDataId;
    private UtcsCrossParam crossParam;
    private Date crossDate;
    private Date crossTime;
    private Integer eastLeft;
    private Integer eastStraight;
    private Integer eastRight;
    private Integer southLeft;
    private Integer southStraight;
    private Integer southRight;
    private Integer westLeft;
    private Integer westStraight;
    private Integer westRight;
    private Integer northLeft;
    private Integer northStraight;
    private Integer northRight;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAFFIC_DATA_ID")
    public Integer getTrafficDataId() {
        return trafficDataId;
    }

    public void setTrafficDataId(Integer trafficDataId) {
        this.trafficDataId = trafficDataId;
    }

    @ManyToOne(targetEntity = UtcsCrossParam.class)
    @JoinColumn(name = "CROSS_ID", nullable = false)
    public UtcsCrossParam getCrossParam() {
        return crossParam;
    }

    public void setCrossParam(UtcsCrossParam crossParam) {
        this.crossParam = crossParam;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CROSS_DATE")
    public Date getCrossDate() {
        return crossDate;
    }

    public void setCrossDate(Date crossDate) {
        this.crossDate = crossDate;
    }

    @Temporal(TemporalType.TIME)
    @Column(name = "CROSS_TIME")
    public Date getCrossTime() {
        return crossTime;
    }

    public void setCrossTime(Date crossTime) {
        this.crossTime = crossTime;
    }

    @Column(name = "EAST_LEFT")
    public Integer getEastLeft() {
        return eastLeft;
    }

    public void setEastLeft(Integer eastLeft) {
        this.eastLeft = eastLeft;
    }

    @Column(name = "EAST_STRAIGHT")
    public Integer getEastStraight() {
        return eastStraight;
    }

    public void setEastStraight(Integer eastStraight) {
        this.eastStraight = eastStraight;
    }

    @Column(name = "EAST_RIGHT")
    public Integer getEastRight() {
        return eastRight;
    }

    public void setEastRight(Integer eastRight) {
        this.eastRight = eastRight;
    }

    @Column(name = "SOUTH_LEFT")
    public Integer getSouthLeft() {
        return southLeft;
    }

    public void setSouthLeft(Integer southLeft) {
        this.southLeft = southLeft;
    }

    @Column(name = "SOUTH_STRAIGHT")
    public Integer getSouthStraight() {
        return southStraight;
    }

    public void setSouthStraight(Integer southStraight) {
        this.southStraight = southStraight;
    }

    @Column(name = "SOUTH_RIGHT")
    public Integer getSouthRight() {
        return southRight;
    }

    public void setSouthRight(Integer southRight) {
        this.southRight = southRight;
    }

    @Column(name = "WEST_LEFT")
    public Integer getWestLeft() {
        return westLeft;
    }

    public void setWestLeft(Integer westLeft) {
        this.westLeft = westLeft;
    }

    @Column(name = "WEST_STRAIGHT")
    public Integer getWestStraight() {
        return westStraight;
    }

    public void setWestStraight(Integer westStraight) {
        this.westStraight = westStraight;
    }

    @Column(name = "WEST_RIGHT")
    public Integer getWestRight() {
        return westRight;
    }

    public void setWestRight(Integer westRight) {
        this.westRight = westRight;
    }

    @Column(name = "NORTH_LEFT")
    public Integer getNorthLeft() {
        return northLeft;
    }

    public void setNorthLeft(Integer northLeft) {
        this.northLeft = northLeft;
    }

    @Column(name = "NORTH_STRAIGHT")
    public Integer getNorthStraight() {
        return northStraight;
    }

    public void setNorthStraight(Integer northStraight) {
        this.northStraight = northStraight;
    }

    @Column(name = "NORTH_RIGHT")
    public Integer getNorthRight() {
        return northRight;
    }

    public void setNorthRight(Integer northRight) {
        this.northRight = northRight;
    }
}
