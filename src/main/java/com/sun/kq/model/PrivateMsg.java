package com.sun.kq.model;

/**
 * 私聊消息实体类
 */
public class PrivateMsg {

    /**
     * 对方QQ号
     */
    private Long user_id;

    /**
     * 要发送的内容
     */
    private String message;

    /**
     * 消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     * 默认值 false
     */
    private boolean auto_escape;

    public PrivateMsg() {
    }

    public PrivateMsg(Long user_id, String message, boolean auto_escape) {
        this.user_id = user_id;
        this.message = message;
        this.auto_escape = auto_escape;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAuto_escape() {
        return auto_escape;
    }

    public void setAuto_escape(boolean auto_escape) {
        this.auto_escape = auto_escape;
    }

    @Override
    public String toString() {
        return "PrivateMsg{" +
                "user_id=" + user_id +
                ", message='" + message + '\'' +
                ", auto_escape=" + auto_escape +
                '}';
    }
}
