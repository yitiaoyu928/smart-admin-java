package net.lab1024.sa.admin.module.business.project.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.project.domain.entity.ProjectEntity;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectQueryForm;
import net.lab1024.sa.admin.module.business.project.domain.vo.ProjectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目 dao
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Mapper
public interface ProjectDao extends BaseMapper<ProjectEntity> {

    /**
     * 查询项目列表
     */
    List<ProjectVO> queryProject(Page page, @Param("queryForm") ProjectQueryForm queryForm);

    /**
     * 查询所有项目
     */
    List<ProjectVO> selectProjectByDisabledAndDeleted(@Param("disabledFlag") Boolean disabledFlag, @Param("deletedFlag") Boolean deletedFlag);

    /**
     * 更新禁用状态
     */
    void updateDisableFlag(@Param("id") Long id, @Param("disabledFlag") Boolean disabledFlag);

}