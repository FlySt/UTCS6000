package com.ncjk.utcs.modules.system.services.interfaces;

import com.ncjk.utcs.modules.system.pojo.UtcsPluginParam;
import net.sf.json.JSONObject;

/**
 * Created by swl on 2016/11/30.
 */
public interface IPluginParamService {
    /**
     * 查找参数信息
     * @return
     */
    UtcsPluginParam findPluginParam();

    /**
     * 获取参数信息
     * @return
     */
    JSONObject getPluginParam(String fileName);

    /**
     * 获取Tcp的端口号
     * @return
     */
    Integer getTcpPort();
}
