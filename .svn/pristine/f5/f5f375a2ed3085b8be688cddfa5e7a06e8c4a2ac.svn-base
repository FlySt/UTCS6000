package com.ncjk.utcs.modules.resources.resources.services.interfaces;

import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignal;


import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalControler;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalTrafficPic;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface ISignalDeviceService {
	/**
	 * 查询所有信号机信息
	 */
	 JSONArray findSignalDevices(Integer regionId, Integer crossId, Integer signalId, int page, int pageSize);
	
	/**
	 *根据信号机设备ID查询信号机设备
	 * */
	UtcsSignalControler findSignalDeviceById(int id);
	
	/**
	 * 根据HQL语句创建信号机设备树
	 * @param hql 查询HQL语句
	 * @param page 当前页码数
	 * @param pageSize 每页显示条数
	 * @return 返回JSON对象数组
	 */
	 JSONArray buildSignalDeviceTree(String hql, int page, int pageSize);
	
	/**
	 * 保存信号机
	 * */

	UtcsSignalControler saveOrUpdateSignalDevice(UtcsSignalControler utcsSignalControler);

	String getIpAddr();
	/**
	 * 删除信号机
	 * */
	 boolean delSignal(Integer signalId);
	
	 JSONArray findServerJSON();
	
	 JSONArray findCrossJSON();
	
	 JSONObject userAndIpJson();
	
	 boolean delTrafficPic(Integer signalId);
	
	 boolean saveTrafficPic(UtcsSignalTrafficPic traffic);

	UtcsSignalControler findSignalById(Integer signalId);
	
	
	  long  queryCrossIdByDeptOrCrossOrDevice(Integer deptId, Integer roadId, Integer signalId);
	
	/**
	 * 相机
	 * */
	public JSONArray findCameraJSON();

	/**
	 * 删除相机与信号机关联
	 * */
//	 public boolean delSignalCameraBySignalId(Long signalId);
	 
	 /**
	  * 保存相机与信号机关联
	  * */
	// public boolean saveSignalCamera(UtcsSignalCamera signalCamera);
}

