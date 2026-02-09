package net.lab1024.sa.user.module.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.user.module.login.domain.request.UserLoginForm;
import net.lab1024.sa.user.module.login.domain.request.UserRegisterForm;
import net.lab1024.sa.user.module.login.domain.request.UserResetPasswordForm;
import net.lab1024.sa.user.module.login.domain.vo.UserLoginVO;
import net.lab1024.sa.user.module.login.service.LoginService;
import net.lab1024.sa.user.module.login.service.VerificationCodeService;
import net.lab1024.sa.base.common.annoation.NoNeedLogin;
import net.lab1024.sa.base.module.support.repeatsubmit.annoation.RepeatSubmit;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/auth")
@Tag(name = "用户认证")
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private VerificationCodeService verificationCodeService;

    @NoNeedLogin
    @RepeatSubmit
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public ResponseDTO<String> register(@RequestBody @Valid UserRegisterForm form) {
        return loginService.register(form);
    }

    @NoNeedLogin
    @RepeatSubmit
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ResponseDTO<UserLoginVO> login(@RequestBody @Valid UserLoginForm form) {
        return loginService.login(form);
    }

    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public ResponseDTO<String> logout() {
        return loginService.logout();
    }

    @NoNeedLogin
    @RepeatSubmit
    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/send-code")
    public ResponseDTO<String> sendCode(@RequestParam String email) {
        return verificationCodeService.sendEmailCode(email);
    }

    @NoNeedLogin
    @RepeatSubmit
    @Operation(summary = "重置密码(忘记密码)")
    @PostMapping("/reset-password")
    public ResponseDTO<String> resetPassword(@RequestBody @Valid UserResetPasswordForm form) {
        return loginService.resetPassword(form);
    }
}
