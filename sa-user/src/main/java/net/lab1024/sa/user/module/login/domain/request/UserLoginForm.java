package net.lab1024.sa.user.module.login.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginForm {

    @Schema(description = "登录账号(邮箱/手机号)")
    @NotBlank(message = "账号不能为空")
    private String loginName;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "图形验证码")
    private String captchaCode;

    @Schema(description = "图形验证码UUID")
    private String captchaUuid;
}
