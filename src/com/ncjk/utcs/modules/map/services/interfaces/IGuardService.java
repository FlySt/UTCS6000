package com.ncjk.utcs.modules.map.services.interfaces;

import com.ncjk.utcs.modules.map.pojo.UtcsGuard;
import com.ncjk.utcs.modules.map.pojo.UtcsGuardSignal;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by swl on 2016/12/13.
 * 警卫任务业务接口类
 */
public interface IGuardService {

    /**
     * 查找方案
     * @param guardId
     * @param guardName
     * @param guardStatus
     * @return
     */
    JSONObject queryGuards(Integer guardId,String guardName,Integer guardStatus);
    /**
     * 保存或者更新方案
     * @param utcsGuard
     * @return
     */
    boolean saveOrUpdateGuard(UtcsGuard utcsGuard);

    /**
     * 保存或更新方案信号机表
     * @param  utcsGuardSignalList
     * @return
     */
    boolean saveOrUpdateGuardSignal(List<UtcsGuardSignal> utcsGuardSignalList , Integer guardId);

    /**
     * 保存方案信号机信息
     * @param utcsGuardSignal
     * @return
     */
    boolean saveGuardSignal(UtcsGuardSignal utcsGuardSignal);
    /**
     * 根据ID查找方案
     * @param guardId
     * @return
     */
    UtcsGuard findGuardById(Integer guardId);

    /**
     * 根据信号机ID查找方案信号机
     * @param signalControlerId
     * @return
     */
    UtcsGuardSignal findGuardSignalBySignalControlerId(Integer signalControlerId);

    /**
     * 根据ID查找方案信号机
     * @param guardId
     * @return
     */
    UtcsGuardSignal findGuardSignalById(Integer guardId);

    /**
     * 获取方案信号机
     * @param signalControlerId
     * @return
     */
    JSONObject getGuardSignal(Integer signalControlerId,Integer guardId);

    /**
     * 根据方案ID查询方案信号机
     * @param guardId
     * @return
     */
    JSONObject getGuardSignalByGuardId(Integer guardId);
    /**
     * 根据方案ID获取信号机信息
     * @param guardId
     * @return
     */
    JSONArray getSignalControlers(Integer guardId);
    /**
     * 删除方案信号机
     * @param guardSignalId
     * @return
     */
    boolean deleteGuardSignal(Integer guardSignalId);

    /**
     * 删除方案
     * @param guardIds
     * @return
     */
    boolean deleteGuard(Integer[] guardIds);
}
