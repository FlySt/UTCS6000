package com.ncjk.utcs.modules.map.action;

import com.ncjk.utcs.common.servlet.CommonPro;
import com.ncjk.utcs.modules.map.services.interfaces.ITrafficElementService;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 交通元素Action
 * Created by shiwanglai on 2016/9/8.
 */
@Controller("trafficElementAction")
@Scope("prototype")
public class TrafficElementAction extends ActionSupport {

    @Resource
    private ITrafficElementService trafficElementService;
    // 保存地图元素类型
    private String type;
    // 保存地图元素对象的ID
    private Integer objId;
    // 保存地图元素对象的经度
    private String lon;
    // 保存地图元素对象的纬度
    private String lat;

    /**
     * 保存地图元素
     */
    public void saveElementOnMap(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        boolean isSuccess = trafficElementService.saveTrafficeElement(type,objId,lon,lat);
        JSONObject js = new JSONObject();
        js.put("result",isSuccess);
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找已在地图上标注的元素
     *
     */
    public void findElementOnMap(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONArray array = trafficElementService.findTrafficElementOnMap();
        try {
            response.getWriter().write(array.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除地图上的元素
     * @return
     */
    public void deleteElementOnMap(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        boolean isSuccess = trafficElementService.deleteTrafficElementOnMap(type,objId);
        JSONObject js = new JSONObject();
        js.put("result",isSuccess);
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queryMapParams(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject js = new JSONObject();
        if( CommonPro.layers!=null && CommonPro.ipAddr!=null
                && CommonPro.lon!=null && CommonPro.lat!=null && CommonPro.port!=null){
            js.put("msg",true);
            js.put("layers", CommonPro.layers);
            js.put("ipAddr",CommonPro.ipAddr);
            js.put("port",CommonPro.port);
            js.put("lon",CommonPro.lon);
            js.put("lat",CommonPro.lat);
        }else{
            js.put("msg",false);
        }
        try {
            response.getWriter().write(js.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
