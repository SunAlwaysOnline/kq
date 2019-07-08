package com.sun.kq.service;

import com.sun.kq.model.ReplyMsg;

import java.util.List;

public interface KmzService {


    /**
     * 获取n张妹子图的key
     *
     * @param n
     * @return
     */
    List<String> getKmzImageKey(int n);


}
