package net.lab1024.sa.user.module.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.user.module.user.domain.request.UserPasswordUpdateForm;
import net.lab1024.sa.user.module.user.domain.request.UserUpdateForm;
import net.lab1024.sa.user.module.user.domain.vo.UserVO;
import net.lab1024.sa.user.module.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "个人信息")
public class UserController {

    @Resource
    private UserService userService;

    @Operation(summary = "获取个人信息")
    @GetMapping("/info")
    public ResponseDTO<UserVO> getUserInfo() {
        return userService.getUserInfo();
    }

    @Operation(summary = "修改个人信息")
    @PostMapping("/update")
    public ResponseDTO<String> updateUserInfo(@RequestBody @Valid UserUpdateForm form) {
        return userService.updateUserInfo(form);
    }

    @Operation(summary = "修改密码")
    @PostMapping("/password/update")
    public ResponseDTO<String> updatePassword(@RequestBody @Valid UserPasswordUpdateForm form) {
        return userService.updatePassword(form);
    }
}
