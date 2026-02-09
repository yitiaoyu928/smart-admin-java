package net.lab1024.sa.base.module.support.im.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * IM群组实体
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
@TableName("t_im_group")
public class ImGroupEntity {

    /**
     * 群组ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 群组名称
     */
    private String name;

    /**
     * 群组头像
     */
    private String avatar;

    /**
     * 群组成员最大数量
     */
    private Integer memberCount;

    /**
     * 群组创建者ID
     */
    private String ownerId;

    /**
     * 群组类型
     */
    private Integer type;

    /**
     * 群组有效期
     */
    private LocalDateTime duration;

    /**
     * 群组状态
     */
    private Integer statusFlag;

    /**
     * 是否删除
     */
    private Boolean deletedFlag;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}