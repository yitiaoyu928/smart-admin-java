# SmartAdmin 项目上下文

## 项目概述

**SmartAdmin** 是一个基于 Java 17 和 Spring Boot 3.5.4 构建的企业级后台管理系统。项目采用模块化设计，分为 `sa-parent`（父POM）、`sa-base`（基础模块）和 `sa-admin`（应用模块）三个子项目。

## 技术栈

| 分类 | 技术/版本 |
|------|-----------|
| 核心框架 | Java 17, Spring Boot 3.5.4 |
| ORM | MyBatis-Plus 3.5.12 |
| 数据库 | MySQL 9.3.0, Druid 1.2.25 (连接池), P6Spy 3.9.1 (SQL监控) |
| 缓存 | Redis + Redisson 3.50.0, Caffeine (本地缓存) |
| 权限认证 | Sa-Token 1.44.0 |
| API文档 | Knife4j 4.6.0 (Swagger 3.0 + SpringDoc OpenAPI 2.8.9) |
| 定时任务 | SmartJob |
| 工具库 | Hutool 5.8.39, Guava 20.0, Apache Commons (Lang3, IO, Collections4, Text, Codec, Compress) |
| 文件处理 | Apache POI 5.4.1, FastExcel 1.2.0, Tika 3.1.0, Jsoup 1.21.1 |
| HTTP客户端 | Apache HttpClient 5.5 |
| JSON处理 | Fastjson 2.0.57 |
| 日志框架 | Log4j2 |
| 模板引擎 | Velocity 2.4.1, FreeMarker 2.3.34 |
| 云存储 | AWS S3 SDK 2.31.78 |
| IP地理定位 | ip2region 2.7.0 |
| 加密工具 | BouncyCastle 1.80 |
| 邮件服务 | Spring Boot Mail |
| 数据库工具 | SmartDB 1.2.0 |
| 反射工具 | Reflections 0.10.2 |
| YAML解析 | SnakeYAML 2.4 |

## 项目结构

