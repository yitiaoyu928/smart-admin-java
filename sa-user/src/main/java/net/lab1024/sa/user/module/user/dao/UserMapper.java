package net.lab1024.sa.user.module.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.user.module.user.domain.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户 Mapper
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 根据邮箱查询用户
     */
    UserEntity selectByEmail(@Param("email") String email);

    /**
     * 根据手机号查询用户
     */
    UserEntity selectByPhone(@Param("phone") String phone);

    /**
     * 根据昵称查询用户
     */
    UserEntity selectByNickname(@Param("nickname") String nickname);
    
    /**
     * 更新积分
     */
    void updatePoints(@Param("userId") Long userId, @Param("points") Integer points);
}
