package net.lab1024.sa.user.module.key.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.user.module.key.dao.KeyDao;
import net.lab1024.sa.user.module.key.domain.entity.KeyEntity;
import net.lab1024.sa.user.module.key.domain.form.KeyActivationForm;
import net.lab1024.sa.user.module.key.domain.vo.KeyVO;
import net.lab1024.sa.user.module.key.service.KeyService;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 项目密钥 service 实现类（用户端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-05
 */
@Service
@Slf4j
public class KeyServiceImpl implements KeyService {

    @Resource
    private KeyDao keyDao;

    @Override
    public ResponseDTO<String> resetKey(String currentKey) {
        // 根据当前密钥查询密钥信息
        LambdaQueryWrapper<KeyEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KeyEntity::getKey, currentKey)
                .eq(KeyEntity::getDeletedFlag, false);
        KeyEntity keyEntity = keyDao.selectOne(queryWrapper);

        if (keyEntity == null) {
            log.warn("密钥重置失败，密钥不存在: {}", currentKey);
            return ResponseDTO.userErrorParam("密钥不存在");
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
            LambdaQueryWrapper<KeyEntity> checkWrapper = new LambdaQueryWrapper<>();
            checkWrapper.eq(KeyEntity::getKey, newKey)
                    .eq(KeyEntity::getDeletedFlag, false);
            KeyEntity existKey = keyDao.selectOne(checkWrapper);
            if (existKey == null) {
                break;
            }
            attempts++;
        } while (attempts < maxAttempts);

        if (attempts >= maxAttempts) {
            log.warn("密钥重置失败，生成新密钥失败，密钥ID: {}", keyEntity.getId());
            return ResponseDTO.userErrorParam("密钥生成失败，请重试");
        }

        // 更新密钥
        KeyEntity updateEntity = new KeyEntity();
        updateEntity.setId(keyEntity.getId());
        updateEntity.setKey(newKey);
        keyDao.updateById(updateEntity);

        log.info("密钥重置成功: 密钥ID={}, 旧密钥={}, 新密钥={}", keyEntity.getId(), currentKey, newKey);

        return ResponseDTO.ok(newKey);
    }

    @Override
    public ResponseDTO<KeyVO> activateKey(KeyActivationForm activationForm) {
        String keyStr = activationForm.getKey();

        // 根据密钥查询密钥信息
        LambdaQueryWrapper<KeyEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KeyEntity::getKey, keyStr)
                .eq(KeyEntity::getDeletedFlag, false);
        KeyEntity keyEntity = keyDao.selectOne(queryWrapper);

        if (keyEntity == null) {
            log.warn("密钥激活失败，密钥不存在: {}", keyStr);
            return ResponseDTO.userErrorParam("密钥不存在");
        }

        if (keyEntity.getDisabledFlag()) {
            log.warn("密钥激活失败，密钥已被禁用: {}", keyStr);
            return ResponseDTO.userErrorParam("密钥已被禁用");
        }

        if (keyEntity.getUsingFlag()) {
            log.warn("密钥激活失败，密钥已被使用: {}", keyStr);
            return ResponseDTO.userErrorParam("密钥已被使用");
        }

        // 检查密钥是否已过期（如果之前被使用过）
        if (keyEntity.getExpireTime() != null && keyEntity.getExpireTime().isBefore(LocalDateTime.now())) {
            log.warn("密钥激活失败，密钥已过期: {}", keyStr);
            return ResponseDTO.userErrorParam("密钥已过期");
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
                log.warn("密钥激活失败，密钥周期类型无效: {}", cycleType);
                return ResponseDTO.userErrorParam("密钥周期类型无效");
        }

        // 获取当前登录用户ID（从请求上下文中获取）
        Long userId = SmartRequestUtil.getRequestUserId();
        Long projectId = keyEntity.getProjectId();

        // 更新使用状态、使用时间、过期时间、状态
        KeyEntity updateEntity = new KeyEntity();
        updateEntity.setId(keyEntity.getId());
        updateEntity.setUsingFlag(true);
        updateEntity.setUsingTime(now);
        updateEntity.setExpireTime(expireTime);
        updateEntity.setStatus(0); // 0=正常
        keyDao.updateById(updateEntity);

        log.info("密钥激活成功: 密钥ID={}, 项目ID={}, 用户ID={}, 过期时间={}", keyEntity.getId(), projectId, userId, expireTime);

        // 返回更新后的密钥信息
        KeyVO keyVO = SmartBeanUtil.copy(keyEntity, KeyVO.class);
        keyVO.setUsingFlag(true);
        keyVO.setUsingTime(now);
        keyVO.setExpireTime(expireTime);
        keyVO.setStatus(0); // 0=正常

        return ResponseDTO.ok(keyVO);
    }

    @Override
    public ResponseDTO<KeyVO> getUserActivatedKey(Long projectId) {
        // 获取当前登录用户ID
        Long userId = SmartRequestUtil.getRequestUserId();

        // 查询该用户在该项目下已激活且未过期的密钥
        LambdaQueryWrapper<KeyEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KeyEntity::getProjectId, projectId)
                .eq(KeyEntity::getDeletedFlag, false)
                .eq(KeyEntity::getUsingFlag, true)
                .eq(KeyEntity::getStatus, 0); // 0=正常

        // 获取第一个匹配的密钥
        KeyEntity keyEntity = keyDao.selectOne(queryWrapper);

        if (keyEntity == null) {
            return ResponseDTO.ok();
        }

        // 检查是否已过期
        if (keyEntity.getExpireTime() != null && keyEntity.getExpireTime().isBefore(LocalDateTime.now())) {
            // 更新状态为已过期
            KeyEntity updateEntity = new KeyEntity();
            updateEntity.setId(keyEntity.getId());
            updateEntity.setStatus(1); // 1=过期
            keyDao.updateById(updateEntity);

            KeyVO keyVO = SmartBeanUtil.copy(keyEntity, KeyVO.class);
            keyVO.setStatus(1);
            return ResponseDTO.ok(keyVO);
        }

        KeyVO keyVO = SmartBeanUtil.copy(keyEntity, KeyVO.class);
        return ResponseDTO.ok(keyVO);
    }

    @Override
    public KeyEntity getById(Long id) {
        return keyDao.selectById(id);
    }

}