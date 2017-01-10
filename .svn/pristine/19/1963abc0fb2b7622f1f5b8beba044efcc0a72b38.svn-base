package com.ncjk.utcs.modules.resources.resources.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.logs.services.interfaces.ILogService;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;
import com.ncjk.utcs.modules.resources.resources.pojo.*;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalDeviceService;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;


@Scope("prototype")
@Service("signalDeviceService")
public class SignalDeviceService implements ISignalDeviceService {
	@Resource
	private ICommonDAO comDAO;
	// 日志service
	@Resource
	private ILogService logService;

	/**
	 * 查询所有信号机信息
	 */
	@SuppressWarnings("unchecked")
	public JSONArray findSignalDevices(Integer regionId, Integer crossId, Integer signalId, int page, int pageSize) {
		String hql = " from UtcsSignalControler t  where 1=1 ";
		StringBuffer condition = new StringBuffer();

		if (regionId != null && regionId!=0) {
			condition.append(" and t.crossParam.regionParam.regionId= " + regionId);
		}

		if (crossId != null && crossId!=0) {
			condition.append(" and t.crossParam.crossId= " + crossId);
		}

		if (signalId != null && signalId!=0) {
			condition.append(" and t.signalControlerId= " + signalId);
		}

		condition.append(" and t.signalType=0");
		JSONArray invdata = new JSONArray();
		// 分页查询监控点信息
		List<UtcsSignalControler> signalDevices = findSignalDevicesByHql(hql + condition.toString() + " order by t.signalControlerName,t.crossParam.crossName ", page, pageSize);
		if (signalDevices != null && !signalDevices.isEmpty()) {
			for (UtcsSignalControler signalControler : signalDevices) {
				JSONObject js = new JSONObject();
				js.put("id", signalControler.getSignalControlerId());
				js.put("name", signalControler.getSignalControlerName()); // 信号机名称
				js.put("road", signalControler.getCrossParam().getCrossId()); // 信号机所属路口名称
				js.put("num", "360010"); // 资产编码
				js.put("type", signalControler.getType()); // 信号机类型
				js.put("roadType", signalControler.getRoadType()); // 路口类型
				js.put("ip", signalControler.getDeviceIp());
				js.put("port", signalControler.getDevicePort());
		//		js.put("pNum", signalControler.getProtocolNum());
				js.put("server", signalControler.getServerParam().getServerId());
				js.put("light", signalControler.getLightSet());
				js.put("imgWidth", signalControler.getBackgroundMapWidth());
				js.put("imgHeight", signalControler.getBackgroundMapHeight());
				js.put("traffic", signalControler.getTrafficpicSet());
				js.put("specialLight", signalControler.getSpecialLightName());
				List<UtcsSignalLight> signalLightList = (List<UtcsSignalLight>) comDAO.findByHql(" from UtcsSignalLight t where t.utcsSignalControler.signalControlerId = " + signalControler.getSignalControlerId(), 0, 0);
				List<UtcsSignalDriverWay> signalDriverWayList = (List<UtcsSignalDriverWay>) comDAO.findByHql(" from UtcsSignalDriverWay t where t.utcsSignalControler.signalControlerId = "
						+ signalControler.getSignalControlerId(), 0, 0);
				List<UtcsSignalTrafficPic> signalTrafficPicList = (List<UtcsSignalTrafficPic>) comDAO.findByHql(" from UtcsSignalTrafficPic t where t.utcsSignalControler.signalControlerId = "
						+ signalControler.getSignalControlerId(), 0, 0);
				if (signalLightList != null && !signalLightList.isEmpty()) {
					JSONArray lightJSONA = new JSONArray();
					for (UtcsSignalLight signalLight : signalLightList) {
						JSONObject lightJSON = new JSONObject();
						lightJSON.put("id", signalLight.getLightId());
						lightJSON.put("signal", signalLight.getUtcsSignalControler().getSignalControlerId());
						lightJSON.put("width", signalLight.getLightWidth());
						lightJSON.put("height", signalLight.getLightHeight());
						lightJSON.put("num", signalLight.getLightNum());
						lightJSON.put("output", signalLight.getLightOutPut());
						lightJSON.put("x", signalLight.getLightX());
						lightJSON.put("y", signalLight.getLightY());
						lightJSON.put("type", signalLight.getDriverWayType());
						lightJSON.put("dir", signalLight.getDriverWayDirection());
						lightJSON.put("dirType", signalLight.getDriverWayDirectionType());
						lightJSON.put("angle", signalLight.getEddyAngle());
						lightJSONA.add(lightJSON);
					}
					js.put("lightSet", lightJSONA);
				} else {
					js.put("lightSet", "null");
				}

				if (signalDriverWayList != null && !signalDriverWayList.isEmpty()) {
					JSONArray driverWayJSONA = new JSONArray();
					for (UtcsSignalDriverWay signalDriverWay : signalDriverWayList) {
						JSONObject driverWayJSON = new JSONObject();
						driverWayJSON.put("id", signalDriverWay.getDriverWayId());
						driverWayJSON.put("signal", signalDriverWay.getUtcsSignalControler().getSignalControlerId());
						driverWayJSON.put("name", signalDriverWay.getDriverWayName());
						driverWayJSON.put("x", signalDriverWay.getDriverWayX());
						driverWayJSON.put("y", signalDriverWay.getDriverWayY());
						driverWayJSON.put("angle", signalDriverWay.getEddyAngle());
						driverWayJSON.put("color", signalDriverWay.getFontColor());
						driverWayJSON.put("size", signalDriverWay.getFontSize());
						driverWayJSONA.add(driverWayJSON);
					}
					js.put("laneSet", driverWayJSONA);
				} else {
					js.put("laneSet", "null");
				}

				if (signalTrafficPicList != null && !signalTrafficPicList.isEmpty()) {
					JSONArray jSONA = new JSONArray();
					for (UtcsSignalTrafficPic traffic : signalTrafficPicList) {
						JSONObject jSON = new JSONObject();
						jSON.put("id", traffic.getTrafficpicId());
						jSON.put("signal", traffic.getUtcsSignalControler().getSignalControlerId());
						jSON.put("x", traffic.getTrafficpicX());
						jSON.put("y", traffic.getTrafficpicY());
						jSON.put("angle", traffic.getTrafficpicAngle());
						jSON.put("color", traffic.getTrafficpicFontColor());
						jSON.put("size", traffic.getTrafficpicFontSize());
						jSON.put("height", traffic.getTrafficpicHeight());
						jSON.put("width", traffic.getTrafficpicWidth());
						jSON.put("yellow", traffic.getTrafficpicYellow());
						jSON.put("red", traffic.getTrafficpicRed());
						jSON.put("direction", traffic.getTrafficpicDirection());
						jSONA.add(jSON);
					}
					js.put("trafficList", jSONA);
				} else {
					js.put("trafficList", "null");
				}
				invdata.add(js);
			}
		}
		return invdata;
	}

