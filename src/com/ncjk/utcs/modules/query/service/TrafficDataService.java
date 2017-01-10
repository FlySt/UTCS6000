package com.ncjk.utcs.modules.query.service;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.query.pojo.UtcsTrafficData;
import com.ncjk.utcs.modules.query.pojo.UtcsTrafficDayData;
import com.ncjk.utcs.modules.query.service.interfaces.ITrafficDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by swl on 2016/12/27.
 * 交通流量业务实现类
 */
@Service("trafficDataService")
public class TrafficDataService implements ITrafficDataService {
    private Logger logger = Logger.getLogger(TrafficDataService.class);
    @Resource
    ICommonDAO commonDAO;
    /**
     * 按时间段查询车流量
     * @param crossId   路口ID
     * @param crossDate      日期
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public JSONObject queryDataByTime(Integer crossId, String crossDate, String startTime, String endTime) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String hql = "from UtcsTrafficData t where t.crossParam.crossId="+crossId;
        StringBuffer condition = new StringBuffer("");
        if(crossDate!=null && crossDate.length()>0){
            condition.append(" and t.crossDate='").append(crossDate).append("'");
        }
        if(startTime!=null && startTime.length()>0){
            condition.append(" and t.crossTime>'").append(startTime).append("'");
        }
        if(endTime!=null && endTime.length()>0){
            condition.append(" and t.crossTime<'").append(endTime).append("'");
        }
        condition.append(" order by t.crossTime asc");
        List<UtcsTrafficData> trafficDataList = ( List<UtcsTrafficData>)commonDAO.findByHql(hql+condition.toString(),0,0);
        if(trafficDataList!=null && !trafficDataList.isEmpty()){
            for(UtcsTrafficData trafficData:trafficDataList){
                JSONObject object = new JSONObject();
                object.put("crossTime",timeFormat.format(trafficData.getCrossTime()));
                object.put("crossDate",dateFormat.format(trafficData.getCrossDate()));
                object.put("eastLeft",trafficData.getEastLeft());
                object.put("eastStraight",trafficData.getEastStraight());
                object.put("eastRight",trafficData.getEastRight());
                object.put("southLeft",trafficData.getSouthLeft());
                object.put("southStraight",trafficData.getSouthStraight());
                object.put("southRight",trafficData.getSouthRight());
                object.put("westLeft",trafficData.getWestLeft());
                object.put("westStraight",trafficData.getWestStraight());
                object.put("westRight",trafficData.getWestRight());
                object.put("northLeft",trafficData.getNorthLeft());
                object.put("northStraight",trafficData.getNorthStraight());
                object.put("northRight",trafficData.getNorthRight());
                array.add(object);
            }
        }
        jsonObject.put("data",array);
        return jsonObject;
    }

    /**
     * 查询一个月的车流量
     * @param crossId 路口ID
     * @param month   月数
     * @return
     */
    @Override
    public JSONObject queryOneMonthData(Integer crossId, String month) {
        String startDate = null;
        String endDate = null;
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String hql = "from UtcsTrafficDayData t where t.crossParam.crossId="+crossId;
        StringBuffer condition = new StringBuffer("");
        if(month!=null && month.length()>0){
            startDate = month+"-00";
            endDate = month+"-31";
            condition.append(" and t.crossDate>='").append(startDate).append("'");
            condition.append(" and t.crossDate<='").append(endDate).append("'");
        }
        condition.append(" order by t.crossDate asc");
        List<UtcsTrafficDayData> trafficDayDataList = ( List<UtcsTrafficDayData>)commonDAO.findByHql(hql+condition.toString(),0,0);
        if(trafficDayDataList!=null && !trafficDayDataList.isEmpty()){
            for(UtcsTrafficDayData trafficDayData:trafficDayDataList){
                JSONObject object = new JSONObject();
                object.put("crossDate",dateFormat.format(trafficDayData.getCrossDate()));
                object.put("eastLeft",trafficDayData.getEastLeft());
                object.put("eastStraight",trafficDayData.getEastStraight());
                object.put("eastRight",trafficDayData.getEastRight());
                object.put("southLeft",trafficDayData.getSouthLeft());
                object.put("southStraight",trafficDayData.getSouthStraight());
                object.put("southRight",trafficDayData.getSouthRight());
                object.put("westLeft",trafficDayData.getWestLeft());
                object.put("westStraight",trafficDayData.getWestStraight());
                object.put("westRight",trafficDayData.getWestRight());
                object.put("northLeft",trafficDayData.getNorthLeft());
                object.put("northStraight",trafficDayData.getNorthStraight());
                object.put("northRight",trafficDayData.getNorthRight());
                array.add(object);
            }
        }
        jsonObject.put("data",array);
        return jsonObject;
    }

