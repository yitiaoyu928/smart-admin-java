# 项目密钥 API 文档

## 接口访问地址

- **Swagger UI**: http://localhost:1024/doc.html
- **分组**: 业务-项目密钥

## 接口列表

### 1. 查询项目密钥列表

**接口地址**: `POST /key/query`

**接口描述**: 查询项目密钥列表（分页）

**请求参数**:

```json
{
  "pageNum": 1,
  "pageSize": 10,
  "keyword": "搜索关键词",
  "projectId": 10000,
  "cycleType": "Month",
  "disabledFlag": false,
  "usingFlag": false,
  "usingTimeBegin": "2026-01-01 00:00:00",
  "usingTimeEnd": "2026-12-31 23:59:59",
  "expireTimeBegin": "2026-01-01 00:00:00",
  "expireTimeEnd": "2026-12-31 23:59:59",
  "deletedFlag": false
}
```

**参数说明**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| pageNum | Integer | 是 | 页码 |
| pageSize | Integer | 是 | 每页数量 |
| keyword | String | 否 | 搜索关键词（匹配密钥或项目名称） |
| projectId | Long | 否 | 项目ID |
| cycleType | String | 否 | 密钥周期类型（DAY、Month、quarter、Year） |
| disabledFlag | Boolean | 否 | 是否禁用 |
| usingFlag | Boolean | 否 | 是否使用 |
| usingTimeBegin | LocalDateTime | 否 | 使用时间开始 |
| usingTimeEnd | LocalDateTime | 否 | 使用时间结束 |
| expireTimeBegin | LocalDateTime | 否 | 过期时间开始 |
| expireTimeEnd | LocalDateTime | 否 | 过期时间结束 |
| deletedFlag | Boolean | 否 | 是否删除（默认false） |

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "total": 100,
    "pages": 10,
    "rows": [
      {
        "id": 1,
        "projectId": 10000,
        "projectName": "智慧办公APP",
        "key": "ABC123XYZ789",
        "cycleType": "Month",
        "disabledFlag": false,
        "usingFlag": false,
        "usingTime": null,
        "expireTime": "2026-03-04 10:00:00",
        "status": 0,
        "remark": "月卡密钥",
        "createTime": "2026-02-04 10:00:00",
        "updateTime": "2026-02-04 10:00:00"
      }
    ]
  }
}
```

---

### 2. 添加项目密钥

**接口地址**: `POST /key/add`

**接口描述**: 添加新项目密钥（系统自动生成密钥）

**权限**: `business:key:add`

**请求参数**:

```json
{
  "projectId": 10000,
  "key": "ABC123XYZ789",
  "cycleType": "Month",
  "disabledFlag": false,
  "usingFlag": false,
  "usingTime": null,
  "expireTime": "2026-03-04 10:00:00",
  "remark": "月卡密钥"
}
```

**参数说明**:

| 参数名 | 类型 | 必填 | 说明 | 校验规则 |
|--------|------|------|------|----------|
| projectId | Long | 是 | 项目ID | - |
| key | String | 是 | 密钥（系统会自动生成，此字段会被覆盖） | 最多255字符 |
| cycleType | String | 是 | 密钥周期类型 | DAY、Month、quarter、Year |
| disabledFlag | Boolean | 是 | 是否禁用 | - |
| usingFlag | Boolean | 是 | 是否使用 | - |
| usingTime | LocalDateTime | 否 | 使用时间 | - |
| expireTime | LocalDateTime | 否 | 过期时间 | - |
| remark | String | 否 | 备注 | 最多255字符 |

**密钥格式**: 6位随机字符 + UUID(无横线)，共38位
- 示例：`ij9ei0a3b7c9d2e4f5g6h7i8j9k0l1m2n3o4`

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 3. 更新项目密钥

**接口地址**: `POST /key/update`

**接口描述**: 更新项目密钥信息

**权限**: `business:key:update`

**请求参数**:

```json
{
  "id": 1,
  "projectId": 10000,
  "key": "ABC123XYZ789",
  "cycleType": "Month",
  "disabledFlag": false,
  "usingFlag": true,
  "usingTime": "2026-02-04 10:00:00",
  "expireTime": "2026-03-04 10:00:00",
  "remark": "月卡密钥"
}
```

**参数说明**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 密钥ID |
| projectId | Long | 是 | 项目ID |
| key | String | 是 | 密钥（唯一） |
| cycleType | String | 是 | 密钥周期类型 |
| disabledFlag | Boolean | 是 | 是否禁用 |
| usingFlag | Boolean | 是 | 是否使用 |
| usingTime | LocalDateTime | 否 | 使用时间 |
| expireTime | LocalDateTime | 否 | 过期时间 |
| remark | String | 否 | 备注 |

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 4. 更新禁用/启用状态

**接口地址**: `GET /key/update/disabled/{id}`

**接口描述**: 切换项目密钥的禁用/启用状态

**权限**: `business:key:disabled`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 密钥ID |

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 5. 批量删除项目密钥

**接口地址**: `POST /key/update/batch/delete`

**接口描述**: 批量逻辑删除项目密钥

**权限**: `business:key:delete`

**请求参数**:

```json
[1, 2, 3]
```

**参数说明**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| - | Long[] | 是 | 密钥ID列表 |

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 6. 重置密钥

**接口地址**: `GET /key/reset/{id}`

**接口描述**: 重置指定密钥，自动生成新的随机密钥并替换原密钥

**权限**: `business:key:reset`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 密钥ID |

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "A3B7C9D2E4F5G6H7"
}
```

