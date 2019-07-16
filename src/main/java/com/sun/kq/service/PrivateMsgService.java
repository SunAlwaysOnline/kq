package com.sun.kq.service;

import com.sun.kq.model.PrivateMsg;
import com.sun.kq.model.ReceiveMsg;
import com.sun.kq.model.ReplyMsg;
import com.sun.kq.model.Result;

/**
 * 私聊消息服务接口
 */
public interface PrivateMsgService {

    /**
     * 发送私聊消息
     *
     * @param msg
     * @return
     */
    Result sendPrivateMsg(PrivateMsg msg);

    /**
     * 由收到的私聊消息的内容转发给特定的方法
     *
     * @param receiveMsg
     * @return
     */
    ReplyMsg handlePrivateMsg(ReceiveMsg receiveMsg);

}
