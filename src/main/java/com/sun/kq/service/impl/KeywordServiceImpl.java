package com.sun.kq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.kq.dao.KeywordMapper;
import com.sun.kq.entity.Keyword;
import com.sun.kq.service.KeywordService;
import com.sun.kq.util.UserKeywordCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class KeywordServiceImpl extends ServiceImpl<KeywordMapper, Keyword> implements KeywordService {

    @Autowired
    KeywordMapper keywordMapper;

    @PostConstruct
    public void init() {
        //将数据库中关键词加载进缓存
        getAllDataIntoCache();
    }

    @Override
    public boolean addKeyword(Keyword keyword) {
        int line = keywordMapper.insert(keyword);
        if (line > 0) {
            UserKeywordCache.add(keyword.getUser_id(), keyword.getWord());
        }
        return line > 0;
    }

    @Override
    public boolean delKeyword(Keyword keyword) {
        QueryWrapper<Keyword> keywordQW = new QueryWrapper<>();
        keywordQW.eq("user_id", keyword.getUser_id())
                .eq("word", keyword.getWord());
        int line = keywordMapper.delete(keywordQW);
        if (line > 0) {
            UserKeywordCache.del(keyword.getUser_id(), keyword.getWord());
        }
        return line > 0;
    }

    @Override
    public List<String> getWordList(Long user_id) {
        //先查缓存
        List<String> keywordListInCache = UserKeywordCache.getKeywordList(user_id);
        System.out.println("查询关键词列表-查询缓存");
        if (null == keywordListInCache) {
            //查询数据库
            System.out.println("查询关键词列表-查询数据库");
            List<String> wordList = new ArrayList<>();
            QueryWrapper<Keyword> keywordQW = new QueryWrapper<>();
            keywordQW.eq("user_id", user_id);
            List<Keyword> keywordListInDB = keywordMapper.selectList(keywordQW);
            for (Keyword temp : keywordListInDB) {
                wordList.add(temp.getWord());
            }
            //存入缓存
            UserKeywordCache.addList(user_id, wordList);
            return wordList;
        }
        return keywordListInCache;
    }

    public void getAllDataIntoCache() {
        List<Keyword> allData = keywordMapper.getAllData();
        //此时Keyword中的word是数据库中以，分割的word
        for (Keyword temp : allData) {
            String[] wordArray = temp.getWord().split(",");
            List<String> wordList = new ArrayList<>(Arrays.asList(wordArray));
            UserKeywordCache.addList(temp.getUser_id(), wordList);
        }
        System.out.println("----------关键词注入缓存成功----------");
    }


}
