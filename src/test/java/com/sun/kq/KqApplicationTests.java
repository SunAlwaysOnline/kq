package com.sun.kq;

import com.sun.kq.constant.URLConst;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KqApplicationTests {

    @Autowired
    RestTemplate restTemplate;

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

}
