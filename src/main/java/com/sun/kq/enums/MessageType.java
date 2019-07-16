package com.sun.kq.enums;


public enum MessageType {
    PRIVATE(0, "private"),
    GROUP(1, "group"),
    DISCUSS(2, "discuss"),
    ;

    private int code;

    private String type;

    MessageType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static MessageType getEnumByType(String type) {
        for (MessageType temp : values()) {
            if (type.equals(temp.type)) {
                return temp;
            }
        }
        return null;
    }
}
