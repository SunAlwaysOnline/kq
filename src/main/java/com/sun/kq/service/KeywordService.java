package com.sun.kq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.kq.entity.Keyword;
import com.sun.kq.model.ReceiveMsg;

import java.util.List;

public interface KeywordService extends IService<Keyword> {

    /**
     * 监控群消息的关键词，并通知关注该关键词的用户
     *
     * @param receiveMsg
     */
    void getPrivateMsgOnKeyword(ReceiveMsg receiveMsg);

    /**
     * 增加关键词
     *
     * @param keyword
     * @return
     */
    boolean addKeyword(Keyword keyword);

    /**
     * 删除关键词
     *
     * @param keyword
     * @return
     */
    boolean delKeyword(Keyword keyword);

    /**
     * 得到该用户的关键词列表
     *
     * @param user_id
     * @return
     */
    List<String> getWordList(Long user_id);

    /**
     * 在系统初始化后，加载数据内所有的关键词进入缓存，
     * 目前数据较少，可以采用这种方式
     */
    void getAllDataIntoCache();
}
