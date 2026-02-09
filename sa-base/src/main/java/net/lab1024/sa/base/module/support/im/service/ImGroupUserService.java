package net.lab1024.sa.base.module.support.im.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.module.support.im.dao.ImGroupDao;
import net.lab1024.sa.base.module.support.im.dao.ImGroupUserDao;
import net.lab1024.sa.base.module.support.im.domain.entity.ImGroupEntity;
import net.lab1024.sa.base.module.support.im.domain.entity.ImGroupUserEntity;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupUserAddForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupUserQueryForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupUserUpdateForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImGroupUserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * IM群组成员 Service
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Service
@Slf4j
public class ImGroupUserService {

    @Resource
    private ImGroupUserDao imGroupUserDao;

    @Resource
    private ImGroupDao imGroupDao;

    /**
     * 添加群组成员（批量）
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> addMembers(String userId, ImGroupUserAddForm addForm) {
        // 检查群组是否存在
        ImGroupEntity group = imGroupDao.selectById(addForm.getGroupId());
        if (group == null) {
            return ResponseDTO.userErrorParam("群组不存在");
        }

        // 检查用户是否是群主或管理员
        ImGroupUserEntity currentUser = imGroupUserDao.selectByUserIdAndGroupId(userId, addForm.getGroupId());
        if (currentUser == null && !group.getOwnerId().equals(userId)) {
            return ResponseDTO.userErrorParam("您不在该群组中");
        }

        // 检查是否超过群组最大成员数
        Long currentCount = imGroupUserDao.getGroupMemberCount(addForm.getGroupId());
        if (currentCount + addForm.getUserIds().size() > group.getMemberCount()) {
            return ResponseDTO.userErrorParam("群组成员已达到上限");
        }

        LocalDateTime now = LocalDateTime.now();
        for (String memberId : addForm.getUserIds()) {
            // 检查用户是否已在群组中
            ImGroupUserEntity existMember = imGroupUserDao.selectByUserIdAndGroupId(memberId, addForm.getGroupId());
            if (existMember != null) {
                continue;
            }

            ImGroupUserEntity entity = new ImGroupUserEntity();
            entity.setUserId(memberId);
            entity.setGroupId(addForm.getGroupId());
            entity.setManagerFlag(addForm.getManagerFlag() != null ? addForm.getManagerFlag() : false);
            entity.setJoinTime(now);
            entity.setDeletedFlag(false);
            entity.setCreateTime(now);
            entity.setUpdateTime(now);

            imGroupUserDao.insert(entity);
        }

        return ResponseDTO.ok();
    }

    /**
     * 更新群组成员信息
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(String userId, ImGroupUserUpdateForm updateForm) {
        // 检查群组是否存在
        ImGroupEntity group = imGroupDao.selectById(updateForm.getGroupId());
        if (group == null) {
            return ResponseDTO.userErrorParam("群组不存在");
        }

        // 只有群主可以设置管理员
        if (!group.getOwnerId().equals(userId)) {
            return ResponseDTO.userErrorParam("只有群主可以设置管理员");
        }

        ImGroupUserEntity entity = imGroupUserDao.selectByUserIdAndGroupId(updateForm.getUserId(), updateForm.getGroupId());
        if (entity == null) {
            return ResponseDTO.userErrorParam("成员不存在");
        }

        // 不能修改群主身份
        if (group.getOwnerId().equals(updateForm.getUserId())) {
            return ResponseDTO.userErrorParam("不能修改群主身份");
        }

        entity.setManagerFlag(updateForm.getManagerFlag());
        entity.setUpdateTime(LocalDateTime.now());

        imGroupUserDao.updateById(entity);

        return ResponseDTO.ok();
    }

    /**
     * 移除群组成员
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> removeMember(String userId, Long groupId, String memberId) {
        // 检查群组是否存在
        ImGroupEntity group = imGroupDao.selectById(groupId);
        if (group == null) {
            return ResponseDTO.userErrorParam("群组不存在");
        }

        // 只有群主或自己可以移除
        if (!group.getOwnerId().equals(userId) && !userId.equals(memberId)) {
            return ResponseDTO.userErrorParam("只有群主或本人可以移除成员");
        }

        // 不能移除群主
        if (group.getOwnerId().equals(memberId)) {
            return ResponseDTO.userErrorParam("不能移除群主");
        }

        ImGroupUserEntity entity = imGroupUserDao.selectByUserIdAndGroupId(memberId, groupId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("成员不存在");
        }

        entity.setDeletedFlag(true);
        entity.setUpdateTime(LocalDateTime.now());
        imGroupUserDao.updateById(entity);

        return ResponseDTO.ok();
    }

    /**
     * 退出群组
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> quitGroup(String userId, Long groupId) {
        // 检查群组是否存在
        ImGroupEntity group = imGroupDao.selectById(groupId);
        if (group == null) {
            return ResponseDTO.userErrorParam("群组不存在");
        }

        // 群主不能退出群组
        if (group.getOwnerId().equals(userId)) {
            return ResponseDTO.userErrorParam("群主不能退出群组，请先转让群主或解散群组");
        }

        ImGroupUserEntity entity = imGroupUserDao.selectByUserIdAndGroupId(userId, groupId);
        if (entity == null) {
            return ResponseDTO.userErrorParam("您不在该群组中");
        }

        entity.setDeletedFlag(true);
        entity.setUpdateTime(LocalDateTime.now());
        imGroupUserDao.updateById(entity);

        return ResponseDTO.ok();
    }

    /**
     * 分页查询群组成员
     */
    public ResponseDTO<PageResult<ImGroupUserVO>> query(ImGroupUserQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ImGroupUserVO> list = imGroupUserDao.query(page, queryForm);
        PageResult<ImGroupUserVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 查询群组成员列表
     */
    public ResponseDTO<List<ImGroupUserVO>> queryGroupMembers(Long groupId) {
        List<ImGroupUserVO> list = imGroupUserDao.queryGroupMembers(groupId);
        return ResponseDTO.ok(list);
    }

    /**
     * 获取群组成员数量
     */
    public ResponseDTO<Long> getGroupMemberCount(Long groupId) {
        Long count = imGroupUserDao.getGroupMemberCount(groupId);
        return ResponseDTO.ok(count);
    }
}