package net.lab1024.sa.user.module.key.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 密钥激活表单（用户端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-05
 */
@Data
public class KeyActivationForm {

    @Schema(description = "密钥")
    @NotBlank(message = "密钥不能为空")
    @Length(max = 255, message = "密钥最多255字符")
    private String key;

}