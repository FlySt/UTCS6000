package com.ncjk.utcs.common.servlet;

import java.util.List;

import javax.annotation.Resource;

import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;
import com.ncjk.utcs.modules.resources.resources.pojo.*;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.*;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Scope("prototype")
@Controller("signalDeviceServlet")
public class SignalDeviceServlet implements ISignalDeviceServlet {
	@Resource
	private ISignalDeviceService signalDeviceService;
	@Resource
	private ISignalDriverWayService signalDriverWayService;
	@Resource
	private ISignalLightService signalLightService;
	@Resource
	private IServerParamService serverService;
	@Resource
	private ICrossParamService crossParamService;
	// 日志service
	@Resource
	private ILogService logService;

	public JSONObject saveOrUpdateSignal(String content, List<byte[]> imageList) {
		String result = "0";
		JSONObject jsonObj = new JSONObject();
		try {
			// "{name:\"\u8DEF\u53E3\u4E00\",id:0,num:\"\",ip:\"127.0.0.1\",port:9000,type:4,img_width:0,img_height:0,server:\"\",road:\"\",lightSet:[{id:0,width:0,height:0,num:1,output:1,x:0,y:0,type:0,dir:0,dirtype:0},{id:0,width:0,height:0,num:1,output:1,x:0,y:0,type:0,dir:0,dirtype:0}],laneSet:[{id:0,name:\"\",x:0,y:0,angle:0,color:Black,size:10},{id:0,name:\"\",x:0,y:0,angle:0,color:\"Black\",size:10}]}";
			// "{name:\"\u8DEF\u53E3\u4E00\",id:0,num:\"\",ip:\"127.0.0.1\",port:9000,type:4,img_width:0,img_height:0,server:\"\",road:\"\"
			JSONObject js = new JSONObject(content);
			System.out.println("content："+content);
			String signalName = (String) js.get("name");
			Integer signalId = (Integer) js.get("id");
			String signalIp = (String) js.get("ip");
			Integer signalPort = (Integer) js.get("port");
			Integer signalType = (Integer) js.get("type");
			Integer imgWidth = (Integer) js.get("imgWidth");
			String light = (String) js.get("light");
			Integer imgHeight = (Integer) js.get("imgHeight");
			Integer serverId = (Integer) js.get("server");
			Integer roadId = (Integer) js.get("road");
			String trafficpicSet = (String) js.get("traffic");
		//	UtcsUser user = (UtcsUser) ServletActionContext.getRequest().getSession().getAttribute("user");
			String userName = (String) js.get("userName");
		//	String userIp = ServletActionContext.getRequest().getRemoteAddr();
			String userIp = (String) js.get("userIp");
			String specialLight = (String) js.get("specialLight");
			UtcsServerParam server = serverService.findServerParamById(serverId);
			UtcsCrossParam cross = crossParamService.findCrossParamById(roadId);
			UtcsSignalControler utcsSignalControler = new UtcsSignalControler();
			utcsSignalControler.setBackgroundMapHeight(imgHeight);
			utcsSignalControler.setBackgroundMapWidth(imgWidth);
			utcsSignalControler.setCrossParam(cross);
			utcsSignalControler.setDeviceIp(signalIp);
			utcsSignalControler.setLightSet(light);
			utcsSignalControler.setServerParam(server);
			utcsSignalControler.setSignalControlerId(signalId);
			utcsSignalControler.setSignalControlerName(signalName);
			utcsSignalControler.setSignalType(signalType);
			utcsSignalControler.setDevicePort(signalPort);
			utcsSignalControler.setRoadBackgroundMap(imageList.get(0));
			utcsSignalControler.setTrafficpicSet(trafficpicSet);
			utcsSignalControler.setUpdateAccount(userName);
			utcsSignalControler.setSpecialLightName(specialLight);
			utcsSignalControler.setErrorId(0);
			UtcsSignalControler newSignal = signalDeviceService.saveOrUpdateSignalDevice(utcsSignalControler);
			if (newSignal == null) {
				jsonObj.put("id", 0);
				jsonObj.put("num", "0");
				jsonObj.put("result", "0");
				return jsonObj;
			}
			String logMsg = "失败";
			String opType = "新增";
			if (newSignal != null) {
				if (signalId != 0) {
					opType = "修改";
				}
				logMsg = "成功";
				String lightSet = String.valueOf(js.get("lightSet"));
			//	if (lightSet != null && !"[{}]".equals(lightSet) && !"".equals(lightSet)) {
				if (lightSet != null && !"".equals(lightSet)) {
					//删除信号灯信息
					signalLightService.delSignalLightBySignal(newSignal.getSignalControlerId());
					if("[{}]".equals(lightSet)){//清空

					}else{
						JSONArray lightJsonA = (JSONArray) js.get("lightSet");
						for (int i = 0; i < lightJsonA.length(); i++) {
							//{id:0,width:0,height:0,num:1,output:1,x:0,y:0,type:0,dir:0,dirtype:0}
							JSONObject lightJson = (JSONObject) lightJsonA.get(i);
							//Integer lightId = (Integer) lightJson.get("id");
							Integer width = (Integer) lightJson.get("width");
							Integer height = (Integer) lightJson.get("height");
							Integer num = (Integer) lightJson.get("num");
							Integer output = (Integer) lightJson.get("output");
							Integer x = (Integer) lightJson.get("x");
							Integer y = (Integer) lightJson.get("y");
							Integer type = (Integer) lightJson.get("type");
							Integer dir = (Integer) lightJson.get("dir");
							Integer dirtype = (Integer) lightJson.get("dirType");
							//	Double angle = Double.valueOf((Integer) lightJson.get("angle"));
							Integer angle = (Integer) lightJson.get("angle");
							UtcsSignalLight lightObj = new UtcsSignalLight();
							lightObj.setDriverWayDirection(dir);
							lightObj.setDriverWayDirectionType(dirtype);
							lightObj.setDriverWayType(type);
							lightObj.setEddyAngle(angle);
							lightObj.setLightHeight(height);
							lightObj.setLightNum(num);
							lightObj.setLightOutPut(output);
							lightObj.setLightWidth(width);
							lightObj.setLightX(x);
							lightObj.setLightY(y);
							lightObj.setUtcsSignalControler(newSignal);
							lightObj.setLightId(0);
							signalLightService.saveOrUpdateSignalLight(lightObj);
						}
					}
				}
				// {id:0,name:\"\",x:0,y:0,angle:0,color:\"Black\",size:10}
				String laneSet = String.valueOf(js.get("laneSet"));
				if (laneSet != null && !"".equals(laneSet)) {
					//删除车道信息
					signalDriverWayService.delSignalDriverWayBySignalId(newSignal.getSignalControlerId());
					if("[{}]".equals(laneSet)){

					}else{
						JSONArray lanJsonA = (JSONArray) js.get("laneSet");
						for (int i = 0; i < lanJsonA.length(); i++) {
							JSONObject lanJson = (JSONObject) lanJsonA.get(i);
							//Integer lanId = (Integer) lanJson.get("id");
							String lanName = (String) lanJson.get("name");
							Integer lanX = (Integer) lanJson.get("x");
							Integer lanY = (Integer) lanJson.get("y");
							//	Double angle = Double.valueOf((Integer) lanJson.get("angle"));
							Integer angle = (Integer) lanJson.get("angle");
							String color = (String) lanJson.get("color");
							Integer size = (Integer) lanJson.get("size");
							UtcsSignalDriverWay newSignalDriverWay = new UtcsSignalDriverWay();
							newSignalDriverWay.setDriverWayId(0);
							newSignalDriverWay.setDriverWayName(lanName);
							newSignalDriverWay.setDriverWayX(lanX);
							newSignalDriverWay.setDriverWayY(lanY);
							newSignalDriverWay.setEddyAngle(angle);
							newSignalDriverWay.setFontColor(color);
							newSignalDriverWay.setFontSize(size);
							newSignalDriverWay.setUtcsSignalControler(newSignal);
							signalDriverWayService.saveOrUpdateSignalDriverWay(newSignalDriverWay);
						}
					}
				}
				String trafficList = String.valueOf(js.get("trafficList"));
				if (trafficList != null && !"".equals(trafficList)) {
					//删除车流量
					signalDeviceService.delTrafficPic(newSignal.getSignalControlerId());
					if("[{}]".equals(trafficList)){

					}else{
						JSONArray trafficJsonA = (JSONArray) js.get("trafficList");
						for (int i = 0; i < trafficJsonA.length(); i++) {
							JSONObject trafficJson = (JSONObject) trafficJsonA.get(i);
							//Integer id = (Integer) trafficJson.get("id");
							Integer height = (Integer) trafficJson.get("height");
							Integer width = (Integer) trafficJson.get("width");
							Integer yellow = (Integer) trafficJson.get("yellow");
							Integer red = (Integer) trafficJson.get("red");
							Integer direction = (Integer) trafficJson.get("direction");
							Integer x = (Integer) trafficJson.get("x");
							Integer y = (Integer) trafficJson.get("y");
							Integer angle = (Integer) trafficJson.get("angle");
							String color = (String) trafficJson.get("color");
							Integer size = (Integer) trafficJson.get("size");
							UtcsSignalTrafficPic signalTrafficPic = new UtcsSignalTrafficPic();
							signalTrafficPic.setUtcsSignalControler(newSignal);
							signalTrafficPic.setTrafficpicAngle(Double.valueOf(angle));
							signalTrafficPic.setTrafficpicDirection(direction);
							signalTrafficPic.setTrafficpicFontColor(color);
							signalTrafficPic.setTrafficpicFontSize(size);
							signalTrafficPic.setTrafficpicHeight(height);
							signalTrafficPic.setTrafficpicId(0);
							signalTrafficPic.setTrafficpicRed(red);
							signalTrafficPic.setTrafficpicWidth(width);
							signalTrafficPic.setTrafficpicX(x);
							signalTrafficPic.setTrafficpicY(y);
							signalTrafficPic.setTrafficpicYellow(yellow);
							signalDeviceService.saveTrafficPic(signalTrafficPic);
						}
					}
				}

				// 相机对应保存
		/*		if (js.toString().indexOf("cameraList") != -1) {
					// 展会版本JS
					String cameraList = "";
					try {
						cameraList = String.valueOf(js.get("cameraList"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (cameraList != null && !"[{}]".equals(cameraList) && !"".equals(cameraList)) {
						signalDeviceService.delSignalCameraBySignalId(newSignal.getSignalId());
						JSONArray cameraJsonA = (JSONArray) js.get("cameraList");
						for (int i = 0; i < cameraJsonA.length(); i++) {
							JSONObject cameraJson = (JSONObject) cameraJsonA.get(i);
							Long cameraId = (Long) cameraJson.get("cameraId");
							Long signalId1 = (Long) cameraJson.get("signalId");
							Long cameraX = (Long) cameraJson.get("cameraX");
							Long cameraY = (Long) cameraJson.get("cameraY");
							UtmsSignalCamera camera = new UtmsSignalCamera();
							camera.setCameraX(cameraX);
							camera.setCameraY(cameraY);
							camera.setDeviceId(cameraId);
							camera.setSignalId(signalId1);
							signalDeviceService.saveSignalCamera(camera);
						}
					}
				}*/
			}
			jsonObj.put("id", newSignal.getSignalControlerId());
			jsonObj.put("num", "360010");
			result = "1";
			logService.signalSaveLog(opType + "信号机【" + newSignal.getSignalControlerName() + "】信息" + logMsg, "信号机管理", userName, userIp);
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		}
		try {
			jsonObj.put("result", result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}

	/**
	 * 删除信号灯
	 * */
	public String delSignalDevice(Integer signalId, String content) {
		UtcsSignalControler utcsSignalControler = signalDeviceService.findSignalById(signalId);
		String result = "0";
		String signalName = "未找到信号机";
		if (utcsSignalControler != null) {
			signalName = utcsSignalControler.getSignalControlerName();
		}
		boolean b = signalDeviceService.delSignal(signalId);

		String logMsg = "失败";
		if (b) {
			result = "1";
			logMsg = "成功";
		}
		try {
			JSONObject js = new JSONObject(content);
			String userName = (String) js.get("userName");
			String userIp = (String) js.get("userIp");
			logService.signalSaveLog("删除信号机【" + signalName + "】信息" + logMsg, "信号机管控", userName, userIp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public byte[] getImage(Integer id) {
		UtcsSignalControler utcsSignalControler = signalDeviceService.findSignalDeviceById(id);
		byte[] byteImage = utcsSignalControler.getRoadBackgroundMap();
		return byteImage;
	}

	public String saveSignalOperateLog(String content) {
		String saveResult = "0";
		try {
			JSONObject js = new JSONObject(content);
			String userName = (String) js.get("userName");
			String userIp = (String) js.get("userIp");
			String operateType = (String) js.get("operateType");
			String operateContent = (String) js.get("operateContent");
			boolean b = logService.signalSaveLog(operateContent, operateType, userName, userIp);
			if (b) {
				saveResult = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saveResult;
	}
}
