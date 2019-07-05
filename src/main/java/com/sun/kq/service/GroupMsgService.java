package com.sun.kq.service;

import com.sun.kq.model.ReceiveMsg;
import com.sun.kq.model.ReplyMsg;

public interface GroupMsgService {

    /**
     * 由收到的群消息的内容转发给特定的方法
     *
     * @param receiveMsg
     */
    ReplyMsg handleGroupMsg(ReceiveMsg receiveMsg);
}
