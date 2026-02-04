package net.lab1024.sa.admin.module.business.project.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新项目
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Data
public class ProjectUpdateForm extends ProjectAddForm {

    @Schema(description = "项目id")
    @NotNull(message = "项目id不能为空")
    private Long id;

}