package com.ncjk.utcs.common.webservice.server.interfaces;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Created by swl on 2016/12/30.
 * 命令解析
 */
public interface CommadAnalysis {
    /**
     * 解析命令
     * @param command
     * @return
     */
    String annlysis(String command);


}
