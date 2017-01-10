package com.ncjk.utcs.modules.system.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.system.pojo.UtcsSystemParam;
import com.ncjk.utcs.modules.system.services.interfaces.ISystemParaService;


import net.sf.json.JSONObject;

/** 
* @ClassName: SystemParaService 
* @Description: 系统参数业务实现类
* @author 石望来
* @date 2016年8月26日 下午4:21:06 
* 
* tags 
*/
@Service("systemPararService")
public class SystemParaService implements ISystemParaService{
	@Resource
	private ICommonDAO commonDAO;
	
	/**  
	* @Description: 查找参数表的第一条数据
	* @param     
	* @return UtcsSystemParam   
	* @author shiwanglai
	* @date 2016年8月26日 下午4:37:34  
	*/
	public UtcsSystemParam findSystemParam(){
		UtcsSystemParam systemParam = new UtcsSystemParam();
		String hql = "from UtcsSystemParam t where 1=1";
		@SuppressWarnings("unchecked")
		List<UtcsSystemParam> systemParams = (List<UtcsSystemParam>)commonDAO.findByHql(hql, 0, 0);
		if(systemParams!=null && !systemParams.isEmpty()){
			 systemParam = systemParams.get(0);
		}
		return systemParam;
	}
	/**  
	* @Description: 保存参数
	* @param utcsSystemParam
	* @return boolean
	* @author shiwanglai
	* @date 2016年8月29日 上午8:49:51  
	*/
	public boolean saveParam(UtcsSystemParam utcsSystemParam){
		utcsSystemParam.setLastUpdateTime(new Date());
		boolean isSave = commonDAO.saveOrUpdate(utcsSystemParam);
		return isSave;
	}
}
