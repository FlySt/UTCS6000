package com.ncjk.utcs.common.webservice.server;

import com.ncjk.utcs.common.webservice.server.interfaces.CommadAnalysis;
import com.ncjk.utcs.common.webservice.server.interfaces.UtcsJkService;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by swl on 2016/12/29.
 */
@WebService(endpointInterface= "com.ncjk.utcs.common.webservice.server.interfaces.UtcsJkService",
        serviceName="UtcsJkService",targetNamespace = "http://utcs.ncjk.com/")
public class UtcsJkServiceImpl implements UtcsJkService {
    @Resource
    CommadAnalysis commadAnalysis;
    @Override
    public String utcsCommand(@WebParam(name="command", targetNamespace="http://utcs.ncjk.com/")String command) {
       return commadAnalysis.annlysis(command);
    }
}