```
smart-admin-java/
├── pom.xml                          # 父POM，统一管理依赖和构建配置
├── IFLOW.md                         # 本文件
├── README.md                        # 项目说明文档
├── mysql/                           # 数据库脚本目录
│   ├── smart_admin_v3.sql          # 初始化数据库脚本
│   ├── t_project.sql               # 项目表脚本
│   └── sql-update-log/             # 数据库更新日志
│       ├── v3.15.0.sql
│       ├── v3.18.0.sql
│       ├── v3.21.0.sql
│       ├── v3.23.0.sql
│       ├── v3.25.0.sql
│       └── v3.26.0.sql
├── sa-admin/                        # 管理后台应用模块
│   ├── pom.xml
│   └── src/main/
│       ├── java/net/lab1024/sa/admin/
│       │   ├── AdminApplication.java      # 启动类
│       │   ├── config/                    # MVC、拦截器、日志切面配置
│       │   ├── constant/                  # 常量定义（缓存KEY、Swagger标签等）
│       │   ├── interceptor/               # 拦截器
│       │   ├── module/
│       │   │   ├── business/              # 业务模块
│       │   │   │   ├── category/          # 分类管理
│       │   │   │   ├── goods/             # 商品管理
│       │   │   │   └── oa/                # OA办公模块
│       │   │   │       ├── bank/          # 银行管理
│       │   │   │       ├── enterprise/    # 企业管理
│       │   │   │       ├── invoice/       # 发票管理
│       │   │   │       └── notice/        # 通知管理
│       │   │   └── system/                # 系统模块
│       │   │       ├── datascope/         # 数据权限
│       │   │       ├── department/        # 部门管理
│       │   │       ├── employee/          # 员工管理
│       │   │       ├── login/             # 登录认证
│       │   │       ├── menu/              # 菜单管理
│       │   │       ├── message/           # 消息管理
│       │   │       ├── position/          # 岗位管理
│       │   │       ├── role/              # 角色管理
│       │   │       └── support/           # 系统支持功能
│       │   └── util/                      # 工具类
│       └── resources/
│           ├── dev/test/pre/prod/         # 多环境配置文件
│           └── mapper/                    # MyBatis XML映射文件
│               ├── business/              # 业务模块映射
│               └── system/                # 系统模块映射
└── sa-base/                           # 基础模块（可复用组件）
    ├── pom.xml
    └── src/main/
        ├── java/net/lab1024/sa/base/
        │   ├── common/                    # 公共组件
        │   │   ├── annoation/             # 自定义注解
        │   │   ├── code/                  # 响应码定义
        │   │   ├── constant/              # 常量
        │   │   ├── controller/            # 基础控制器
        │   │   ├── domain/                # 领域对象
        │   │   ├── enumeration/           # 枚举
        │   │   ├── exception/             # 异常处理
        │   │   ├── json/                  # JSON处理配置
        │   │   ├── swagger/               # Swagger配置
        │   │   ├── util/                  # 工具类
        │   │   └── validator/             # 校验器
        │   ├── config/                    # Spring配置类
        │   │   ├── AsyncConfig.java       # 异步配置
        │   │   ├── CacheConfig.java       # 缓存配置
        │   │   ├── CorsFilterConfig.java  # 跨域配置
        │   │   ├── DataSourceConfig.java  # 数据源配置
        │   │   ├── FileConfig.java        # 文件上传配置
        │   │   ├── JsonConfig.java        # JSON配置
        │   │   ├── MybatisPlusConfig.java # MyBatis-Plus配置
        │   │   ├── RedisConfig.java       # Redis配置
        │   │   ├── SwaggerConfig.java     # Swagger配置
        │   │   ├── TokenConfig.java       # Token配置
        │   │   └── ...                    # 更多配置
        │   ├── constant/                  # 常量定义
        │   ├── handler/                   # MyBatis处理器
        │   ├── listener/                  # 应用监听器
        │   └── module/                    # 基础功能模块
        │       └── support/               # 支持模块
        │           ├── apiencrypt/        # API加密
        │           ├── cache/             # 缓存管理
        │           ├── captcha/           # 验证码
        │           ├── changelog/         # 变更日志
        │           ├── codegenerator/     # 代码生成器
        │           ├── config/            # 系统配置
        │           ├── datamasking/       # 数据脱敏
        │           ├── datatracer/        # 数据追踪
        │           ├── dict/              # 数据字典
        │           ├── feedback/          # 用户反馈
        │           ├── file/              # 文件管理
        │           ├── heartbeat/         # 心跳检测
        │           ├── helpdoc/           # 帮助文档
        │           ├── job/               # 定时任务
        │           ├── loginlog/          # 登录日志
        │           ├── mail/              # 邮件服务
        │           ├── message/           # 消息中心
        │           ├── operatelog/        # 操作日志
        │           ├── redis/             # Redis管理
        │           ├── reload/            # 热加载
        │           ├── repeatsubmit/      # 防重复提交
        │           ├── securityprotect/   # 安全防护
        │           ├── serialnumber/      # 序列号生成
        │           └── table/             # 表字段管理
        └── resources/
            ├── dev/test/pre/prod/         # 多环境配置文件 (sa-base.yaml)
            ├── mapper/                    # MyBatis Mapper XML
            ├── ip2region.xdb             # IP地址库
            └── code-generator-template/   # 代码生成器模板
                ├── java/                  # Java代码模板
                ├── js/                    # JavaScript代码模板
                └── ts/                    # TypeScript代码模板
```

## 构建与运行

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+ (推荐 MySQL 9.3.0)
- Redis 6.0+

### Maven Profiles

| Profile | 说明 | 激活方式 |
|---------|------|----------|
| dev | 开发环境（默认） | `-P dev` 或不指定 |
| test | 测试环境 | `-P test` |
| pre | 预发布环境 | `-P pre` |
| prod | 生产环境 | `-P prod` |

### 常用命令

```bash
# 开发环境运行（默认）
mvn spring-boot:run

# 指定环境运行
mvn spring-boot:run -P dev

# 打包
mvn clean package -P dev

# 打包并跳过测试
mvn clean package -P dev -DskipTests

# 运行打包后的应用
java -jar sa-admin-dev-3.0.0.jar

# 运行测试
mvn test

# 查看活跃的 Profile
mvn help:active-profiles
```

### 默认配置

- **端口**: 1024
- **上下文路径**: `/`
- **项目版本**: 3.0.0
- **数据库**: `smart` (MySQL)
- **Redis**: 数据库 1
- **日志目录**: `${localPath:/home}/logs/smart_admin_v3/sa-admin/${profiles.active}`
- **Tomcat 访问日志**: 保留 7 天

## 开发规范

### 代码结构规范

