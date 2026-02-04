package net.lab1024.sa.admin.module.business.project.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 添加项目
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Data
public class ProjectAddForm {

    @Schema(description = "项目名称")
    @NotBlank(message = "项目名称不能为空")
    @Length(max = 255, message = "项目名称最多255字符")
    private String name;

    @Schema(description = "项目标识")
    @NotBlank(message = "项目标识不能为空")
    @Length(max = 255, message = "项目标识最多255字符")
    private String mark;

    @Schema(description = "项目版本")
    @Length(max = 255, message = "项目版本最多255字符")
    private String version;

    @Schema(description = "项目类型，APP,WEB,Windows")
    @NotBlank(message = "项目类型不能为空")
    @Length(max = 255, message = "项目类型最多255字符")
    private String type;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "是否禁用")
    @NotNull(message = "是否禁用不能为空")
    private Boolean disabledFlag;

    @Schema(description = "备注")
    @Length(max = 255, message = "备注最多255字符")
    private String remark;

}