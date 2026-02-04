package net.lab1024.sa.admin.module.business.key.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.key.dao.KeyDao;
import net.lab1024.sa.admin.module.business.key.domain.entity.KeyEntity;
import net.lab1024.sa.base.module.support.job.core.SmartJob;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 密钥过期检测定时任务
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Slf4j
@Service
public class KeyExpireCheckJob implements SmartJob {

    @Resource
    private KeyDao keyDao;

    /**
     * 执行密钥过期检测
     * 查询过期时间小于当前时间且状态为正常的密钥，将其状态更新为过期
     *
     * @param param 可选参数
     * @return 执行结果
     */
    @Override
    public String run(String param) {
        try {
            LocalDateTime now = LocalDateTime.now();

            // 查询过期时间小于当前时间且状态为正常的密钥
            // 只检测已使用、未禁用、未删除的密钥
            LambdaQueryWrapper<KeyEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.lt(KeyEntity::getExpireTime, now)
                    .eq(KeyEntity::getStatus, 0)
                    .eq(KeyEntity::getUsingFlag, true)
                    .eq(KeyEntity::getDisabledFlag, false)
                    .eq(KeyEntity::getDeletedFlag, false);
            List<KeyEntity> expiredKeys = keyDao.selectList(queryWrapper);

            if (expiredKeys == null || expiredKeys.isEmpty()) {
                log.info("密钥过期检测完成，无过期密钥");
                return "执行完毕，无过期密钥";
            }

            // 批量更新密钥状态为过期
            int count = 0;
            for (KeyEntity keyEntity : expiredKeys) {
                KeyEntity updateEntity = new KeyEntity();
                updateEntity.setId(keyEntity.getId());
                updateEntity.setStatus(1); // 1=过期
                keyDao.updateById(updateEntity);
                count++;
            }

            log.info("密钥过期检测完成，更新过期密钥数量：{}", count);
            return "执行完毕，更新过期密钥数量：" + count;

        } catch (Exception e) {
            log.error("密钥过期检测任务执行失败", e);
            return "执行失败：" + e.getMessage();
        }
    }

}