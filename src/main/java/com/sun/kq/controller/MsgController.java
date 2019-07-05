package com.sun.kq.controller;

import com.sun.kq.model.PrivateMsg;
import com.sun.kq.model.ReplyMsg;
import com.sun.kq.model.Result;
import com.sun.kq.service.MsgService;
import com.sun.kq.service.PrivateMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    MsgService msgService;

    @Autowired
    PrivateMsgService privateMsgService;

    @RequestMapping("/sendPrivateMsg")
    public Result sendPrivateMsg(PrivateMsg msg) {
        return privateMsgService.sendPrivateMsg(msg);
    }

    /**
     * 消息上报接口
     *
     * @param request
     */
    @RequestMapping("/receive")
    public ReplyMsg receive(HttpServletRequest request) {
        return msgService.receive(request);
    }

}
