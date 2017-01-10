package com.ncjk.utcs.modules.resources.resources.pojo;

import com.ncjk.utcs.modules.resources.basic.pojo.UtcsDept;

import javax.persistence.*;

/**
 * 区域参数实体类
 * Created by swl on 2016/9/7.
 */
@Entity
@Table(name = "UTCS_REGION_PARAM")
public class UtcsRegionParam {
    //区域ID
    private Integer regionId;
    //区域编号
    private String regionNum;
    //区域名称
    private String regionName;
    //所属部门
    private UtcsDept dept;
    //区域类型
    private Integer regionType;
    //父级区域
    private Integer fatherRegionId;
    //区域状态
    private Integer regionState;
    //区域描述
    private String regionDesc;

    public UtcsRegionParam() {
    }

    /**
     * 区域ID 自增
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGION_ID")
    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    /**
     * 区域编号
     * @return
     */
    @Column(name = "REGION_NUM",nullable = false)
    public String getRegionNum() {
        return regionNum;
    }

    public void setRegionNum(String regionNum) {
        this.regionNum = regionNum;
    }

    /**
     * 区域名称
     * @return
     */
    @Column(name = "REGION_NAME",nullable = false)
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * 所属部门
     * @return
     */
    @ManyToOne(targetEntity = UtcsDept.class)
    @JoinColumn(name = "DEPT_ID")
    public UtcsDept getDept() {
        return dept;
    }

    public void setDept(UtcsDept dept) {
        this.dept = dept;
    }

    /**
     * 区域类型
     * @return
     */
    @Column(name = "REGION_TYPE",nullable = false)
    public Integer getRegionType() {
        return regionType;
    }

    public void setRegionType(Integer regionType) {
        this.regionType = regionType;
    }

    /**
     * 父级区域
     * @return
     */
    @Column(name = "FATHER_REGION_ID")
    public Integer getFatherRegionId() {
        return fatherRegionId;
    }

    public void setFatherRegionId(Integer fatherRegionId) {
        this.fatherRegionId = fatherRegionId;
    }

    /**
     * 区域状态
     * @return
     */
    @Column(name = "REGION_STATE")
    public Integer getRegionState() {
        return regionState;
    }

    public void setRegionState(Integer regionState) {
        this.regionState = regionState;
    }

    /**
     * 区域描述
     * @return
     */
    @Column(name = "REGION_DESC")
    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }
}
