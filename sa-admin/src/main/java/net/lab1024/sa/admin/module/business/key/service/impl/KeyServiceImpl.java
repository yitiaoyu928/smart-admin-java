package net.lab1024.sa.admin.module.business.key.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
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

import java.time.Duration;
import java.time.LocalDateTime;
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
        // 检查项目是否存在
        if (keyAddForm.getProjectId() == null) {
            // 可以添加项目存在性检查逻辑
            return ResponseDTO.userErrorParam("项目不存在");
        }
        // 检查密钥是否已存在
        LambdaQueryWrapper<KeyEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KeyEntity::getKey, keyAddForm.getKey())
                .eq(KeyEntity::getDeletedFlag, false);
        KeyEntity existKey = keyDao.selectOne(queryWrapper);
        if (existKey != null) {
            return ResponseDTO.userErrorParam("密钥已存在");
        }
        // 随机生成密钥：6位随机字符 + UUID(无横线)
        String baseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        String randomPrefix = RandomUtil.randomString(baseString, 6);
        String uuidWithoutDash = IdUtil.fastSimpleUUID();
        String key = randomPrefix + uuidWithoutDash;
        keyAddForm.setKey(key);
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

    @Override
    public ResponseDTO<String> resetKey(Long id) {
        // 检查密钥是否存在
        KeyEntity keyEntity = keyDao.selectById(id);
        if (keyEntity == null) {
            return ResponseDTO.userErrorParam("项目密钥不存在");
        }

        // 生成新的随机密钥：6位随机字符 + UUID(无横线)
        String baseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        String newKey;
        int maxAttempts = 10;
        int attempts = 0;

        // 循环生成直到找到一个不重复的密钥
        do {
            String randomPrefix = RandomUtil.randomString(baseString, 6);
            String uuidWithoutDash = IdUtil.fastSimpleUUID();
            newKey = randomPrefix + uuidWithoutDash;
            LambdaQueryWrapper<KeyEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(KeyEntity::getKey, newKey)
                    .eq(KeyEntity::getDeletedFlag, false);
            KeyEntity existKey = keyDao.selectOne(queryWrapper);
            if (existKey == null) {
                break;
            }
            attempts++;
        } while (attempts < maxAttempts);

        if (attempts >= maxAttempts) {
            return ResponseDTO.userErrorParam("密钥生成失败，请重试");
        }

        // 更新密钥
        KeyEntity updateEntity = new KeyEntity();
        updateEntity.setId(id);
        updateEntity.setKey(newKey);
        keyDao.updateById(updateEntity);

        return ResponseDTO.ok(newKey);
    }

    @Override
    public ResponseDTO<KeyVO> useKey(String key) {
        // 根据密钥查询密钥信息
        LambdaQueryWrapper<KeyEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KeyEntity::getKey, key)
                .eq(KeyEntity::getDeletedFlag, false);
        KeyEntity keyEntity = keyDao.selectOne(queryWrapper);

        if (keyEntity == null) {
            return ResponseDTO.userErrorParam("密钥不存在");
        }

        if (keyEntity.getDisabledFlag()) {
            return ResponseDTO.userErrorParam("密钥已被禁用");
        }

        if (keyEntity.getUsingFlag()) {
            return ResponseDTO.userErrorParam("密钥已被使用");
        }

        // 计算过期时间
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime;
        String cycleType = keyEntity.getCycleType();

        switch (cycleType.toUpperCase()) {
            case "DAY":
                expireTime = now.plusHours(24);
                break;
            case "MONTH":
                expireTime = now.plusHours(31 * 24);
                break;
            case "QUARTER":
                expireTime = now.plusHours(3 * 31 * 24);
                break;
            case "YEAR":
                expireTime = now.plusHours(12 * 31 * 24);
                break;
            default:
                return ResponseDTO.userErrorParam("密钥周期类型无效");
        }

        // 更新使用状态、使用时间、过期时间、状态
        KeyEntity updateEntity = new KeyEntity();
        updateEntity.setId(keyEntity.getId());
        updateEntity.setUsingFlag(true);
        updateEntity.setUsingTime(now);
        updateEntity.setExpireTime(expireTime);
        updateEntity.setStatus(0); // 0=正常
        keyDao.updateById(updateEntity);

        // 返回更新后的密钥信息
        KeyVO keyVO = SmartBeanUtil.copy(keyEntity, KeyVO.class);
        keyVO.setUsingFlag(true);
        keyVO.setUsingTime(now);
        keyVO.setExpireTime(expireTime);
        keyVO.setStatus(0); // 0=正常

        return ResponseDTO.ok(keyVO);
    }

}