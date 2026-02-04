package net.lab1024.sa.admin.module.business.project.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 项目 实体表
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 */
@Data
@TableName("t_project")
public class ProjectEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目标识
     */
    private String mark;

    /**
     * 项目版本
     */
    private String version;

    /**
     * 项目类型，APP,WEB,Windows
     */
    private String type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否禁用
     */
    private Boolean disabledFlag;

    /**
     * 是否删除
     */
    private Boolean deletedFlag;

    /**
     * 备注
     */
    private String remark;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

}