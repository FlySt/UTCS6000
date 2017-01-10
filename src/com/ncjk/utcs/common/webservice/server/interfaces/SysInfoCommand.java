package com.ncjk.utcs.common.webservice.server.interfaces;

import org.dom4j.Element;

/**
 * Created by swl on 2016/12/30.
 */
public interface SysInfoCommand {

    /**
     * 系统参数解析
     * @param message
     * @return
     */
    String sysInfoAnalysis(Element message);
}
