package com.ncjk.utcs.modules.resources.resources.services;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsCrossParam;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ICrossParamService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 路口业务逻辑实现类
 * Created by swl on 2016/9/19.
 */
@Service("crossParamService")
public class CrossParamService implements ICrossParamService{
    @Resource
    ICommonDAO commonDAO;
    /**
     * 查询所有路口信息
     * @param crossId
     * @param crossName
     * @return
     */
    @Override
    public JSONObject findCrossParams(Integer crossId, String crossName) {
        JSONObject js = new JSONObject();
        JSONArray array = new JSONArray();
        String hql = "from UtcsCrossParam t where 1=1";
        StringBuffer condition = new StringBuffer("");
        if(crossId!=null && !"".equals(crossId)){
            condition.append(" and t.crossId="+crossId);
        }
        if(crossName!=null && !"".equals(crossName)){
            condition.append(" and t.crossName like '%"+crossName+"%'");
        }
        List<UtcsCrossParam> crossParams = ( List<UtcsCrossParam>)commonDAO.findByHql(hql+condition.toString(),0,0);
        if(crossParams!=null && !crossParams.isEmpty()){
            for(UtcsCrossParam crossParam:crossParams){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("crossId",crossParam.getCrossId());
                jsonObject.put("crossNum",crossParam.getCrossNum());
                jsonObject.put("crossName",crossParam.getCrossName());
                jsonObject.put("regionName",crossParam.getRegionParam().getRegionName());
                switch (crossParam.getFeature()){
                    case 00:
                        jsonObject.put("feature","环形交叉路口");
                        break;
                    case 11:
                        jsonObject.put("feature","匝道/出入口");
                        break;
                    case 21:
                        jsonObject.put("feature","路段<只有两个方向的路口");
                        break;
                    case 31:
                        jsonObject.put("feature","T形路口");
                        break;
                    case 32:
                        jsonObject.put("feature","Y形路口");
                        break;
                    case 33:
                        jsonObject.put("feature","错位T形路口");
                        break;
                    case 34:
                        jsonObject.put("feature","错位Y形路口");
                        break;
                    case 41:
                        jsonObject.put("feature","十字形路口");
                        break;
                    case 42:
                        jsonObject.put("feature","斜交路口");
                        break;
                    case 51:
                        jsonObject.put("feature","多路路口");
                        break;
                    case 99:
                        jsonObject.put("feature","其他");
                        break;
                    default:
                        break;
                }
                if(crossParam.getIsKey()==1){
                    jsonObject.put("isKey","关键路口");
                }else{
                    jsonObject.put("isKey","非关键路口");
                }
                if(crossParam.getCrossType()==0){
                    jsonObject.put("crossType","一般路口");
                }else{
                    jsonObject.put("crossType","特殊路口");
                }

                if(crossParam.getCrossState()==0){
                    jsonObject.put("crossState","正常在线");
                }else if(crossParam.getCrossState()==2){
                    jsonObject.put("crossState","脱机/断线");
                }else{
                    jsonObject.put("crossState","异常故障");
                }
                jsonObject.put("crossDesc",crossParam.getCrossDesc());
                array.add(jsonObject);
            }
        }
        js.put("data",array);
        return js;
    }
    /**
     * 根据路口ID查询路口信息
     * @param crossId
     * @return
     */
    @Override
    public UtcsCrossParam findCrossParamById(Integer crossId) {
        UtcsCrossParam crossParam = (UtcsCrossParam)commonDAO.findObjectById(UtcsCrossParam.class,crossId);
        return crossParam;
    }

/*    *//**
     * 根据信号机ID查询路口信息
     * @param signalControlerId
     * @return
     *//*
    @Override
    public UtcsCrossParam findCrossParamBySignalControlerId(Integer signalControlerId) {
        String hql = "from UtcsCrossParam t where t.signalControler.signalControlerId ="+signalControlerId;
        UtcsCrossParam crossParam = (UtcsCrossParam)commonDAO.findByHql(hql);
        return crossParam;
    }*/

    /**
     * 保存路口信息
     * @param crossParam
     * @return
     */
    @Override
    public boolean saveOrUpdateCrossParam(UtcsCrossParam crossParam) {
        boolean isSuccess;
        UtcsCrossParam cross = new UtcsCrossParam();
        if(crossParam.getCrossId()==null){
            cross.setCrossState(0);
        }else{
            cross = findCrossParamById(crossParam.getCrossId());
        }
        cross.setCrossName(crossParam.getCrossName());
        cross.setCrossNum(crossParam.getCrossNum());
        cross.setRegionParam(crossParam.getRegionParam());
        cross.setFeature(crossParam.getFeature());
        cross.setIsKey(crossParam.getIsKey());
        cross.setCrossType(crossParam.getCrossType());
        cross.setCrossDesc(crossParam.getCrossDesc());
        isSuccess = commonDAO.saveOrUpdate(cross);
        return isSuccess;
    }

    /**
     * 删除路口信息
     * @param ids
     * @return
     */
    @Override
    public boolean delCrossParams(Integer[] ids) {
        boolean isSuccess = false;
        if(ids!=null && ids.length>0){
            for(Integer crossId:ids){
                isSuccess = commonDAO.deleteByHql("delete UtcsCrossParam t where t.crossId="+crossId);
            }
        }
        return isSuccess;
    }

    /**
     * 检查路口名称是否存在
     * @param crossId
     * @param crossName
     * @return
     */
    @Override
    public boolean isExistCrossName(Integer crossId, String crossName) {
        boolean isExist = true;
        UtcsCrossParam crossParam = (UtcsCrossParam)commonDAO.findByHql("from UtcsCrossParam t where t.crossName='"+crossName+"'");
        if(crossId!=null){
            if(crossParam!=null && !crossId.equals(crossParam.getCrossId())){
                isExist = false;
            }
        }else{
            if(crossParam!=null){
                isExist = false;
            }
        }
        return isExist;
    }

    /**
     * 检查路口编号是否存在
     *
     * @param crossId
     * @param crossNum
     * @return
     */
    @Override
    public boolean isExistCrossNum(Integer crossId, String crossNum) {
        boolean isExist = true;
        UtcsCrossParam crossParam = (UtcsCrossParam)commonDAO.findByHql("from UtcsCrossParam t where t.crossNum='"+crossNum+"'");
        if(crossId!=null){
            if((crossParam != null) && !crossId.equals(crossParam.getCrossId())){
                isExist = false;
            }
        }else{
            if(crossParam!=null){
                isExist = false;
            }
        }
        return isExist;
    }

    /**
     * 创建路口树
     * @return
     */
    @Override
    public JSONArray buildCrossParamTrees() {
        JSONArray array = new JSONArray();
        List<UtcsCrossParam> crossParams = (List<UtcsCrossParam>)commonDAO.findByHql("from UtcsCrossParam t where t.crossState=0",0,0);
        if(crossParams!=null && !crossParams.isEmpty()){
            for(UtcsCrossParam crossParam:crossParams){
                Integer parentId = crossParam.getRegionParam().getRegionId();
                JSONObject js = new JSONObject();
                js.put("id","cross_"+crossParam.getCrossId());
                js.put("pId","region_"+parentId);
                js.put("name",crossParam.getCrossName());
                array.add(js);
            }
        }
        return array;
    }
}
