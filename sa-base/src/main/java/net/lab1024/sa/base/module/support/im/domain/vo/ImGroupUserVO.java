package net.lab1024.sa.base.module.support.im.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * IM群组成员VO
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class ImGroupUserVO {

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "群组ID")
    private Long groupId;

    @Schema(description = "是否管理员")
    private Boolean managerFlag;

    @Schema(description = "加入时间")
    private LocalDateTime joinTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}