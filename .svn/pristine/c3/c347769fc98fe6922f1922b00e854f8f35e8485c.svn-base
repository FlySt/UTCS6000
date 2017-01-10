package com.ncjk.utcs.modules.map.services;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.map.services.interfaces.ITrafficElementService;

import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalControler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 交通元素业务实现类
 * Created by shiwanglai on 2016/9/8.
 */
@Service("trafficElementService")
public class TrafficElementService implements ITrafficElementService{
     @Resource
     private ICommonDAO commonDAO;
    /**
     * 保存地图上的元素
     * @param type 类型
     * @param objId 元素ID
     * @param lon 经度
     * @param lat 纬度
     * @return
     */
    @Override
    public boolean saveTrafficeElement(String type, Integer objId, String lon, String lat) {
        boolean isSuccess = false;
        if("signal".equals(type)){
            String hql = "from UtcsSignalControler t where t.signalControlerId="+objId;
            UtcsSignalControler utcsSignalControler =(UtcsSignalControler) commonDAO.findByHql(hql);
            if(utcsSignalControler!=null){
                utcsSignalControler.setLongitude(lon);
                utcsSignalControler.setLatitude(lat);
                utcsSignalControler.setMapSign(1);
                isSuccess = commonDAO.saveOrUpdate(utcsSignalControler);
            }
        }
        return isSuccess;
    }
    /**
     * 查找已在地图上标注的元素
     * @return
     */
    @Override
    public JSONArray findTrafficElementOnMap() {
        JSONArray array = new JSONArray();
        String hql = "from UtcsSignalControler t where t.mapSign=1 and t.longitude is not null and t.latitude is not null";
        List<UtcsSignalControler> utcsSignalControlers = (List<UtcsSignalControler>)commonDAO.findByHql(hql,0,0);

        if(utcsSignalControlers!=null && !utcsSignalControlers.isEmpty()){
            for(UtcsSignalControler utcsSignalControler:utcsSignalControlers){
                JSONObject js = new JSONObject();
                js.put("id",utcsSignalControler.getSignalControlerId());
                js.put("name",utcsSignalControler.getSignalControlerName());
                js.put("type","signal");
                js.put("lon",utcsSignalControler.getLongitude());
                js.put("lat",utcsSignalControler.getLatitude());
                array.add(js);
            }
        }
        return array;
    }
    /**
     * 删除地图上的元素
     * @param type 类型
     * @param objId id
     * @return
     */
    @Override
    public boolean deleteTrafficElementOnMap(String type, Integer objId) {
        boolean isSuccess = false ;
        if("signal".equals(type)){
            String hql = "from UtcsSignalControler t where t.signalControlerId="+objId;
            UtcsSignalControler utcsSignalControler = (UtcsSignalControler)commonDAO.findByHql(hql);
            if(utcsSignalControler!=null){
                utcsSignalControler.setLongitude(null);
                utcsSignalControler.setLatitude(null);
                utcsSignalControler.setMapSign(0);
                isSuccess = commonDAO.saveOrUpdate(utcsSignalControler);
            }
        }
        return isSuccess;
    }
}
