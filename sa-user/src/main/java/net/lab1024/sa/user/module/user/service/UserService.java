package net.lab1024.sa.user.module.user.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.enumeration.UserTypeEnum;
import net.lab1024.sa.base.module.support.loginlog.LoginLogResultEnum;
import net.lab1024.sa.base.module.support.loginlog.LoginLogService;
import net.lab1024.sa.base.module.support.loginlog.domain.LoginLogVO;
import net.lab1024.sa.base.module.support.securityprotect.service.SecurityPasswordService;
import net.lab1024.sa.user.module.login.domain.RequestUserEntity;
import net.lab1024.sa.user.module.user.dao.UserMapper;
import net.lab1024.sa.user.module.user.domain.entity.UserEntity;
import net.lab1024.sa.user.module.user.domain.request.UserPasswordUpdateForm;
import net.lab1024.sa.user.module.user.domain.request.UserUpdateForm;
import net.lab1024.sa.user.module.user.domain.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private SecurityPasswordService securityPasswordService;

    @Resource
    private LoginLogService loginLogService;

    /**
     * 获取当前用户信息
     */
    public ResponseDTO<UserVO> getUserInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        UserEntity userEntity = userMapper.selectById(userId);
        if (userEntity == null || Boolean.TRUE.equals(userEntity.getDeletedFlag())) {
            return ResponseDTO.userErrorParam("用户不存在");
        }
        UserVO userVO = BeanUtil.copyProperties(userEntity, UserVO.class);
        userVO.setStatus(Boolean.TRUE.equals(userEntity.getDisabledFlag()) ? 0 : 1);
        userVO.setLevel(1);
        userVO.setPoints(0);
        LoginLogVO loginLogVO = loginLogService.queryLastByUserId(userId, UserTypeEnum.USER, LoginLogResultEnum.LOGIN_SUCCESS);
        if (loginLogVO != null) {
            userVO.setLastLoginTime(loginLogVO.getCreateTime());
        }
        return ResponseDTO.ok(userVO);
    }

    /**
     * 更新个人信息
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updateUserInfo(UserUpdateForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        UserEntity userEntity = userMapper.selectById(userId);
        if (userEntity == null || Boolean.TRUE.equals(userEntity.getDeletedFlag())) {
            return ResponseDTO.userErrorParam("用户不存在");
        }

        if (StrUtil.isNotBlank(form.getNickname()) && !form.getNickname().equals(userEntity.getNickname())) {
            if (userMapper.selectByNickname(form.getNickname()) != null) {
                return ResponseDTO.userErrorParam("昵称已存在");
            }
        }

        if (StrUtil.isNotBlank(form.getNickname())) {
            userEntity.setNickname(form.getNickname());
        }
        if (StrUtil.isNotBlank(form.getAvatar())) {
            userEntity.setAvatar(form.getAvatar());
        }
        if (form.getGender() != null) {
            userEntity.setGender(form.getGender());
        }
        userMapper.updateById(userEntity);
        return ResponseDTO.okMsg("更新成功");
    }

    /**
     * 修改密码
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updatePassword(UserPasswordUpdateForm form) {
        Long userId = StpUtil.getLoginIdAsLong();
        UserEntity userEntity = userMapper.selectById(userId);
        if (userEntity == null || Boolean.TRUE.equals(userEntity.getDeletedFlag())) {
            return ResponseDTO.userErrorParam("用户不存在");
        }

        if (!SecurityPasswordService.matchesPwd(form.getOldPassword(), userEntity.getLoginPwd())) {
            return ResponseDTO.userErrorParam("原密码错误");
        }

        ResponseDTO<String> pwdCheck = securityPasswordService.validatePasswordComplexity(form.getNewPassword());
        if (!pwdCheck.getOk()) {
            return pwdCheck;
        }

        RequestUserEntity requestUser = new RequestUserEntity();
        requestUser.setUserId(userId);
        requestUser.setUserType(UserTypeEnum.USER);
        requestUser.setUserName(userEntity.getNickname());
        requestUser.setIp(null);
        requestUser.setUserAgent(null);

        ResponseDTO<String> repeatCheck = securityPasswordService.validatePasswordRepeatTimes(requestUser, form.getNewPassword());
        if (!repeatCheck.getOk()) {
            return ResponseDTO.error(repeatCheck);
        }

        String oldEncryptPassword = userEntity.getLoginPwd();
        String newEncryptPassword = SecurityPasswordService.getEncryptPwd(form.getNewPassword());
        userEntity.setLoginPwd(newEncryptPassword);
        userMapper.updateById(userEntity);

        securityPasswordService.saveUserChangePasswordLog(requestUser, newEncryptPassword, oldEncryptPassword);

        StpUtil.logout(userId);

        return ResponseDTO.okMsg("密码修改成功，请重新登录");
    }
}
