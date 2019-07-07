package com.sun.kq.model;

public class MzImg {

    private String pinId;

    private String key;

    private String type;

    public String getPinId() {
        return pinId;
    }

    public void setPinId(String pinId) {
        this.pinId = pinId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MzImg{" +
                "pinId='" + pinId + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
