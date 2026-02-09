package net.lab1024.sa.base.module.support.im.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.lab1024.sa.base.common.swagger.SchemaEnum;
import net.lab1024.sa.base.module.support.im.constant.ImGroupTypeEnum;
import net.lab1024.sa.base.module.support.im.constant.ImGroupStatusEnum;

import java.time.LocalDateTime;

/**
 * IM群组更新Form
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class ImGroupUpdateForm {

    @Schema(description = "群组ID")
    @NotNull(message = "群组ID不能为空")
    private Long id;

    @Schema(description = "群组名称")
    private String name;

    @Schema(description = "群组头像")
    private String avatar;

    @Schema(description = "群组成员最大数量")
    private Integer memberCount;

    @SchemaEnum(value = ImGroupTypeEnum.class, desc = "群组类型")
    private Integer type;

    @Schema(description = "群组有效期")
    private LocalDateTime duration;

    @SchemaEnum(value = ImGroupStatusEnum.class, desc = "群组状态")
    private Integer statusFlag;
}