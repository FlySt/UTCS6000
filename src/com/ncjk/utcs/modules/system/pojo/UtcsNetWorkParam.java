package com.ncjk.utcs.modules.system.pojo;

import javax.persistence.*;

/**
 * Created by swl on 2016/11/28.
 * 网络参数实体类
 */
@Entity
@Table(name = "UTCS_NETWORK_PARAM")
public class UtcsNetWorkParam {

    private Integer netId;
    //内部协议监听端口
    private Integer insidePort;
    //内部协议连接数
    private Integer insideConNum;
    //内部协议有效心跳树
    private Integer insideHJNum;
    //内部协议心跳步长
    private Integer insideHJStep;
    //GAT1049协议监听端口
    private Integer gat1049Port;
    //GAT1049协议连接数
    private Integer gat1049ConNum;
    //GAT1049有效心跳树
    private Integer gat1049HJNum;
    //GAT1049心跳步长
    private Integer gat1049HJStep;
    //GAT1049协议登录用户名
    private String gat1049User;
    //GAT1049协议登录密码
    private  String gat1049Pwd;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NET_ID")
    public Integer getNetId() {
        return netId;
    }

    public void setNetId(Integer netId) {
        this.netId = netId;
    }

    @Column(name = "INSIDE_PORT",nullable = false)
    public Integer getInsidePort() {
        return insidePort;
    }

    public void setInsidePort(Integer insidePort) {
        this.insidePort = insidePort;
    }

    @Column(name = "INSIDE_CONNUM",nullable = false)
    public Integer getInsideConNum() {
        return insideConNum;
    }

    public void setInsideConNum(Integer insideConNum) {
        this.insideConNum = insideConNum;
    }

    @Column(name = "INSIDE_HJNUM",nullable = false)
    public Integer getInsideHJNum() {
        return insideHJNum;
    }

    public void setInsideHJNum(Integer insideHJNum) {
        this.insideHJNum = insideHJNum;
    }

    @Column(name = "INSIDE_HJSTEP",nullable = false)
    public Integer getInsideHJStep() {
        return insideHJStep;
    }

    public void setInsideHJStep(Integer insideHJStep) {
        this.insideHJStep = insideHJStep;
    }

    @Column(name = "GAT1049_PORT",nullable = false)
    public Integer getGat1049Port() {
        return gat1049Port;
    }

    public void setGat1049Port(Integer gat1049Port) {
        this.gat1049Port = gat1049Port;
    }

    @Column(name = "GAT1049_CONNUM",nullable = false)
    public Integer getGat1049ConNum() {
        return gat1049ConNum;
    }

    public void setGat1049ConNum(Integer gat1049ConNum) {
        this.gat1049ConNum = gat1049ConNum;
    }

    @Column(name = "GAT1049_HJNUM",nullable = false)
    public Integer getGat1049HJNum() {
        return gat1049HJNum;
    }

    public void setGat1049HJNum(Integer gat1049HJNum) {
        this.gat1049HJNum = gat1049HJNum;
    }

    @Column(name = "GAT1049_HJSTEP",nullable = false)
    public Integer getGat1049HJStep() {
        return gat1049HJStep;
    }

    public void setGat1049HJStep(Integer gat1049HJStep) {
        this.gat1049HJStep = gat1049HJStep;
    }

    @Column(name = "GAT1049_USER",nullable = false)
    public String getGat1049User() {
        return gat1049User;
    }

    public void setGat1049User(String gat1049User) {
        this.gat1049User = gat1049User;
    }

    @Column(name = "GAT1049_PWD",nullable = false)
    public String getGat1049Pwd() {
        return gat1049Pwd;
    }

    public void setGat1049Pwd(String gat1049Pwd) {
        this.gat1049Pwd = gat1049Pwd;
    }
}
