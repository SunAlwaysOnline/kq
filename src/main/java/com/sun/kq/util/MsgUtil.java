package com.sun.kq.util;

import com.sun.kq.model.PrivateMsg;
import com.sun.kq.model.ReceiveMsg;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MsgUtil {

    //有人加入了群
    //{"group_id":865452446,"notice_type":"group_increase","operator_id":2455279933,"post_type":"notice","self_id":1767638734,"sub_type":"approve","time":1562212021,"user_id":1553060962}


    /**
     * 由收到消息的情况，发送私聊消息给设定的人
     *
     * @param receiveMsg
     * @return
     */
    public static PrivateMsg getPrivateMsgByReceiveMsgOnkeyword(Long user_id, String keyword, ReceiveMsg receiveMsg) {
        PrivateMsg privateMsg = new PrivateMsg();
        privateMsg.setUser_id(user_id);
        StringBuilder str = new StringBuilder();
        str.append("关键词：" + keyword + "\n");
        str.append("发送者QQ：" + receiveMsg.getUser_id() + "\n");
        str.append("昵称：" + receiveMsg.getSender().getNickname() + "\n");
        str.append("所在群号：" + receiveMsg.getGroup_id() + "\n");
        str.append("内容：" + receiveMsg.getRaw_message() + "\n");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        str.append("时间：" + sdf.format(new Date(receiveMsg.getTime() * 1000)) + "\n");
        privateMsg.setMessage(str.toString());
        return privateMsg;

    }


    /**
     * 判断是否是已知的指令
     *
     * @param raw_message
     * @return
     */
    public static boolean isKnownMsg(String raw_message) {
        String[] knownMsg = new String[]{
                "垃圾分类", "增加垃圾",
                "设置关键词", "增加关键词", "删除关键词", "列出关键词", "增加监听群组", "删除监听群组", "列出监听群组",
                "我要充值"};
        for (String temp : knownMsg) {
            if (raw_message.contains(temp)) {
                return true;
            }
        }
        return false;
    }

    public static String getMenu(String raw_message) {
        StringBuilder str = new StringBuilder();
        if (!isKnownMsg(raw_message)) {
            str.append("没听懂" + "\n");
            str.append("目前支持的指令有:" + "\n");
            str.append("垃圾分类" + "\n");
            str.append("设置关键词" + "\n");
            str.append("请输入某一个指令以继续");
        } else if (raw_message.contains("垃圾分类") && !raw_message.contains("+")) {
            str.append("输入垃圾分类+垃圾名称获得分类" + "\n");
            str.append("例如: 垃圾分类+塑料");
        } else if (raw_message.contains("设置关键词")) {
            str.append("增加关键词" + "\n");
            str.append("删除关键词" + "\n");
            str.append("列出关键词" + "\n");
            str.append("增加监听群组" + "\n");
            str.append("删除监听群组" + "\n");
            str.append("请输入某一个指令以继续");
        } else if (raw_message.contains("增加关键词") && !raw_message.contains("+")) {
            str.append("请输入增加关键词+关键词" + "\n");
            str.append("例如: 增加关键词+二手车");
        } else if (raw_message.contains("删除关键词") && !raw_message.contains("+")) {
            str.append("请输入删除关键词+关键词" + "\n");
            str.append("例如: 删除关键词+二手车");
        } else if (raw_message.contains("增加监听群组") && !raw_message.contains("+")) {
            str.append("请输入增加监听群组+群号" + "\n");
            str.append("例如: 增加监听群组+123456");
        } else if (raw_message.contains("删除监听群组") && !raw_message.contains("+")) {
            str.append("请输入删除监听群组+群号" + "\n");
            str.append("例如: 删除监听群组+123456");
        }

        return str.length() == 0 ? null : str.toString();
    }

}

