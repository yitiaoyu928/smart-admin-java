package net.lab1024.sa.base.module.support.im.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;

/**
 * IM群组成员查询Form
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class ImGroupUserQueryForm extends PageParam {

    @Schema(description = "群组ID")
    private Long groupId;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "是否管理员")
    private Boolean managerFlag;
}