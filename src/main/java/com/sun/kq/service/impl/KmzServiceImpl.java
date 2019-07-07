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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KmzServiceImpl implements KmzService {
    @Override
    public List<String> getKmzImageURL(int n) {
        List<String> urlList = new ArrayList<>();
        try {
            Element element = Jsoup.connect(URLConst.HUABAN_MZ_INDEX).get().body();
            List<MzImg> mzImgList = parsePinsFromXml(element.toString());
            String lastPid = mzImgList.get(mzImgList.size() - 1).getPinId();
            //Element element2 = Jsoup.connect("https://huaban.com/boards/481662/?jxt53umu&max=" + lastPid + "&limit=20&wfl=1").get().body();
            //System.out.println("第二波");
            //List<MzImg> mzImgList2 = parsePinsFromXml(element2.toString());

            //if (n < 20) {
                for (int i = 0; i < 30; i++) {
                    urlList.add("https://hbimg.huabanimg.com/" + mzImgList.get(i).getKey());
               // }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlList;
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
