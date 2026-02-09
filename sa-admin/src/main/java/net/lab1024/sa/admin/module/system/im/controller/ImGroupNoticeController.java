package net.lab1024.sa.admin.module.system.im.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupNoticeAddForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupNoticeQueryForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupNoticeUpdateForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImGroupNoticeVO;
import net.lab1024.sa.base.module.support.im.service.ImGroupNoticeService;
import org.springframework.web.bind.annotation.*;

/**
 * IM群组公告控制器
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@RestController
@Tag(name = AdminSwaggerTagConst.System.IM_GROUP_NOTICE)
@RequestMapping("/im/group/notice")
public class ImGroupNoticeController {

    @Resource
    private ImGroupNoticeService imGroupNoticeService;

    @Operation(summary = "新增群组公告")
    @PostMapping("/add")
    @SaCheckLogin
    public ResponseDTO<ImGroupNoticeVO> add(@RequestBody @Valid ImGroupNoticeAddForm addForm) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupNoticeService.add(userId, addForm);
    }

    @Operation(summary = "更新群组公告")
    @PostMapping("/update")
    @SaCheckLogin
    public ResponseDTO<String> update(@RequestBody @Valid ImGroupNoticeUpdateForm updateForm) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupNoticeService.update(userId, updateForm);
    }

    @Operation(summary = "删除群组公告")
    @GetMapping("/delete/{id}")
    @SaCheckLogin
    public ResponseDTO<String> delete(@PathVariable String id) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imGroupNoticeService.delete(id, userId);
    }

    @Operation(summary = "分页查询群组公告")
    @PostMapping("/query")
    @SaCheckLogin
    public ResponseDTO<PageResult<ImGroupNoticeVO>> query(@RequestBody ImGroupNoticeQueryForm queryForm) {
        return imGroupNoticeService.query(queryForm);
    }

    @Operation(summary = "获取群组最新公告")
    @GetMapping("/latest/{groupId}")
    @SaCheckLogin
    public ResponseDTO<ImGroupNoticeVO> getLatestNotice(@PathVariable Long groupId) {
        return imGroupNoticeService.getLatestNotice(groupId);
    }
}