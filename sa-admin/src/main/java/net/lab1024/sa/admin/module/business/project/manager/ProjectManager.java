package net.lab1024.sa.admin.module.business.project.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.project.dao.ProjectDao;
import net.lab1024.sa.admin.module.business.project.domain.entity.ProjectEntity;
import org.springframework.stereotype.Service;

/**
 * 项目 manager
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Service
public class ProjectManager extends ServiceImpl<ProjectDao, ProjectEntity> {

    @Resource
    private ProjectDao projectDao;

}