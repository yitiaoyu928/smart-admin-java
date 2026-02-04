package net.lab1024.sa.admin.module.business.key.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.PageParam;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * 项目密钥列表查询
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Data
public class KeyQueryForm extends PageParam {

    @Schema(description = "搜索词")
    @Length(max = 20, message = "搜索词最多20字符")
    private String keyword;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "密钥周期类型，DAY=天卡，Month=月卡，quarter=季卡，Year=年卡")
    private String cycleType;

    @Schema(description = "是否禁用")
    private Boolean disabledFlag;

    @Schema(description = "是否使用")
    private Boolean usingFlag;

    @Schema(description = "使用时间开始")
    private LocalDateTime usingTimeBegin;

    @Schema(description = "使用时间结束")
    private LocalDateTime usingTimeEnd;

    @Schema(description = "过期时间开始")
    private LocalDateTime expireTimeBegin;

    @Schema(description = "过期时间结束")
    private LocalDateTime expireTimeEnd;

    @Schema(description = "状态：0=正常,1=过期")
    private Integer status;

    @Schema(description = "删除标识", hidden = true)
    private Boolean deletedFlag;

}