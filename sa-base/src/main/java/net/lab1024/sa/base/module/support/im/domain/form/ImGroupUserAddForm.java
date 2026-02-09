package net.lab1024.sa.base.module.support.im.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * IM群组成员新增Form
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class ImGroupUserAddForm {

    @Schema(description = "群组ID")
    @NotNull(message = "群组ID不能为空")
    private Long groupId;

    @Schema(description = "用户ID列表")
    @NotNull(message = "用户ID列表不能为空")
    private List<String> userIds;

    @Schema(description = "是否管理员")
    private Boolean managerFlag;
}