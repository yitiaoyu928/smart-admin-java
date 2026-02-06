package net.lab1024.sa.base.module.support.im.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.swagger.SchemaEnum;
import net.lab1024.sa.base.module.support.im.constant.ImMessageTypeEnum;

import java.time.LocalDateTime;

/**
 * IM消息VO
 *
 * @Author 1024创新实验室
 * @Date 2026-02-06
 */
@Data
public class ImMessageVO {

    @Schema(description = "消息ID")
    private String uId;

    @Schema(description = "发送者ID")
    private String fromId;

    @Schema(description = "接收者ID")
    private String receiveId;

    @SchemaEnum(value = ImMessageTypeEnum.class, desc = "消息类型")
    private Integer type;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "是否已读")
    private Boolean readFlag;

    @Schema(description = "是否撤回")
    private Boolean revokeFlag;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}