package com.sun.kq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 发送者
 * <p>
 * 需要注意的是，sender 中的各字段是尽最大努力提供的，也就是说，
 * 不保证每个字段都一定存在，也不保证存在的字段都是完全正确的（缓存可能过期）。
 * 尤其对于匿名消息，此字段不具有参考价值。
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sender {

    /**
     * 发送者QQ号
     */
    private Long user_id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 群名片／备注
     */
    private String card;

    /**
     * 性别, male 或 female 或 unknown
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", card='" + card + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
