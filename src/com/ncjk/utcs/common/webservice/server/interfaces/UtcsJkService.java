package com.ncjk.utcs.common.webservice.server.interfaces;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by swl on 2016/12/29.
 * 国标webservice接口
 */
@WebService(targetNamespace = "http://utcs.ncjk.com/")
public interface UtcsJkService {
    String utcsCommand(@WebParam(name="command",targetNamespace="http://utcs.ncjk.com/") String command);
}
