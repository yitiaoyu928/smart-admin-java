package net.lab1024.sa.admin.module.business.key.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 更新项目密钥
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Data
public class KeyUpdateForm extends KeyAddForm {

    @Schema(description = "项目密钥id")
    @NotNull(message = "项目密钥id不能为空")
    private Long id;

}