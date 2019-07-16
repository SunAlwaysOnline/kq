package com.sun.kq.service.impl;

import com.sun.kq.constant.FileConst;
import com.sun.kq.constant.URLConst;
import com.sun.kq.entity.Rubbish;
import com.sun.kq.model.*;
import com.sun.kq.model.ReceiveMsg;
import com.sun.kq.model.ReplyMsg;
import com.sun.kq.service.*;
import com.sun.kq.util.ImageDownloadUtil;
import com.sun.kq.util.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
public class GroupMsgServiceImpl implements GroupMsgService {

    @Autowired
    PrivateMsgService privateMsgService;

    @Autowired
    RubbishService rubbishService;

    @Autowired
    KmzService kmzService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KeywordService keywordService;

    @Override
    public Result sendGroupMsg(GroupMsg groupMsg) {
        Result result = restTemplate.postForObject(URLConst.URL + URLConst.SEND_GROUP_MSG, groupMsg, Result.class);
        return result;
    }


    public ReplyMsg handleGroupMsg(ReceiveMsg receiveMsg) {
        String raw_message = receiveMsg.getRaw_message();

        //关键词监控
        keywordService.getPrivateMsgOnKeyword(receiveMsg);

        //KMZ不需要@我
        if (raw_message.contains("看妹子")) {
            kmzService.sendMZPicByMsg(receiveMsg);
        }

        //只有at我的才可以触发
        if (raw_message.contains("CQ:at,qq=1767638734")) {

            //查询指令是否标准，不标准则反馈提示
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
