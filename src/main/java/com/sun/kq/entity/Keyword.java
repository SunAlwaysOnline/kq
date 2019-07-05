package com.sun.kq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 关键词表
 */
public class Keyword {

    /**
     * 自增序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户的QQ号
     */
    private Long user_id;

    /**
     * 关键词
     */
    private String word;

    public Keyword() {
    }

    public Keyword(Long user_id, String word) {
        this.user_id = user_id;
        this.word = word;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", word='" + word + '\'' +
                '}';
    }
}
