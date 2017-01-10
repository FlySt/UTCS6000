package com.ncjk.utcs.modules.map.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by swl on 2016/12/13.
 */
@Entity
@Table(name = "UTCS_GUARD_SIGNAL")
public class UtcsGuardSignal {

    private Integer guardSignalId;
    //所属方案
    private UtcsGuard utcsGuard;
    //所在方案的序号
    private Integer guardIndex;
    //上一个路口到达该路口所需时间秒数
    private Integer lastToTime;
    //通过该路口所需秒数
    private Integer passTime;
    //方向
    private Integer direction;
    //所属信号机ID
    private Integer signalControlerId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUARD_SIGNAL_ID")
    public Integer getGuardSignalId() {
        return guardSignalId;
    }

    public void setGuardSignalId(Integer guardSignalId) {
        this.guardSignalId = guardSignalId;
    }

    @ManyToOne(targetEntity = UtcsGuard.class,cascade=CascadeType.ALL)
    @JoinColumn(name = "GUARD_ID", nullable = false)
    public UtcsGuard getUtcsGuard() {
        return utcsGuard;
    }

    public void setUtcsGuard(UtcsGuard utcsGuard) {
        this.utcsGuard = utcsGuard;
    }

    @Column(name = "GUARD_INDEX",nullable = false)
    public Integer getGuardIndex() {
        return guardIndex;
    }

    public void setGuardIndex(Integer guardIndex) {
        this.guardIndex = guardIndex;
    }

    @Column(name = "LAST_TO_TIME",nullable = false)
    public Integer getLastToTime() {
        return lastToTime;
    }

    public void setLastToTime(Integer lastToTime) {
        this.lastToTime = lastToTime;
    }

    @Column(name = "PASS_TIME",nullable = false)
    public Integer getPassTime() {
        return passTime;
    }

    public void setPassTime(Integer passTime) {
        this.passTime = passTime;
    }

    @Column(name = "DIRECTION",nullable = false)
    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @Column(name = "SIGNAL_CONTROLER_ID",nullable = false)
    public Integer getSignalControlerId() {
        return signalControlerId;
    }

    public void setSignalControlerId(Integer signalControlerId) {
        this.signalControlerId = signalControlerId;
    }
}
