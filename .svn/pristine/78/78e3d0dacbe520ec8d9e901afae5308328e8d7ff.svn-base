package com.ncjk.utcs.modules.query.action;

import com.ncjk.utcs.modules.query.service.interfaces.ITrafficDataService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by swl on 2016/12/27.
 * 车流量Action
 */
@Scope("prototype")
@Controller("trafficDataAction")
public class TrafficDataAction {
    private Logger logger = Logger.getLogger(TrafficDataAction.class);
    @Resource
    private ITrafficDataService trafficDataService;

    private Integer crossId;
    private String crossDate;
    private String startTime;
    private String endTime;
    private String month;
    private String year;
    /**
     * 根据时间段查询
     */
    public void queryTrafficDataByTime(){
        logger.info("query data");
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject object = trafficDataService.queryDataByTime(crossId,crossDate,startTime,endTime);
        try {
            response.getWriter().write(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询一个月的车流量信息
     */
    public void queryTrafficDayData(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject object = trafficDataService.queryOneMonthData(crossId,month);
        try {
            response.getWriter().write(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询一年的车流量
     */
    public void queryTrafficYearData(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/json;charset=utf-8");
        JSONObject object = trafficDataService.queryOneYearData(crossId,year);
        try {
            response.getWriter().write(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getCrossId() {
        return crossId;
    }

    public void setCrossId(Integer crossId) {
        this.crossId = crossId;
    }

    public String getCrossDate() {
        return crossDate;
    }

    public void setCrossDate(String crossDate) {
        this.crossDate = crossDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
