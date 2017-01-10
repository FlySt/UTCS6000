package com.ncjk.utcs.common.servlet;



/**
 * Created by swl on 2016/9/5.
 */
public class CommonPro {
    public static String version = "";
    public static String company = "";
    public static String copyright = "";
    public static String layers = "";
    public static String ipAddr = "";
    public static String port ;
    public static Double lon ;
    public static Double lat ;

    public CommonPro() {
        System.out.println("==正在执行CommonPro无参数构造器===");
    }

    public void setVersion(String version) {
        CommonPro.version = version;
    }
    public void setCompany(String company) {
        CommonPro.company = company;
    }
    public void setCopyright(String copyright) {
        CommonPro.copyright = copyright;
    }
    public void setLayers(String layers) {
        CommonPro.layers = layers;
    }
    public void setIpAddr(String ipAddr) {
        CommonPro.ipAddr = ipAddr;
    }

    public void setPort(String port) {
        CommonPro.port = port;
    }

    public void setLon(Double lon) {
        CommonPro.lon = lon;
    }
    public void setLat(Double lat) {
        CommonPro.lat = lat;
    }
}
