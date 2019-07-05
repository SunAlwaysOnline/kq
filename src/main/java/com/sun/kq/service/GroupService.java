package com.sun.kq.service;

import com.sun.kq.model.GroupInfo;

import java.util.List;

public interface GroupService {
    /**
     * 获取群
     *
     * @return
     */
    List<GroupInfo> getGroupInfo();
}
