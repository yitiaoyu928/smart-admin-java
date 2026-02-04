package net.lab1024.sa.admin.module.business.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectAddForm;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectQueryForm;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectUpdateForm;
import net.lab1024.sa.admin.module.business.project.domain.vo.ProjectVO;
import net.lab1024.sa.admin.module.business.project.service.ProjectService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.PROJECT)
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @PostMapping("/project/query")
    @Operation(summary = "项目管理查询")
    public ResponseDTO<PageResult<ProjectVO>> query(@Valid @RequestBody ProjectQueryForm query) {
        return projectService.queryProject(query);
    }

    @Operation(summary = "添加项目")
    @PostMapping("/project/add")
    @SaCheckPermission("business:project:add")
    public ResponseDTO<String> addProject(@Valid @RequestBody ProjectAddForm projectAddForm) {
        return projectService.addProject(projectAddForm);
    }

    @Operation(summary = "更新项目")
    @PostMapping("/project/update")
    @SaCheckPermission("business:project:update")
    public ResponseDTO<String> updateProject(@Valid @RequestBody ProjectUpdateForm projectUpdateForm) {
        return projectService.updateProject(projectUpdateForm);
    }

    @Operation(summary = "更新项目禁用/启用状态")
    @GetMapping("/project/update/disabled/{id}")
    @SaCheckPermission("business:project:disabled")
    public ResponseDTO<String> updateDisableFlag(@PathVariable Long id) {
        return projectService.updateDisableFlag(id);
    }

    @Operation(summary = "批量删除项目")
    @PostMapping("/project/update/batch/delete")
    @SaCheckPermission("business:project:delete")
    public ResponseDTO<String> batchUpdateDeleteFlag(@RequestBody List<Long> idList) {
        return projectService.batchUpdateDeleteFlag(idList);
    }

    @Operation(summary = "查询所有项目")
    @GetMapping("/project/queryAll")
    public ResponseDTO<List<ProjectVO>> queryAllProject(@RequestParam(value = "disabledFlag", required = false) Boolean disabledFlag) {
        return projectService.queryAllProject(disabledFlag);
    }

}