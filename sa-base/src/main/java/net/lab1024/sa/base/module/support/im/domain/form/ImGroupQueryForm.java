package net.lab1024.sa.base.module.support.im.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import net.lab1024.sa.base.common.swagger.SchemaEnum;
import net.lab1024.sa.base.module.support.im.constant.ImGroupTypeEnum;
import net.lab1024.sa.base.module.support.im.constant.ImGroupStatusEnum;

/**
 * IM群组查询Form
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class ImGroupQueryForm extends PageParam {

    @Schema(description = "群组名称")
    private String name;

    @Schema(description = "群组创建者ID")
    private String ownerId;

    @SchemaEnum(value = ImGroupTypeEnum.class, desc = "群组类型")
    private Integer type;

    @SchemaEnum(value = ImGroupStatusEnum.class, desc = "群组状态")
    private Integer statusFlag;
}