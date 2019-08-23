package com.sun.kq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * csdn记录表
 */
public class Csdn {

    /**
     * 自增序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 原创总数
     */
    private Integer article_sum;

    /**
     * 粉丝总数
     */
    private Integer fan_sum;

    /**
     * 获赞总数
     */
    private Integer praise_sum;

    /**
     * 评论总数
     */
    private Integer comment_sum;

    /**
     * 博客等级
     */
    private Integer level;

    /**
     * 总访问量
     */
    private Integer visit_sum;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 排名
     */
    private Integer rank;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticle_sum() {
        return article_sum;
    }

    public void setArticle_sum(Integer article_sum) {
        this.article_sum = article_sum;
    }

    public Integer getFan_sum() {
        return fan_sum;
    }

    public void setFan_sum(Integer fan_sum) {
        this.fan_sum = fan_sum;
    }

    public Integer getPraise_sum() {
        return praise_sum;
    }

    public void setPraise_sum(Integer praise_sum) {
        this.praise_sum = praise_sum;
    }

    public Integer getComment_sum() {
        return comment_sum;
    }

    public void setComment_sum(Integer comment_sum) {
        this.comment_sum = comment_sum;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getVisit_sum() {
        return visit_sum;
    }

    public void setVisit_sum(Integer visit_sum) {
        this.visit_sum = visit_sum;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Csdn{" +
                "id=" + id +
                ", article_sum=" + article_sum +
                ", fan_sum=" + fan_sum +
                ", praise_sum=" + praise_sum +
                ", comment_sum=" + comment_sum +
                ", level=" + level +
                ", visit_sum=" + visit_sum +
                ", integral=" + integral +
                ", rank=" + rank +
                '}';
    }
}