	/**
	 * 查询信号机信息
	 */
	@SuppressWarnings("unchecked")
	public List<UtcsSignalControler> findSignalDevicesByHql(String hql, int page, int pageSize) {
		List<UtcsSignalControler> signalDevices = (List<UtcsSignalControler>) comDAO.findByHql(hql, page, pageSize);
		return signalDevices;
	}

	/**
	 * 根据HQL语句查询信号机总条数
	 */
	public int countSignalDevices(String hql) {
		int count = 0;
		StringBuffer sb = new StringBuffer(" select count(t.id) from  UtcsSignalControler t where 1=1 and t.signalType=0");
		sb.append(hql);
		Object objs = comDAO.findByHql(sb.toString());
		if (objs != null) {
			count = ((Long) objs).intValue();
		}
		return count;
	}

	/**
	 *根据信号机设备ID查询信号机设备
	 * */
	public UtcsSignalControler findSignalDeviceById(int id) {
		String hql = "from UtcsSignalControler t where t.signalType=0";
		UtcsSignalControler utcsSignalControler = (UtcsSignalControler) comDAO.findByHql(hql);
		return utcsSignalControler;
	}

	/**
	 * 查询一个可用的信号机IP地址，为了通信使用
	 * @return
	 */
	@Override
	public String getIpAddr() {
		String ipAddr="127.0.0.1";
		List<UtcsSignalControler> utcsSignalControlers = (List<UtcsSignalControler>)comDAO.findByHql("from UtcsSignalControler t where t.signalType=0",0,0);
		if(utcsSignalControlers!=null && !utcsSignalControlers.isEmpty()){
			for(UtcsSignalControler utcsSignalControler:utcsSignalControlers){
				if(utcsSignalControler.getDeviceIp()!=null && utcsSignalControler.getDeviceIp().length()>0){
					ipAddr = utcsSignalControler.getDeviceIp();
				}
			}
		}
		return ipAddr;
	}

