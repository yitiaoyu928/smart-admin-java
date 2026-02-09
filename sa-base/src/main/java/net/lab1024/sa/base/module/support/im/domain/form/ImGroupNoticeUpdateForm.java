package net.lab1024.sa.base.module.support.im.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * IM群组公告更新Form
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class ImGroupNoticeUpdateForm {

    @Schema(description = "公告ID")
    @NotBlank(message = "公告ID不能为空")
    private String id;

    @Schema(description = "群组ID")
    @NotNull(message = "群组ID不能为空")
    private Long groupId;

    @Schema(description = "公告标题")
    @NotBlank(message = "公告标题不能为空")
    private String title;

    @Schema(description = "公告内容")
    @NotBlank(message = "公告内容不能为空")
    private String content;
}