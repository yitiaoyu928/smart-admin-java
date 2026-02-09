package net.lab1024.sa.base.module.support.im.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * IM群组公告实体
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Data
@TableName("t_im_group_notice")
public class ImGroupNoticeEntity {

    /**
     * 公告ID
     */
    @TableId
    private String id;

    /**
     * 群组ID
     */
    private Long groupId;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 创建者ID
     */
    private String createBy;

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