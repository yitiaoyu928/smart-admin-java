package net.lab1024.sa.user.module.im.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupAddForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupQueryForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupUpdateForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImGroupVO;
import net.lab1024.sa.base.module.support.im.service.ImGroupService;
import org.springframework.web.bind.annotation.*;

/**
 * IM群组控制器（用户端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@RestController
@Tag(name = "IM群组")
@RequestMapping("/im/group")
public class ImGroupController {

    @Resource
    private ImGroupService imGroupService;

    @Operation(summary = "创建群组")
    @PostMapping("/create")
    @SaCheckLogin
    public ResponseDTO<ImGroupVO> create(@RequestBody @Valid ImGroupAddForm addForm) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupService.add(userId, addForm);
    }

    @Operation(summary = "更新群组")
    @PostMapping("/update")
    @SaCheckLogin
    public ResponseDTO<String> update(@RequestBody @Valid ImGroupUpdateForm updateForm) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupService.update(userId, updateForm);
    }

    @Operation(summary = "解散群组")
    @GetMapping("/dissolve/{id}")
    @SaCheckLogin
    public ResponseDTO<String> dissolve(@PathVariable Long id) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupService.delete(id, userId);
    }

    @Operation(summary = "查询我的群组列表")
    @GetMapping("/myGroups")
    @SaCheckLogin
    public ResponseDTO<PageResult<ImGroupVO>> queryMyGroups(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupService.queryUserGroups(userId, pageNum, pageSize);
    }

    @Operation(summary = "获取群组详情")
    @GetMapping("/detail/{id}")
    @SaCheckLogin
    public ResponseDTO<ImGroupVO> getDetail(@PathVariable Long id) {
        return imGroupService.getDetail(id);
    }
}