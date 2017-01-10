package com.ncjk.utcs.modules.map.services.interfaces;

import net.sf.json.JSONArray;


/**
 * 交通元素业务接口类
 * Created by shiwanglai on 2016/9/8.
 */
public interface ITrafficElementService {
    /**
     * 保存地图上的元素对象
     * @param type 类型
     * @param objId 元素ID
     * @param lon 经度
     * @param lat 纬度
     * @return
     */
     boolean  saveTrafficeElement(String type,Integer objId,String lon,String lat );

    /**
     * 查找已在地图上标注的元素
     * @return
     */
    JSONArray findTrafficElementOnMap();

    /**
     * 删除地图上的元素
     * @param type 类型
     * @param objId id
     * @return
     */
    public boolean deleteTrafficElementOnMap(String type,Integer objId);
}
