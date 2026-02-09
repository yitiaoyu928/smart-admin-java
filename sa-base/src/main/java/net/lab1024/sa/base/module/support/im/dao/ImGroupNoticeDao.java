package net.lab1024.sa.base.module.support.im.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.base.module.support.im.domain.entity.ImGroupNoticeEntity;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupNoticeQueryForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImGroupNoticeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * IM群组公告 Dao
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Mapper
public interface ImGroupNoticeDao extends BaseMapper<ImGroupNoticeEntity> {

    /**
     * 分页查询群组公告
     */
    List<ImGroupNoticeVO> query(Page<?> page, @Param("query") ImGroupNoticeQueryForm queryForm);

    /**
     * 查询群组最新公告
     */
    ImGroupNoticeVO getLatestNotice(@Param("groupId") Long groupId);
}