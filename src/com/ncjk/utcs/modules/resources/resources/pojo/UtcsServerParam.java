package com.ncjk.utcs.modules.resources.resources.pojo;

import javax.persistence.*;

/**
 * Created by swl on 2016/11/29.
 * 服务器参数表实体类
 */
@Entity
@Table(name = "UTCS_SERVER_PARAM")
public class UtcsServerParam {
    private Integer serverId;
    //服务器编号
    private String serverNo;
    //服务器IP地址
    private String serverIP;
    //中心/区域服务器标志
    private Integer isCenter;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVER_ID")
    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    @Column(name = "SERVER_NO",nullable = false)
    public String getServerNo() {
        return serverNo;
    }

    public void setServerNo(String serverNo) {
        this.serverNo = serverNo;
    }

    @Column(name ="SERVER_IP",nullable = false)
    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    @Column(name = "ISCENTER")
    public Integer getIsCenter() {
        return isCenter;
    }

    public void setIsCenter(Integer isCenter) {
        this.isCenter = isCenter;
    }
}
