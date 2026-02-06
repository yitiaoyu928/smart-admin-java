package net.lab1024.sa.base.module.support.im.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.base.module.support.im.domain.entity.ImMessageEntity;
import net.lab1024.sa.base.module.support.im.domain.form.ImMessageQueryForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImMessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * IM消息 Dao
 *
 * @Author 1024创新实验室
 * @Date 2026-02-06
 */
@Mapper
public interface ImMessageDao extends BaseMapper<ImMessageEntity> {

    /**
     * 分页查询消息
     */
    List<ImMessageVO> query(Page<?> page, @Param("query") ImMessageQueryForm queryForm);

    /**
     * 查询对话消息列表
     */
    List<ImMessageVO> queryChatMessages(@Param("page") Page<?> page,
                                         @Param("userId") String userId,
                                         @Param("otherUserId") String otherUserId);

    /**
     * 标记消息为已读
     */
    Integer markAsRead(@Param("userId") String userId, @Param("otherUserId") String otherUserId);

    /**
     * 获取未读消息数
     */
    Long getUnreadCount(@Param("userId") String userId);
}