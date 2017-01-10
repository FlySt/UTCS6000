package com.ncjk.utcs.modules.resources.resources.services.interfaces;

import com.ncjk.utcs.modules.resources.resources.pojo.UtcsCrossParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 路口业务逻辑接口类
 * Created by swl on 2016/9/19.
 */
public interface ICrossParamService {
    /**
     * 查询所有路口信息
     * @param crossId
     * @param crossName
     * @return
     */
    JSONObject findCrossParams(Integer crossId, String crossName);

    /**
     * 保存路口信息
     * @param crossParam
     * @return
     */
    boolean saveOrUpdateCrossParam(UtcsCrossParam crossParam);

    /**
     * 删除路口信息
     * @param ids
     * @return
     */
    boolean delCrossParams(Integer ids[]);
    /**
     * 根据路口ID查询路口信息
     * @param crossId
     * @return
     */
    UtcsCrossParam findCrossParamById(Integer crossId);

    /**
     * 根据信号机ID查询路口信息
     * @param signalControlerId
     * @return
     */
   /* UtcsCrossParam findCrossParamBySignalControlerId(Integer signalControlerId);*/
    /**
     * 检查路口名称是否存在
     * @param crossId
     * @param crossName
     * @return
     */
    boolean isExistCrossName(Integer crossId,String crossName);

    /**
     * 检查路口编号是否存在
     * @param crossId
     * @param crossNum
     * @return
     */
    boolean isExistCrossNum(Integer crossId,String crossNum);

    /**
     * 创建路口树
     * @return
     */
    JSONArray buildCrossParamTrees();
}
