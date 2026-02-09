package net.lab1024.sa.user.module.login.service;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.user.module.login.domain.RequestUserEntity;
import org.springframework.stereotype.Service;

/**
 * 用户端登录服务
 *
 * @Author 1024创新实验室
 * @Date 2026-02-09
 */
@Service
@Slf4j
public class LoginService {

    /**
     * 获取登录用户信息
     *
     * @param loginId 登录ID
     * @param request 请求对象
     * @return 用户信息
     */
    public RequestUserEntity getLoginUser(String loginId, HttpServletRequest request) {
        // TODO: 根据实际业务需求实现用户信息查询逻辑
        // 这里需要根据 loginId 查询数据库获取用户信息
        // 示例代码：
        // UserEntity userEntity = userDao.selectById(Long.parseLong(loginId));
        // if (userEntity == null) {
        //     return null;
        // }
        //
        // RequestUserEntity requestUserEntity = new RequestUserEntity();
        // BeanUtil.copyProperties(userEntity, requestUserEntity);
        // requestUserEntity.setIp(SmartRequestUtil.getIp(request));
        // requestUserEntity.setUserAgent(request.getHeader("User-Agent"));
        // return requestUserEntity;

        // 暂时返回 null，待实现具体业务逻辑
        return null;
    }

    /**
     * 更新用户最后活跃时间
     * 用户端不需要实现自动下线逻辑
     */
    public void updateLastActiveTime(String loginId) {
        // 用户端不需要实现活跃时间更新逻辑
    }
}