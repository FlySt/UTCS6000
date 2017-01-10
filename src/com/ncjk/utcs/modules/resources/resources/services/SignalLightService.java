package com.ncjk.utcs.modules.resources.resources.services;

import java.util.List;

import javax.annotation.Resource;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalLight;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalLightService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("prototype")
@Service("signalLightService")
public class SignalLightService implements ISignalLightService {
	@Resource
	private ICommonDAO comDAO;

	@SuppressWarnings("unchecked")
	public List<UtcsSignalLight> findSignalLightBySignal(Integer siganlId) {
		List<UtcsSignalLight> signalLightList = (List<UtcsSignalLight>) comDAO.findByHql(" from UtcsSignalLight t where t.utcsSignalControler.signalControlerId=" + siganlId, 0, 0);
		return signalLightList;
	}

	public boolean saveOrUpdateSignalLight(UtcsSignalLight signalLight) {
		boolean b = false;
		UtcsSignalLight newSignalLight = new UtcsSignalLight();
		if (signalLight.getLightId() != 0) {
			newSignalLight = (UtcsSignalLight) comDAO.findByHql(" from UtcsSignalLight t where t.lightId = " + signalLight.getLightId());
			if (newSignalLight == null) {
				newSignalLight = new UtcsSignalLight();
			}
		}
		newSignalLight.setDriverWayDirection(signalLight.getDriverWayDirection());
		newSignalLight.setDriverWayDirectionType(signalLight.getDriverWayDirectionType());
		newSignalLight.setDriverWayType(signalLight.getDriverWayType());
		newSignalLight.setEddyAngle(signalLight.getEddyAngle());
		newSignalLight.setLightHeight(signalLight.getLightHeight());
		newSignalLight.setLightNum(signalLight.getLightNum());
		newSignalLight.setLightOutPut(signalLight.getLightOutPut());
		newSignalLight.setLightWidth(signalLight.getLightWidth());
		newSignalLight.setLightX(signalLight.getLightX());
		newSignalLight.setLightY(signalLight.getLightY());
		newSignalLight.setUtcsSignalControler(signalLight.getUtcsSignalControler());
		b = comDAO.saveOrUpdate(newSignalLight);
		return b;
	}

	public boolean delSignalLightBySignal(Integer siganlId) {
		boolean b = comDAO.deleteByHql(" delete UtcsSignalLight t where t.utcsSignalControler.signalControlerId=" + siganlId);
		return b;
	}

}