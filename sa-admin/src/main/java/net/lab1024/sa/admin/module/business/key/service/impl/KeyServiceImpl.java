package net.lab1024.sa.admin.module.business.key.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.key.dao.KeyDao;
import net.lab1024.sa.admin.module.business.key.domain.entity.KeyEntity;
import net.lab1024.sa.admin.module.business.key.domain.form.KeyAddForm;
import net.lab1024.sa.admin.module.business.key.domain.form.KeyQueryForm;
import net.lab1024.sa.admin.module.business.key.domain.form.KeyUpdateForm;
import net.lab1024.sa.admin.module.business.key.domain.vo.KeyVO;
import net.lab1024.sa.admin.module.business.key.service.KeyService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目密钥 service 实现类
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Service
public class KeyServiceImpl implements KeyService {

    @Resource
    private KeyDao keyDao;

    @Override
    public ResponseDTO<PageResult<KeyVO>> queryKey(KeyQueryForm keyQueryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(keyQueryForm);
        List<KeyVO> list = keyDao.queryKey(page, keyQueryForm);
        return ResponseDTO.ok(SmartPageUtil.convert2PageResult(page, list));
    }

    @Override
    public ResponseDTO<String> addKey(KeyAddForm keyAddForm) {
        // 检查密钥是否已存在
        LambdaQueryWrapper<KeyEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KeyEntity::getKey, keyAddForm.getKey())
                .eq(KeyEntity::getDeletedFlag, false);
        KeyEntity existKey = keyDao.selectOne(queryWrapper);
        if (existKey != null) {
            return ResponseDTO.userErrorParam("密钥已存在");
        }

        // 检查项目是否存在
        if (keyAddForm.getProjectId() != null) {
            // 可以添加项目存在性检查逻辑
        }

        KeyEntity keyEntity = SmartBeanUtil.copy(keyAddForm, KeyEntity.class);
        keyDao.insert(keyEntity);
        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO<String> updateKey(KeyUpdateForm keyUpdateForm) {
        // 检查密钥是否已被其他记录使用
        LambdaQueryWrapper<KeyEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KeyEntity::getKey, keyUpdateForm.getKey())
                .eq(KeyEntity::getDeletedFlag, false)
                .ne(KeyEntity::getId, keyUpdateForm.getId());
        KeyEntity existKey = keyDao.selectOne(queryWrapper);
        if (existKey != null) {
            return ResponseDTO.userErrorParam("密钥已存在");
        }

        KeyEntity keyEntity = SmartBeanUtil.copy(keyUpdateForm, KeyEntity.class);
        keyDao.updateById(keyEntity);
        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO<String> updateDisableFlag(Long id) {
        KeyEntity keyEntity = keyDao.selectById(id);
        if (keyEntity == null) {
            return ResponseDTO.userErrorParam("项目密钥不存在");
        }
        Boolean newStatus = !keyEntity.getDisabledFlag();
        keyDao.updateDisableFlag(id, newStatus);
        return ResponseDTO.ok();
    }

    @Override
    public ResponseDTO<String> batchUpdateDeleteFlag(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }
        keyDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    @Override
    public KeyEntity getById(Long id) {
        return keyDao.selectById(id);
    }

}