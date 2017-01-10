package com.ncjk.utcs.modules.resources.resources.services.interfaces;

import com.ncjk.utcs.modules.resources.resources.pojo.UtcsSignalLight;

import java.util.List;



public interface ISignalLightService {
	
	
	public List<UtcsSignalLight> findSignalLightBySignal(Integer siganlId);
	
	
	public boolean saveOrUpdateSignalLight(UtcsSignalLight signalLight);
	
	public boolean delSignalLightBySignal(Integer siganlId);
}

