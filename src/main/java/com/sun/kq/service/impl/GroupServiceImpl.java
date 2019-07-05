package com.sun.kq.service.impl;

import com.sun.kq.model.GroupInfo;
import com.sun.kq.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.sun.kq.constant.URLConst.*;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<GroupInfo> getGroupInfo() {
        List<GroupInfo> groupInfoList = (List<GroupInfo>) restTemplate.getForObject(URL + GET_GROUP_LIST, GroupInfo.class);
        return groupInfoList;
    }
}
