package com.sun.kq.entity;

import java.io.Serializable;

/**
 * 垃圾的实体类
 */
public class Rubbish implements Serializable {

    /**
     * 垃圾名称
     */
    private String name;

    /**
     * 垃圾类型
     */
    private String type;

    public Rubbish() {
    }

    public Rubbish(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Rubbish{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
