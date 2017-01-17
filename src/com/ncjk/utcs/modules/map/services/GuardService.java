package com.ncjk.utcs.modules.map.services;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.map.pojo.UtcsGuard;
import com.ncjk.utcs.modules.map.pojo.UtcsGuardSignal;
import com.ncjk.utcs.modules.map.services.interfaces.IGuardService;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalControler;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalControlerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by swl on 2016/12/13.
 * 警卫任务业务实现类
 */
@Service("guardService")
public class GuardService implements IGuardService{
    @Resource
    ICommonDAO commonDAO;
    @Resource
    ISignalControlerService signalControlerService;
    /**
     * 查找方案
     * @param guardId
     * @param guardName
     * @return
     */
    @Override
    public JSONObject queryGuards(Integer guardId, String guardName,Integer guardStatus) {
        JSONObject js = new JSONObject();
        JSONArray array = new JSONArray();
        String hql = "from UtcsGuard t where 1=1";
        StringBuffer condition = new StringBuffer("");
        if(guardId!=null){
            condition.append(" and t.guardId="+guardId);
        }
        if(guardName!=null && guardName.length()>0){
            condition.append(" and t.guardName='"+guardName+"'");
        }
        if(guardStatus!=null){
            condition.append(" and t.guardStatus="+guardStatus);
        }
        List<UtcsGuard> utcsGuardList = ( List<UtcsGuard>)commonDAO.findByHql(hql+condition.toString(),0,0);
        if(utcsGuardList!=null && !utcsGuardList.isEmpty()){
            for (UtcsGuard utcsGuard:utcsGuardList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("guardId",utcsGuard.getGuardId());
                jsonObject.put("guardName",utcsGuard.getGuardName());
                jsonObject.put("guardStatus",utcsGuard.getGuardStatus());
                jsonObject.put("points",utcsGuard.getPoints());
                array.add(jsonObject);
            }
        }
        js.put("data",array);
        return js;
    }


    /**
     * 保存或者更新方案
     * @param utcsGuard
     * @return
     */
    @Override
    public boolean saveOrUpdateGuard(UtcsGuard utcsGuard) {
        UtcsGuard guard = new UtcsGuard();
        if(utcsGuard.getGuardId()==null){
            guard.setGuardStatus(0);
        }else{
            guard = findGuardById(utcsGuard.getGuardId());
            guard.setGuardStatus(utcsGuard.getGuardStatus());
        }
        guard.setGuardName(utcsGuard.getGuardName());
        guard.setPoints(utcsGuard.getPoints());
        guard.setUtcsGuardSignalSet(utcsGuard.getUtcsGuardSignalSet());
        boolean isSuccess = commonDAO.saveOrUpdate(guard);
        return isSuccess;
    }

    /**
     * 保存或更新方案信号机表
     * @param utcsGuardSignalList
     * @return
     */
    @Override
    public boolean saveOrUpdateGuardSignal( List<UtcsGuardSignal> utcsGuardSignalList ,Integer guardId) {
        boolean isSuccess ;
        if(utcsGuardSignalList==null || utcsGuardSignalList.isEmpty()){
            return false;
        }
        if(guardId==null){
            return false;
        }
        UtcsGuard utcsGuard = findGuardById(guardId);
        for(UtcsGuardSignal utcsGuardSignal:utcsGuardSignalList){
            utcsGuard.getUtcsGuardSignalSet().add(utcsGuardSignal);
            utcsGuardSignal.setUtcsGuard(utcsGuard);
        }
        utcsGuard.setGuardStatus(1);
        isSuccess = commonDAO.saveOrUpdate(utcsGuard);

        UtcsGuard utcsGuard1 = findGuardById(guardId);
        Set<UtcsGuardSignal> utcsGuardSignalSet = utcsGuard1.getUtcsGuardSignalSet();
        System.out.println(utcsGuardSignalSet.size());
        return isSuccess;
    }

    /**
     * 保存方案信号机信息
     * @param utcsGuardSignal
     * @return
     */
    @Override
    public boolean saveGuardSignal(UtcsGuardSignal utcsGuardSignal) {
        boolean isSuccess = commonDAO.saveOrUpdate(utcsGuardSignal);
        return isSuccess;
    }

    /**
     * 根据ID查找方案
     * @param guardId
     * @return
     */
    @Override
    public UtcsGuard findGuardById(Integer guardId) {
        String hql = "from UtcsGuard t where t.guardId="+guardId;
        UtcsGuard utcsGuard = (UtcsGuard)commonDAO.findByHql(hql);
        return utcsGuard;
    }
    /**
     * 根据信号机ID查找方案信号机
     * @param signalControlerId
     * @return
     */
    @Override
    public UtcsGuardSignal findGuardSignalBySignalControlerId(Integer signalControlerId) {
        String hql = "from UtcsGuardSignal t where t.signalControlerId="+signalControlerId;
        UtcsGuardSignal utcsGuardSignal = (UtcsGuardSignal)commonDAO.findByHql(hql);
        return utcsGuardSignal;
    }

    /**
     * 很据ID查找
     * @param guardSignalId
     * @return
     */
    @Override
    public UtcsGuardSignal findGuardSignalById(Integer guardSignalId){
        String hql = "from UtcsGuardSignal t where t.guardSignalId="+guardSignalId;
        UtcsGuardSignal utcsGuardSignal = (UtcsGuardSignal)commonDAO.findByHql(hql);
        return utcsGuardSignal;
    }

