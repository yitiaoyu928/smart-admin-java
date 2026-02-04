package net.lab1024.sa.user.config;

import net.lab1024.sa.base.common.domain.DataScopePlugin;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 用户端 MyBatis 插件（空实现，数据权限功能通常仅用于管理端）
 *
 * @Author 1024创新实验室
 * @Date 2026-02-04
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Intercepts({@Signature(type = org.apache.ibatis.executor.Executor.class, method = "query", args = {org.apache.ibatis.mapping.MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Component
public class MyBatisPlugin extends DataScopePlugin {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 用户端暂不需要数据权限拦截，直接执行原方法
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 用户端暂无配置项
    }
}