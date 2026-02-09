package net.lab1024.sa.admin.module.system.im.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
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
 * IM群组成员控制器
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@RestController
@Tag(name = AdminSwaggerTagConst.System.IM_GROUP_USER)
@RequestMapping("/im/group/user")
public class ImGroupUserController {

    @Resource
    private ImGroupUserService imGroupUserService;

    @Operation(summary = "添加群组成员（批量）")
    @PostMapping("/addMembers")
    @SaCheckLogin
    public ResponseDTO<String> addMembers(@RequestBody @Valid ImGroupUserAddForm addForm) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupUserService.addMembers(userId, addForm);
    }

    @Operation(summary = "更新群组成员信息")
    @PostMapping("/update")
    @SaCheckLogin
    public ResponseDTO<String> update(@RequestBody @Valid ImGroupUserUpdateForm updateForm) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupUserService.update(userId, updateForm);
    }

    @Operation(summary = "移除群组成员")
    @GetMapping("/remove/{groupId}/{memberId}")
    @SaCheckLogin
    public ResponseDTO<String> removeMember(@PathVariable Long groupId, @PathVariable String memberId) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupUserService.removeMember(userId, groupId, memberId);
    }

    @Operation(summary = "退出群组")
    @GetMapping("/quit/{groupId}")
    @SaCheckLogin
    public ResponseDTO<String> quitGroup(@PathVariable Long groupId) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupUserService.quitGroup(userId, groupId);
    }

    @Operation(summary = "分页查询群组成员")
    @PostMapping("/query")
    @SaCheckLogin
    public ResponseDTO<PageResult<ImGroupUserVO>> query(@RequestBody ImGroupUserQueryForm queryForm) {
        return imGroupUserService.query(queryForm);
    }

    @Operation(summary = "查询群组成员列表")
    @GetMapping("/members/{groupId}")
    @SaCheckLogin
    public ResponseDTO<List<ImGroupUserVO>> queryGroupMembers(@PathVariable Long groupId) {
        return imGroupUserService.queryGroupMembers(groupId);
    }

    @Operation(summary = "获取群组成员数量")
    @GetMapping("/count/{groupId}")
    @SaCheckLogin
    public ResponseDTO<Long> getGroupMemberCount(@PathVariable Long groupId) {
        return imGroupUserService.getGroupMemberCount(groupId);
    }
}