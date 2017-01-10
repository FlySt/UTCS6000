package com.ncjk.utcs.common.webservice.server.interfaces;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Created by swl on 2016/12/30.
 */
public interface CommonCommand {

    /**
     * 创建错误命令
     * @param errObj
     * @param errType
     * @param message
     * @return
     */
    String createError(String errObj,String errType,Element message);

    /**
     * 创建命令头部
     * @param type
     * @return
     */
    String createHeader(String type);

    /**
     * 设置命令头部值
     * @param document
     * @return
     */
    Document setHeaderValue(Document document,Element message);
    /**
     * 格式化输出
     * @param document
     * @return
     */
    String outPut(Document document);
}