    /**
     * 获取方案信号机
     * @param signalControlerId
     * @return
     */
    @Override
    public JSONObject getGuardSignal(Integer signalControlerId,Integer guardId) {
        JSONObject jsonObject = new JSONObject();
        UtcsGuardSignal utcsGuardSignal = findGuardSignalBySignalControlerId(signalControlerId);
        if(utcsGuardSignal!=null && guardId.equals(utcsGuardSignal.getUtcsGuard().getGuardId())){
            jsonObject.put("result",true);
            jsonObject.put("lastToTime",utcsGuardSignal.getLastToTime());
            jsonObject.put("passTime",utcsGuardSignal.getPassTime());
            jsonObject.put("direction",utcsGuardSignal.getDirection());
            jsonObject.put("guardSignalId",utcsGuardSignal.getGuardSignalId());
        }else{
            jsonObject.put("result",false);
        }
        return jsonObject;
    }

    /**
     * 根据方案ID查询方案信号机
     * @param guardId
     * @return
     */
    @Override
    public JSONObject getGuardSignalByGuardId(Integer guardId) {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        String hql = "from UtcsGuardSignal t where t.utcsGuard.guardId="+guardId;
        List<UtcsGuardSignal> utcsGuardSignalList = ( List<UtcsGuardSignal> )commonDAO.findByHql(hql,0,0);
        if(utcsGuardSignalList!=null && !utcsGuardSignalList.isEmpty()){
            for(UtcsGuardSignal guardSignal:utcsGuardSignalList){
                JSONObject js = new JSONObject();
                UtcsSignalControler utcsSignalControler = signalControlerService.findSignalControlerById(guardSignal.getSignalControlerId());
                js.put("lastToTime",guardSignal.getLastToTime());
                js.put("passTime",guardSignal.getPassTime());
                js.put("direction",guardSignal.getDirection());
                js.put("guardSignalId",guardSignal.getGuardSignalId());
                js.put("guardIndex",guardSignal.getGuardIndex());
                js.put("signalControlerName",utcsSignalControler.getSignalControlerName());
                array.add(js);
            }
            object.put("data",array);
        }
        return object;
    }

    /**
     * 根据方案ID获取信号机信息
     * @param guardId
     * @return
     */
    @Override
    public JSONArray getSignalControlers(Integer guardId) {
        JSONArray array = new JSONArray();
        UtcsGuard utcsGuard = findGuardById(guardId);
        Set<UtcsGuardSignal> utcsGuardSignals = utcsGuard.getUtcsGuardSignalSet();
        if(utcsGuardSignals!=null && !utcsGuardSignals.isEmpty()){
            for(UtcsGuardSignal utcsGuardSignal:utcsGuardSignals){
                //// TODO: 2016/12/15 后续可能需要区分国标信号机和非国标信号机
                UtcsSignalControler utcsSignalControler = signalControlerService.findSignalControlerById(utcsGuardSignal.getSignalControlerId());
                JSONObject js = new JSONObject();
                js.put("id",utcsSignalControler.getSignalControlerId());
                js.put("name",utcsSignalControler.getSignalControlerName());
                js.put("type","signal");
                js.put("lon",utcsSignalControler.getLongitude());
                js.put("lat",utcsSignalControler.getLatitude());
                array.add(js);
            }
        }
        return array;
    }

    /**
     * 删除方案信号机
     * @param guardSignalId
     * @return
     */
    @Override
    public boolean deleteGuardSignal(Integer guardSignalId) {
        String hql = "delete UtcsGuardSignal t where t.guardSignalId="+guardSignalId;
        UtcsGuardSignal guardSignal = findGuardSignalById(guardSignalId);
        UtcsGuard utcsGuard = guardSignal.getUtcsGuard();
        if(utcsGuard.getUtcsGuardSignalSet().size()==1){
            utcsGuard.setGuardStatus(0);
            commonDAO.saveOrUpdate(utcsGuard);
        }
        boolean isSuccess = commonDAO.deleteByHql(hql);
        return isSuccess;
    }

    /**
     * 删除方案
     * @param guardIds
     * @return
     */
    @Override
    public boolean deleteGuard(Integer[] guardIds) {
        boolean isSuccess = false;
        if(guardIds!=null && guardIds.length>0){
            for(Integer id:guardIds){
                UtcsGuard utcsGuard = findGuardById(id);
                if(utcsGuard.getGuardStatus()!=null && utcsGuard.getGuardStatus()==1){//已审核
                    Set<UtcsGuardSignal> utcsGuardSignals = utcsGuard.getUtcsGuardSignalSet();
                    if(utcsGuardSignals!=null && !utcsGuardSignals.isEmpty()){
                        for(UtcsGuardSignal utcsGuardSignal:utcsGuardSignals){
                            deleteGuardSignal(utcsGuardSignal.getGuardSignalId());
                        }
                    }
                }
                String hql = "delete UtcsGuard t where t.guardId="+id;
                isSuccess = commonDAO.deleteByHql(hql);
            }
        }
        return isSuccess;
    }
}
