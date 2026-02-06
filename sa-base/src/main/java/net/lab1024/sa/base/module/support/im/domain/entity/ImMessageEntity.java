package net.lab1024.sa.base.module.support.im.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * IM消息实体
 *
 * @Author 1024创新实验室
 * @Date 2026-02-06
 */
@Data
@TableName("t_im_message")
public class ImMessageEntity {

    /**
     * 消息ID
     */
    @TableId
    private String uId;

    /**
     * 发送者ID
     */
    private String fromId;

    /**
     * 接收者ID
     */
    private String receiveId;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否删除
     */
    private Integer deletedFlag;

    /**
     * 是否已读
     */
    private Integer readFlag;

    /**
     * 是否撤回
     */
    private Integer revokeFlag;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}