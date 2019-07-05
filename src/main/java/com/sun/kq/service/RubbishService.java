package com.sun.kq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.kq.entity.Rubbish;

public interface RubbishService extends IService<Rubbish> {

    /**
     * 由原始消息直接获取垃圾类型并返回响应消息
     *
     * @param raw_message
     * @return
     */
    String handleRubbishType(String raw_message);


    /**
     * 由垃圾名称获取垃圾
     *
     * @param name
     * @return
     */
    Rubbish getRubbishByName(String name);

    /**
     * 增加垃圾分类
     *
     * @param rubbish
     * @return
     */
    int add(Rubbish rubbish);

    /**
     * 处理增加垃圾分类的请求
     *
     * @param rubbish
     * @return
     */
    String handleAddRubbish(Rubbish rubbish);
}
