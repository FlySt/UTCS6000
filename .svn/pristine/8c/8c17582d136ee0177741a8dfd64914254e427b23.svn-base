package com.ncjk.utcs.modules.resources.resources.services;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.common.netty.server.NettyChannelParam;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsCrossParam;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignal;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ICrossParamService;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalControlerService;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalControler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 信号机业务实现类
 * Created by 石望来 on 2016/9/7.
 */
@Service("signalControlerService")
public class SignalControlerService implements ISignalControlerService {

    @Resource
    private ICommonDAO commonDAO;
    @Resource
    private ICrossParamService crossParamService;
    @Resource
    private SignalDeviceService signalDeviceService;
    /**
     * 查找所有信号机
     *@return  JSONObject
     */
    @Override
    public JSONObject findSignalControlers(Integer signalControlerId,String signalControlerName){
        JSONObject js = new JSONObject();
        JSONArray array = new JSONArray();
        StringBuffer condition = new StringBuffer();
        String hql = "from UtcsSignalControler t where 1=1";
        if(signalControlerId!=null&&!"".equals(signalControlerId)){
            condition.append(" and t.signalControlerId="+signalControlerId);
        }
        if(signalControlerName!=null&&!"".equals(signalControlerName)){
            condition.append(" and t.signalControlerName like '%"+signalControlerName+"%'");
        }
        List<UtcsSignalControler> utcsSignalControlers = commonDAO.findByHql(hql+condition.toString(),0,0);
        if(utcsSignalControlers!=null && !utcsSignalControlers.isEmpty()){
            for(UtcsSignalControler utcsSignalControler:utcsSignalControlers){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("signalControlerId",utcsSignalControler.getSignalControlerId());
                jsonObject.put("signalControlerName",utcsSignalControler.getSignalControlerName());
                jsonObject.put("signalControlerNum",utcsSignalControler.getSignalControlerNum());
                jsonObject.put("serverNo",utcsSignalControler.getServerParam().getServerNo());
                jsonObject.put("crossName",utcsSignalControler.getCrossParam().getCrossName());
                jsonObject.put("crossId",utcsSignalControler.getCrossParam().getCrossId());
                jsonObject.put("signalType",utcsSignalControler.getSignalType());
                if(utcsSignalControler.getSupplier()==0){
                    jsonObject.put("supplier","南昌金科");
                    Integer type = utcsSignalControler.getType();
                    switch(type/10){
                        case 1:
                            jsonObject.put("type","JK-C3");
                            break;
                        case 2:
                            jsonObject.put("type","JK-C6");
                            break;
                        case 3:
                            jsonObject.put("type","JK-D3");
                            break;
                        case 4:
                            jsonObject.put("type","JK-D6");
                            break;
                        case 5:
                            jsonObject.put("type","JK-E3");
                            break;
                        case 6:
                            jsonObject.put("type","JK-XT-100");
                            break;
                        default:
                            break;
                    }
                    jsonObject.put("protocolNum",utcsSignalControler.getProtocolNum());
                }else{
                    jsonObject.put("supplier","-");
                    jsonObject.put("type","-");
                    jsonObject.put("protocolNum","-");
                }

                jsonObject.put("deviceIp",utcsSignalControler.getDeviceIp());
                Integer signalStatus = NettyChannelParam.signalStatus.get(utcsSignalControler.getDeviceIp());
                if(signalStatus!=null && signalStatus==0){
                    jsonObject.put("signalState",signalStatus);
                }else{
                    jsonObject.put("signalState",2);
                }
  /*              switch (utcsSignalControler.getSignalState()){
                    case 0:
                        jsonObject.put("signalState","正常在线");
                        break;
                    case 2:
                        jsonObject.put("signalState","脱机/断线");
                        break;
                    case 4:
                        jsonObject.put("signalState","异常故障");
                        break;
                }*/
                switch(utcsSignalControler.getErrorId()){
                    case 0:
                        jsonObject.put("error","无错误");
                        break;
                    case 1:
                        jsonObject.put("error","灯输出故障");
                        break;
                    case 2:
                        jsonObject.put("error","电源故障");
                        break;
                    case 3:
                        jsonObject.put("error","时钟故障");
                        break;
                    case 4:
                        jsonObject.put("error","运行故障");
                        break;
                    case 5:
                        jsonObject.put("error","方案故障");
                        break;
                    case 9:
                        jsonObject.put("error","其他错误");
                        break;
                }
                jsonObject.put("longitude",utcsSignalControler.getLongitude());
                jsonObject.put("latitude",utcsSignalControler.getLatitude());
                jsonObject.put("mapSign",utcsSignalControler.getMapSign());
                array.add(jsonObject);
            }
        }
        js.put("data",array);
        return js;
    }

