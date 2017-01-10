package com.ncjk.utcs.modules.resources.resources.action;

import java.io.IOException;
import java.io.OutputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignal;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalControler;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalDeviceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionSupport;


@Scope("prototype")
@Controller("signalDeviceAction")
public class SignalDeviceAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private ISignalDeviceService signalDeviceService;
	private int page;				//当前页面数
	private int limit;				//页面显示数
	private int[] ids;				//信号机id数组
	private int objId ;			//信号机ID
	private UtcsSignalControler signalDevice = new UtcsSignalControler();	//用户对象
	private String deviceCode;		//信号机代码
	private String searchName;		//信号机名称
	private String deviceNames;
	private Integer regionId;
	private Integer crossId;
	private Integer signalId;
	//日志service
	private ILogService logService;
	
	/**
	 * 查询信号机设备
	 */
	public void queryAllSignalDevices(){
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonObjA  = signalDeviceService.findSignalDevices(regionId, crossId, signalId, page, limit);
		long crossId1 = signalDeviceService.queryCrossIdByDeptOrCrossOrDevice(regionId, crossId, signalId);
		jsonObj.put("deviceList", jsonObjA);
		jsonObj.put("crossId", crossId1);
		response.setContentType("text/json; charset=utf-8");
		try {
			System.out.println(jsonObj.toString());
			response.getWriter().write(jsonObj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initSignalActiveX(){
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject jsonObj = new JSONObject();
		JSONArray signalJsonObjA  = signalDeviceService.findSignalDevices(0, 0, 0, 0, 0);
		JSONArray crossJsonObjA  = signalDeviceService.findCrossJSON();
		JSONArray serverJsonObjA  = signalDeviceService.findServerJSON();
	//	JSONArray cameraJsonObjA  = signalDeviceService.findCameraJSON();
		JSONObject jsonObjB = signalDeviceService.userAndIpJson();
		JSONArray cameraJsonObjA = new JSONArray();
	//	JSONObject jsonObjB = new JSONObject();
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = request.getLocalAddr();
		int port = request.getLocalPort();
		jsonObj.put("ip", ip);
		jsonObj.put("port", port);
		jsonObj.put("deviceList", signalJsonObjA);
		jsonObj.put("crossingList", crossJsonObjA);
		jsonObj.put("serverList", serverJsonObjA);
		jsonObj.put("cameraList", cameraJsonObjA);
		jsonObj.put("userData", jsonObjB);
		response.setContentType("text/json; charset=utf-8");
		try {
			response.getWriter().write(jsonObj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查看信号机设备详细信息
	 */
	public String  modifySignalDevice(){
		
		if(objId != 0){
			signalDevice = signalDeviceService.findSignalDeviceById(objId);
			
		}
		return "modifySignalDevice";
	}
	
	/**
	 * 查看信号机路口背景图片
	 */
	public String  showBackGroundPhoto(){
		if(objId != 0){
			HttpServletResponse response = ServletActionContext.getResponse();
			OutputStream ops = null;
			signalDevice = signalDeviceService.findSignalDeviceById(objId);
			try {
				response.getOutputStream().write(signalDevice.getRoadBackgroundMap());
				ops = response.getOutputStream();
				ops.flush();
				ops.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public ISignalDeviceService getSignalDeviceService() {
		return signalDeviceService;
	}
	@Resource(name = "signalDeviceService")
	public void setSignalDeviceService(ISignalDeviceService signalDeviceService) {
		this.signalDeviceService = signalDeviceService;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int[] getIds() {
		return ids;
	}
	public void setIds(int[] ids) {
		this.ids = ids;
	}
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}

	public UtcsSignalControler getSignalDevice() {
		return signalDevice;
	}

	public void setSignalDevice(UtcsSignalControler signalDevice) {
		this.signalDevice = signalDevice;
	}

	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getDeviceNames() {
		return deviceNames;
	}
	public void setDeviceNames(String deviceNames) {
		this.deviceNames = deviceNames;
	}
	public ILogService getLogService() {
		return logService;
	}
	@Resource(name = "logService")
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getCrossId() {
		return crossId;
	}

	public void setCrossId(Integer crossId) {
		this.crossId = crossId;
	}

	public Integer getSignalId() {
		return signalId;
	}

	public void setSignalId(Integer signalId) {
		this.signalId = signalId;
	}
}