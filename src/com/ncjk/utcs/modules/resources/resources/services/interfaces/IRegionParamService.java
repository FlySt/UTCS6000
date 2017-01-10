package com.ncjk.utcs.modules.resources.resources.services.interfaces;

import com.ncjk.utcs.modules.resources.resources.pojo.UtcsRegionParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 区域业务逻辑接口类
 * Created by swl on 2016/9/19.
 */
public interface IRegionParamService {
    /**
     * 查找所有区域信息
     * @param regionId
     * @param regionName
     * @return
     */
    JSONObject findRegionParams(Integer regionId,String regionName);

    /**
     * 保存或更改区域信息
     * @param regionParam
     * @return
     */
    boolean saveOrUpdateReginParam(UtcsRegionParam regionParam);

    /**
     * 删除区域信息
     * @param ids
     * @return
     */
    boolean delRegionParams(Integer[] ids);
    /**
     * 根据区域ID查找区域信息
     * @param regionId
     * @return
     */
    UtcsRegionParam findRegionParamById(Integer regionId);

    /**
     * 创建区域树
     * @return
     */
    JSONArray buildRegionParamTrees();

    /**
     * 检查区域名称是否存在
     * @param regionId
     * @param regionName
     * @return
     */
    boolean isExistRegionName(Integer regionId,String regionName);
}
