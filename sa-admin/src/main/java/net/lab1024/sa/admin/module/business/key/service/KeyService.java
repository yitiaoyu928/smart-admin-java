package net.lab1024.sa.admin.module.business.key.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.key.domain.entity.KeyEntity;
import net.lab1024.sa.admin.module.business.key.domain.form.KeyAddForm;
import net.lab1024.sa.admin.module.business.key.domain.form.KeyQueryForm;
import net.lab1024.sa.admin.module.business.key.domain.form.KeyUpdateForm;
import net.lab1024.sa.admin.module.business.key.domain.vo.KeyVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;

import java.util.List;

/**
 * 项目密钥 service
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
public interface KeyService {

    /**
     * 查询项目密钥列表
     */
    ResponseDTO<PageResult<KeyVO>> queryKey(KeyQueryForm keyQueryForm);

    /**
     * 添加项目密钥
     */
    ResponseDTO<String> addKey(KeyAddForm keyAddForm);

    /**
     * 更新项目密钥
     */
    ResponseDTO<String> updateKey(KeyUpdateForm keyUpdateForm);

    /**
     * 更新禁用/启用状态
     */
    ResponseDTO<String> updateDisableFlag(Long id);

    /**
     * 批量删除项目密钥
     */
    ResponseDTO<String> batchUpdateDeleteFlag(List<Long> idList);

    /**
     * 根据ID获取项目密钥
     */
    KeyEntity getById(Long id);

    /**
     * 重置密钥
     */
    ResponseDTO<String> resetKey(Long id);

    /**
     * 使用密钥
     */
    ResponseDTO<KeyVO> useKey(String key);

}