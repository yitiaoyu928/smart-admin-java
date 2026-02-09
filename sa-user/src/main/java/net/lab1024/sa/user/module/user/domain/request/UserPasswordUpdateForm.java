package net.lab1024.sa.user.module.user.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserPasswordUpdateForm {

    @Schema(description = "原密码")
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @Schema(description = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
