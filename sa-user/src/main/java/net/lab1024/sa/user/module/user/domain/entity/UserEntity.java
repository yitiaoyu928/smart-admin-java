package net.lab1024.sa.user.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("t_employee")
public class UserEntity {

    @TableId(value = "employee_id", type = IdType.AUTO)
    private Long userId;

    private String employeeUid;

    private String loginName;

    private String loginPwd;

    @TableField("actual_name")
    private String nickname;

    private String avatar;

    private Integer gender;

    private String email;

    private String phone;

    private Long departmentId;

    private Long positionId;

    private Boolean administratorFlag;

    private Boolean disabledFlag;

    private Boolean deletedFlag;

    private String remark;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
