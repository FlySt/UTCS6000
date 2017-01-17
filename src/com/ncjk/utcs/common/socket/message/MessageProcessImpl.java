package com.ncjk.utcs.common.socket.message;

import com.ncjk.utcs.common.socket.message.Interfaces.MessageProcess;
import org.springframework.stereotype.Service;

/**
 * Created by swl on 2017/1/10.
 * 消息处理实现类
 */
@Service("messageProcess")
public class MessageProcessImpl implements MessageProcess {

    /**
     * 获取消息类型
     * @param message
     * @return
     */
    @Override
    public InternalProtocolParams.MessageType getMessageType(String message) {
        return null;
    }
}