    /**
     * 查询一年的车流量
     * @param crossId
     * @param year
     * @return
     */
    @Override
    public JSONObject queryOneYearData(Integer crossId, String year) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        if(year!=null && year.length()>0){
            for(int i=1;i<=12;i++){
                HashMap<String,Integer> hashMap = new HashMap<>();
                Integer[] datas = new Integer[]{0,0,0,0,0,0,0,0,0,0,0,0};
                String month = year+"-"+String.format("%02d",i);
                JSONObject object = queryOneMonthData(crossId,month);
                if(object!=null){
                    JSONArray jsonArray = object.getJSONArray("data");
                    if(jsonArray!=null){
                        for(int j=0;j<jsonArray.size();j++){
                            datas[0] += jsonArray.getJSONObject(j).getInt("eastLeft");
                            datas[1] += jsonArray.getJSONObject(j).getInt("eastStraight");
                            datas[2] += jsonArray.getJSONObject(j).getInt("eastRight");
                            datas[3] += jsonArray.getJSONObject(j).getInt("southLeft");
                            datas[4] += jsonArray.getJSONObject(j).getInt("southStraight");
                            datas[5] += jsonArray.getJSONObject(j).getInt("southRight");
                            datas[6] += jsonArray.getJSONObject(j).getInt("westLeft");
                            datas[7] += jsonArray.getJSONObject(j).getInt("westStraight");
                            datas[8] += jsonArray.getJSONObject(j).getInt("westRight");
                            datas[9] += jsonArray.getJSONObject(j).getInt("northLeft");
                            datas[10] += jsonArray.getJSONObject(j).getInt("northStraight");
                            datas[11] += jsonArray.getJSONObject(j).getInt("northRight");
         /*                   hashMap.put("eastLeft", hashMap.get("eastLeft")==null?0:hashMap.get("eastLeft")+jsonArray.getJSONObject(j).getInt("eastLeft"));
                            hashMap.put("eastStraight", hashMap.get("eastStraight")==null?0:hashMap.get("eastStraight")+jsonArray.getJSONObject(j).getInt("eastStraight"));
                            hashMap.put("eastRight", hashMap.get("eastRight")==null?0:hashMap.get("eastRight")+jsonArray.getJSONObject(j).getInt("eastRight"));
                            hashMap.put("southLeft", hashMap.get("southLeft")==null?0:hashMap.get("southLeft")+jsonArray.getJSONObject(j).getInt("southLeft"));
                            hashMap.put("southStraight", hashMap.get("southStraight")==null?0:hashMap.get("southStraight")+jsonArray.getJSONObject(j).getInt("southStraight"));
                            hashMap.put("southRight", hashMap.get("southRight")==null?0:hashMap.get("southRight")+jsonArray.getJSONObject(j).getInt("southRight"));
                            hashMap.put("westLeft", hashMap.get("westLeft")==null?0:hashMap.get("westLeft")+jsonArray.getJSONObject(j).getInt("westLeft"));
                            hashMap.put("westStraight", hashMap.get("westStraight")==null?0:hashMap.get("westStraight")+jsonArray.getJSONObject(j).getInt("westStraight"));
                            hashMap.put("westRight", hashMap.get("westRight")==null?0:hashMap.get("westRight")+jsonArray.getJSONObject(j).getInt("westRight"));
                            hashMap.put("northLeft", hashMap.get("northLeft")==null?0:hashMap.get("northLeft")+jsonArray.getJSONObject(j).getInt("northLeft"));
                            hashMap.put("northStraight", hashMap.get("northStraight")==null?0:hashMap.get("northStraight")+jsonArray.getJSONObject(j).getInt("northStraight"));
                            hashMap.put("northRight", hashMap.get("northRight")==null?0:hashMap.get("northRight")+jsonArray.getJSONObject(j).getInt("northRight"));*/
                        }
                        JSONObject js = new JSONObject();
                        js.put("crossDate",month);
                        js.put("eastLeft",datas[0] );
                        js.put("eastStraight",datas[1]);
                        js.put("eastRight",datas[2]);
                        js.put("southLeft",datas[3]);
                        js.put("southStraight",datas[4]);
                        js.put("southRight",datas[5]);
                        js.put("westLeft",datas[6]);
                        js.put("westStraight",datas[7]);
                        js.put("westRight",datas[8]);
                        js.put("northLeft",datas[9]);
                        js.put("northStraight",datas[10]);
                        js.put("northRight",datas[11]);
                        array.add(js);
                    }
                }
            }
        }
        jsonObject.put("data",array);
        return jsonObject;
    }
}
