package com.ncjk.utcs.modules.resources.resources.services;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsServerParam;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.IServerParamService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by swl on 2016/11/29.
 * 服务器接口业务实现类
 */
@Service("serviceParamService")
public class ServiceParamService implements IServerParamService{

    @Resource
    ICommonDAO commonDAO;
    /**
     * 查找服务器
     * @param serverId
     * @param serverNo
     * @return
     */
    @Override
    public JSONObject findServerParams(Integer serverId, String serverNo) {
        JSONObject js = new JSONObject();
        JSONArray  array = new JSONArray();
        String hql = "from UtcsServerParam t where 1=1";
        StringBuffer condition = new StringBuffer("");
        if(serverId!=null && !"".equals(serverId)){
            condition.append(" and t.serverId="+serverId);
        }
        if(serverNo!=null && !"".equals(serverNo)){
            condition.append(" and t.serverNo='"+serverNo+"'");
        }
        List<UtcsServerParam> utcsServerParams = (List<UtcsServerParam>)commonDAO.findByHql(hql+condition.toString(),0,0);
        if(utcsServerParams!=null && !utcsServerParams.isEmpty()){
            for(UtcsServerParam utcsServerParam:utcsServerParams){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("serverId",utcsServerParam.getServerId());
                jsonObject.put("serverNo",utcsServerParam.getServerNo());
                jsonObject.put("serverIP",utcsServerParam.getServerIP());
                jsonObject.put("isCenter",utcsServerParam.getIsCenter());
                array.add(jsonObject);
            }
        }
        js.put("data",array);
        return js;
    }

    /**
     * 保存服务器信息
     * @param utcsServerParam
     * @return
     */
    @Override
    public boolean saveOrUpdateServerParam(UtcsServerParam utcsServerParam) {
        UtcsServerParam serverParam = new UtcsServerParam();
        if(utcsServerParam.getServerId()!=null){
            serverParam = findServerParamById(utcsServerParam.getServerId());
        }
        serverParam.setServerNo(utcsServerParam.getServerNo());
        serverParam.setServerIP(utcsServerParam.getServerIP());
        serverParam.setIsCenter(utcsServerParam.getIsCenter());
        boolean isSuccess = commonDAO.saveOrUpdate(serverParam);
        return isSuccess;
    }

    /**
     * 删除服务器
     * @param ids
     * @return
     */
    @Override
    public boolean delServers(Integer[] ids) {
        boolean isSuccess = false;
        if(ids!=null && ids.length>0){
            for(Integer serverId:ids){
                isSuccess = commonDAO.deleteByHql("delete UtcsServerParam t where t.serverId="+serverId);
            }
        }
        return isSuccess;
    }

    /**
     * 根据ID查找服务器信息
     * @param serverId
     * @return
     */
    @Override
    public UtcsServerParam findServerParamById(Integer serverId) {
        UtcsServerParam serverParam = (UtcsServerParam) commonDAO.findObjectById(UtcsServerParam.class,serverId);
        return serverParam;
    }


    /**
     * 检查服务器编号是否存在
     * @param serverId
     * @param serverNo
     * @return
     */
    @Override
    public boolean isExistServerNo(Integer serverId, String serverNo) {
        boolean isExist = true;
        UtcsServerParam serverParam = (UtcsServerParam)commonDAO.findByHql("from UtcsServerParam t where t.serverNo='"+serverNo+"'");
        if(serverId!=null){
            if(serverParam!=null && !serverId.equals(serverParam.getServerId())){
                isExist = false;
            }
        }else{
            if(serverParam!=null){
                isExist = false;
            }
        }
        return isExist;
    }

    /**
     * 检查中心服务器是否存在
     * @return false表示存在，true表示不存在
     */
    @Override
    public boolean isExistCenterServer(Integer serverId) {
        boolean isExist = true;
        UtcsServerParam serverParam = (UtcsServerParam)commonDAO.findByHql("from UtcsServerParam t where t.isCenter=0");
        if(serverId!=null){
            if(serverParam!=null && !serverId.equals(serverParam.getServerId())){
                isExist = false;
            }
        }else{
            if(serverParam!=null){
                isExist = false;
            }
        }
        return isExist;
    }
}
