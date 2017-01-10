package com.ncjk.utcs.modules.system.pojo;

import javax.persistence.*;

/**
 * Created by swl on 2016/11/30.
 * 控件通信参数表
 */
@Entity
@Table(name = "UTCS_PLUGIN_PARAM")
public class UtcsPluginParam {

    private Integer pluginId;
    private String serverIP;
    private String ftpUser;
    private String ftpPwd;
    private Integer ftpPort;
    private Integer tcpPort;
    private String ftpDir;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLUGIN_ID")
    public Integer getPluginId() {
        return pluginId;
    }

    public void setPluginId(Integer pluginId) {
        this.pluginId = pluginId;
    }

    @Column(name = "SERVER_IP")
    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    @Column(name = "FTP_USER")
    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    @Column(name = "FTP_PWD")
    public String getFtpPwd() {
        return ftpPwd;
    }

    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }

    @Column(name = "FTP_PORT")
    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    @Column(name = "TCP_PORT")
    public Integer getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(Integer tcpPort) {
        this.tcpPort = tcpPort;
    }

    @Column(name = "FTP_DIR")
    public String getFtpDir() {
        return ftpDir;
    }

    public void setFtpDir(String ftpDir) {
        this.ftpDir = ftpDir;
    }
}
