package net.lab1024.sa.user.module.login.service;

import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.module.support.captcha.CaptchaService;
import net.lab1024.sa.base.module.support.loginlog.LoginLogService;
import net.lab1024.sa.base.module.support.securityprotect.service.SecurityPasswordService;
import net.lab1024.sa.user.module.login.domain.request.UserLoginForm;
import net.lab1024.sa.user.module.login.domain.request.UserRegisterForm;
import net.lab1024.sa.user.module.user.dao.UserMapper;
import net.lab1024.sa.user.module.user.domain.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private VerificationCodeService verificationCodeService;

    @Mock
    private CaptchaService captchaService;

    @Mock
    private SecurityPasswordService securityPasswordService;
    
    @Mock
    private LoginLogService loginLogService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testRegister_Success() {
        UserRegisterForm form = new UserRegisterForm();
        form.setEmail("test@example.com");
        form.setPassword("Test@1234");
        form.setVerificationCode("123456");

        when(verificationCodeService.validateCode(anyString(), anyString())).thenReturn(true);
        when(securityPasswordService.validatePasswordComplexity(anyString())).thenReturn(ResponseDTO.ok());
        when(userMapper.selectByEmail(anyString())).thenReturn(null);
        when(userMapper.insert(any(UserEntity.class))).thenReturn(1);

        ResponseDTO<String> result = loginService.register(form);
        Assertions.assertTrue(result.getOk());
    }

    @Test
    public void testRegister_Fail_DuplicateEmail() {
        UserRegisterForm form = new UserRegisterForm();
        form.setEmail("test@example.com");
        form.setPassword("Test@1234");
        form.setVerificationCode("123456");

        when(verificationCodeService.validateCode(anyString(), anyString())).thenReturn(true);
        when(securityPasswordService.validatePasswordComplexity(anyString())).thenReturn(ResponseDTO.ok());
        when(userMapper.selectByEmail(anyString())).thenReturn(new UserEntity());

        ResponseDTO<String> result = loginService.register(form);
        Assertions.assertFalse(result.getOk());
        Assertions.assertEquals("该邮箱已被注册", result.getMsg());
    }
}
