package net.lab1024.sa.user.module.user.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.lab1024.sa.base.common.enumeration.GenderEnum;

import java.time.LocalDate;

@Data
public class UserUpdateForm {

    @Schema(description = "昵称")
    @Size(max = 64, message = "昵称长度不能超过64")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "生日")
    private LocalDate birthday;
}
