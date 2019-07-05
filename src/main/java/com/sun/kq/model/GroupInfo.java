package com.sun.kq.model;

/**
 * 群的实体类
 */
public class GroupInfo {

    /**
     * 群ID
     */
    private Long group_id;

    /**
     * 群名称
     */
    private String group_name;

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
                "group_id=" + group_id +
                ", group_name='" + group_name + '\'' +
                '}';
    }
}
