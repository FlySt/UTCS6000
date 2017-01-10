package com.ncjk.utcs.modules.common.services;

import javax.annotation.Resource;

import com.ncjk.utcs.modules.resources.resources.services.interfaces.ICrossParamService;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.IRegionParamService;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalControlerService;
import org.springframework.stereotype.Service;

import com.ncjk.utcs.modules.common.services.interfaces.ITreeResourceService;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IDeptService;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IModuleService;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IUserService;

import net.sf.json.JSONArray;

/**
* @ClassName: TreeResourceService
* @Description: 树资源业务实现类
* @author shiwanglai
* @date 2016年8月20日 上午11:34:56
*
* 
*/
@Service("treeResourceService")
public class TreeResourceService implements ITreeResourceService{
	
	@Resource(name = "deptService")
	IDeptService deptService;
	@Resource(name = "userService")
	IUserService userService;
	@Resource(name="moduleService")
	IModuleService moduleService;
	@Resource
	ISignalControlerService signalControlerService;
	@Resource
	IRegionParamService regionParamService;
	@Resource
	ICrossParamService crossParamService;
	/**
	* @Description: 创建部门树
	* @param deptLevel   type  
	* @return JSONArray    
	* @date 2016年8月20日
	*/
	public JSONArray buildDeptsTree(String deptLevel,String type){
		JSONArray array = deptService.buildDeptsTree(deptLevel, type);
		return array;
	}
	/**
	* @Description: 创建组织结构树
	* @param deptLevel type   
	* @return JSONArray    
	* @date 2016年8月21日
	*/
	public JSONArray buildOrganizationTrees(String deptLevel,String type){		
		JSONArray users = userService.bulidUserTree();
		JSONArray depts = deptService.buildDeptsTree(deptLevel, type);
		JSONArray arrays = new JSONArray();		
		arrays.addAll(users);
		arrays.addAll(depts);
		return arrays;
	}
	/**  
	* @Description: 创建模块树
	* @param
	* @return JSONArray   
	* @author shiwanglai
	* @date 2016年8月22日 上午9:27:46  
	*/
	public JSONArray buildModuleTrees(){
		JSONArray array = moduleService.buildModuleTrees();	
		return array;
	}

	/**
	 * 创建信号机树
	 * @param type 0-地图上没有标记的信号机 1-已在地图上标记的信号机
	 * @return
	 */
	public JSONArray buildSignalControlerTrees(int type){
		JSONArray regions = regionParamService.buildRegionParamTrees();
		JSONArray crosss = crossParamService.buildCrossParamTrees();
		JSONArray signals = signalControlerService.buildSignalControlerTrees(type);
		JSONArray arrays = new JSONArray();
		arrays.addAll(regions);
		arrays.addAll(crosss);
		arrays.addAll(signals);
		return arrays;
	}

	/**
	 * 创建区域树
	 * @return
	 * @author swl
	 */
	@Override
	public JSONArray buildRegionTrees() {
		JSONArray array = regionParamService.buildRegionParamTrees();
		return array;
	}

	/**
	 * 创建路口树
	 * @return
	 * @author swl
	 */
	@Override
	public JSONArray buildCrossTrees() {
		JSONArray regions = regionParamService.buildRegionParamTrees();
		JSONArray crosss = crossParamService.buildCrossParamTrees();
		JSONArray arrays = new JSONArray();
		arrays.addAll(regions);
		arrays.addAll(crosss);
		return arrays;
	}
}
