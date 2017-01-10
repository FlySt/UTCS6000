package com.ncjk.utcs.modules.system.services.interfaces;

import com.ncjk.utcs.modules.system.pojo.UtcsNetWorkParam;

/**
 * Created by swl on 2016/11/29.
 * 网络参数业务接口类
 */
public interface INetWorkParamService {

    /**
     * 查找网络参数表第一条数据
     * @return
     */
    UtcsNetWorkParam findNetWorkParam();

    /**
     * 保存网络参数
     * @param utcsNetWorkParam
     * @return
     */
    boolean saveParam(UtcsNetWorkParam utcsNetWorkParam);

    /**
     * 获取协议端口号
     * @param type  0-1049协议  1-内部协议
     * @return
     */
    Integer getPort(int type);

    /**
     * 获取有效心跳次数
     * @return
     */
    Integer getHjNum();

    /**
    获取心跳步长
     */
    Integer getHjStep();
    /**
     * 获取内部协议最大连接数
     * @return
     */
    Integer getInsideConNum();

    /**
     * 获取1049协议最大连接数
     */
    Integer getGat1049ConNum();
}