```
模块包结构:
net.lab1024.sa.模块名.功能
  ├── controller      # 控制器层
  ├── service         # 服务层接口
  ├── service/impl    # 服务层实现
  ├── dao/mapper      # 数据访问层
  ├── domain          # 实体类
  ├── domain/form     # 表单对象
  ├── domain/query    # 查询对象
  ├── domain/vo       # 视图对象
  └── constant        # 常量定义
```

### 响应格式

所有接口统一返回 `Result<T>` 格式：

```java
{
  "code": 200,        // 状态码（200=成功）
  "msg": "操作成功",   // 消息
  "data": {}          // 数据
}
```

### 注解使用

- `@SaCheckLogin`: 检查用户是否登录
- `@SaCheckPermission`: 检查用户权限
- `@OperateLog`: 记录操作日志
- `@RepeatSubmit`: 防止重复提交
- `@SmartJob`: 定时任务注解

### Swagger/Knife4j 文档访问

- **文档地址**: `http://localhost:1024/doc.html`
- **接口分组**: 按模块分组显示
- **认证**: 支持 Bearer Token 认证
- **配置**: 可通过 `knife4j.basic` 配置 Basic 认证

### 数据库规范

- 使用 MyBatis-Plus 自动填充功能处理公共字段
- 使用逻辑删除（`is_deleted` 或 `delete_flag`）
- 使用 P6Spy 监控 SQL 执行
- 使用 Druid SQL 监控（可通过 `druid.login.enabled` 开启）

## 配置管理

### 配置文件优先级

1. `sa-admin/src/main/resources/{profile}/application.yaml` (应用配置)
2. `sa-base/src/main/resources/{profile}/sa-base.yaml` (基础配置)
3. JVM 系统参数 `-Dkey=value`
4. 环境变量

### 敏感配置示例

```yaml
# 数据库
spring.datasource.url: jdbc:mysql://...
spring.datasource.username: root
spring.datasource.password: ${DB_PASSWORD:your_password}

# Redis
spring.data.redis.host: ${REDIS_HOST:127.0.0.1}
spring.data.redis.port: 6379
spring.data.redis.password: ${REDIS_PASSWORD:}

# 邮件配置
spring.mail.host: smtp.163.com
spring.mail.port: 465
spring.mail.username: ${MAIL_USERNAME:}
spring.mail.password: ${MAIL_PASSWORD:}

# 云存储（OSS）
file.storage.cloud.region: oss-cn-hangzhou
file.storage.cloud.bucket-name: ${OSS_BUCKET:}
file.storage.cloud.access-key: ${OSS_ACCESS_KEY:}
file.storage.cloud.secret-key: ${OSS_SECRET_KEY:}
```

### 多环境切换

通过 Maven `-P` 参数切换：

```bash
# 开发环境
mvn spring-boot:run -P dev

# 测试环境
mvn spring-boot:run -P test

# 预发布环境
mvn spring-boot:run -P pre

# 生产环境
mvn spring-boot:run -P prod
```

## 关键配置说明

### Sa-Token 配置

```yaml
sa-token:
  token-name: Authorization       # Token名称
  token-prefix: Bearer            # Token前缀
  timeout: 2592000                # 有效期30天
  is-concurrent: false            # 禁止同账号多地登录
  is-share: false                 # 不共享Token
  auto-renew: true                # 自动续签
  token-style: simple-uuid        # Token风格
  is-log: true                    # 输出操作日志
  log-level: debug                # 日志等级
  is-read-cookie: false           # 不从cookie读取token
```

### 文件上传配置

```yaml
file:
  storage:
    mode: local                   # local 或 cloud
    local:
      upload-path: /home/smart_admin_v3/upload/
      url-prefix:
    cloud:
      region: oss-cn-hangzhou
      endpoint: oss-cn-hangzhou.aliyuncs.com
      bucket-name: 1024lab-smart-admin
      url-prefix: https://...
      private-url-expire-seconds: 3600
```

### 缓存配置

```yaml
spring:
  cache:
    type: redis                  # 缓存类型

spring.data.redis:
  database: 1
  host: 127.0.0.1
  port: 6379
  lettuce:
    pool:
      max-active: 5
      min-idle: 1
      max-idle: 3
      max-wait: 30000ms
```

### HTTP 客户端配置

