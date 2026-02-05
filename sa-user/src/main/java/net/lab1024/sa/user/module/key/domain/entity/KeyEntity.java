package net.lab1024.sa.user.module.key.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 项目密钥 实体表（用户端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-05
 */
@Data
@TableName("t_key")
public class KeyEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 密钥
     */
    @TableField("`key`")
    private String key;

    /**
     * 密钥周期类型，DAY=天卡，Month=月卡，quarter=季卡，年卡=Year
     */
    private String cycleType;

    /**
     * 是否禁用
     */
    private Boolean disabledFlag;

    /**
     * 是否删除
     */
    private Boolean deletedFlag;

    /**
     * 是否使用
     */
    private Boolean usingFlag;

    /**
     * 使用时间
     */
    private LocalDateTime usingTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 状态：0=正常,1=过期
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

}