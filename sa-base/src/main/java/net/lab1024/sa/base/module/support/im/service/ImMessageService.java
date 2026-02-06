package net.lab1024.sa.base.module.support.im.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.module.support.im.dao.ImMessageDao;
import net.lab1024.sa.base.module.support.im.domain.entity.ImMessageEntity;
import net.lab1024.sa.base.module.support.im.domain.form.ImMessageQueryForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImMessageRevokeForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImMessageSendForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImMessageVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * IM消息 Service
 *
 * @Author 1024创新实验室
 * @Date 2026-02-06
 */
@Service
@Slf4j
public class ImMessageService {

    @Resource
    private ImMessageDao imMessageDao;

    /**
     * 发送消息
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<ImMessageVO> send(String fromId, ImMessageSendForm sendForm) {
        ImMessageEntity entity = new ImMessageEntity();
        entity.setUId(UUID.randomUUID().toString().replace("-", ""));
        entity.setFromId(fromId);
        entity.setReceiveId(sendForm.getReceiveId());
        entity.setType(sendForm.getType());
        entity.setContent(sendForm.getContent());
        entity.setDeletedFlag(0);
        entity.setReadFlag(0);
        entity.setRevokeFlag(0);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());

        imMessageDao.insert(entity);

        ImMessageVO vo = SmartBeanUtil.copy(entity, ImMessageVO.class);
        vo.setReadFlag(false);
        vo.setRevokeFlag(false);

        return ResponseDTO.ok(vo);
    }

    /**
     * 撤回消息
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> revoke(String uId, String userId) {
        ImMessageEntity entity = imMessageDao.selectById(uId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("消息不存在");
        }

        if (!entity.getFromId().equals(userId)) {
            return ResponseDTO.userErrorParam("只能撤回自己发送的消息");
        }

        if (entity.getRevokeFlag() == 1) {
            return ResponseDTO.userErrorParam("消息已撤回");
        }

        entity.setRevokeFlag(1);
        entity.setUpdateTime(LocalDateTime.now());
        imMessageDao.updateById(entity);

        return ResponseDTO.ok();
    }

    /**
     * 删除消息
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(String uId, String userId) {
        ImMessageEntity entity = imMessageDao.selectById(uId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("消息不存在");
        }

        if (!entity.getFromId().equals(userId) && !entity.getReceiveId().equals(userId)) {
            return ResponseDTO.userErrorParam("只能删除自己发送或接收的消息");
        }

        entity.setDeletedFlag(1);
        entity.setUpdateTime(LocalDateTime.now());
        imMessageDao.updateById(entity);

        return ResponseDTO.ok();
    }

    /**
     * 分页查询消息
     */
    public ResponseDTO<PageResult<ImMessageVO>> query(ImMessageQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ImMessageVO> list = imMessageDao.query(page, queryForm);
        PageResult<ImMessageVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 查询对话消息列表
     */
    public ResponseDTO<PageResult<ImMessageVO>> queryChatMessages(String userId, String otherUserId, Integer pageNum, Integer pageSize) {
        Page<?> page = new Page<>(pageNum, pageSize);
        List<ImMessageVO> list = imMessageDao.queryChatMessages(page, userId, otherUserId);
        PageResult<ImMessageVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 标记消息为已读
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> markAsRead(String userId, String otherUserId) {
        imMessageDao.markAsRead(userId, otherUserId);
        return ResponseDTO.ok();
    }

    /**
     * 获取未读消息数
     */
    public ResponseDTO<Long> getUnreadCount(String userId) {
        Long count = imMessageDao.getUnreadCount(userId);
        return ResponseDTO.ok(count);
    }
}