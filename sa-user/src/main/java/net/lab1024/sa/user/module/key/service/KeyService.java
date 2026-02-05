package net.lab1024.sa.user.module.key.service;

import net.lab1024.sa.user.module.key.domain.entity.KeyEntity;
import net.lab1024.sa.user.module.key.domain.form.KeyActivationForm;
import net.lab1024.sa.user.module.key.domain.vo.KeyVO;
import net.lab1024.sa.base.common.domain.ResponseDTO;

/**
 * 项目密钥 service（用户端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-05
 */
public interface KeyService {

    /**
     * 激活密钥
     */
    ResponseDTO<KeyVO> activateKey(KeyActivationForm activationForm);

    /**
     * 查询用户已激活的密钥列表
     */
    ResponseDTO<KeyVO> getUserActivatedKey(Long projectId);

    /**
     * 根据ID获取项目密钥
     */
    KeyEntity getById(Long id);

}