package com.ncjk.utcs.modules.map.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by swl on 2016/12/13.
 * 信号机方案表实体类
 */
@Entity
@Table(name = "UTCS_GUARD")
public class UtcsGuard {

    private Integer guardId;
    //方案名称
    private String guardName;
    //方案状态 0-未审核 1-已审核
    private Integer guardStatus;
    //地图描点集合
    private String points;

    private Set<UtcsGuardSignal> utcsGuardSignalSet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUARD_ID")
    public Integer getGuardId() {
        return guardId;
    }

    public void setGuardId(Integer guardId) {
        this.guardId = guardId;
    }

    @OneToMany(mappedBy = "utcsGuard",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<UtcsGuardSignal> getUtcsGuardSignalSet() {
        return utcsGuardSignalSet;
    }

    public void setUtcsGuardSignalSet(Set<UtcsGuardSignal> utcsGuardSignalSet) {
        this.utcsGuardSignalSet = utcsGuardSignalSet;
    }

    @Column(name ="GUARD_NAME",nullable = false)
    public String getGuardName() {
        return guardName;
    }

    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }
    @Column(name = "GUARD_STATUS",nullable = false)
    public Integer getGuardStatus() {
        return guardStatus;
    }

    public void setGuardStatus(Integer guardStatus) {
        this.guardStatus = guardStatus;
    }

    @Column(name = "POINTS")
    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
