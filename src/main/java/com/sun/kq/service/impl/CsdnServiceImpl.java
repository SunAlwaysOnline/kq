package com.sun.kq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.kq.constant.URLConst;
import com.sun.kq.dao.CsdnMapper;
import com.sun.kq.entity.Csdn;
import com.sun.kq.service.CsdnService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class CsdnServiceImpl extends ServiceImpl<CsdnMapper, Csdn> implements CsdnService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CsdnMapper csdnMapper;


    @Override
    public Csdn getCsdnHomeInfo() {
        try {
            Document document = Jsoup.connect(URLConst.CSDN_HOME).get();

            Elements elements = document.select("span[class=count]");
            Integer article_sum = Integer.valueOf(elements.get(0).text());
            Integer fan_sum = Integer.valueOf(elements.get(1).text());
            Integer praise_sum = Integer.valueOf(elements.get(2).text());
            Integer comment_sum = Integer.valueOf(elements.get(3).text());

            Element element = document.select("div[class=grade-box clearfix]").get(0);
            String level_unformat = element.select("dl").get(0).select("dd")
                    .select("a").attr("title");
            Integer level = Integer.valueOf(level_unformat.substring(0, 1));
            Integer visit_sum = Integer.valueOf(element.select("dl").get(1).select("dd").attr("title"));
            Integer integral = Integer.valueOf(element.select("dl").get(2).select("dd").attr("title"));
            Integer rank = Integer.valueOf(element.select("dl").get(3).select("dd").text());

            Csdn csdn = new Csdn();
            csdn.setArticle_sum(article_sum);
            csdn.setFan_sum(fan_sum);
            csdn.setPraise_sum(praise_sum);
            csdn.setComment_sum(comment_sum);
            csdn.setLevel(level);
            csdn.setVisit_sum(visit_sum);
            csdn.setIntegral(integral);
            csdn.setRank(rank);
            System.out.println(csdn);
            csdnMapper.insert(csdn);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
