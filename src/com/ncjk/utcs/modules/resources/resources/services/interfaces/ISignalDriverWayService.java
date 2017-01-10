package com.ncjk.utcs.modules.resources.resources.services.interfaces;

import java.util.List;

import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalDriverWay;

public interface ISignalDriverWayService {

	public List<UtcsSignalDriverWay> findSignalDriverWayBySignalId(Integer signalId);
	
	public boolean saveOrUpdateSignalDriverWay(UtcsSignalDriverWay driverWay);
	
	public boolean  delSignalDriverWayBySignalId(Integer signalId);
}
