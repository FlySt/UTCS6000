package com.ncjk.utcs.modules.resources.basic.services;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsModule;

import com.ncjk.utcs.modules.resources.basic.services.interfaces.IModuleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
* @ClassName: ModuleService 
* @Description: 模块业务实现类 
* @author 石望来
* @date 2016年8月19日 上午11:08:52 
* 
* tags 
*/
@Scope("prototype")
@Component("moduleService")
public class ModuleService implements IModuleService{
	
	@Resource(name="commonDAO")
	private ICommonDAO comDAO;
	/**
	* @Description: 根据ID查询模块信息
	* @param tags    
	* @return UtcsModule   
	* @author shiwanglai
	* @date 2016年8月19日 上午11:10:37  
	*/
	public UtcsModule findModuleById(int moduleId) {
		UtcsModule module = (UtcsModule) comDAO.findObjectById(UtcsModule.class, moduleId);
		return module;
	}
	/**
	 * 分页查询用户信息
	 */

	public List<UtcsModule> findModulesByHql(String hql, int page, int pageSize) {
		@SuppressWarnings("unchecked")
		List<UtcsModule> utcsModules = (List<UtcsModule>) comDAO.findByHql(hql, page, pageSize);
		return utcsModules;
	}
	/**  
	* @Description: 获取模块树
	* @param tags    
	* @return JSONArray   
	* @author shiwanglai
	* @date 2016年8月22日 上午8:35:57  
	*/
	public JSONArray buildModuleTrees(){
		JSONArray array = new JSONArray();
		String hql = "from UtcsModule t where 1=1";
		List<UtcsModule> modules = findModulesByHql(hql,0,0);
		if (modules != null && !modules.isEmpty()) {
			for(UtcsModule module:modules){
				JSONObject js = new JSONObject();
				JSONArray powers = new JSONArray();
				js.put("id", "module_"+module.getModuleId());
				js.put("pId", "module_"+module.getParentModuleId());
				js.put("name", module.getModuleName());
				powers.add(js);
				String power = module.getModulePower();
				if(!power.equals("00000000")){
					if(power.charAt(0)=='1'){
						JSONObject add = new JSONObject();
						add.put("id", "watch_"+module.getModuleId());
						add.put("pId", "module_"+module.getModuleId());
						add.put("name", "查看");
						powers.add(add);
					}
					if(power.charAt(1)=='1'){
						JSONObject watch = new JSONObject();
						watch.put("id", "add_"+module.getModuleId());
						watch.put("pId", "module_"+module.getModuleId());
						watch.put("name", "增加");
						powers.add(watch);
					}
					if(power.charAt(2)=='1'){
						JSONObject modify = new JSONObject();
						modify.put("id", "modify_"+module.getModuleId());
						modify.put("pId", "module_"+module.getModuleId());
						modify.put("name", "修改");
						powers.add(modify);
					}
					if(power.charAt(3)=='1'){
						JSONObject del = new JSONObject();
						del.put("id", "del_"+module.getModuleId());
						del.put("pId", "module_"+module.getModuleId());
						del.put("name", "删除");
						powers.add(del);
					}
				}
				array.addAll(powers);
			}
		}
		return array;
	}
}
