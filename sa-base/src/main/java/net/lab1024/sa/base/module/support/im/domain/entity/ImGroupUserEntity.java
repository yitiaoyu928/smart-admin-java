package net.lab1024.sa.base.module.support.im.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * IM群组成员实体
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
@TableName("t_im_group_user")
public class ImGroupUserEntity {

    /**
     * 用户ID
     */
    @TableId
    private String userId;

    /**
     * 群组ID
     */
    private Long groupId;

    /**
     * 是否管理员
     */
    private Boolean managerFlag;

    /**
     * 加入时间
     */
    private LocalDateTime joinTime;

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