    /**
     * 添加或修改信号机信息
     * @param utcsSignalControler
     * @author swl
     * @return
     */
    @Override
    public boolean saveOrUpdateSignalControler(UtcsSignalControler utcsSignalControler){
        UtcsSignalControler signalControler = new UtcsSignalControler();
        if(utcsSignalControler.getSignalControlerId()==null){
            signalControler.setSignalState(0);
            signalControler.setErrorId(0);
            signalControler.setMapSign(0);
            signalControler.setUseStatus(0);
            signalControler.setBackgroundMapWidth(0);
            signalControler.setBackgroundMapHeight(0);
            signalControler.setTrafficpicSet("");
            signalControler.setSpecialLightName("");
            signalControler.setLightSet("");
            signalControler.setDevicePort(9000);
        }else{
            signalControler =findSignalControlerById(utcsSignalControler.getSignalControlerId());
        }
        signalControler.setCrossParam(utcsSignalControler.getCrossParam());
        signalControler.setServerParam(utcsSignalControler.getServerParam());
        signalControler.setSignalControlerNum(utcsSignalControler.getSignalControlerNum());
        signalControler.setSignalControlerName(utcsSignalControler.getSignalControlerName());
        signalControler.setSupplier(utcsSignalControler.getSupplier());
        Integer type = utcsSignalControler.getType()+Integer.valueOf(utcsSignalControler.getProtocolNum());
        signalControler.setType(type);
        signalControler.setProtocolNum(utcsSignalControler.getProtocolNum());
        signalControler.setDeviceIp(utcsSignalControler.getDeviceIp());
        signalControler.setSignalType(utcsSignalControler.getSignalType());

        boolean  isSuccess = commonDAO.saveOrUpdate(signalControler);

        return  isSuccess;
    }

    /**
     * 删除信号机
     * @param ids
     * @return
     */
    @Override
    public boolean delSignalControlers(Integer[] ids) {
        boolean isSuccess = false;
        if(ids!=null && ids.length>0){
            for(Integer signalControlerId:ids){
                isSuccess = commonDAO.deleteByHql("delete UtcsSignalControler t where t.signalControlerId="+signalControlerId);
            }
        }
        return isSuccess;
    }

    /**
     * 检查信号机名称是否存在
     * @param signalControlerId
     * @param signalControlerName
     * @return
     */
    @Override
    public boolean isExistSignalControlerName(Integer signalControlerId, String signalControlerName) {
        boolean isExist = true;
        UtcsSignalControler signalControler = (UtcsSignalControler)commonDAO.findByHql("from UtcsSignalControler t where t.signalControlerName='"+signalControlerName+"'");
        if(signalControlerId!=null){
            if(signalControler!=null && !signalControlerId.equals(signalControler.getSignalControlerId())){
                isExist = false;
            }
        }else{
            if(signalControler!=null){
                isExist = false;
            }
        }
        return isExist;
    }

    /**
     * 检查信号机编号是否存在
     * @param signalControlerId
     * @param signalControlerNum
     * @return
     */
    @Override
    public boolean isExistSignalControlerNum(Integer signalControlerId, String signalControlerNum) {
        boolean isExist = true;
        UtcsSignalControler signalControler = (UtcsSignalControler)commonDAO.findByHql("from UtcsSignalControler t where t.signalControlerNum='"+signalControlerNum+"'");
        if(signalControlerId!=null){
            if((signalControler != null) && !signalControlerId.equals(signalControler.getSignalControlerId())){
                isExist = false;
            }
        }else{
            if(signalControler!=null){
                isExist = false;
            }
        }
        return isExist;
    }

    /**
     * 根据信号机ID查询
     * @param signalControlerId
     * @return
     */
    @Override
    public UtcsSignalControler findSignalControlerById(Integer signalControlerId){
        UtcsSignalControler utcsSignalControler = (UtcsSignalControler)commonDAO.findObjectById(UtcsSignalControler.class,signalControlerId);
        return utcsSignalControler;
    }