	/**
	 * 根据HQL语句创建信号机设备树
	 * 
	 * @param hql 查询HQL语句
	 * @param page 当前页码数
	 * @param pageSize 每页显示条数
	 * @return 返回JSON对象数组
	 */
	public JSONArray buildSignalDeviceTree(String hql, int page, int pageSize) {
		JSONArray invdata = new JSONArray();
		List<UtcsSignalControler> signalDevices = findSignalDevicesByHql(hql, page, pageSize);
		if (signalDevices != null && !signalDevices.isEmpty()) {
			for (UtcsSignalControler signalDevice : signalDevices) {
				JSONObject js = new JSONObject();
				UtcsCrossParam cross = signalDevice.getCrossParam();
				Integer cossId = cross.getCrossId();
				js.put("id", "signal_" + signalDevice.getSignalControlerId());
				js.put("pId", "crossing_" + cossId);
				js.put("name", signalDevice.getSignalControlerName());
				js.put("lon", signalDevice.getLongitude());
				js.put("lat", signalDevice.getLatitude());
				invdata.add(js);
			}
		}
		return invdata;
	}

	/**
	 * 查询部门、或路口、或信号机设备下的信号机
	 * */
	public JSONArray querySignalByDeptOrCrossOrDevice(String deptId, String crossId, String deviceId) {
		JSONArray invdata = new JSONArray();
		StringBuffer hql = new StringBuffer(" from UtcsSignalControler t where t.signalType=0 ");
		if (deptId != null && !"".equals(deptId)) {
			hql.append(" and t.crossParam.utcsDept.deptId=" + deptId);
		}
		if (crossId != null && !"".equals(crossId)) {
			hql.append(" and t.crossParam.crossId=" + crossId);
		}
		return invdata;
	}

	/**
	 * 保存信号机
	 * */
	public UtcsSignalControler saveOrUpdateSignalDevice(UtcsSignalControler signalControler) {
		System.out.println("getSignalControlerId:"+signalControler.getSignalControlerId());
		UtcsSignalControler newSignal = (UtcsSignalControler) comDAO.findByHql(" from UtcsSignalControler t where t.signalControlerId = " + signalControler.getSignalControlerId());
		if (newSignal == null) {
			return null;
		}
		newSignal.setBackgroundMapHeight(signalControler.getBackgroundMapHeight());
		newSignal.setBackgroundMapWidth(signalControler.getBackgroundMapWidth());
		newSignal.setLightSet(signalControler.getLightSet());
		newSignal.setRoadBackgroundMap(signalControler.getRoadBackgroundMap());
		newSignal.setUpdateTime(new Date());
		newSignal.setTrafficpicSet(signalControler.getTrafficpicSet());
		newSignal.setSpecialLightName(signalControler.getSpecialLightName());
		String updateAccount = "账号丢失";
		if (signalControler.getUpdateAccount() != null) {
			updateAccount = signalControler.getUpdateAccount();
		}
		newSignal.setUpdateAccount(updateAccount);
		boolean bSave = comDAO.saveOrUpdate(newSignal);
		if (!bSave)
			return null;
		return newSignal;
	}

	/**
	 * 删除信号机
	 * */
	public boolean delSignal(Integer signalControlerId) {
		boolean b = false;
		b = comDAO.deleteByHql(" delete UtcsSignalControler t where t.signalControlerId = " + signalControlerId);
		return b;
	}

	public UtcsSignalControler findSignalById(Integer signalControlerId) {
		UtcsSignalControler utcsSignalControler = (UtcsSignalControler) comDAO.findByHql(" from UtcsSignalControler t where t.signalControlerId = " + signalControlerId);
		return utcsSignalControler;
	}

	@SuppressWarnings("unchecked")
	public JSONArray findCrossJSON() {
		List<UtcsCrossParam> crosses = (List<UtcsCrossParam>) comDAO.findByHql(" from UtcsCrossParam t ", 0, 0);
		JSONArray invdata = new JSONArray();
		if (crosses != null && !crosses.isEmpty()) {
			for (UtcsCrossParam cross : crosses) {
				JSONObject js = new JSONObject();
				js.put("id", cross.getCrossId());
				js.put("name", cross.getCrossName());
				invdata.add(js);
			}
		} else {
			return null;
		}
		return invdata;
	}

	@SuppressWarnings("unchecked")
	public JSONArray findServerJSON() {
		UtcsServerParam serverParam = (UtcsServerParam)comDAO.findByHql(" from UtcsServerParam t where 1=1");
		JSONArray invdata = new JSONArray();
/*		if (servers != null && !servers.isEmpty()) {
			for (UtcsServerParam server : servers) {
				JSONObject js = new JSONObject();
				js.put("id", server.getServerId());
				js.put("name", "信号机接入服务器");
				js.put("ip", server.getServerIP());
				js.put("port", 6000);
				invdata.add(js);
			}
		} else {
			return null;
		}*/
		JSONObject js = new JSONObject();
		js.put("id", serverParam.getServerId());
		js.put("name", "信号机接入服务器");
		js.put("ip", "192.168.1.102");
		js.put("port", "6080");
		invdata.add(js);
		return invdata;
	}

