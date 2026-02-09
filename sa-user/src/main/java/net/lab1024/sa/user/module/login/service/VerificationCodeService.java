package net.lab1024.sa.user.module.login.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.module.support.mail.MailService;
import net.lab1024.sa.base.module.support.redis.RedisService;
import net.lab1024.sa.base.common.code.SystemErrorCode;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class VerificationCodeService {

    @Resource
    private RedisService redisService;

    @Resource
    private MailService mailService;

    private static final String VERIFICATION_CODE_PREFIX = "user:verification:code:";

    public ResponseDTO<String> sendEmailCode(String email) {
        if (StrUtil.isBlank(email)) {
            return ResponseDTO.userErrorParam("邮箱不能为空");
        }

        // 1. Check cooldown (60s)
        String key = VERIFICATION_CODE_PREFIX + email;
        long expire = redisService.getExpire(key);
        if (expire > 240) {
            return ResponseDTO.userErrorParam("请求过于频繁，请稍后再试");
        }

        // 2. Generate Code
        String code = RandomUtil.randomNumbers(6);

        // 3. Store in Redis (5 mins)
        redisService.set(key, code, 300);

        // 4. Send Email
        try {
            mailService.sendMail("注册验证码", "您的验证码是：" + code + "，有效期5分钟。", null, Collections.singletonList(email), false);
        } catch (Exception e) {
            return ResponseDTO.error(SystemErrorCode.SYSTEM_ERROR, "邮件发送失败");
        }

        return ResponseDTO.okMsg("验证码已发送");
    }
    
    public ResponseDTO<String> sendPhoneCode(String phone) {
        String key = VERIFICATION_CODE_PREFIX + phone;
        String code = RandomUtil.randomNumbers(6);
        redisService.set(key, code, 300);
        return ResponseDTO.okMsg("验证码已发送");
    }

    public boolean validateCode(String target, String code) {
        if (StrUtil.isBlank(target) || StrUtil.isBlank(code)) {
            return false;
        }
        String key = VERIFICATION_CODE_PREFIX + target;
        String cachedCode = (String) redisService.get(key);
        return code.equals(cachedCode);
    }
    
    public void deleteCode(String target) {
        redisService.delete(VERIFICATION_CODE_PREFIX + target);
    }
}
