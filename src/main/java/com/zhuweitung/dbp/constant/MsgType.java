package com.zhuweitung.dbp.constant;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 钉钉消息类型
 *
 * @author zhuweitung
 * @create 2022/7/21
 */
public enum MsgType {
    text,
    link,
    markdown;

    public static boolean present(String name) {
        for (MsgType value : MsgType.values()) {
            if (value.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static String print() {
        return Arrays.stream(MsgType.values()).map(MsgType::name).collect(Collectors.joining(","));
    }
}
