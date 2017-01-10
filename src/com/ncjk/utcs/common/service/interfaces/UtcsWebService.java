package com.ncjk.utcs.common.service.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by Administrator on 2016/12/1.
 */
@WebService(targetNamespace ="urn:signal")
public interface UtcsWebService {
    String Command(@WebParam(name="cmdid",targetNamespace="urn:signal") int cmdid,
                   @WebParam(name="server",targetNamespace="urn:signal")String server,
                   @WebParam(name="cmdbuffer",targetNamespace="urn:signal")String cmdbuffer);
}
