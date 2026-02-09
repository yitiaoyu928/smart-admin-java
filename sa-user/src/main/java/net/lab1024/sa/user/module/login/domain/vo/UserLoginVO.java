package net.lab1024.sa.user.module.login.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserLoginVO {

    @Schema(description = "Token")
    private String token;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;
    
    @Schema(description = "等级")
    private Integer level;
}
