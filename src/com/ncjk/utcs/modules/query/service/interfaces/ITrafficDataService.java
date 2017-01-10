package com.ncjk.utcs.modules.query.service.interfaces;

import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2016/12/27.
 */
public interface ITrafficDataService {
    /**
     * 按时间段查询车流量
     * @param crossId 路口ID
     * @param crossDate 日期
     * @param startTime 起始时间
     * @param endTime  结束时间
     * @return
     */
    JSONObject queryDataByTime(Integer crossId, String crossDate, String startTime, String endTime);

    /**
     * 查询一个月的车流量
     * @param crossId 路口ID
     * @param month 月数
     * @return
     */
    JSONObject queryOneMonthData(Integer crossId, String month);

    /**
     * 查询一年的车流量
     * @param crossId
     * @param year
     * @return
     */
    JSONObject queryOneYearData(Integer crossId, String year);
}
