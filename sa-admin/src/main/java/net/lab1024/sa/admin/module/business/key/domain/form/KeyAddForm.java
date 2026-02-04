package net.lab1024.sa.admin.module.business.key.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * 添加项目密钥
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Data
public class KeyAddForm {

    @Schema(description = "项目ID")
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @Schema(description = "密钥")
    @NotBlank(message = "密钥不能为空")
    @Length(max = 255, message = "密钥最多255字符")
    private String key;

    @Schema(description = "密钥周期类型，DAY=天卡，Month=月卡，quarter=季卡，Year=年卡")
    @NotBlank(message = "密钥周期类型不能为空")
    @Length(max = 255, message = "密钥周期类型最多255字符")
    private String cycleType;

    @Schema(description = "是否禁用")
    @NotNull(message = "是否禁用不能为空")
    private Boolean disabledFlag;

    @Schema(description = "是否使用")
    @NotNull(message = "是否使用不能为空")
    private Boolean usingFlag;

    @Schema(description = "使用时间")
    private LocalDateTime usingTime;

    @Schema(description = "过期时间")
    private LocalDateTime expireTime;

    @Schema(description = "备注")
    @Length(max = 255, message = "备注最多255字符")
    private String remark;

}