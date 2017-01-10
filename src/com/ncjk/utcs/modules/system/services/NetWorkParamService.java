package com.ncjk.utcs.modules.system.services;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.system.pojo.UtcsNetWorkParam;
import com.ncjk.utcs.modules.system.pojo.UtcsSystemParam;
import com.ncjk.utcs.modules.system.services.interfaces.INetWorkParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by swl on 2016/11/29.
 * 网络参数业务实现类
 */
@Service("netWorkParamService")
public class NetWorkParamService implements INetWorkParamService{
    @Resource
    ICommonDAO commonDAO;
    /**
     * 查找网络参数表第一条数据
     * @return
     */
    @Override
    public UtcsNetWorkParam findNetWorkParam() {
        UtcsNetWorkParam netParam = new UtcsNetWorkParam();
        String hql = "from UtcsNetWorkParam t where 1=1";
        @SuppressWarnings("unchecked")
        List<UtcsNetWorkParam> netParams = (List<UtcsNetWorkParam>)commonDAO.findByHql(hql, 0, 0);
        if(netParams!=null && !netParams.isEmpty()){
            netParam = netParams.get(0);
        }
        return netParam;
    }

    /**
     * 保存网络参数
     * @param utcsNetWorkParam
     * @return
     */
    @Override
    public boolean saveParam(UtcsNetWorkParam utcsNetWorkParam) {
        boolean isSuccess = commonDAO.saveOrUpdate(utcsNetWorkParam);
        return isSuccess;
    }

    /**
     * 获取协议端口号
     * @param type  0-1049协议  1-内部协议
     * @return
     */
    @Override
    public Integer getPort(int type){
        Integer port ;
        if(type==0){
            port = findNetWorkParam().getGat1049Port();
        }else{
            port = findNetWorkParam().getInsidePort();
        }
        return port;
    }

    /**
     * 获取有效心跳次数
     * @return
     */
    @Override
    public Integer getHjNum() {
        return findNetWorkParam().getInsideHJNum();
    }

    /**
     * 获取心跳步长
     */
    @Override
    public Integer getHjStep() {
        return findNetWorkParam().getInsideHJStep();
    }

    /**
     * 获取内部协议最大连接数
     *
     * @return
     */
    @Override
    public Integer getInsideConNum() {
        return findNetWorkParam().getInsideConNum();
    }

    /**
     * 获取1049协议最大连接数
     */
    @Override
    public Integer getGat1049ConNum() {
        return findNetWorkParam().getGat1049ConNum();
    }
}
