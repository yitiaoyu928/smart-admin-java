# SmartAdmin API (Java 17 + Spring Boot 3)

<p align="center">
    <a href="https://1024lab.net">
        <img src="https://img.shields.io/badge/SmartAdmin-3.0.0-blue" alt="SmartAdmin Version">
    </a>
    <a href="https://www.oracle.com/java/technologies/javase/jdk17-redirect.html">
        <img src="https://img.shields.io/badge/JDK-17-green" alt="JDK Version">
    </a>
    <a href="https://spring.io/projects/spring-boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-3.5.4-green" alt="Spring Boot Version">
    </a>
    <a href="https://opensource.org/licenses/MIT">
        <img src="https://img.shields.io/badge/License-MIT-yellow" alt="License">
    </a>
</p>

## 📖 项目简介

SmartAdmin 是一款基于 Java 17 和 Spring Boot 3.5.4 的企业级后台管理系统 API。采用模块化设计，提供完善的基础功能模块，包括用户管理、角色权限、数据字典、系统配置等。

## ✨ 特性

- **技术栈先进**：采用 Java 17、Spring Boot 3、MyBatis-Plus 等主流技术
- **权限管理完善**：基于 Sa-Token 的完整权限体系，支持菜单权限、数据权限
- **接口文档规范**：集成 Knife4j，提供美观易用的 API 文档
- **代码生成器**：支持从数据库表自动生成前后端代码
- **性能优化**：多级缓存设计，支持本地缓存 + Redis 分布式缓存
- **日志审计**：完整的操作日志记录，支持日志查询和分析
- **定时任务**：内置 SmartJob 定时任务管理模块
- **多环境支持**：支持 dev/test/pre/prod 多环境配置

## 🛠 技术架构

```
├── Spring Boot 3.5.4          # 应用框架
├── Spring Security            # 安全框架
├── MyBatis-Plus 3.5.12        # ORM 框架
├── Sa-Token 1.44.0            # 权限认证
├── Knife4j 4.6.0              # API 文档
├── Redis + Redisson           # 缓存与分布式锁
├── Druid 1.2.25               # 数据库连接池
├── Log4j2                     # 日志框架
├── Hutool 5.8.39              # 工具包
└── AWS S3 SDK                 # 云存储
```

## 📦 模块结构

```
smart-admin-api/
├── sa-parent/                 # 父POM，统一管理依赖
├── sa-base/                   # 基础模块
│   ├── common/                # 公共组件（注解、异常、工具类等）
│   ├── config/                # Spring 配置类
│   └── module/                # 基础功能模块
│       ├── code-generator/    # 代码生成器
│       ├── file/              # 文件管理
│       ├── heartbeat/         # 心跳检测
│       ├── loginlog/          # 登录日志
│       ├── operate/           # 操作日志
│       ├── reload/            # 热加载
│       └── smartjob/          # 定时任务
└── sa-admin/                  # 管理后台应用模块
    ├── config/                # 应用配置
    ├── module/
    │   ├── business/          # 业务模块
    │   │   ├── category/      # 分类管理
    │   │   ├── goods/         # 商品管理
    │   │   └── oa/            # OA办公模块
    │   └── system/            # 系统模块
    │       ├── department/    # 部门管理
    │       ├── employee/      # 员工管理
    │       ├── login/         # 登录认证
    │       ├── menu/          # 菜单管理
    │       ├── message/       # 消息管理
    │       ├── position/      # 岗位管理
    │       └── role/          # 角色管理
    └── resources/             # 配置文件
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 1. 导入数据库

创建数据库 `smart_admin_v3`，导入 SQL 脚本（位于数据库初始化文件）。

### 2. 修改配置

编辑 `sa-base/src/main/resources/dev/sa-base.yaml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/smart_admin_v3?...
    username: your_username
    password: your_password
  data:
    redis:
      host: 127.0.0.1
      port: 6379
      password: your_redis_password
```

### 3. 编译运行

```bash
# 开发环境启动
mvn spring-boot:run -P dev

# 或打包后运行
mvn clean package -P dev
java -jar sa-admin/target/sa-admin-dev-3.0.0.jar
```

### 4. 访问应用

| 服务 | 地址 |
|------|------|
| API 文档 | http://localhost:1024/doc.html |
| 健康检查 | http://localhost:1024/actuator/health |

默认账号：`superadmin`
默认密码：`1024lab`

## 📁 项目结构

```
src/main/
├── java/net/lab1024/sa/
│   ├── base/                    # sa-base 模块
│   │   ├── common/              # 公共组件
│   │   │   ├── annoation/       # 自定义注解
│   │   │   ├── controller/      # 基础控制器
│   │   │   ├── domain/          # 领域对象
│   │   │   ├── enumeration/     # 枚举
│   │   │   ├── exception/       # 异常处理
│   │   │   ├── json/            # JSON配置
│   │   │   ├── swagger/         # Swagger配置
│   │   │   └── util/            # 工具类
│   │   ├── config/              # Spring配置类
│   │   └── module/              # 功能模块
│   └── admin/                   # sa-admin 模块
│       ├── config/              # 应用配置
│       ├── constant/            # 常量定义
│       ├── interceptor/         # 拦截器
│       ├── module/              # 业务模块
│       └── util/                # 工具类
└── resources/
    ├── dev/test/pre prod/       # 多环境配置
    └── mapper/                  # MyBatis XML
```

## 🔧 配置说明

### Maven Profiles

| Profile | 说明 |
|---------|------|
| dev | 开发环境（默认） |
| test | 测试环境 |
| pre | 预发布环境 |
| prod | 生产环境 |

### 核心配置

```yaml
# 应用配置
server:
  port: 1024

# 数据库配置
spring:
  datasource:
    url: jdbc:p6spy:mysql://127.0.0.1:3306/smart_admin_v3?...

# Redis配置
  data:
    redis:
      database: 1
      host: 127.0.0.1
      port: 6379

# Sa-Token配置
sa-token:
  token-name: Authorization
  timeout: 2592000
  is-concurrent: false
```

## 📚 API 文档

项目集成了 Knife4j 提供 API 文档支持：

- **访问地址**: http://localhost:1024/doc.html
- **认证方式**: Bearer Token
- **接口分组**: 按模块分组显示

## 🛡 权限注解

| 注解 | 说明 |
|------|------|
| `@SaCheckLogin` | 检查用户是否已登录 |
| `@SaCheckPermission(value = "xxx")` | 检查用户权限 |
| `@OperateLog` | 记录操作日志 |
| `@RepeatSubmit` | 防止重复提交 |

## 📊 代码生成器

项目内置代码生成器，支持从数据库表自动生成：

- 实体类 (Entity)
- 表单对象 (Form/Query/Update)
- 视图对象 (VO)
- Mapper 接口与 XML
- Service 接口与实现
- Controller 控制器
- 前端 Vue 组件

## 🤝 贡献指南

1. Fork 本项目
2. 创建分支 (`git checkout -b feature/AmazingFeature`)
3. 提交改动 (`git commit -m 'Add some AmazingFeature'`)
4. 推送分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 📄 许可证

本项目基于 [MIT](https://opensource.org/licenses/MIT) 协议开源。

## 📞 联系我们

- **官网**: https://1024lab.net
- **邮箱**: lab1024@163.com
- **微信**: zhuoda1024

---

<p align="center">
    Made with ❤️ by 1024创新实验室
</p>
