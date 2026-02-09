package net.lab1024.sa.base.module.support.im.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lab1024.sa.base.common.enumeration.BaseEnum;

/**
 * IM群组类型枚举
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Getter
@AllArgsConstructor
public enum ImGroupTypeEnum implements BaseEnum {

    NORMAL(1, "普通群组"),
    PRIVATE(2, "私有群组"),
    TEMPORARY(3, "临时群组");

    private final Integer value;
    private final String desc;
}