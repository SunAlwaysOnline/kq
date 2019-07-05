package com.sun.kq.service;

import com.sun.kq.model.PrivateMsg;
import com.sun.kq.model.ReplyMsg;
import com.sun.kq.model.Result;

import javax.servlet.http.HttpServletRequest;

public interface MsgService {



    /**
     * 接收消息(私聊、群、讨论组、其他)并响应
     *
     * @param request
     * @return
     */
    ReplyMsg receive(HttpServletRequest request);
}
