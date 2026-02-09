package net.lab1024.sa.user.module.login.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.domain.RequestUser;
import net.lab1024.sa.base.common.enumeration.UserTypeEnum;

import java.io.Serializable;

/**
 * 请求用户登录信息（用户端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
public class RequestUserEntity implements RequestUser, Serializable {

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "用户类型")
    private UserTypeEnum userType;

    @Schema(description = "登录账号")
    private String loginName;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "是否禁用")
    private Boolean disabledFlag;

    @Schema(description = "请求ip")
    private String ip;

    @Schema(description = "请求user-agent")
    private String userAgent;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public String getUserName() {
        return userName;
    }
}