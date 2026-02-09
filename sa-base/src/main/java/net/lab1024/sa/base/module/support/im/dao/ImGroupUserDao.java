package net.lab1024.sa.base.module.support.im.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.base.module.support.im.domain.entity.ImGroupUserEntity;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupUserQueryForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImGroupUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * IM群组成员 Dao
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Mapper
public interface ImGroupUserDao extends BaseMapper<ImGroupUserEntity> {

    /**
     * 分页查询群组成员
     */
    List<ImGroupUserVO> query(Page<?> page, @Param("query") ImGroupUserQueryForm queryForm);

    /**
     * 查询群组成员列表
     */
    List<ImGroupUserVO> queryGroupMembers(@Param("groupId") Long groupId);

    /**
     * 根据用户ID和群组ID查询成员关系
     */
    ImGroupUserEntity selectByUserIdAndGroupId(@Param("userId") String userId, @Param("groupId") Long groupId);

    /**
     * 获取群组成员数量
     */
    Long getGroupMemberCount(@Param("groupId") Long groupId);
}