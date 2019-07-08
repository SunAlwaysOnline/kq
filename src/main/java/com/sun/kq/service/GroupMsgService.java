package com.sun.kq.service;

import com.sun.kq.model.GroupMsg;
import com.sun.kq.model.ReceiveMsg;
import com.sun.kq.model.ReplyMsg;
import com.sun.kq.model.Result;

public interface GroupMsgService {

    /**
     * 发送群消息
     *
     * @param groupMsg
     * @return
     */
    Result sendGroupMsg(GroupMsg groupMsg);

    /**
     * 由收到的群消息的内容转发给特定的方法
     *
     * @param receiveMsg
     */
    ReplyMsg handleGroupMsg(ReceiveMsg receiveMsg);

}
