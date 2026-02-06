package net.lab1024.sa.base.module.support.im.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * IM消息撤回Form
 *
 * @Author 1024创新实验室
 * @Date 2026-02-06
 */
@Data
public class ImMessageRevokeForm {

    @Schema(description = "消息ID")
    @NotBlank(message = "消息ID不能为空")
    private String uId;
}