package net.lab1024.sa.base.module.support.im.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartEnumUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.module.support.im.constant.ImGroupStatusEnum;
import net.lab1024.sa.base.module.support.im.constant.ImGroupTypeEnum;
import net.lab1024.sa.base.module.support.im.dao.ImGroupDao;
import net.lab1024.sa.base.module.support.im.domain.entity.ImGroupEntity;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupAddForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupQueryForm;
import net.lab1024.sa.base.module.support.im.domain.form.ImGroupUpdateForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImGroupVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * IM群组 Service
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Service
@Slf4j
public class ImGroupService {

    @Resource
    private ImGroupDao imGroupDao;

    /**
     * 新增群组
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<ImGroupVO> add(String userId, ImGroupAddForm addForm) {
        ImGroupEntity entity = SmartBeanUtil.copy(addForm, ImGroupEntity.class);
        entity.setOwnerId(userId);
        entity.setStatusFlag(ImGroupStatusEnum.NORMAL.getValue());
        entity.setDeletedFlag(false);
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());

        imGroupDao.insert(entity);

        ImGroupVO vo = SmartBeanUtil.copy(entity, ImGroupVO.class);
        vo.setTypeDesc(SmartEnumUtil.getEnumByValue(vo.getType(), ImGroupTypeEnum.class).getDesc());
        vo.setStatusFlagDesc(SmartEnumUtil.getEnumByValue(vo.getStatusFlag(), ImGroupStatusEnum.class).getDesc());

        return ResponseDTO.ok(vo);
    }

    /**
     * 更新群组
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(String userId, ImGroupUpdateForm updateForm) {
        ImGroupEntity entity = imGroupDao.selectById(updateForm.getId());
        if (entity == null) {
            return ResponseDTO.userErrorParam("群组不存在");
        }

        if (!entity.getOwnerId().equals(userId)) {
            return ResponseDTO.userErrorParam("只有群主可以修改群组信息");
        }

        SmartBeanUtil.copyProperties(updateForm, entity);
        entity.setUpdateTime(LocalDateTime.now());

        imGroupDao.updateById(entity);

        return ResponseDTO.ok();
    }

    /**
     * 删除群组
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id, String userId) {
        ImGroupEntity entity = imGroupDao.selectById(id);
        if (entity == null) {
            return ResponseDTO.userErrorParam("群组不存在");
        }

        if (!entity.getOwnerId().equals(userId)) {
            return ResponseDTO.userErrorParam("只有群主可以删除群组");
        }

        entity.setDeletedFlag(true);
        entity.setUpdateTime(LocalDateTime.now());
        imGroupDao.updateById(entity);

        return ResponseDTO.ok();
    }

    /**
     * 分页查询群组
     */
    public ResponseDTO<PageResult<ImGroupVO>> query(ImGroupQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ImGroupVO> list = imGroupDao.query(page, queryForm);
        PageResult<ImGroupVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 查询用户所在的群组列表
     */
    public ResponseDTO<PageResult<ImGroupVO>> queryUserGroups(String userId, Integer pageNum, Integer pageSize) {
        Page<?> page = new Page<>(pageNum, pageSize);
        List<ImGroupVO> list = imGroupDao.queryUserGroups(page, userId);
        PageResult<ImGroupVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 获取群组详情
     */
    public ResponseDTO<ImGroupVO> getDetail(Long id) {
        ImGroupEntity entity = imGroupDao.selectById(id);
        if (entity == null) {
            return ResponseDTO.userErrorParam("群组不存在");
        }

        ImGroupVO vo = SmartBeanUtil.copy(entity, ImGroupVO.class);
        vo.setTypeDesc(SmartEnumUtil.getEnumByValue(vo.getType(), ImGroupTypeEnum.class).getDesc());
        vo.setStatusFlagDesc(SmartEnumUtil.getEnumByValue(vo.getStatusFlag(), ImGroupStatusEnum.class).getDesc());

        return ResponseDTO.ok(vo);
    }
}