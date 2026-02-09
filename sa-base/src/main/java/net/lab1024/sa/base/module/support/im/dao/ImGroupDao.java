package net.lab1024.sa.base.module.support.im.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.base.module.support.im.domain.entity.ImGroupEntity;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupQueryForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImGroupVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * IM群组 Dao
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Mapper
public interface ImGroupDao extends BaseMapper<ImGroupEntity> {

    /**
     * 分页查询群组
     */
    List<ImGroupVO> query(Page<?> page, @Param("query") ImGroupQueryForm queryForm);

    /**
     * 查询用户所在的群组列表
     */
    List<ImGroupVO> queryUserGroups(@Param("page") Page<?> page, @Param("userId") String userId);
}