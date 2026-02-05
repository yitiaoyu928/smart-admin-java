package net.lab1024.sa.user.module.key.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.user.module.key.domain.form.KeyActivationForm;
import net.lab1024.sa.user.module.key.domain.vo.KeyVO;
import net.lab1024.sa.user.module.key.service.KeyService;
import net.lab1024.sa.base.common.annoation.NoNeedLogin;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

/**
 * 项目密钥（用户端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-05
 */
@RestController
@Tag(name = "用户端密钥管理")
@RequestMapping("/key")
public class KeyController {

    @Resource
    private KeyService keyService;

    @PostMapping("/activate")
    @Operation(summary = "激活密钥")
    public ResponseDTO<KeyVO> activateKey(@Valid @RequestBody KeyActivationForm activationForm) {
        return keyService.activateKey(activationForm);
    }

    @PostMapping("/reset")
    @Operation(summary = "重置密钥")
    public ResponseDTO<String> resetKey(@RequestParam String key, @RequestParam Long projectId) {
        return keyService.resetKey(key, projectId);
    }

    @PostMapping("/update")
    @Operation(summary = "更新密钥")
    public ResponseDTO<String> updateKey(@RequestParam String oldKey, @RequestParam String newKey, @RequestParam Long projectId) {
        return keyService.updateKey(oldKey, newKey, projectId);
    }

    @GetMapping("/query")
    @Operation(summary = "查询密钥")
    public ResponseDTO<KeyVO> getKey(@RequestParam String key, @RequestParam Long projectId) {
        return keyService.getKey(key, projectId);
    }

}