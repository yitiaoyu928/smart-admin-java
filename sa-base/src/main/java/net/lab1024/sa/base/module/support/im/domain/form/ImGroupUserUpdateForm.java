package net.lab1024.sa.base.module.support.im.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * IM群组成员更新Form
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class ImGroupUserUpdateForm {

    @Schema(description = "群组ID")
    @NotNull(message = "群组ID不能为空")
    private Long groupId;

    @Schema(description = "用户ID")
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    @Schema(description = "是否管理员")
    private Boolean managerFlag;
}