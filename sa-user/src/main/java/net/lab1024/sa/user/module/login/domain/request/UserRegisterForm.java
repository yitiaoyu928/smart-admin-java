package net.lab1024.sa.user.module.login.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import net.lab1024.sa.base.common.validator.enumeration.CheckEnum;

@Data
public class UserRegisterForm {

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String verificationCode;
    
    @Schema(description = "图形验证码")
    private String captchaCode;
    
    @Schema(description = "图形验证码UUID")
    private String captchaUuid;
}