    /**
     * 根据路口ID查看该路口是否已有信号机
     * @param crossId
     * @return
     */
    @Override
    public boolean isCrossExistSignalControler(Integer crossId,Integer signalControlerId) {
        String hql = "from UtcsSignalControler t where t.crossParam.crossId="+crossId;
        UtcsSignalControler utcsSignalControler = (UtcsSignalControler)commonDAO.findByHql(hql);
        if(utcsSignalControler!=null){
            if(signalControlerId!=null && signalControlerId.equals(utcsSignalControler.getSignalControlerId())){
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    /**
     * 根据信号机编号查询IP地址
     * @param signalControlerNum
     * @return
     */
    @Override
    public String findSignalControlerIpByNum(String signalControlerNum){
        String hql = "select deviceIp from  UtcsSignalControler t where t.signalControlerNum='"+signalControlerNum+"'";
        String deviceIp = (String)commonDAO.findByHql(hql);
        return deviceIp;
    }

    /**
     * 根据信号机ip查询信号机编号
     * @param deviceIp
     * @return
     */
    @Override
    public String findSignalControlerNumByIp(String deviceIp){
        String hql = "select signalControlerNum from  UtcsSignalControler t where t.deviceIp='"+deviceIp+"'";
        String signalControlerNum = (String)commonDAO.findByHql(hql);
        return signalControlerNum;
    }
    /**
     * 创建信号机树
     * @param type 0-地图上没有标记的信号机 1-地图上已有的信号机 2-所有信号机
     * @return
     */
    @Override
    public JSONArray buildSignalControlerTrees(int type){
        JSONArray array = new JSONArray();
        String hql = "from UtcsSignalControler t where 1=1";
        List<UtcsSignalControler> utcsSignalControlers = commonDAO.findByHql(hql,0,0);
        if(utcsSignalControlers!=null && !utcsSignalControlers.isEmpty()){
            for(UtcsSignalControler utcsSignalControler:utcsSignalControlers){
                JSONObject js = new JSONObject();
                if(type==0 && utcsSignalControler.getMapSign()!=1){
                    js.put("id","signal_"+utcsSignalControler.getSignalControlerId());
                    js.put("pId","-1");
                    js.put("name",utcsSignalControler.getSignalControlerName());
                    array.add(js);
                }else  if(type==1 && utcsSignalControler.getMapSign()==1){
                        Integer parentId = utcsSignalControler.getCrossParam().getCrossId();
                     //  Integer parentId = 1;
                    //已在地图上标注
                        js.put("id","signal_"+utcsSignalControler.getSignalControlerId());
                        js.put("pId","cross_"+parentId);
                        js.put("name",utcsSignalControler.getSignalControlerName());
                        js.put("lon",utcsSignalControler.getLongitude());
                        js.put("lat",utcsSignalControler.getLatitude());
                        array.add(js);
                }else if(type==2){
                    Integer parentId = utcsSignalControler.getCrossParam().getCrossId();
                    //  Integer parentId = 1;
                    //已在地图上标注
                    js.put("id","signal_"+utcsSignalControler.getSignalControlerId());
                    js.put("pId","cross_"+parentId);
                    js.put("name",utcsSignalControler.getSignalControlerName());
                    js.put("lon",utcsSignalControler.getLongitude());
                    js.put("lat",utcsSignalControler.getLatitude());
                    js.put("signalType",utcsSignalControler.getSignalType());
                    js.put("signalControlerNum",utcsSignalControler.getSignalControlerNum());
                    js.put("typeS",utcsSignalControler.getType());
                    Integer singnalState = NettyChannelParam.signalStatus.get(utcsSignalControler.getDeviceIp());
                    if(singnalState!=null && singnalState==0){
                        js.put("signalState",singnalState);
                    }else{
                        js.put("signalState",2);
                    }
                    array.add(js);
                }
            }
        }
        return array;
    }

    /**
     * 改变信号机状态 信号机状态（0-正常在线 2-脱机/断线 4-异常故障）
     * @param state
     * @param deviceIp
     */
    @Override
    public void setSignalControlerState(String deviceIp,Integer state) {
        String hql = "from  UtcsSignalControler t where t.deviceIp='"+deviceIp+"'";
        UtcsSignalControler utcsSignalControler = (UtcsSignalControler)commonDAO.findByHql(hql);
        if(utcsSignalControler!=null){
            utcsSignalControler.setSignalState(state);
            commonDAO.saveOrUpdate(utcsSignalControler);
        }

    }

    /**
     * 改变所有信号机的状态为断线
     */
    @Override
    public void setAllSignalControlerBreak() {
        String hql = "from  UtcsSignalControler t where 1=1";
        List<UtcsSignalControler> utcsSignalControlers = (List<UtcsSignalControler>)commonDAO.findByHql(hql,0,0);
        if(utcsSignalControlers!=null && !utcsSignalControlers.isEmpty()){
            for(UtcsSignalControler utcsSignalControler:utcsSignalControlers){
                utcsSignalControler.setSignalState(2);
                commonDAO.saveOrUpdate(utcsSignalControler);
            }
        }
    }


}
