package com.ncjk.utcs.modules.resources.resources.services;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsDept;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IDeptService;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsRegionParam;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.IRegionParamService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 区域业务逻辑实现类
 * Created by swl on 2016/9/19.
 */
@Service("regionParamService")
public class RegionParamService implements IRegionParamService{
    @Resource
    ICommonDAO commonDAO;
    @Resource
    IDeptService deptService;
    /**
     * 查找所有区域信息
     * @param regionId
     * @param regionName
     * @return
     */
    @Override
    public JSONObject findRegionParams(Integer regionId, String regionName) {
        JSONObject js = new JSONObject();
        JSONArray array = new JSONArray();
        String hql = "from UtcsRegionParam t where 1=1";
        StringBuffer condition = new StringBuffer("");
        if(regionId!=null && !"".equals(regionId)){
            condition.append(" and t.regionId="+regionId);
        }
        if(regionName!=null && !"".equals(regionName)){
            condition.append(" and t.regionName like '%"+regionName+"%'");
        }
        List<UtcsRegionParam> regionParams = (List<UtcsRegionParam>)commonDAO.findByHql(hql+condition.toString(),0,0);
        if(regionParams!=null && !regionParams.isEmpty()){
            for(UtcsRegionParam regionParam:regionParams){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("regionId",regionParam.getRegionId());
                jsonObject.put("regionNum",regionParam.getRegionNum());
                jsonObject.put("regionName",regionParam.getRegionName());
                if(regionParam.getRegionType()==0){
                    jsonObject.put("regionType","区域");
                    jsonObject.put("fatherRegionName","-");
                }else{
                    jsonObject.put("regionType","子区");
                    UtcsRegionParam fatherRegionParam = findRegionParamById(regionParam.getFatherRegionId());
                    jsonObject.put("fatherRegionName",fatherRegionParam.getRegionName());
                }
                switch (regionParam.getRegionState()){
                    case 0:
                        jsonObject.put("regionState","正常在线");
                        break;
                    case 2:
                        jsonObject.put("regionState","脱机/断线");
                        break;
                    case 4:
                        jsonObject.put("regionState","异常故障");
                        break;
                    default:
                        break;
                }
                jsonObject.put("regionDesc",regionParam.getRegionDesc());
                array.add(jsonObject);
            }
        }
        js.put("data",array);
        return js;
    }

    /**
     * 保存或更改区域信息
     * @param regionParam
     * @return
     */
    @Override
    public boolean saveOrUpdateReginParam(UtcsRegionParam regionParam) {
        boolean isSuccess ;
        UtcsRegionParam region = new UtcsRegionParam();
        if(regionParam.getRegionId()==null){
            region.setRegionState(0);
        }else{
            region = findRegionParamById(regionParam.getRegionId());
        }
        region.setRegionNum(regionParam.getRegionNum());
        region.setRegionName(regionParam.getRegionName());
        region.setRegionType(regionParam.getRegionType());
        region.setFatherRegionId(regionParam.getFatherRegionId());
        region.setRegionDesc(regionParam.getRegionDesc());
        isSuccess = commonDAO.saveOrUpdate(region);
        return isSuccess;
    }

    /**
     * 删除区域信息
     * @param ids
     * @return
     */
    @Override
    public boolean delRegionParams(Integer[] ids) {
        boolean isSuccess = false;
        if(ids!=null && ids.length>0){
            for(Integer regionId:ids){
                UtcsRegionParam regionParam = (UtcsRegionParam)commonDAO.findObjectById(UtcsRegionParam.class,regionId);
                isSuccess = commonDAO.deleteByHql("delete UtcsRegionParam t where t.regionId="+regionId);
                if(isSuccess){
                    if(regionParam.getFatherRegionId()==-1){
                        commonDAO.updateByHql(" update UtcsRegionParam t set t.regionType=0"
                                + " where t.fatherRegionId = " + regionId);
                    }
                    commonDAO.updateByHql(" update UtcsRegionParam t set t.fatherRegionId=" + regionParam.getFatherRegionId()
                            + " where t.fatherRegionId = " + regionId);
                }
            }
        }
        return isSuccess;
    }

    /**
     * 根据区域ID查找区域信息
     * @param regionId
     * @return
     */
    @Override
    public UtcsRegionParam findRegionParamById(Integer regionId) {
        UtcsRegionParam regionParam = (UtcsRegionParam)commonDAO.findObjectById(UtcsRegionParam.class,regionId);
        return  regionParam;
    }

    /**
     * 创建区域树
     * @return
     */
    @Override
    public JSONArray buildRegionParamTrees() {
        JSONArray array = new JSONArray();
        List<UtcsRegionParam> regionParams = (List<UtcsRegionParam>)commonDAO.findByHql("from UtcsRegionParam t where 1=1",0,0);
        if(regionParams!=null && !regionParams.isEmpty()){
            for (UtcsRegionParam regionParam:regionParams){
                JSONObject js = new JSONObject();
                Integer parentId = regionParam.getFatherRegionId();
                if(regionParam.getRegionType()==0){
                    parentId = -1;
                }
                js.put("id", "region_" + regionParam.getRegionId());
                js.put("pId", "region_" + parentId);
                js.put("name", regionParam.getRegionName());
                array.add(js);
            }
        }
        return array;
    }

    /**
     * 检查区域名称是否存在
     * @param  regionId
     * @param regionName
     * @return
     */
    @Override
    public boolean isExistRegionName(Integer regionId,String regionName) {
        boolean isExist = true;
        UtcsRegionParam regionParam = (UtcsRegionParam)commonDAO.findByHql("from UtcsRegionParam t where t.regionName='"+regionName+"'");
        if(regionId!=null){
            if(regionParam!=null && !regionId.equals(regionParam.getRegionId())){
                isExist = false;
            }
        }else{
            if(regionParam!=null){
                isExist = false;
            }
        }
        return isExist;
    }
}
