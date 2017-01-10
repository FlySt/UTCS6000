package com.ncjk.ticp.ticp.wgb;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import javax.xml.namespace.QName;

/**
 * Created by Administrator on 2016/12/30.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        //这个是用cxf 客户端访问cxf部署的webservice服务
        //千万记住，访问cxf的webservice必须加上namespace ,否则通不过
        //现在又另外一个问题，传递过去的参数服务端接收不到
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = dcf.createClient("http://192.168.200.244:7080/UTCS6000/webservice/UtcsJk/?wsdl");
        //url为调用webService的wsdl地址
        QName name=new QName("http://server.webservice.common.utcs.ncjk.com/","UtcsJkService");
        //namespace是命名空间，methodName是方法名
        String xmlStr = "<?xml version='1.0' encoding='UTF-8'?><Message><Version>1.0</Version><Token/><From><Address><Sys>UTCS</Sys><SubSys/><Instance/></Address></From><To><Address><Sys>TICP</Sys><SubSys/><Instance/></Address></To><Type>REQUEST</Type><Seq>20161219141840000001</Seq><Body><Operation order='1' name='Login'><SDO_User><UserName>Admin</UserName><Pwd>12345</Pwd></SDO_User></Operation></Body></Message>";
        //paramvalue为参数值
        Object[] objects=client.invoke(name,xmlStr);
        //调用web Service//输出调用结果
        System.out.println(objects[0].toString());
    }
}
