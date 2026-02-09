package net.lab1024.sa.base.module.support.im.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.swagger.SchemaEnum;
import net.lab1024.sa.base.module.support.im.constant.ImGroupTypeEnum;
import net.lab1024.sa.base.module.support.im.constant.ImGroupStatusEnum;

import java.time.LocalDateTime;

/**
 * IM群组VO
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class ImGroupVO {

    @Schema(description = "群组ID")
    private Long id;

    @Schema(description = "群组名称")
    private String name;

    @Schema(description = "群组头像")
    private String avatar;

    @Schema(description = "群组成员最大数量")
    private Integer memberCount;

    @Schema(description = "群组创建者ID")
    private String ownerId;

    @SchemaEnum(value = ImGroupTypeEnum.class, desc = "群组类型")
    private Integer type;

    @SchemaEnum(value = ImGroupTypeEnum.class, desc = "群组类型描述")
    private String typeDesc;

    @Schema(description = "群组有效期")
    private LocalDateTime duration;

    @SchemaEnum(value = ImGroupStatusEnum.class, desc = "群组状态")
    private Integer statusFlag;

    @SchemaEnum(value = ImGroupStatusEnum.class, desc = "群组状态描述")
    private String statusFlagDesc;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}