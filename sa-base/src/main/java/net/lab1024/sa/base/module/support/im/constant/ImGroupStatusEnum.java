package net.lab1024.sa.base.module.support.im.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lab1024.sa.base.common.enumeration.BaseEnum;

/**
 * IM群组状态枚举
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Getter
@AllArgsConstructor
public enum ImGroupStatusEnum implements BaseEnum {

    NORMAL(0, "正常"),
    BANNED(1, "封禁");

    private final Integer value;
    private final String desc;
}