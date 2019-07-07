package com.sun.kq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.kq.entity.Keyword;
import com.sun.kq.entity.Rubbish;
import com.sun.kq.model.PrivateMsg;
import com.sun.kq.model.ReceiveMsg;
import com.sun.kq.model.ReplyMsg;
import com.sun.kq.model.Result;
import com.sun.kq.service.*;
import com.sun.kq.util.KeywordsUtil;
import com.sun.kq.util.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.sun.kq.constant.URLConst.*;

@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RubbishService rubbishService;

    @Autowired
    KeywordService keywordService;

    @Autowired
    PrivateMsgService privateMsgService;

    @Autowired
    GroupMsgService groupMsgService;


    @Override
    public ReplyMsg receive(HttpServletRequest request) {
        String msg = null;
        try {
            msg = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (msg == null) {
            return null;
        }

        //解析收到的消息
        ReceiveMsg receiveMsg = null;
        try {
            receiveMsg = new ObjectMapper().readValue(msg, ReceiveMsg.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return handleReceiveMsg(receiveMsg);


    }


    /**
     * 由收到的讨论组消息的内容转发给特定的方法（暂时用不到，先留着）
     *
     * @param receiveMsg
     */
    private ReplyMsg handleDiscussMsg(ReceiveMsg receiveMsg) {
        return null;
    }


    /**
     * 由收到的消息类型转发给特定的方法
     *
     * @param receiveMsg
     */
    private ReplyMsg handleReceiveMsg(ReceiveMsg receiveMsg) {
        String post_type = receiveMsg.getPost_type();
        switch (post_type) {

            case "message":
                //收到消息
                String message_type = receiveMsg.getMessage_type();
                switch (message_type) {
                    case "private":
                        //私聊消息
                        return privateMsgService.handlePrivateMsg(receiveMsg);
                    case "group":
                        //群消息
                        return groupMsgService.handleGroupMsg(receiveMsg);
                    case "discuss":
                        return handleDiscussMsg(receiveMsg);
                }
                break;

            case "notice":
                //群、讨论组变动等通知类事件
                break;

            case "request":
                //加好友请求、加群请求／邀请
                break;
            case "meta_event":
                //生命周期元事件
                //可以初始化缓存
                //keywordService.getAllDataIntoCache();
                break;
        }

        return null;
    }

}
