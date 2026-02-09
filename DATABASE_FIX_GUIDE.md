# 数据库连接问题诊断与修复指南

## 📋 问题描述

```
java.sql.SQLNonTransientConnectionException: Could not create connection to database server.
Attempted reconnect 3 times. Giving up.
```

**错误信息分析:**
- `SQLNonTransientConnectionException`: 非瞬态连接异常，表示重试后仍无法连接
- `state 08001`: SQL 状态码，表示无法建立连接
- 数据库 URL: `jdbc:p6spy:mysql://62.234.184.96:3928/smart`

## 🔍 问题原因

### 主要原因：
1. **远程数据库服务器不可达** - `62.234.184.96:3928` 服务器可能已关闭或网络不通
2. **连接超时配置不足** - 默认连接超时时间过短
3. **连接池重试次数限制** - Druid 连接池只重试了 3 次

### 验证方法：

#### 方法 1: 测试远程数据库连接
```bash
# 使用 telnet 测试端口连通性
telnet 62.234.184.96 3928

# 或使用 PowerShell
Test-NetConnection -ComputerName 62.234.184.96 -Port 3928
```

#### 方法 2: 使用 MySQL 客户端测试
```bash
mysql -h 62.234.184.96 -P 3928 -u smart -p
# 密码: anzieHQ5GsM6JmGF
```

## ✅ 已应用的修复

### 1. 优化数据库连接配置

已修改 `sa-base/src/main/resources/dev/sa-base.yaml`：

```yaml
datasource:
  # URL 中添加连接超时和 Socket 超时参数
  url: jdbc:p6spy:mysql://62.234.184.96:3928/smart?...&connectTimeout=30000&socketTimeout=30000

  # 调整连接池配置
  initial-size: 1          # 减少初始连接数，避免启动时大量连接失败
  min-idle: 1              # 减少最小空闲连接
  max-wait: 300000         # 增加最大等待时间（从 60s 增加到 300s）

  # 添加连接验证配置
  validation-query: SELECT 1
  test-while-idle: true    # 空闲时验证连接有效性
  test-on-borrow: false    # 借用时不验证（避免性能问题）
  test-on-return: false    # 归还时不验证
```

### 2. 配置说明

| 参数 | 原值 | 新值 | 说明 |
|------|------|------|------|
| `connectTimeout` | 未设置 | 30000ms | 连接超时时间 |
| `socketTimeout` | 未设置 | 30000ms | Socket 读取超时时间 |
| `initial-size` | 2 | 1 | 初始连接数 |
| `min-idle` | 2 | 1 | 最小空闲连接数 |
| `max-wait` | 60000ms | 300000ms | 最大等待时间 |
| `validation-query` | 未设置 | SELECT 1 | 连接验证 SQL |
| `test-while-idle` | 未设置 | true | 空闲时验证连接 |

## 🛠 修复方案

### 方案 1: 等待远程数据库恢复（临时方案）

如果远程数据库服务器只是暂时不可用，可以等待服务器恢复后重启应用：

```bash
# 停止应用
# Ctrl+C

# 重新启动应用
cd sa-admin
mvn spring-boot:run -P dev
```

### 方案 2: 使用本地数据库（推荐）

#### 步骤 1: 安装本地 MySQL

```bash
# Windows: 下载并安装 MySQL 8.0+
# 下载地址: https://dev.mysql.com/downloads/mysql/
```

#### 步骤 2: 创建数据库

```sql
CREATE DATABASE IF NOT EXISTS smart CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

#### 步骤 3: 导入数据库脚本

```bash
# 导入主数据库
mysql -u root -p smart < mysql/smart_admin_v3.sql

# 导入项目表
mysql -u root -p smart < mysql/t_project.sql
```

#### 步骤 4: 修改配置文件

有两种方式：

**方式 A: 直接修改现有配置文件**
编辑 `sa-base/src/main/resources/dev/sa-base.yaml`：

```yaml
datasource:
  url: jdbc:p6spy:mysql://127.0.0.1:3306/smart?...
  username: root
  password: your_password
```

**方式 B: 使用提供的本地配置文件**
```bash
# 备份原配置
cp sa-base/src/main/resources/dev/sa-base.yaml sa-base/src/main/resources/dev/sa-base.yaml.backup

# 使用本地配置
cp sa-base/src/main/resources/dev/sa-base.yaml.local sa-base/src/main/resources/dev/sa-base.yaml

# 编辑本地配置，修改数据库密码
# 将 your_local_password 改为实际的 MySQL 密码
```

#### 步骤 5: 启动应用

```bash
cd sa-admin
mvn spring-boot:run -P dev
```

### 方案 3: 使用 Docker 启动本地 MySQL（可选）

如果使用 Docker，可以快速启动本地 MySQL：

```bash
# 启动 MySQL 容器
docker run -d \
  --name smart-mysql \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=root123456 \
  -e MYSQL_DATABASE=smart \
  mysql:8.0 \
  --character-set-server=utf8mb4 \
  --collation-server=utf8mb4_general_ci

# 等待 MySQL 启动（约 10-20 秒）

# 导入数据库脚本
docker exec -i smart-mysql mysql -uroot -proot123456 smart < mysql/smart_admin_v3.sql
docker exec -i smart-mysql mysql -uroot -proot123456 smart < mysql/t_project.sql

# 修改配置文件使用本地数据库
# url: jdbc:p6spy:mysql://127.0.0.1:3306/smart?...
# username: root
# password: root123456
```

## 📝 验证修复

启动应用后，检查日志：

```bash
# 应该看到类似以下成功日志
[INFO] DruidDataSource - {dataSource-1} inited
[INFO] HikariPool-1 - Start completed.
[INFO] Started AdminApplication in X.XXX seconds
```

访问健康检查接口：
```bash
curl http://localhost:1024/actuator/health
```

应该返回：
```json
{
  "status": "UP"
}
```

## 🚨 其他可能的问题

### 问题 1: Redis 连接失败

如果 Redis 也使用远程服务器 `62.234.184.96:6928`，同样会失败。

**解决方案：**
- 安装本地 Redis
- 或修改配置为本地 Redis 地址

```yaml
data:
  redis:
    host: 127.0.0.1
    port: 6379
    password:
```

### 问题 2: RabbitMQ 连接失败

同样，RabbitMQ 也连接到远程服务器。

**解决方案：**
- 安装本地 RabbitMQ
- 或暂时禁用 RabbitMQ 功能（修改配置注释掉相关配置）

```yaml
# rabbitmq:
#   host: 62.234.184.96
#   ...
```

## 📞 联系方式

如果以上方案都无法解决问题，建议：

1. 联系远程数据库服务器管理员，确认服务器状态
2. 检查网络防火墙设置
3. 确认数据库用户权限是否正常

---

**最后更新:** 2026-02-09