package net.lab1024.sa.base.module.support.im.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;

/**
 * IM消息查询Form
 *
 * @Author 1024创新实验室
 * @Date 2026-02-06
 */
@Data
public class ImMessageQueryForm extends PageParam {

    @Schema(description = "发送者ID")
    private String fromId;

    @Schema(description = "接收者ID")
    private String receiveId;

    @Schema(description = "是否已读")
    private Boolean readFlag;

    @Schema(description = "是否撤回")
    private Boolean revokeFlag;
}