package com.ncjk.utcs.modules.common.services.interfaces;

import net.sf.json.JSONArray;

public interface ITreeResourceService {

	/**
	* @Description: 创建部门树
	* @param deptLevel   type  
	* @return JSONArray    
	* @date 2016年8月20日
	*/
	JSONArray buildDeptsTree(String deptLevel,String type);
	/**
	* @Description: 创建组织结构树
	* @param deptLevel   type  
	* @return JSONArray    
	* @date 2016年8月21日
	*/
	 JSONArray buildOrganizationTrees(String deptLevel,String type);
	/**  
	* @Description: 创建模块树
	* @param
	* @return JSONArray   
	* @author shiwanglai
	* @date 2016年8月22日 上午9:27:46  
	*/
	 JSONArray buildModuleTrees();

	/**
	 * 创建信号机树、
	 * @author shiwanglai
	 * @param @param type 0-地图上没有标记的信号机 1-已在地图上标记的信号机
	 * @return
	 */
	 JSONArray buildSignalControlerTrees(int type);

	/**
	 * 创建区域树
	 * @author swl
	 * @return
	 */
	 JSONArray buildRegionTrees();

	/**
	 * 创建路口树
	 * @return
	 */
	JSONArray buildCrossTrees();

}
