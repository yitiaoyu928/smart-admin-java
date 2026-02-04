# 项目管理 API 文档

## 接口访问地址

- **Swagger UI**: http://localhost:1024/doc.html
- **分组**: 业务-项目管理

## 接口列表

### 1. 查询项目列表

**接口地址**: `POST /project/query`

**接口描述**: 查询项目列表（分页）

**请求参数**:

```json
{
  "pageNum": 1,
  "pageSize": 10,
  "keyword": "搜索关键词",
  "type": "APP",
  "disabledFlag": false,
  "deletedFlag": false
}
```

**参数说明**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| pageNum | Integer | 是 | 页码 |
| pageSize | Integer | 是 | 每页数量 |
| keyword | String | 否 | 搜索关键词（匹配项目名称或项目标识） |
| type | String | 否 | 项目类型（APP、WEB、Windows） |
| disabledFlag | Boolean | 否 | 是否禁用 |
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
        "id": 10000,
        "name": "智慧办公APP",
        "mark": "smart-office",
        "version": "v1.0.0",
        "type": "APP",
        "sort": 1,
        "disabledFlag": false,
        "remark": "企业智慧办公系统",
        "createTime": "2026-02-04 10:00:00",
        "updateTime": "2026-02-04 10:00:00"
      }
    ]
  }
}
```

---

### 2. 添加项目

**接口地址**: `POST /project/add`

**接口描述**: 添加新项目

**权限**: `business:project:add`

**请求参数**:

```json
{
  "name": "智慧办公APP",
  "mark": "smart-office",
  "version": "v1.0.0",
  "type": "APP",
  "sort": 1,
  "disabledFlag": false,
  "remark": "企业智慧办公系统"
}
```

**参数说明**:

| 参数名 | 类型 | 必填 | 说明 | 校验规则 |
|--------|------|------|------|----------|
| name | String | 是 | 项目名称 | 最多255字符 |
| mark | String | 是 | 项目标识（唯一） | 最多255字符 |
| version | String | 否 | 项目版本 | 最多255字符，默认v0.0.1 |
| type | String | 是 | 项目类型 | APP、WEB、Windows |
| sort | Integer | 是 | 排序 | - |
| disabledFlag | Boolean | 是 | 是否禁用 | - |
| remark | String | 否 | 备注 | 最多255字符 |

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 3. 更新项目

**接口地址**: `POST /project/update`

**接口描述**: 更新项目信息

**权限**: `business:project:update`

**请求参数**:

```json
{
  "id": 10000,
  "name": "智慧办公APP",
  "mark": "smart-office",
  "version": "v1.1.0",
  "type": "APP",
  "sort": 1,
  "disabledFlag": false,
  "remark": "企业智慧办公系统"
}
```

**参数说明**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 项目ID |
| name | String | 是 | 项目名称 |
| mark | String | 是 | 项目标识（唯一） |
| version | String | 否 | 项目版本 |
| type | String | 是 | 项目类型 |
| sort | Integer | 是 | 排序 |
| disabledFlag | Boolean | 是 | 是否禁用 |
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

**接口地址**: `GET /project/update/disabled/{id}`

**接口描述**: 切换项目的禁用/启用状态

**权限**: `business:project:disabled`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 项目ID |

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 5. 批量删除项目

**接口地址**: `POST /project/update/batch/delete`

**接口描述**: 批量逻辑删除项目

**权限**: `business:project:delete`

**请求参数**:

```json
[10000, 10001, 10002]
```

**参数说明**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| - | Long[] | 是 | 项目ID列表 |

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 6. 查询所有项目

**接口地址**: `GET /project/queryAll`

**接口描述**: 查询所有项目（不分页）

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| disabledFlag | Boolean | 否 | 是否禁用（不传则查询所有） |

**返回示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "id": 10000,
      "name": "智慧办公APP",
      "mark": "smart-office",
      "version": "v1.0.0",
      "type": "APP",
      "sort": 1,
      "disabledFlag": false,
      "remark": "企业智慧办公系统",
      "createTime": "2026-02-04 10:00:00",
      "updateTime": "2026-02-04 10:00:00"
    }
  ]
}
```

---

## 数据字典

### 项目类型 (type)

| 值 | 说明 |
|----|------|
| APP | 移动应用 |
| WEB | 网页应用 |
| Windows | 桌面应用 |

### 状态标识

| 字段 | 值 | 说明 |
|------|----|------|
| disabledFlag | false | 启用 |
| disabledFlag | true | 禁用 |
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
2. 项目标识（mark）必须全局唯一
3. 删除操作为逻辑删除，不会真正删除数据
4. 查询接口默认只查询未删除的数据
5. 需要登录后才能访问部分接口
6. 带权限标识的接口需要相应的权限才能访问

---

## 测试账号

使用 Knife4j 文档时，需要先登录获取 Token：

1. 访问 http://localhost:1024/doc.html
2. 点击右上角"文档管理" -> "安全认证"
3. 输入 Token（格式：`Bearer {token}`）
4. Token 可通过登录接口获取

---

## 数据库表结构

表名: `t_project`

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | BIGINT | 主键ID |
| name | VARCHAR(255) | 项目名称 |
| mark | VARCHAR(255) | 项目标识（唯一） |
| version | VARCHAR(255) | 项目版本 |
| type | VARCHAR(255) | 项目类型 |
| sort | INT | 排序 |
| disabled_flag | TINYINT | 是否禁用 |
| deleted_flag | TINYINT | 是否删除 |
| remark | VARCHAR(255) | 备注 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |