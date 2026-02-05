package net.lab1024.sa.user.module.key.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 项目密钥信息（用户端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-05
 */
@Data
public class KeyVO {

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "密钥")
    private String key;

    @Schema(description = "密钥周期类型，DAY=天卡，Month=月卡，quarter=季卡，Year=年卡")
    private String cycleType;

    @Schema(description = "是否禁用")
    private Boolean disabledFlag;

    @Schema(description = "是否使用")
    private Boolean usingFlag;

    @Schema(description = "使用时间")
    private LocalDateTime usingTime;

    @Schema(description = "过期时间")
    private LocalDateTime expireTime;

    @Schema(description = "状态：0=正常,1=过期")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}