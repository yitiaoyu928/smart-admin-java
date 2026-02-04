package net.lab1024.sa.admin.module.business.key.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.key.domain.form.KeyAddForm;
import net.lab1024.sa.admin.module.business.key.domain.form.KeyQueryForm;
import net.lab1024.sa.admin.module.business.key.domain.form.KeyUpdateForm;
import net.lab1024.sa.admin.module.business.key.domain.vo.KeyVO;
import net.lab1024.sa.admin.module.business.key.service.KeyService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目密钥
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.KEY)
public class KeyController {

    @Resource
    private KeyService keyService;

    @PostMapping("/key/query")
    @Operation(summary = "项目密钥查询")
    public ResponseDTO<PageResult<KeyVO>> query(@Valid @RequestBody KeyQueryForm query) {
        return keyService.queryKey(query);
    }

    @Operation(summary = "添加项目密钥")
    @PostMapping("/key/add")
    @SaCheckPermission("business:key:add")
    public ResponseDTO<String> addKey(@Valid @RequestBody KeyAddForm keyAddForm) {
        return keyService.addKey(keyAddForm);
    }

    @Operation(summary = "更新项目密钥")
    @PostMapping("/key/update")
    @SaCheckPermission("business:key:update")
    public ResponseDTO<String> updateKey(@Valid @RequestBody KeyUpdateForm keyUpdateForm) {
        return keyService.updateKey(keyUpdateForm);
    }

    @Operation(summary = "更新项目密钥禁用/启用状态")
    @GetMapping("/key/update/disabled/{id}")
    @SaCheckPermission("business:key:disabled")
    public ResponseDTO<String> updateDisableFlag(@PathVariable Long id) {
        return keyService.updateDisableFlag(id);
    }

    @Operation(summary = "批量删除项目密钥")
    @PostMapping("/key/update/batch/delete")
    @SaCheckPermission("business:key:delete")
    public ResponseDTO<String> batchUpdateDeleteFlag(@RequestBody List<Long> idList) {
        return keyService.batchUpdateDeleteFlag(idList);
    }

    @Operation(summary = "重置密钥")
    @GetMapping("/key/reset/{id}")
    @SaCheckPermission("business:key:reset")
    public ResponseDTO<String> resetKey(@PathVariable Long id) {
        return keyService.resetKey(id);
    }

    @Operation(summary = "使用密钥")
    @GetMapping("/key/use")
    public ResponseDTO<KeyVO> useKey(@RequestParam String key) {
        return keyService.useKey(key);
    }

}