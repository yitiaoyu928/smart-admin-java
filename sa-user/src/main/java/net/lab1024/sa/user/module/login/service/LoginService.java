package net.lab1024.sa.user.module.login.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.enumeration.UserTypeEnum;
import net.lab1024.sa.base.module.support.captcha.CaptchaService;
import net.lab1024.sa.base.module.support.captcha.domain.CaptchaForm;
import net.lab1024.sa.base.module.support.loginlog.LoginLogResultEnum;
import net.lab1024.sa.base.module.support.loginlog.LoginLogService;
import net.lab1024.sa.base.module.support.loginlog.domain.LoginLogEntity;
import net.lab1024.sa.base.common.util.SmartIpUtil;
import net.lab1024.sa.base.module.support.securityprotect.service.SecurityPasswordService;
import net.lab1024.sa.user.module.login.domain.RequestUserEntity;
import net.lab1024.sa.user.module.login.domain.request.UserLoginForm;
import net.lab1024.sa.user.module.login.domain.request.UserRegisterForm;
import net.lab1024.sa.user.module.login.domain.request.UserResetPasswordForm;
import net.lab1024.sa.user.module.login.domain.vo.UserLoginVO;
import net.lab1024.sa.user.module.user.dao.UserMapper;
import net.lab1024.sa.user.module.user.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Service
public class LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private LoginLogService loginLogService;

    @Resource
    private VerificationCodeService verificationCodeService;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private SecurityPasswordService securityPasswordService;

    @Value("${sa-user.register.default-department-id:1}")
    private Long defaultDepartmentId;

    /**
     * 获取登录用户
     */
    public RequestUserEntity getLoginUser(String loginId, HttpServletRequest request) {
        // 检查loginId是否为空或"null"字符串
        if (StrUtil.isBlank(loginId) || "null".equals(loginId)) {
            return null;
        }
        
        Long userId;
        try {
            userId = Long.parseLong(loginId);
        } catch (NumberFormatException e) {
            // 如果转换失败，说明loginId不是有效的数字格式
            return null;
        }
        
        UserEntity user = userMapper.selectById(userId);
        if (user == null || Boolean.TRUE.equals(user.getDeletedFlag())) {
            return null;
        }

        RequestUserEntity requestUser = new RequestUserEntity();
        requestUser.setUserId(user.getUserId());
        requestUser.setUserType(UserTypeEnum.USER);
        requestUser.setLoginName(StrUtil.blankToDefault(user.getLoginName(), StrUtil.blankToDefault(user.getEmail(), user.getPhone())));
        requestUser.setUserName(user.getNickname());
        requestUser.setAvatar(user.getAvatar());
        requestUser.setPhone(user.getPhone());
        requestUser.setEmail(user.getEmail());
        requestUser.setDisabledFlag(Boolean.TRUE.equals(user.getDisabledFlag()));
        requestUser.setIp(request.getRemoteAddr());
        requestUser.setUserAgent(request.getHeader("User-Agent"));

        return requestUser;
    }

    /**
     * 用户注册
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> register(UserRegisterForm form) {
        // 1. 校验验证码 (Email/Phone Code)
        if (StrUtil.isNotBlank(form.getEmail())) {
            if (!verificationCodeService.validateCode(form.getEmail(), form.getVerificationCode())) {
                return ResponseDTO.userErrorParam("验证码错误或已失效");
            }
        } else if (StrUtil.isNotBlank(form.getPhone())) {
            return ResponseDTO.userErrorParam("暂不支持手机号注册");
        } else {
            return ResponseDTO.userErrorParam("邮箱或手机号不能为空");
        }
        
        // 2. 校验图形验证码 (可选)
        if (StrUtil.isNotBlank(form.getCaptchaCode())) {
            CaptchaForm captchaForm = new CaptchaForm();
            captchaForm.setCaptchaUuid(form.getCaptchaUuid());
            captchaForm.setCaptchaCode(form.getCaptchaCode());
            ResponseDTO<String> captchaCheck = captchaService.checkCaptcha(captchaForm);
            if (!captchaCheck.getOk()) {
                return captchaCheck;
            }
        }

        // 3. 校验密码强度
        ResponseDTO<String> pwdCheck = securityPasswordService.validatePasswordComplexity(form.getPassword());
        if (!pwdCheck.getOk()) {
            return pwdCheck;
        }

        // 4. 检查重复
        if (userMapper.selectByEmail(form.getEmail()) != null) {
            return ResponseDTO.userErrorParam("该邮箱已被注册");
        }
        
        // 5. 创建用户
        UserEntity user = new UserEntity();
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setLoginName(StrUtil.blankToDefault(form.getEmail(), form.getPhone()));
        user.setNickname(form.getEmail().split("@")[0]); 
        user.setLoginPwd(SecurityPasswordService.getEncryptPwd(form.getPassword()));
        user.setEmployeeUid(IdUtil.fastSimpleUUID());
        user.setDepartmentId(defaultDepartmentId);
        user.setAdministratorFlag(false);
        user.setDisabledFlag(false);
        user.setDeletedFlag(false);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        userMapper.insert(user);
        
        // 6. 清除验证码
        verificationCodeService.deleteCode(form.getEmail());

        return ResponseDTO.okMsg("注册成功");
    }

    /**
     * 用户登录
     */
    public ResponseDTO<UserLoginVO> login(UserLoginForm form) {
        // 1. 校验图形验证码
        if (StrUtil.isNotBlank(form.getCaptchaCode())) {
            CaptchaForm captchaForm = new CaptchaForm();
            captchaForm.setCaptchaUuid(form.getCaptchaUuid());
            captchaForm.setCaptchaCode(form.getCaptchaCode());
            ResponseDTO<String> captchaCheck = captchaService.checkCaptcha(captchaForm);
            if (!captchaCheck.getOk()) {
                return ResponseDTO.error(captchaCheck);
            }
        }

        // 2. 查询用户
        UserEntity user = userMapper.selectByLoginName(form.getLoginName());
        System.out.println(user);
        if (user == null || Boolean.TRUE.equals(user.getDeletedFlag())) {
            logLogin(0L, form.getLoginName(), LoginLogResultEnum.LOGIN_FAIL, "账号不存在");
            return ResponseDTO.userErrorParam("账号或密码错误");
        }

        // 3. 校验密码
        if (!SecurityPasswordService.matchesPwd(form.getPassword(), user.getLoginPwd())) {
            logLogin(user.getUserId(), form.getLoginName(), LoginLogResultEnum.LOGIN_FAIL, "密码错误");
            return ResponseDTO.userErrorParam("账号或密码错误");
        }

        // 4. 校验状态
        if (Boolean.TRUE.equals(user.getDisabledFlag())) {
            logLogin(user.getUserId(), form.getLoginName(), LoginLogResultEnum.LOGIN_FAIL, "账号被禁用");
            return ResponseDTO.userErrorParam("账号已被禁用");
        }

        // 5. 登录 (Sa-Token)
        StpUtil.login(String.valueOf(user.getUserId()));
        
        // 6. 更新登录信息
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
        
        // 记录登录日志
        logLogin(user.getUserId(), user.getNickname(), LoginLogResultEnum.LOGIN_SUCCESS, "登录成功");

        // 7. 返回结果
        UserLoginVO vo = BeanUtil.copyProperties(user, UserLoginVO.class);
        vo.setToken(StpUtil.getTokenValue());
        vo.setLevel(1);
        
        return ResponseDTO.ok(vo);
    }
    
    /**
     * 退出登录
     */
    public ResponseDTO<String> logout() {
        StpUtil.logout();
        return ResponseDTO.okMsg("退出成功");
    }

    /**
     * 重置密码 (忘记密码)
     */
    public ResponseDTO<String> resetPassword(UserResetPasswordForm form) {
        // 1. 校验验证码
        if (!verificationCodeService.validateCode(form.getEmail(), form.getCode())) {
            return ResponseDTO.userErrorParam("验证码错误或已失效");
        }

        // 2. 查询用户
        UserEntity user = userMapper.selectByEmail(form.getEmail());
        if (user == null) {
            return ResponseDTO.userErrorParam("用户不存在");
        }

        // 3. 校验密码强度
        ResponseDTO<String> pwdCheck = securityPasswordService.validatePasswordComplexity(form.getNewPassword());
        if (!pwdCheck.getOk()) {
            return pwdCheck;
        }

        // 4. 更新密码
        user.setLoginPwd(SecurityPasswordService.getEncryptPwd(form.getNewPassword()));
        userMapper.updateById(user);

        // 5. 清除验证码
        verificationCodeService.deleteCode(form.getEmail());

        return ResponseDTO.okMsg("密码重置成功");
    }

    /**
     * 记录登录日志
     */
    private void logLogin(Long userId, String userName, LoginLogResultEnum result, String remark) {
        try {
            HttpServletRequest request = getCurrentRequest();
            String ip = request == null ? null : JakartaServletUtil.getClientIP(request);
            LoginLogEntity logEntity = LoginLogEntity.builder()
                    .userId(userId)
                    .userType(UserTypeEnum.USER.getValue())
                    .userName(userName)
                    .loginIp(ip)
                    .loginIpRegion(SmartIpUtil.getRegion(ip))
                    .userAgent(request == null ? null : request.getHeader("User-Agent"))
                    .loginResult(result.getValue())
                    .remark(remark)
                    .createTime(LocalDateTime.now())
                    .build();
            loginLogService.log(logEntity);
        } catch (Exception e) {
        }
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }
}
