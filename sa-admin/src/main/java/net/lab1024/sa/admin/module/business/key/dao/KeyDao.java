package net.lab1024.sa.admin.module.business.key.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.key.domain.entity.KeyEntity;
import net.lab1024.sa.admin.module.business.key.domain.form.KeyQueryForm;
import net.lab1024.sa.admin.module.business.key.domain.vo.KeyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目密钥 dao
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Mapper
public interface KeyDao extends BaseMapper<KeyEntity> {

    /**
     * 查询项目密钥列表
     */
    List<KeyVO> queryKey(Page page, @Param("queryForm") KeyQueryForm queryForm);

    /**
     * 更新禁用状态
     */
    void updateDisableFlag(@Param("id") Long id, @Param("disabledFlag") Boolean disabledFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") Boolean deletedFlag);

}