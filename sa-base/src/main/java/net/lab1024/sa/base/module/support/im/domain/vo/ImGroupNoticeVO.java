package net.lab1024.sa.base.module.support.im.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * IM群组公告VO
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class ImGroupNoticeVO {

    @Schema(description = "公告ID")
    private String id;

    @Schema(description = "群组ID")
    private Long groupId;

    @Schema(description = "公告标题")
    private String title;

    @Schema(description = "公告内容")
    private String content;

    @Schema(description = "创建者ID")
    private String createBy;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}