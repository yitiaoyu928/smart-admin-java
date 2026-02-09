package net.lab1024.sa.base.module.support.im.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.module.support.im.dao.ImGroupDao;
import net.lab1024.sa.base.module.support.im.dao.ImGroupNoticeDao;
import net.lab1024.sa.base.module.support.im.dao.ImGroupUserDao;
import net.lab1024.sa.base.module.support.im.domain.entity.ImGroupNoticeEntity;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupNoticeAddForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupNoticeQueryForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupNoticeUpdateForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImGroupNoticeVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * IM群组公告 Service
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Service
@Slf4j
public class ImGroupNoticeService {

    @Resource
    private ImGroupNoticeDao imGroupNoticeDao;

    @Resource
    private ImGroupDao imGroupDao;

    @Resource
    private ImGroupUserDao imGroupUserDao;

    /**
     * 新增群组公告
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<ImGroupNoticeVO> add(String userId, ImGroupNoticeAddForm addForm) {
        // 检查群组是否存在
        if (imGroupDao.selectById(addForm.getGroupId()) == null) {
            return ResponseDTO.userErrorParam("群组不存在");
        }

        // 检查用户是否在群组中
        if (imGroupUserDao.selectByUserIdAndGroupId(userId, addForm.getGroupId()) == null) {
            return ResponseDTO.userErrorParam("您不在该群组中");
        }

        ImGroupNoticeEntity entity = new ImGroupNoticeEntity();
        entity.setId(UUID.randomUUID().toString().replace("-", ""));
        entity.setGroupId(addForm.getGroupId());
        entity.setTitle(addForm.getTitle());
        entity.setContent(addForm.getContent());
        entity.setCreateBy(userId);
        entity.setDeletedFlag(false);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());

        imGroupNoticeDao.insert(entity);

        ImGroupNoticeVO vo = SmartBeanUtil.copy(entity, ImGroupNoticeVO.class);

        return ResponseDTO.ok(vo);
    }

    /**
     * 更新群组公告
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(String userId, ImGroupNoticeUpdateForm updateForm) {
        ImGroupNoticeEntity entity = imGroupNoticeDao.selectById(updateForm.getId());
        if (entity == null) {
            return ResponseDTO.userErrorParam("公告不存在");
        }

        // 只有公告创建者可以修改
        if (!entity.getCreateBy().equals(userId)) {
            return ResponseDTO.userErrorParam("只有公告创建者可以修改");
        }

        SmartBeanUtil.copyProperties(updateForm, entity);
        entity.setUpdateTime(LocalDateTime.now());

        imGroupNoticeDao.updateById(entity);

        return ResponseDTO.ok();
    }

    /**
     * 删除群组公告
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(String id, String userId) {
        ImGroupNoticeEntity entity = imGroupNoticeDao.selectById(id);
        if (entity == null) {
            return ResponseDTO.userErrorParam("公告不存在");
        }

        // 只有公告创建者可以删除
        if (!entity.getCreateBy().equals(userId)) {
            return ResponseDTO.userErrorParam("只有公告创建者可以删除");
        }

        entity.setDeletedFlag(true);
        entity.setUpdateTime(LocalDateTime.now());
        imGroupNoticeDao.updateById(entity);

        return ResponseDTO.ok();
    }

    /**
     * 分页查询群组公告
     */
    public ResponseDTO<PageResult<ImGroupNoticeVO>> query(ImGroupNoticeQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ImGroupNoticeVO> list = imGroupNoticeDao.query(page, queryForm);
        PageResult<ImGroupNoticeVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 获取群组最新公告
     */
    public ResponseDTO<ImGroupNoticeVO> getLatestNotice(Long groupId) {
        ImGroupNoticeVO vo = imGroupNoticeDao.getLatestNotice(groupId);
        return ResponseDTO.ok(vo);
    }
}