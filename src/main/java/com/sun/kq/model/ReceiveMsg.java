package com.sun.kq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 收到消息的实体类
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveMsg {

    /**
     * 上报类型
     * message	收到消息
     * notice	群、讨论组变动等通知类事件
     * request	加好友请求、加群请求／邀请
     */
    private String post_type;

    /**
     * 消息类型
     * private  私聊消息
     * group    群组消息
     * discuss  讨论组消息
     */
    private String message_type;

    /**
     * 消息子类型
     * 私聊消息中，如果是好友则是 friend，如果从群或讨论组来的临时会话则分别是 group、discuss,其他则为other
     * 群组消息中，正常消息是 normal，匿名消息是 anonymous，系统提示（如「管理员已禁止群内匿名聊天」）是 notice
     */
    private String sub_type;

    /**
     * 消息ID
     */
    private Long message_id;

    /**
     * 发送者 QQ 号
     */
    private Long user_id;

    /**
     * 所在群ID
     */
    private Long group_id;

    /**
     * 讨论组 ID
     */
    private Long discuss_id;

    /**
     * 匿名信息，如果不是匿名消息则为 null
     */
    private Object anonymous;

    /**
     * 消息内容
     * 特别地，数据类型 message 表示该参数是一个消息类型的参数。
     * 在上报数据中，message 的实际类型根据配置项 post_message_format 的不同而不同，
     * post_message_format 设置为 string 和 array 分别对应字符串和消息段数组；
     * 而在上报请求的回复中，message 类型的字段允许接受字符串、消息段数组、单个消息段对象三种类型的数据。
     */
    private String message;

    /**
     * 原始消息内容
     */
    private String raw_message;

    /**
     * 字体
     */
    private Long font;

    /**
     * 时间戳
     */
    private Long time;

    /**
     * 上报的机器人QQ号
     */
    private Long self_id;

    /**
     * 发送者
     */
    private Sender sender;

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public Long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public Long getDiscuss_id() {
        return discuss_id;
    }

    public void setDiscuss_id(Long discuss_id) {
        this.discuss_id = discuss_id;
    }

    public Object getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Object anonymous) {
        this.anonymous = anonymous;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRaw_message() {
        return raw_message;
    }

    public void setRaw_message(String raw_message) {
        this.raw_message = raw_message;
    }

    public Long getFont() {
        return font;
    }

    public void setFont(Long font) {
        this.font = font;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getSelf_id() {
        return self_id;
    }

    public void setSelf_id(Long self_id) {
        this.self_id = self_id;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "ReceiveMsg{" +
                "post_type='" + post_type + '\'' +
                ", message_type='" + message_type + '\'' +
                ", sub_type='" + sub_type + '\'' +
                ", message_id=" + message_id +
                ", user_id=" + user_id +
                ", group_id=" + group_id +
                ", discuss_id=" + discuss_id +
                ", anonymous=" + anonymous +
                ", message='" + message + '\'' +
                ", raw_message='" + raw_message + '\'' +
                ", font=" + font +
                ", time=" + time +
                ", self_id=" + self_id +
                ", sender=" + sender +
                '}';
    }
}
