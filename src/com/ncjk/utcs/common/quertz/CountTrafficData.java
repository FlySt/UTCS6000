package com.ncjk.utcs.common.quertz;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.query.pojo.UtcsTrafficData;
import com.ncjk.utcs.modules.query.pojo.UtcsTrafficDayData;
import com.ncjk.utcs.modules.query.service.interfaces.ITrafficDataService;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsCrossParam;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by swl on 2016/12/28.
 * 每天定时统计当天的车流量
 */
public class CountTrafficData {
    private Logger logger = Logger.getLogger(CountTrafficData.class);
    @Resource
    ITrafficDataService trafficDataService;
    @Resource
    ICommonDAO commonDAO;
    public CountTrafficData() {
    }

    public void doOnceDay(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        logger.info("统计["+date+"]车流量");

        UtcsTrafficDayData trafficDayData = (UtcsTrafficDayData)commonDAO.findByHql("from UtcsTrafficDayData t where t.crossDate='"+date+"'");
        if(trafficDayData==null){
            List<UtcsCrossParam> crossParams = (List<UtcsCrossParam>)commonDAO.findByHql("from UtcsCrossParam t where 1=1",0,0);//查找所有路口
            if(crossParams!=null && !crossParams.isEmpty()){
                for(UtcsCrossParam crossParam:crossParams){
                    Integer eastLeft = 0;
                    Integer eastStraight = 0;
                    Integer eastRight = 0;
                    Integer southLeft = 0;
                    Integer southStraight = 0;
                    Integer southRight = 0;
                    Integer westLeft = 0;
                    Integer westStraight = 0;
                    Integer westRight = 0;
                    Integer northLeft = 0;
                    Integer northStraight = 0;
                    Integer northRight = 0;
                    String hql = "from UtcsTrafficData t where t.crossDate='"+date+"' and t.crossParam.crossId="+crossParam.getCrossId();
                    List<UtcsTrafficData> trafficDataList = (List<UtcsTrafficData>)commonDAO.findByHql(hql,0,0);
                    UtcsTrafficDayData newTrafficDayData = new UtcsTrafficDayData();
                    if(trafficDataList!=null && !trafficDataList.isEmpty()){
                        for(UtcsTrafficData trafficData:trafficDataList){
                            eastLeft +=trafficData.getEastLeft();
                            eastStraight +=trafficData.getEastStraight();
                            eastRight +=trafficData.getEastRight();
                            southLeft +=trafficData.getSouthLeft();
                            southStraight +=trafficData.getSouthStraight();
                            southRight +=trafficData.getSouthRight();
                            westLeft +=trafficData.getWestLeft();
                            westStraight +=trafficData.getWestStraight();
                            westRight +=trafficData.getWestRight();
                            northLeft +=trafficData.getNorthLeft();
                            northStraight +=trafficData.getNorthStraight();
                            northRight +=trafficData.getNorthRight();
                        }
                        newTrafficDayData.setCrossParam(crossParam);
                        try {
                            newTrafficDayData.setCrossDate(dateFormat.parse(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        newTrafficDayData.setEastLeft(eastLeft);
                        newTrafficDayData.setEastStraight(eastStraight);
                        newTrafficDayData.setEastRight(eastRight);
                        newTrafficDayData.setSouthLeft(southLeft);
                        newTrafficDayData.setSouthStraight(southStraight);
                        newTrafficDayData.setSouthRight(southRight);
                        newTrafficDayData.setWestLeft(westLeft);
                        newTrafficDayData.setWestStraight(westStraight);
                        newTrafficDayData.setWestRight(westRight);
                        newTrafficDayData.setNorthLeft(northLeft);
                        newTrafficDayData.setNorthStraight(northStraight);
                        newTrafficDayData.setNorthRight(northRight);
                        commonDAO.saveOrUpdate(newTrafficDayData);
                    }
                }
            }
        }
    }
}
