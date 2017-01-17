package com.ncjk.utcs.common.socket.message.Interfaces;

import com.ncjk.utcs.common.socket.message.InternalProtocolParams;

/**
 * Created by swl on 2017/1/10.
 * 消息处理接口类
 */
public interface MessageProcess {

    /**
     * 获取消息类型
     * @param message
     * @return
     */
    InternalProtocolParams.MessageType getMessageType(String message);
}
