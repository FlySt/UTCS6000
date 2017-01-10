package com.ncjk.utcs.modules.resources.resources.services;

import java.util.List;
import javax.annotation.Resource;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalDriverWay;
import com.ncjk.utcs.modules.resources.resources.services.interfaces.ISignalDriverWayService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Scope("prototype")
@Service("signalDriverWayService")
public class SignalDriverWayService implements ISignalDriverWayService {
	@Resource
	private ICommonDAO comDAO;

	@SuppressWarnings("unchecked")
	public List<UtcsSignalDriverWay> findSignalDriverWayBySignalId(Integer signalId) {
		List<UtcsSignalDriverWay> signalDriverWayList = (List<UtcsSignalDriverWay>) comDAO.findByHql(" from UtcsSignalDriverWay t where t.utcsSignalControler.signalControlerId=" + signalId, 0, 0);
		return signalDriverWayList;
	}

	public boolean delSignalDriverWayBySignalId(Integer signalId) {
		boolean b = comDAO.deleteByHql(" delete UtcsSignalDriverWay t where t.utcsSignalControler.signalControlerId=" + signalId);
		return b;
	}

	public boolean saveOrUpdateSignalDriverWay(UtcsSignalDriverWay driverWay) {
		boolean b = false;
		UtcsSignalDriverWay newDriverWay = new UtcsSignalDriverWay();
		if (driverWay.getDriverWayId() == 0l) {

		} else {
			newDriverWay = (UtcsSignalDriverWay) comDAO.findByHql(" from UtcsSignalDriverWay t where t.driverWayId = " + driverWay.getDriverWayId());
			if (newDriverWay == null) {
				newDriverWay = new UtcsSignalDriverWay();
			}
		}
		newDriverWay.setDriverWayName(driverWay.getDriverWayName());
		newDriverWay.setDriverWayX(driverWay.getDriverWayX());
		newDriverWay.setDriverWayY(driverWay.getDriverWayY());
		newDriverWay.setEddyAngle(driverWay.getEddyAngle());
		newDriverWay.setFontColor(driverWay.getFontColor());
		newDriverWay.setFontSize(driverWay.getFontSize());
		newDriverWay.setUtcsSignalControler(driverWay.getUtcsSignalControler());
		b = comDAO.saveOrUpdate(newDriverWay);
		return b;
	}

}