**说明**:
- 返回的 data 字段为新生成的密钥值
- 新密钥格式：6位随机字符 + UUID(无横线)，共38位
  - 示例：`ij9ei0a3b7c9d2e4f5g6h7i8j9k0l1m2n3o4`
  - 前6位：随机大小写字母+数字
  - 后32位：UUID（去除横线）
- 系统会自动确保新密钥不重复
- 重置后原密钥将失效

---

### 7. 使用密钥

**接口地址**: `POST /key/use`

**接口描述**: 使用密钥，更新使用状态、使用时间和过期时间

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| key | String | 是 | 密钥值 |

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "projectId": 10000,
    "projectName": "智慧办公APP",
    "key": "ij9ei0a3b7c9d2e4f5g6h7i8j9k0l1m2n3o4",
    "cycleType": "Month",
    "disabledFlag": false,
    "usingFlag": true,
    "usingTime": "2026-02-04 10:00:00",
    "expireTime": "2026-03-07 10:00:00",
    "remark": "月卡密钥",
    "createTime": "2026-02-04 09:00:00",
    "updateTime": "2026-02-04 10:00:00"
  }
}
```

**说明**:
- 使用密钥后，`usingFlag` 会更新为 `true`
- `usingTime` 会更新为当前时间
- `expireTime` 会根据密钥周期类型自动计算
- 密钥只能使用一次，已使用的密钥无法再次使用

**过期时间计算规则**:

| 周期类型 | 过期时间计算 |
|---------|-------------|
| DAY | 当前时间 + 24小时 |
| Month | 当前时间 + 31天(744小时) |
| quarter | 当前时间 + 93天(2232小时) |
| Year | 当前时间 + 372天(8928小时) |

**错误处理**:

| 错误信息 | 说明 |
|---------|------|
| 密钥不存在 | 输入的密钥不存在或已删除 |
| 密钥已被禁用 | 密钥已被管理员禁用 |
| 密钥已被使用 | 密钥已被使用过，无法重复使用 |
| 密钥周期类型无效 | 密钥的周期类型设置错误 |

---

## 数据字典

### 密钥周期类型 (cycleType)

| 值 | 说明 | 过期时间 |
|----|------|---------|
| DAY | 天卡 | 24小时 |
| Month | 月卡 | 31天(744小时) |
| quarter | 季卡 | 93天(2232小时) |
| Year | 年卡 | 372天(8928小时) |

### 状态标识

| 字段 | 值 | 说明 |
|------|----|------|
| disabledFlag | false | 启用 |
| disabledFlag | true | 禁用 |
| usingFlag | false | 未使用 |
| usingFlag | true | 已使用 |
| status | 0 | 正常 |
| status | 1 | 过期 |
| deletedFlag | false | 未删除 |
| deletedFlag | true | 已删除 |

---

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 401 | 未登录 |
| 403 | 无权限 |
| 404 | 数据不存在 |
| 500 | 服务器错误 |

---

## 注意事项

1. 所有接口返回格式统一为 `ResponseDTO`
2. 密钥（key）必须全局唯一，添加时会检查是否已存在
3. 删除操作为逻辑删除，不会真正删除数据
4. 查询接口默认只查询未删除的数据
5. 查询时会自动关联项目表获取项目名称
6. 需要登录后才能访问部分接口
7. 带权限标识的接口需要相应的权限才能访问

---

## 测试账号

使用 Knife4j 文档时，需要先登录获取 Token：

1. 访问 http://localhost:1024/doc.html
2. 点击右上角"文档管理" -> "安全认证"
3. 输入 Token（格式：`Bearer {token}`）
4. Token 可通过登录接口获取

---

## 数据库表结构

表名: `t_key`

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | BIGINT | 主键ID |
| project_id | BIGINT | 项目ID（外键关联t_project） |
| key | VARCHAR(255) | 密钥（唯一） |
| cycle_type | VARCHAR(255) | 密钥周期类型 |
| disabled_flag | TINYINT | 是否禁用 |
| deleted_flag | TINYINT | 是否删除 |
| using_flag | TINYINT | 是否使用 |
| using_time | DATETIME | 使用时间 |
| expire_time | TIMESTAMP | 过期时间 |
| status | TINYINT | 状态：0=正常,1=过期 |
| remark | VARCHAR(255) | 备注 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

---

## 关联表

表名: `t_project`（项目管理表）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | BIGINT | 主键ID |
| name | VARCHAR(255) | 项目名称 |
| mark | VARCHAR(255) | 项目标识 |
| version | VARCHAR(255) | 项目版本 |
| type | VARCHAR(255) | 项目类型 |
| sort | INT | 排序 |
| disabled_flag | TINYINT | 是否禁用 |
| deleted_flag | TINYINT | 是否删除 |
| remark | VARCHAR(255) | 备注 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |