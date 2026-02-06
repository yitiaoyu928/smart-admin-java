package net.lab1024.sa.base.module.support.im.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * IM消息类型枚举
 *
 * @Author 1024创新实验室
 * @Date 2026-02-06
 */
@Getter
@AllArgsConstructor
public enum ImMessageTypeEnum {

    TEXT(1, "文本消息"),
    IMAGE(2, "图片消息"),
    VOICE(3, "语音消息"),
    VIDEO(4, "视频消息"),
    FILE(5, "文件消息"),
    SYSTEM(99, "系统消息");

    private final Integer value;
    private final String desc;
}