package net.lab1024.sa.base.module.support.im.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.lab1024.sa.base.common.swagger.SchemaEnum;
import net.lab1024.sa.base.module.support.im.constant.ImMessageTypeEnum;

/**
 * IM消息发送Form
 *
 * @Author 1024创新实验室
 * @Date 2026-02-06
 */
@Data
public class ImMessageSendForm {

    @Schema(description = "接收者ID")
    @NotBlank(message = "接收者ID不能为空")
    private String receiveId;

    @SchemaEnum(value = ImMessageTypeEnum.class, desc = "消息类型")
    @NotNull(message = "消息类型不能为空")
    private Integer type;

    @Schema(description = "消息内容")
    @NotBlank(message = "消息内容不能为空")
    private String content;
}