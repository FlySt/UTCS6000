package com.ncjk.utcs.modules.resources.resources.services.interfaces;

import com.ncjk.utcs.modules.resources.resources.pojo.UtcsServerParam;
import net.sf.json.JSONObject;

/**
 * Created by swl on 2016/11/29.
 * 服务器参数业务接口类
 */
public interface IServerParamService {
    /**
     * 查找服务器
     * @return
     */
    JSONObject findServerParams(Integer serverId,String serverNo);

    /**
     * 保存服务器信息
     * @param utcsServerParam
     * @return
     */
    boolean saveOrUpdateServerParam(UtcsServerParam utcsServerParam);
    /**
     * 根据ID查找服务器信息
     * @param serverId
     * @return
     */
    UtcsServerParam findServerParamById(Integer serverId);

    /**
     * 删除服务器
     * @param ids
     * @return
     */
    boolean delServers(Integer[] ids);
    /**
     * 检查服务器编号是否存在
     * @param serverId
     * @param serverNo
     * @return
     */
    boolean isExistServerNo(Integer serverId,String serverNo);

    /**
     * 检查中心服务器是否存在
     * @return
     */
    boolean isExistCenterServer(Integer serverId);
}
