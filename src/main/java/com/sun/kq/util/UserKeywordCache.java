package com.sun.kq.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.kq.dao.KeywordMapper;
import com.sun.kq.entity.Keyword;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserKeywordCache {

    private static ConcurrentHashMap<Long, List<String>> keywordCache = new ConcurrentHashMap<>();

    /**
     * 加入用户id与单个关键词
     *
     * @param user_id
     * @param keyword
     */
    public static void add(Long user_id, String keyword) {
        List<String> keywordList = keywordCache.get(user_id);
        if (keywordList == null) {
            keywordList = new ArrayList<>();
        }
        keywordList.add(keyword);
        keywordCache.put(user_id, keywordList);
    }

    /**
     * 加入用户id与关键词列表
     *
     * @param user_id
     * @param list
     */
    public static void addList(Long user_id, List<String> list) {
        keywordCache.put(user_id, list);
    }

    /**
     * 删除用户id下指定关键词
     *
     * @param user_id
     * @param keyword
     */
    public static void del(Long user_id, String keyword) {
        List<String> keywordList = keywordCache.get(user_id);
        if (keywordList == null || keywordList.size() == 0) {
            return;
        }
        keywordList.remove(keyword);
    }

    /**
     * 获取用户id下的关键词列表
     *
     * @param user_id
     * @return
     */
    public static List<String> getKeywordList(Long user_id) {
        return keywordCache.get(user_id);
    }

    /**
     * 由指定关键词获取存在该关键词的用户id列表
     *
     * @param keyword
     * @return
     */
    public static List<Long> getUserIdByKeyword(String keyword) {
        List<Long> userIdList = new ArrayList<>();
        for (Long user_id : keywordCache.keySet()) {
            List<String> item = keywordCache.get(user_id);
            if (item.contains(keyword)) {
                userIdList.add(user_id);
                continue;
            }
        }
        return userIdList;
    }


    /**
     * 获取所有不重复的关键词
     *
     * @return
     */
    public static List<String> getDistinctKeword() {
        List<String> keywordList = new ArrayList<>();
        for (List<String> tempList : keywordCache.values()) {
            for (String keyword : tempList) {
                if (!keywordList.contains(keyword)) {
                    keywordList.add(keyword);
                }
            }
        }
        return keywordList;
    }

}
