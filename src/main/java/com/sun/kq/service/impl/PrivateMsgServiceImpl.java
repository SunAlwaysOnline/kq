package com.sun.kq.service.impl;

import com.sun.kq.constant.FileConst;
import com.sun.kq.entity.Keyword;
import com.sun.kq.entity.Rubbish;
import com.sun.kq.model.*;
import com.sun.kq.service.KeywordService;
import com.sun.kq.service.KmzService;
import com.sun.kq.service.PrivateMsgService;
import com.sun.kq.service.RubbishService;
import com.sun.kq.util.ImageDownloadUtil;
import com.sun.kq.util.KeywordsUtil;
import com.sun.kq.util.MsgUtil;
import com.sun.kq.util.UserKeywordCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.sun.kq.constant.URLConst.SEND_PRIVATE_MSG;
import static com.sun.kq.constant.URLConst.URL;

@Service
public class PrivateMsgServiceImpl implements PrivateMsgService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    KeywordService keywordService;

    @Autowired
    RubbishService rubbishService;

    @Autowired
    KmzService kmzService;

    @Override
    public Result sendPrivateMsg(PrivateMsg msg) {
        Result result = restTemplate.postForObject(URL + SEND_PRIVATE_MSG, msg, Result.class);
        return result;
    }


    public ReplyMsg handlePrivateMsg(ReceiveMsg receiveMsg) {
        Long user_id = receiveMsg.getUser_id();
        String raw_message = receiveMsg.getRaw_message();
        if (MsgUtil.getMenu(raw_message) != null) {
            ReplyMsg replyMsg = new ReplyMsg();
            replyMsg.setReply(MsgUtil.getMenu(raw_message));
            return replyMsg;
        }
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
                PrivateMsg privateMsg = new PrivateMsg();
                privateMsg.setMessage("[CQ:image,file=" + FileConst.KMZ_IMG_PREFIX + key + ".jpg]");
                privateMsg.setUser_id(receiveMsg.getUser_id());
                sendPrivateMsg(privateMsg);
            }
            return null;

        }

        if (raw_message.contains("垃圾分类+")) {
            String type = rubbishService.handleRubbishType(raw_message);
            ReplyMsg replyMsg = new ReplyMsg();
            replyMsg.setReply(type);
            return replyMsg;
        }
        if (raw_message.contains("增加垃圾+")) {
            String name = raw_message.split("\\+")[1];
            String type = raw_message.split("\\+")[2];
            String reply = rubbishService.handleAddRubbish(new Rubbish(name, type));
            ReplyMsg replyMsg = new ReplyMsg();
            replyMsg.setReply(reply);
            return replyMsg;
        }
        if (raw_message.contains("列出关键词")) {
            String returnMsg = KeywordsUtil.getWordStringFromList(keywordService.getWordList(receiveMsg.getUser_id()));
            ReplyMsg replyMsg = new ReplyMsg();
            replyMsg.setReply(returnMsg);
            return replyMsg;
        }
        if (raw_message.contains("增加关键词")) {
            String keyword = raw_message.split("\\+")[1];
            boolean isSuccessAdd = keywordService.addKeyword(new Keyword(user_id, keyword));
            ReplyMsg replyMsg = new ReplyMsg();
            if (isSuccessAdd) {
                String keywordListString = KeywordsUtil.getWordStringFromList(keywordService.getWordList(receiveMsg.getUser_id()));
                replyMsg.setReply("增加关键词成功" + "\n" + keywordListString);
            } else {
                replyMsg.setReply("增加关键词失败,请联系767638734");
            }
            return replyMsg;
        }
        if (raw_message.contains("删除关键词")) {
            String keyword = raw_message.split("\\+")[1];
            boolean isSuccessDel = keywordService.delKeyword(new Keyword(user_id, keyword));
            ReplyMsg replyMsg = new ReplyMsg();
            if (isSuccessDel) {
                String keywordListString = KeywordsUtil.getWordStringFromList(keywordService.getWordList(receiveMsg.getUser_id()));
                replyMsg.setReply("删除关键词成功" + "\n" + keywordListString);
            } else {
                replyMsg.setReply("删除关键词失败,不存在该关键词");
            }
            return replyMsg;
        }

        return null;
    }

    @Override
    public void getPrivateMsgOnKeyword(ReceiveMsg receiveMsg) {
        String raw_message = receiveMsg.getRaw_message();
        //获取所有不重复的关键词
        List<String> keywordList = UserKeywordCache.getDistinctKeword();
        for (int i = 0; i < keywordList.size(); i++) {
            String keyword = keywordList.get(i);
            if (raw_message.contains(keyword)) {
                System.out.println("触发关键词：" + keyword);
                //获取所有关注该关键词的用户id
                List<Long> userIdList = UserKeywordCache.getUserIdByKeyword(keyword);
                for (Long user_id : userIdList) {
                    System.out.println(keyword + "关键词发送给用户" + user_id);
                    PrivateMsg privateMsg = MsgUtil.getPrivateMsgByReceiveMsgOnkeyword(user_id, keyword, receiveMsg);
                    sendPrivateMsg(privateMsg);
                }
            }
        }
    }

}
