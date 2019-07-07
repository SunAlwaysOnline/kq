package com.sun.kq.service;

import com.sun.kq.model.ReplyMsg;

import java.util.List;

public interface KmzService {


    /**
     * 获取n张妹子图
     *
     * @param n
     * @return
     */
    List<String> getKmzImageURL(int n);


}