```yaml
http:
  pool:
    max-total: 20
    connect-timeout: 50000       # 连接超时（毫秒）
    read-timeout: 50000          # 读取超时（毫秒）
    write-timeout: 50000         # 写入超时（毫秒）
    keep-alive: 300000           # 保活时间（毫秒）
```

### SmartJob 定时任务配置

```yaml
smart:
  job:
    enabled: true
    init-delay: 10               # 初始化延迟（秒）
    core-pool-size: 2            # 线程池数量
    db-refresh-enabled: true     # 数据库配置检测开关
    db-refresh-interval: 60      # 配置检测间隔（秒）
```

## 数据库管理

### 初始化数据库

首次部署执行：

```bash
mysql -u root -p < mysql/smart_admin_v3.sql
mysql -u root -p < mysql/t_project.sql
```

### 数据库更新

跟随 SmartAdmin 更新，按版本顺序执行 `mysql/sql-update-log/` 目录中的 SQL 脚本：

```bash
mysql -u root -p smart < mysql/sql-update-log/v3.15.0.sql
mysql -u root -p smart < mysql/sql-update-log/v3.18.0.sql
# ... 继续执行更高版本的脚本
```

### 当前版本

最新数据库更新版本：v3.26.0

## 测试

### 运行测试

```bash
# 运行所有测试
mvn test

# 运行测试并跳过测试编译
mvn test -DskipTests=false
```

### 测试配置文件

位于 `sa-admin/src/test/resources/` 和 `sa-base/src/test/resources/`

## 常用工具类

| 类名 | 用途 |
|------|------|
| `Result` | 统一响应包装 |
| `PageUtil` | 分页工具 |
| `LocalDateTimeUtil` | 日期时间处理 |
| `StringUtil` | 字符串处理 |
| `BeanUtil` | Bean拷贝 |
| `SecureUtil` | 加密解密 |
| `Ip2RegionUtil` | IP地理定位 |
| `SmartLogUtil` | 智能日志 |

## 功能模块

### 系统模块

- **部门管理**: 组织架构管理
- **员工管理**: 员工信息维护
- **岗位管理**: 岗位配置
- **角色管理**: 角色权限管理
- **菜单管理**: 菜单资源管理
- **数据权限**: 数据访问范围控制
- **登录认证**: 用户登录和认证
- **消息管理**: 系统消息推送

### 业务模块

- **分类管理**: 业务分类管理
- **商品管理**: 商品信息管理
- **OA模块**:
  - 银行管理
  - 企业管理
  - 发票管理
  - 通知管理

### 支持模块

- **代码生成器**: 自动生成代码
- **数据字典**: 字典数据管理
- **文件管理**: 文件上传下载
- **操作日志**: 操作记录审计
- **登录日志**: 登录记录追踪
- **定时任务**: 定时任务调度
- **数据脱敏**: 敏感数据保护
- **数据追踪**: 数据变更追踪
- **API加密**: 接口数据加密
- **防重复提交**: 防止表单重复提交
- **安全防护**: 安全防护机制
- **序列号生成**: 业务序列号生成
- **邮件服务**: 邮件发送功能
- **帮助文档**: 在线帮助文档
- **用户反馈**: 用户反馈收集
- **心跳检测**: 服务健康检测
- **热加载**: 配置动态更新

## 常见问题

### 1. 数据库连接失败
- 检查 MySQL 服务是否启动
- 确认数据库用户权限
- 检查连接字符串是否正确
- 验证 P6Spy 驱动配置

### 2. Redis 连接失败
- 检查 Redis 服务是否启动
- 确认密码配置正确
- 检查 Redis 连接池配置

### 3. Swagger 文档无法访问
- 检查是否配置了跨域
- 确认 Knife4j 依赖已引入
- 验证 `springdoc.swagger-ui.enabled` 配置

### 4. 文件上传失败
- 检查上传路径是否存在且有写入权限
- 确认文件大小限制配置
- 验证存储模式（local/cloud）配置

### 5. 定时任务不执行
- 检查 `smart.job.enabled` 配置
- 确认数据库配置检测是否开启
- 查看定时任务日志

## 资源链接

- **项目官网**: https://1024lab.net
- **项目文档**: https://smartadmin.vip
- **Sa-Token 文档**: https://sa-token.cc
- **MyBatis-Plus 文档**: https://baomidou.com
- **Knife4j 文档**: https://doc.xiaominfo.com
- **Spring Boot 文档**: https://spring.io/projects/spring-boot
- **SpringDoc 文档**: https://springdoc.org