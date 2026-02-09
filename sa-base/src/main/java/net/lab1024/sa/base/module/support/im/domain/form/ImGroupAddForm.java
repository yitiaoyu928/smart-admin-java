package net.lab1024.sa.base.module.support.im.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.lab1024.sa.base.common.swagger.SchemaEnum;
import net.lab1024.sa.base.module.support.im.constant.ImGroupTypeEnum;
import net.lab1024.sa.base.module.support.im.constant.ImGroupStatusEnum;

import java.time.LocalDateTime;

/**
 * IM群组新增Form
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class ImGroupAddForm {

    @Schema(description = "群组名称")
    @NotBlank(message = "群组名称不能为空")
    private String name;

    @Schema(description = "群组头像")
    @NotBlank(message = "群组头像不能为空")
    private String avatar;

    @Schema(description = "群组成员最大数量")
    @NotNull(message = "群组成员最大数量不能为空")
    private Integer memberCount;

    @SchemaEnum(value = ImGroupTypeEnum.class, desc = "群组类型")
    @NotNull(message = "群组类型不能为空")
    private Integer type;

    @Schema(description = "群组有效期")
    private LocalDateTime duration;

    @SchemaEnum(value = ImGroupStatusEnum.class, desc = "群组状态")
    private Integer statusFlag;
}