package com.ncjk.utcs.common.service;

import com.ncjk.utcs.common.service.interfaces.UtcsWebService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2016/12/1.
 */
public class WebServiceClient {
    /**
     * @param args
     */
    public static void main(String[] args) {

        //Resource resource= new FileSystemResource("F:/workspaces4me2013/.metadata/.me_tcat/WEB-INF/classes/applicationContext.xml");
        //BeanFactory factory= new XmlBeanFactory(resource );
        ApplicationContext factory = new ClassPathXmlApplicationContext("/spring-client.xml");
        UtcsWebService client = (UtcsWebService)factory.getBean("client");
        String res = client.Command(16,"","");
        System.out.println("rws:"+res);

    }
}
