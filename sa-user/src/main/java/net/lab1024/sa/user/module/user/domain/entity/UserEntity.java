package net.lab1024.sa.user.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.base.common.enumeration.GenderEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("t_user")
public class UserEntity {

    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     * @see GenderEnum
     */
    private Integer gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 状态 0禁用 1启用
     */
    private Integer status;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;
}
