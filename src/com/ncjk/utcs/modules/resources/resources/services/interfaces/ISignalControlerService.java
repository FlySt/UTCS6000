package com.ncjk.utcs.modules.resources.resources.services.interfaces;

import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalControler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 信号机业务接口类
 * Created by 石望来 on 2016/9/7.
 */
public interface ISignalControlerService {

    /**
     * 查找所有信号机
     *@return  JSONObject
     */
    JSONObject findSignalControlers(Integer signalControlerId, String signalControlerName);

    /**
     * 添加或修改信号机信息
     * @param utcsSignalControler
     * @author swl
     * @return
     */
     boolean saveOrUpdateSignalControler(UtcsSignalControler utcsSignalControler);
    /**
     * 根据信号机ID查询
     * @param signalControlerId
     * @return
     */
     UtcsSignalControler findSignalControlerById(Integer signalControlerId);

    /**
     * 删除信号机
     * @param ids
     * @return
     */
     boolean delSignalControlers(Integer[] ids);

    /**
     * 检查信号机名称是否存在
     * @param signalControlerId
     * @param signalControlerName
     * @return
     */
     boolean isExistSignalControlerName(Integer signalControlerId,String signalControlerName);

    /**
     * 检查信号机编号是否存在
     * @param signalControlerId
     * @param signalControlerNum
     * @return
     */
    boolean isExistSignalControlerNum(Integer signalControlerId,String signalControlerNum);

    /**
     * 根据路口ID查看该路口是否已有信号机
     * @param crossId
     * @return
     */
    boolean isCrossExistSignalControler(Integer crossId,Integer signalControlerId);
    /**
     * 创建信号机树
     * @param type 0-地图上没有标记的信号机 1-地图上已有的信号机
     * @return
     */
     JSONArray buildSignalControlerTrees(int type);
    /**
     * 根据信号机编号查询IP地址
     * @param signalControlerNum
     * @return
     */
     String findSignalControlerIpByNum(String signalControlerNum);
    /**
     * 根据信号机ip查询信号机编号
     * @param deviceIp
     * @return
     */
    String findSignalControlerNumByIp(String deviceIp);

    /**
     * 改变信号机状态 信号机状态（0-正常在线 2-脱机/断线 4-异常故障）
     * @param state
     * @param deviceIp
     */
    void setSignalControlerState(String deviceIp,Integer state);

    /**
     * 改变所有信号机的状态为断线
     */
    void setAllSignalControlerBreak();
}
