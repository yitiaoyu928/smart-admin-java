package net.lab1024.sa.user.module.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserVO {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "生日")
    private LocalDate birthday;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "状态 0禁用 1启用")
    private Integer status;

    @Schema(description = "等级")
    private Integer level;

    @Schema(description = "积分")
    private Integer points;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;
}
