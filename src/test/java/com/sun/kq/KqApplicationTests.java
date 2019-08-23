package com.sun.kq;

import com.sun.kq.constant.URLConst;
import com.sun.kq.model.MzImg;
import com.sun.kq.service.CsdnService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KqApplicationTests {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CsdnService csdnService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getURLEncodedName() {
        try {
            String name = URLEncoder.encode("花生", "UTF-8");
            System.out.println(name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getType() {
        String body = restTemplate.getForEntity(URLConst.GET_RUBBISH_TYPE + "%E8%8A%B1%E7%94%9F", String.class).getBody();
        Document document = Jsoup.parse(body);
        //定位到row
        Element element = document.select("div[class=row]").get(2);
        Elements select = element.select("div").select("h1").select("span");
        String type = select.get(2).text();
        System.out.println(type);
    }

    @Test
    public void getMZ() throws IOException {
        Element element = Jsoup.connect(URLConst.HUABAN_MZ_INDEX).get().body();
        List<MzImg> mzImgList = parsePinsFromXml(element.toString());
        mzImgList.forEach(System.out::println);
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
            pin.setPinId(m.group(1));
            pin.setKey(m.group(2));
            pin.setType(m.group(3));
            pins.add(pin);
        }
        return pins;
    }

}
