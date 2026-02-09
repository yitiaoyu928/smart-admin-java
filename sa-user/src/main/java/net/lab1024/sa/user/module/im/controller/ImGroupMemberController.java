package net.lab1024.sa.user.module.im.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupUserAddForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupUserQueryForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupUserUpdateForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImGroupUserVO;
import net.lab1024.sa.base.module.support.im.service.ImGroupUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IM群组成员控制器（用户端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@RestController
@Tag(name = "IM群组成员")
@RequestMapping("/im/group/member")
public class ImGroupMemberController {

    @Resource
    private ImGroupUserService imGroupUserService;

    @Operation(summary = "邀请成员加入群组")
    @PostMapping("/invite")
    @SaCheckLogin
    public ResponseDTO<String> invite(@RequestBody @Valid ImGroupUserAddForm addForm) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupUserService.addMembers(userId, addForm);
    }

    @Operation(summary = "设置管理员")
    @PostMapping("/setManager")
    @SaCheckLogin
    public ResponseDTO<String> setManager(@RequestBody @Valid ImGroupUserUpdateForm updateForm) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupUserService.update(userId, updateForm);
    }

    @Operation(summary = "移除成员")
    @GetMapping("/remove/{groupId}/{memberId}")
    @SaCheckLogin
    public ResponseDTO<String> remove(@PathVariable Long groupId, @PathVariable String memberId) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupUserService.removeMember(userId, groupId, memberId);
    }

    @Operation(summary = "退出群组")
    @GetMapping("/quit/{groupId}")
    @SaCheckLogin
    public ResponseDTO<String> quit(@PathVariable Long groupId) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupUserService.quitGroup(userId, groupId);
    }

    @Operation(summary = "查询群组成员列表")
    @GetMapping("/list/{groupId}")
    @SaCheckLogin
    public ResponseDTO<List<ImGroupUserVO>> getMembers(@PathVariable Long groupId) {
        return imGroupUserService.queryGroupMembers(groupId);
    }

    @Operation(summary = "获取群组成员数量")
    @GetMapping("/count/{groupId}")
    @SaCheckLogin
    public ResponseDTO<Long> getMemberCount(@PathVariable Long groupId) {
        return imGroupUserService.getGroupMemberCount(groupId);
    }
}