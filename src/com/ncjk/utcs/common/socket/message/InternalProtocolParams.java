package com.ncjk.utcs.common.socket.message;

/**
 * Created by swl on 2017/1/10.
 * 内部协议通信参数集
 */
public class InternalProtocolParams {

    public enum  MessageType {
        LOGIN,PING,ASK,REPLY,LOGOUT,TIMER,PUSH,OLDSIGNAL,ERROR
    }
    /**包格式:包长+令牌+标志+命令字+发送码/返回码+数据内容**/
    //包长  5位
    public final static int packageLength = 5;
    //令牌长度 32位
    public final static int tokenLength = 32;
    //标志位长度 1位
    public final static int signLength = 1;
    //返回码长度 4 位
    public final static int responseCodeLength = 4;
    //登录初始令牌
    public final static String  logToken = "JK_UTCS_INSIDETOKEN_WH0219_KANG_";
}
