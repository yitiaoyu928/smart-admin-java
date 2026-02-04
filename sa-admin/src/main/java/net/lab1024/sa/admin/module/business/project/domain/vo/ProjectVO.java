package net.lab1024.sa.admin.module.business.project.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 项目信息
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Data
public class ProjectVO {

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "项目标识")
    private String mark;

    @Schema(description = "项目版本")
    private String version;

    @Schema(description = "项目类型，APP,WEB,Windows")
    private String type;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否禁用")
    private Boolean disabledFlag;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}