package net.lab1024.sa.admin.module.business.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.project.domain.entity.ProjectEntity;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectAddForm;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectQueryForm;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectUpdateForm;
import net.lab1024.sa.admin.module.business.project.domain.vo.ProjectVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;

import java.util.List;

/**
 * 项目 service
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
public interface ProjectService {

    /**
     * 查询项目列表
     */
    ResponseDTO<PageResult<ProjectVO>> queryProject(ProjectQueryForm projectQueryForm);

    /**
     * 添加项目
     */
    ResponseDTO<String> addProject(ProjectAddForm projectAddForm);

    /**
     * 更新项目
     */
    ResponseDTO<String> updateProject(ProjectUpdateForm projectUpdateForm);

    /**
     * 更新禁用/启用状态
     */
    ResponseDTO<String> updateDisableFlag(Long id);

    /**
     * 批量删除项目
     */
    ResponseDTO<String> batchUpdateDeleteFlag(List<Long> idList);

    /**
     * 查询全部项目
     */
    ResponseDTO<List<ProjectVO>> queryAllProject(Boolean disabledFlag);

    /**
     * 根据ID获取项目
     */
    ProjectEntity getById(Long id);

}