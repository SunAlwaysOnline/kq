package com.sun.kq.service.impl;

import com.sun.kq.constant.FileConst;
import com.sun.kq.constant.URLConst;
import com.sun.kq.enums.MessageType;
import com.sun.kq.model.*;
import com.sun.kq.service.GroupMsgService;
import com.sun.kq.service.KmzService;
import com.sun.kq.service.PrivateMsgService;
import com.sun.kq.util.ImageDownloadUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KmzServiceImpl implements KmzService {

    @Autowired
    KmzService kmzService;

    @Autowired
    GroupMsgService groupMsgService;

    @Autowired
    PrivateMsgService privateMsgService;

    @Override
    public ReplyMsg sendMZPicByMsg(ReceiveMsg receiveMsg) {
        String raw_message = receiveMsg.getRaw_message();
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
            String message = "[CQ:image,file=" + FileConst.KMZ_IMG_PREFIX + key + ".jpg]";

            switch (MessageType.getEnumByType(receiveMsg.getMessage_type())) {
                case GROUP:
                    GroupMsg groupMsg = new GroupMsg();
                    groupMsg.setMessage(message);
                    groupMsg.setGroup_id(receiveMsg.getGroup_id());
                    groupMsgService.sendGroupMsg(groupMsg);
                    break;
                case PRIVATE:
                    PrivateMsg privateMsg = new PrivateMsg();
                    privateMsg.setMessage(message);
                    privateMsg.setUser_id(receiveMsg.getUser_id());
                    privateMsgService.sendPrivateMsg(privateMsg);
                    break;
            }
        }
        return null;
    }

    @Override
    public List<String> getKmzImageKey(int n) {
        List<String> keyList = new ArrayList<>();
        try {
            Element element = Jsoup.connect(URLConst.HUABAN_MZ_INDEX).get().body();
            List<MzImg> mzImgList = parsePinsFromXml(element.toString());
            String lastPid = mzImgList.get(mzImgList.size() - 1).getPinId();

            for (int page = 1; page <= n / 2 + 1; page++) {
                Element tempElement = Jsoup.connect("https://huaban.com/boards/481662/?jxt53umu&max=" + lastPid + "&limit=20&wfl=" + page).get().body();
                List<MzImg> tempUrlList = parsePinsFromXml(tempElement.toString());
                lastPid = tempUrlList.get(tempUrlList.size() - 1).getPinId();
                mzImgList.addAll(tempUrlList);
            }

            while (true) {
                int random = new Random().nextInt(mzImgList.size() - 1);
                String key = mzImgList.get(random).getKey();
                if (!keyList.contains(key)) {
                    keyList.add(key);
                }
                if (keyList.size() == n) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keyList;
    }


    private List<MzImg> parsePinsFromXml(String xmlStr) {
        List<MzImg> pins = new ArrayList<MzImg>();
        String pattern = "\\{\"pin_id\":(\\d+),.+?\"key\":\"(.+?)\",.\"type\":\"image/(.+?)\",";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(xmlStr);
        while (m.find()) {
            MzImg pin = new MzImg();
            System.out.println(m.group());
            pin.setPinId(m.group(1));
            pin.setKey(m.group(2));
            pin.setType(m.group(3));
            pins.add(pin);
            System.out.println(pin.getPinId() + "," + pin.getKey() + "," + pin.getType());
        }
        return pins;
    }

}
