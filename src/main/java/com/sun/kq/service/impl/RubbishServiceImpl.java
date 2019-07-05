package com.sun.kq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.kq.constant.URLConst;
import com.sun.kq.dao.RubbishMapper;
import com.sun.kq.entity.Rubbish;
import com.sun.kq.service.RubbishService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Service
public class RubbishServiceImpl extends ServiceImpl<RubbishMapper, Rubbish> implements RubbishService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RubbishMapper rubbishMapper;

    @Autowired
    RestTemplate restTemplate;


    public String handleRubbishType(String raw_message) {
        StringBuilder returnMsg = new StringBuilder();
        String name = raw_message.split("\\+")[1];
        Rubbish rubbish = getRubbishByName(name);
        if (null == rubbish) {
            returnMsg.append("暂未查找到该垃圾的分类" + "\n");
            returnMsg.append("你可以发送增加垃圾+西瓜皮+湿垃圾来告诉我" + "\n");
            returnMsg.append("例如：增加垃圾+西瓜皮+湿垃圾");
        } else {
            returnMsg.append(name + "属于" + rubbish.getType());
        }
        return returnMsg.toString();
    }

    public String handleAddRubbish(Rubbish rubbish) {
        StringBuilder returnMsg = new StringBuilder();
        int line = add(rubbish);
        if (line == -1) {
            returnMsg.append("该垃圾已经存在");
        } else if (line == 0) {
            returnMsg.append("数据插入失败");
        } else {
            returnMsg.append("增加垃圾分类成功");
        }
        return returnMsg.toString();
    }

    @Override
    public Rubbish getRubbishByName(String name) {
        Rubbish rubbish = null;
        //首先在redis中查找
        rubbish = getRubbishFromRedis(name);
        //System.out.println("查询redis");
        if (rubbish == null) {
            //再去数据库中查找
            rubbish = getRubbishFromDB(name);
            //System.out.println("查询数据库");
            if (rubbish == null) {
                rubbish = getRubbishFromAPI(name);
                //System.out.println("查询api");
            }

        }
        return rubbish;
    }

    @Override
    public int add(Rubbish rubbish) {
        int line = 0;
        //先查找是否有该垃圾
        QueryWrapper<Rubbish> rubbishQW = new QueryWrapper<>();
        rubbishQW.eq("name", rubbish.getName());
        Rubbish temp = rubbishMapper.selectOne(rubbishQW);
        if (temp != null) {
            return -1;
        }
        line = rubbishMapper.insert(rubbish);
        return line;
    }

    public Rubbish getRubbishFromRedis(String name) {
        Rubbish rubbish = (Rubbish) redisTemplate.opsForValue().get(name);
        return rubbish;
    }

    public Rubbish getRubbishFromDB(String name) {
        QueryWrapper<Rubbish> rubbishQW = new QueryWrapper<>();
        rubbishQW.eq("name", name);
        Rubbish rubbish = rubbishMapper.selectOne(rubbishQW);
        saveRubbishFromDBIntoRedis(rubbish);
        return rubbish;
    }

    public Rubbish getRubbishFromAPI(String name) {
        String type = null;
        try {
            name = URLEncoder.encode(name, "UTF-8");
            String body = restTemplate.getForEntity(URLConst.GET_RUBBISH_TYPE + name, String.class).getBody();
            Document document = Jsoup.parse(body);
            //定位到row
            Element element = document.select("div[class=row]").get(2);
            Elements select = element.select("div").select("h1").select("span");
            String preType = select.text();
            if (preType == null || !preType.contains("属于")) {
                return null;
            }
            type = select.get(2).text();
            name = URLDecoder.decode(name, "UTF-8");
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        Rubbish rubbish = new Rubbish(name, type);
        saveRubbishFromAPTIntoDB(rubbish);
        return rubbish;
    }

    public boolean saveRubbishFromAPTIntoDB(Rubbish rubbish) {
        if (rubbish == null) {
            return false;
        }
        int line = rubbishMapper.insert(rubbish);
        return line > 0;
    }

    public boolean saveRubbishFromDBIntoRedis(Rubbish rubbish) {
        if (rubbish == null) {
            return false;
        }
        redisTemplate.opsForValue().set(rubbish.getName(), rubbish);
        return true;
    }
}
