package com.sun.kq.service.impl;

import com.sun.kq.constant.URLConst;
import com.sun.kq.model.MzImg;
import com.sun.kq.service.KmzService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KmzServiceImpl implements KmzService {
    @Override
    public List<String> getKmzImageKey(int n) {
        List<String> keyList = new ArrayList<>();
        try {
            Element element = Jsoup.connect(URLConst.HUABAN_MZ_INDEX).get().body();
            List<MzImg> mzImgList = parsePinsFromXml(element.toString());
            String lastPid = mzImgList.get(mzImgList.size() - 1).getPinId();

            for (int page = 1; page <= n / 2 + 1; page++) {
                System.out.println("运行了一次");
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
