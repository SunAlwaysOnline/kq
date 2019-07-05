package com.sun.kq.service.impl;

import com.sun.kq.entity.Rubbish;
import com.sun.kq.model.PrivateMsg;
import com.sun.kq.model.ReceiveMsg;
import com.sun.kq.model.ReplyMsg;
import com.sun.kq.service.GroupMsgService;
import com.sun.kq.service.PrivateMsgService;
import com.sun.kq.service.RubbishService;
import com.sun.kq.util.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMsgServiceImpl implements GroupMsgService {

    @Autowired
    PrivateMsgService privateMsgService;

    @Autowired
    RubbishService rubbishService;

    public ReplyMsg handleGroupMsg(ReceiveMsg receiveMsg) {
        String raw_message = receiveMsg.getRaw_message();
        privateMsgService.getPrivateMsgOnKeyword(receiveMsg);
        //只有at我的才可以触发
        if (raw_message.contains("CQ:at,qq=1767638734")) {
            if (MsgUtil.getMenu(raw_message) != null) {
                ReplyMsg replyMsg = new ReplyMsg();
                replyMsg.setReply(MsgUtil.getMenu(raw_message));
                return replyMsg;
            }
            if (raw_message.contains("垃圾分类+")) {
                String type = rubbishService.handleRubbishType(raw_message);
                ReplyMsg replyMsg = new ReplyMsg();
                replyMsg.setReply(type);
                replyMsg.setAt_sender(true);
                return replyMsg;
            }
            if (raw_message.contains("增加垃圾+")) {
                String name = raw_message.split("\\+")[1];
                String type = raw_message.split("\\+")[2];
                String reply = rubbishService.handleAddRubbish(new Rubbish(name, type));
                ReplyMsg replyMsg = new ReplyMsg();
                replyMsg.setReply(reply);
                replyMsg.setAt_sender(true);
                return replyMsg;
            }
        }
        return null;
    }
}