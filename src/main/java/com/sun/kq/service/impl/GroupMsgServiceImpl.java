package com.sun.kq.service.impl;

import com.sun.kq.constant.FileConst;
import com.sun.kq.constant.URLConst;
import com.sun.kq.entity.Rubbish;
import com.sun.kq.model.*;
import com.sun.kq.model.ReceiveMsg;
import com.sun.kq.model.ReplyMsg;
import com.sun.kq.service.GroupMsgService;
import com.sun.kq.service.KmzService;
import com.sun.kq.service.PrivateMsgService;
import com.sun.kq.service.RubbishService;
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

    @Override
    public Result sendGroupMsg(GroupMsg groupMsg) {
        Result result = restTemplate.postForObject(URLConst.URL + URLConst.SEND_GROUP_MSG, groupMsg, Result.class);
        return result;
    }


    public ReplyMsg handleGroupMsg(ReceiveMsg receiveMsg) {
        String raw_message = receiveMsg.getRaw_message();
        privateMsgService.getPrivateMsgOnKeyword(receiveMsg);
        //看妹子不需要@我
        if (raw_message.contains("看妹子")) {
            int n = 1;
            try {
                n = Integer.parseInt(raw_message.split("来")[1].split("张")[0]);
            } catch (Exception e) {
            }
            if (n < 0 || n > 30) {
                ReplyMsg replyMsg = new ReplyMsg();
                replyMsg.setReply("求求你做个正常的人");
                replyMsg.setAt_sender(true);
                return replyMsg;
            }

            List<String> urlList = kmzService.getKmzImageKey(n);
            for (String key : urlList) {
                try {
                    boolean exist = ImageDownloadUtil.isImgExist(key);
                    if (!exist) {
                        ImageDownloadUtil.downloadImg(key);
                        System.out.println("图像不存在");
                    } else {
                        System.out.println("图像存在");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GroupMsg groupMsg = new GroupMsg();
                groupMsg.setMessage("[CQ:image,file=" + FileConst.KMZ_IMG_PREFIX + key + ".jpg]");
                groupMsg.setGroup_id(receiveMsg.getGroup_id());
                sendGroupMsg(groupMsg);
            }
            return null;

        }
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
            if (raw_message.contains("死机")) {
                ReplyMsg replyMsg = new ReplyMsg();
                replyMsg.setReply("好嘞");
                return replyMsg;
            }
        }
        return null;
    }
}
