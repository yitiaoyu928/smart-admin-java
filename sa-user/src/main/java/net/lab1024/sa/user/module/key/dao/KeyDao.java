package net.lab1024.sa.user.module.key.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.user.module.key.domain.entity.KeyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目密钥 dao（用户端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-05
 */
@Mapper
public interface KeyDao extends BaseMapper<KeyEntity> {

    /**
     * 更新禁用状态
     */
    void updateDisableFlag(@Param("id") Long id, @Param("disabledFlag") Boolean disabledFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") Boolean deletedFlag);

}