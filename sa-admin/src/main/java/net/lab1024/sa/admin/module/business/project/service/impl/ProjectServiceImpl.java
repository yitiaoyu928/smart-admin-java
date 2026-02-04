package net.lab1024.sa.admin.module.business.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.project.dao.ProjectDao;
import net.lab1024.sa.admin.module.business.project.domain.entity.ProjectEntity;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectAddForm;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectQueryForm;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectUpdateForm;
import net.lab1024.sa.admin.module.business.project.domain.vo.ProjectVO;
import net.lab1024.sa.admin.module.business.project.manager.ProjectManager;
import net.lab1024.sa.admin.module.business.project.service.ProjectService;
import net.lab1024.sa.base.common.code.UserErrorCode;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目 service 实现
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectDao projectDao;

    @Resource
    private ProjectManager projectManager;

    @Override
    public ProjectEntity getById(Long id) {
        return projectDao.selectById(id);
    }

    /**
     * 查询项目列表
     */
    @Override
    public ResponseDTO<PageResult<ProjectVO>> queryProject(ProjectQueryForm projectQueryForm) {
        projectQueryForm.setDeletedFlag(false);
        Page pageParam = SmartPageUtil.convert2PageQuery(projectQueryForm);

        List<ProjectVO> projectList = projectDao.queryProject(pageParam, projectQueryForm);
        PageResult<ProjectVO> pageResult = SmartPageUtil.convert2PageResult(pageParam, projectList);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 新增项目
     */
    @Override
    public ResponseDTO<String> addProject(ProjectAddForm projectAddForm) {
        // 校验项目标识是否重复
        LambdaQueryWrapper<ProjectEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProjectEntity::getMark, projectAddForm.getMark())
                .eq(ProjectEntity::getDeletedFlag, false);
        ProjectEntity existEntity = projectDao.selectOne(queryWrapper);
        if (existEntity != null) {
            return ResponseDTO.userErrorParam("项目标识已存在");
        }

        ProjectEntity entity = SmartBeanUtil.copy(projectAddForm, ProjectEntity.class);
        entity.setDeletedFlag(false);
        projectDao.insert(entity);
        return ResponseDTO.ok();
    }

    /**
     * 更新项目
     */
    @Override
    public ResponseDTO<String> updateProject(ProjectUpdateForm projectUpdateForm) {
        Long id = projectUpdateForm.getId();
        ProjectEntity projectEntity = projectDao.selectById(id);
        if (projectEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        // 校验项目标识是否重复（排除自己）
        LambdaQueryWrapper<ProjectEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProjectEntity::getMark, projectUpdateForm.getMark())
                .eq(ProjectEntity::getDeletedFlag, false)
                .ne(ProjectEntity::getId, id);
        ProjectEntity existEntity = projectDao.selectOne(queryWrapper);
        if (existEntity != null) {
            return ResponseDTO.userErrorParam("项目标识已存在");
        }

        ProjectEntity entity = SmartBeanUtil.copy(projectUpdateForm, ProjectEntity.class);
        projectDao.updateById(entity);
        return ResponseDTO.ok();
    }

    /**
     * 更新禁用/启用状态
     */
    @Override
    public ResponseDTO<String> updateDisableFlag(Long id) {
        if (id == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        ProjectEntity projectEntity = projectDao.selectById(id);
        if (projectEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        projectDao.updateDisableFlag(id, !projectEntity.getDisabledFlag());
        return ResponseDTO.ok();
    }

    /**
     * 批量删除项目
     */
    @Override
    public ResponseDTO<String> batchUpdateDeleteFlag(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }
        List<ProjectEntity> projectEntityList = projectDao.selectBatchIds(idList);
        if (CollectionUtils.isEmpty(projectEntityList)) {
            return ResponseDTO.ok();
        }
        // 更新删除
        List<ProjectEntity> deleteList = idList.stream().map(id -> {
            ProjectEntity updateProject = new ProjectEntity();
            updateProject.setId(id);
            updateProject.setDeletedFlag(true);
            return updateProject;
        }).collect(Collectors.toList());
        projectManager.updateBatchById(deleteList);
        return ResponseDTO.ok();
    }

    /**
     * 查询全部项目
     */
    @Override
    public ResponseDTO<List<ProjectVO>> queryAllProject(Boolean disabledFlag) {
        List<ProjectVO> projectList = projectDao.selectProjectByDisabledAndDeleted(disabledFlag, false);
        return ResponseDTO.ok(projectList);
    }

}