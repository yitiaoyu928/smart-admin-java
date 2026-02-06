package net.lab1024.sa.admin.module.system.im.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.module.support.im.domain.form.ImMessageQueryForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImMessageRevokeForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImMessageSendForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImMessageVO;
import net.lab1024.sa.base.module.support.im.service.ImMessageService;
import org.springframework.web.bind.annotation.*;

/**
 * IM消息控制器
 *
 * @Author 1024创新实验室
 * @Date 2026-02-06
 */
@RestController
@Tag(name = AdminSwaggerTagConst.System.IM_MESSAGE)
@RequestMapping("/im/message")
public class ImMessageController {

    @Resource
    private ImMessageService imMessageService;

    @Operation(summary = "发送消息")
    @PostMapping("/send")
    @SaCheckLogin
    public ResponseDTO<ImMessageVO> send(@RequestBody @Valid ImMessageSendForm sendForm) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imMessageService.send(userId, sendForm);
    }

    @Operation(summary = "撤回消息")
    @PostMapping("/revoke")
    @SaCheckLogin
    public ResponseDTO<String> revoke(@RequestBody @Valid ImMessageRevokeForm revokeForm) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imMessageService.revoke(revokeForm.getUId(), userId);
    }

    @Operation(summary = "删除消息")
    @GetMapping("/delete/{uId}")
    @SaCheckLogin
    public ResponseDTO<String> delete(@PathVariable String uId) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imMessageService.delete(uId, userId);
    }

    @Operation(summary = "分页查询消息")
    @PostMapping("/query")
    @SaCheckLogin
    public ResponseDTO<PageResult<ImMessageVO>> query(@RequestBody ImMessageQueryForm queryForm) {
        return imMessageService.query(queryForm);
    }

    @Operation(summary = "查询对话消息列表")
    @GetMapping("/chat/{otherUserId}")
    @SaCheckLogin
    public ResponseDTO<PageResult<ImMessageVO>> queryChatMessages(
            @PathVariable String otherUserId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imMessageService.queryChatMessages(userId, otherUserId, pageNum, pageSize);
    }

    @Operation(summary = "标记消息为已读")
    @PostMapping("/markAsRead/{otherUserId}")
    @SaCheckLogin
    public ResponseDTO<String> markAsRead(@PathVariable String otherUserId) {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imMessageService.markAsRead(userId, otherUserId);
    }

    @Operation(summary = "获取未读消息数")
    @GetMapping("/unreadCount")
    @SaCheckLogin
    public ResponseDTO<Long> getUnreadCount() {
        String userId = cn.dev33.satoken.stp.StpUtil.getLoginIdAsString();
        return imMessageService.getUnreadCount(userId);
    }
}