	public JSONObject userAndIpJson() {
		JSONObject jsonObj = new JSONObject();
		UtcsUser user = (UtcsUser) ServletActionContext.getRequest().getSession().getAttribute("user");
		String userName = "控件用户";
		if (user != null) {
			userName = user.getUserAccount();
		}
		String userIp = ServletActionContext.getRequest().getRemoteAddr();
		jsonObj.put("userName", userName);
		jsonObj.put("userIp", userIp);
		return jsonObj;
	}

	/**
	 * 相机
	 * */
	@SuppressWarnings("unchecked")
	public JSONArray findCameraJSON() {
//		List<UtmsDevice> devices = (List<UtmsDevice>) comDAO.findByHql(" from UtmsDevice t where t.deviceType=1", 0, 0);
		JSONArray invdata = new JSONArray();
/*		if (devices != null && !devices.isEmpty()) {
			for (UtmsDevice device : devices) {
				JSONObject js = new JSONObject();
				js.put("id", device.getDeviceId());
				js.put("name", device.getDeviceName());
				js.put("ip", device.getDeviceIpAddr());
				js.put("port", device.getCommPort());
				js.put("user", device.getCommUserName());
				js.put("password", device.getCommPassword());
				js.put("crossing_id", device.getCross().getCrossingId());
				invdata.add(js);
			}
		} else {
			return null;
		}*/
        JSONObject js = new JSONObject();
        js.put("id", 512650);
        js.put("name", "工业四路由东往西");
        js.put("ip", "");
        js.put("port", "");
        js.put("user", "");
        js.put("password", "");
        js.put("crossing_id", 512600);
        invdata.add(js);
		return invdata;
	}

	public boolean delTrafficPic(Integer signalControlerId) {
		boolean b = comDAO.deleteByHql(" delete UtcsSignalTrafficPic t where t.utcsSignalControler.signalControlerId=" + signalControlerId);
		return b;
	}

	public boolean saveTrafficPic(UtcsSignalTrafficPic traffic) {
		boolean b = false;
		UtcsSignalTrafficPic newTraffic = new UtcsSignalTrafficPic();

		newTraffic.setUtcsSignalControler(traffic.getUtcsSignalControler());
		newTraffic.setTrafficpicAngle(traffic.getTrafficpicAngle());
		newTraffic.setTrafficpicDirection(traffic.getTrafficpicDirection());
		newTraffic.setTrafficpicFontColor(traffic.getTrafficpicFontColor());
		newTraffic.setTrafficpicFontSize(traffic.getTrafficpicFontSize());
		newTraffic.setTrafficpicHeight(traffic.getTrafficpicHeight());
		newTraffic.setTrafficpicRed(traffic.getTrafficpicRed());
		newTraffic.setTrafficpicWidth(traffic.getTrafficpicWidth());
		newTraffic.setTrafficpicX(traffic.getTrafficpicX());
		newTraffic.setTrafficpicY(traffic.getTrafficpicY());
		newTraffic.setTrafficpicYellow(traffic.getTrafficpicYellow());
		comDAO.saveOrUpdate(newTraffic);
		return b;
	}

	@SuppressWarnings("unchecked")
	public long queryCrossIdByDeptOrCrossOrDevice(Integer regionId, Integer roadId, Integer signalId) {

		if (regionId != null && regionId != 0l) {
			List<UtcsCrossParam> crosses = (List<UtcsCrossParam>) comDAO.findByHql(" from UtcsCrossParam t where t.regionParam.regionId =" + regionId, 0, 0);
			if (crosses != null && !crosses.isEmpty()) {
				UtcsCrossParam cross = crosses.get(0);
				roadId = cross.getCrossId();
			}
		}

		if (signalId != null && signalId != 0l) {
			UtcsSignalControler utcsSignalControler = findSignalById(signalId);
			if (utcsSignalControler != null) {
				roadId = utcsSignalControler.getCrossParam().getCrossId();
			}
		}

		return roadId;
	}

	/**
	 * 删除相机与信号机关联
	 * */
/*	public boolean delSignalCameraBySignalId(Long signalId) {
		boolean b = comDAO.deleteByHql(" delete UtmsSignalCamera t where t.signalId =" + signalId);
		return b;
	}*/

	/**
	 * 保存相机与信号机关联
	 * */
/*	public boolean saveSignalCamera(UtcsSignalCamera signalCamera) {
		boolean b = comDAO.saveOrUpdate(signalCamera);
		return b;
	}*/

}