package com.sun.kq.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.kq.entity.Keyword;

import java.util.List;

public interface KeywordMapper extends BaseMapper<Keyword> {

    List<Keyword> getAllData();
}
