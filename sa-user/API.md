# SmartAdmin 用户端 API 接口文档

## 基础信息

- **服务名称**: sa-user
- **服务端口**: 1025
- **API 文档地址**: http://localhost:1025/doc.html
- **接口前缀**: `/`
- **认证方式**: Sa-Token (Bearer Token)
- **Token 请求头**: `Authorization: Bearer {token}`

## 通用响应格式

所有接口统一返回以下格式：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

**响应码说明**：
- `200`: 成功
- `4xx`: 用户端错误
- `5xx`: 服务端错误

---

## 用户认证模块

### 1. 用户注册

**接口地址**: `POST /user/auth/register`

**是否需要登录**: 否

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| email | String | 否 | 邮箱 |
| phone | String | 否 | 手机号（暂不支持） |
| password | String | 是 | 密码 |
| verificationCode | String | 是 | 邮箱验证码 |
| captchaCode | String | 否 | 图形验证码 |
| captchaUuid | String | 否 | 图形验证码UUID |

**请求示例**:

```json
{
  "email": "test@example.com",
  "password": "Test@1234",
  "verificationCode": "123456"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "注册成功",
  "data": null
}
```

---

### 2. 用户登录

**接口地址**: `POST /user/auth/login`

**是否需要登录**: 否

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| loginName | String | 是 | 登录账号（邮箱/手机号） |
| password | String | 是 | 密码 |
| captchaCode | String | 否 | 图形验证码 |
| captchaUuid | String | 否 | 图形验证码UUID |

**请求示例**:

```json
{
  "loginName": "test@example.com",
  "password": "Test@1234"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "token": "Bearer xxxxx",
    "userId": 1,
    "nickname": "test",
    "avatar": "",
    "level": 1
  }
}
```

---

### 3. 退出登录

**接口地址**: `GET /user/auth/logout`

**是否需要登录**: 是

**请求参数**: 无

**响应示例**:

```json
{
  "code": 200,
  "msg": "退出成功",
  "data": null
}
```

---

### 4. 发送邮箱验证码

**接口地址**: `POST /user/auth/send-code`

**是否需要登录**: 否

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| email | String | 是 | 邮箱地址 |

**请求示例**:

```json
{
  "email": "test@example.com"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "验证码已发送",
  "data": null
}
```

---

### 5. 重置密码

**接口地址**: `POST /user/auth/reset-password`

**是否需要登录**: 否

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| email | String | 是 | 邮箱地址 |
| code | String | 是 | 邮箱验证码 |
| newPassword | String | 是 | 新密码 |

**请求示例**:

```json
{
  "email": "test@example.com",
  "code": "123456",
  "newPassword": "NewPass@1234"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "密码重置成功",
  "data": null
}
```

---

## 个人信息模块

### 1. 获取个人信息

**接口地址**: `GET /user/info`

**是否需要登录**: 是

**请求参数**: 无

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "userId": 1,
    "nickname": "test",
    "avatar": "",
    "gender": 1,
    "birthday": "2000-01-01",
    "email": "test@example.com",
    "phone": "13800138000",
    "status": 1,
    "level": 1,
    "points": 0,
    "createTime": "2026-02-09T10:00:00",
    "lastLoginTime": "2026-02-09T10:00:00"
  }
}
```

**性别说明**：
- `1`: 男
- `2`: 女

**状态说明**：
- `0`: 禁用
- `1`: 启用

---

### 2. 修改个人信息

**接口地址**: `POST /user/update`

**是否需要登录**: 是

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| nickname | String | 否 | 昵称（最多64字符） |
| avatar | String | 否 | 头像URL |
| gender | Integer | 否 | 性别（1=男，2=女） |
| birthday | LocalDate | 否 | 生日 |

**请求示例**:

```json
{
  "nickname": "新昵称",
  "gender": 1,
  "birthday": "2000-01-01"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "更新成功",
  "data": null
}
```

---

### 3. 修改密码

**接口地址**: `POST /user/password/update`

**是否需要登录**: 是

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| oldPassword | String | 是 | 原密码 |
| newPassword | String | 是 | 新密码 |

**请求示例**:

```json
{
  "oldPassword": "OldPass@1234",
  "newPassword": "NewPass@1234"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "密码修改成功，请重新登录",
  "data": null
}
```

---

## 密钥管理模块

### 1. 激活密钥

**接口地址**: `POST /key/activate`

**是否需要登录**: 否

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| key | String | 是 | 密钥（最多255字符） |

**请求示例**:

```json
{
  "key": "ABC123xyz789"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "projectId": 1,
    "projectName": "测试项目",
    "key": "ABC123xyz789",
    "cycleType": "DAY",
    "disabledFlag": false,
    "usingFlag": true,
    "usingTime": "2026-02-09T10:00:00",
    "expireTime": "2026-02-10T10:00:00",
    "status": 0,
    "remark": "测试密钥",
    "createTime": "2026-02-09T10:00:00",
    "updateTime": "2026-02-09T10:00:00"
  }
}
```

**周期类型说明**：
- `DAY`: 天卡（24小时）
- `MONTH`: 月卡（31天）
- `QUARTER`: 季卡（93天）
- `YEAR`: 年卡（372天）

**状态说明**：
- `0`: 正常
- `1`: 过期

---

### 2. 查询密钥

**接口地址**: `GET /key/query`

**是否需要登录**: 否

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| key | String | 是 | 密钥 |
| projectId | Long | 是 | 项目ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "projectId": 1,
    "projectName": "测试项目",
    "key": "ABC123xyz789",
    "cycleType": "DAY",
    "disabledFlag": false,
    "usingFlag": true,
    "usingTime": "2026-02-09T10:00:00",
    "expireTime": "2026-02-10T10:00:00",
    "status": 0,
    "remark": "测试密钥",
    "createTime": "2026-02-09T10:00:00",
    "updateTime": "2026-02-09T10:00:00"
  }
}
```

---

### 3. 重置密钥

**接口地址**: `POST /key/reset`

**是否需要登录**: 否

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| key | String | 是 | 当前密钥 |
| projectId | Long | 是 | 项目ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "NewKey456xyz789"
}
```

---

### 4. 更新密钥

**接口地址**: `POST /key/update`

**是否需要登录**: 否

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| oldKey | String | 是 | 旧密钥 |
| newKey | String | 是 | 新密钥 |
| projectId | Long | 是 | 项目ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "NewKey456xyz789"
}
```

---

## IM 群组模块

### 1. 创建群组

**接口地址**: `POST /im/group/create`

**是否需要登录**: 是

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| groupName | String | 是 | 群组名称 |
| groupAvatar | String | 否 | 群组头像 |
| groupNotice | String | 否 | 群组公告 |

**请求示例**:

```json
{
  "groupName": "测试群组",
  "groupAvatar": "",
  "groupNotice": "欢迎加入"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "groupId": 1,
    "groupName": "测试群组",
    "groupAvatar": "",
    "groupNotice": "欢迎加入",
    "ownerId": "1",
    "memberCount": 1,
    "createTime": "2026-02-09T10:00:00"
  }
}
```

---

### 2. 更新群组

**接口地址**: `POST /im/group/update`

**是否需要登录**: 是

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| groupId | Long | 是 | 群组ID |
| groupName | String | 否 | 群组名称 |
| groupAvatar | String | 否 | 群组头像 |
| groupNotice | String | 否 | 群组公告 |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 3. 解散群组

**接口地址**: `GET /im/group/dissolve/{id}`

**是否需要登录**: 是

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 群组ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 4. 查询我的群组列表

**接口地址**: `GET /im/group/myGroups`

**是否需要登录**: 是

**请求参数**:

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| pageNum | Integer | 否 | 1 | 页码 |
| pageSize | Integer | 否 | 20 | 每页数量 |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "pageNum": 1,
    "pageSize": 20,
    "total": 1,
    "pages": 1,
    "list": [
      {
        "groupId": 1,
        "groupName": "测试群组",
        "groupAvatar": "",
        "groupNotice": "欢迎加入",
        "ownerId": "1",
        "memberCount": 1,
        "createTime": "2026-02-09T10:00:00"
      }
    ]
  }
}
```

---

### 5. 获取群组详情

**接口地址**: `GET /im/group/detail/{id}`

**是否需要登录**: 是

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 群组ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "groupId": 1,
    "groupName": "测试群组",
    "groupAvatar": "",
    "groupNotice": "欢迎加入",
    "ownerId": "1",
    "memberCount": 1,
    "createTime": "2026-02-09T10:00:00"
  }
}
```

---

## IM 群组成员模块

### 1. 邀请成员加入群组

**接口地址**: `POST /im/group/member/invite`

**是否需要登录**: 是

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| groupId | Long | 是 | 群组ID |
| memberIds | List\<String\> | 是 | 成员ID列表 |

**请求示例**:

```json
{
  "groupId": 1,
  "memberIds": ["2", "3", "4"]
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 2. 设置管理员

**接口地址**: `POST /im/group/member/setManager`

**是否需要登录**: 是

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| groupId | Long | 是 | 群组ID |
| memberId | String | 是 | 成员ID |
| managerFlag | Boolean | 是 | 是否为管理员 |

**请求示例**:

```json
{
  "groupId": 1,
  "memberId": "2",
  "managerFlag": true
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 3. 移除成员

**接口地址**: `GET /im/group/member/remove/{groupId}/{memberId}`

**是否需要登录**: 是

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| groupId | Long | 是 | 群组ID |
| memberId | String | 是 | 成员ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 4. 退出群组

**接口地址**: `GET /im/group/member/quit/{groupId}`

**是否需要登录**: 是

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| groupId | Long | 是 | 群组ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 5. 查询群组成员列表

**接口地址**: `GET /im/group/member/list/{groupId}`

**是否需要登录**: 是

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| groupId | Long | 是 | 群组ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "groupId": 1,
      "userId": "1",
      "nickname": "张三",
      "avatar": "",
      "managerFlag": true,
      "joinTime": "2026-02-09T10:00:00"
    }
  ]
}
```

---

### 6. 获取群组成员数量

**接口地址**: `GET /im/group/member/count/{groupId}`

**是否需要登录**: 是

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| groupId | Long | 是 | 群组ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": 10
}
```

---

## IM 消息模块

### 1. 发送消息

**接口地址**: `POST /im/message/send`

**是否需要登录**: 是

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| receiveId | String | 是 | 接收者ID |
| type | Integer | 是 | 消息类型（1=文本） |
| content | String | 是 | 消息内容 |

**请求示例**:

```json
{
  "receiveId": "2",
  "type": 1,
  "content": "你好"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "uId": "msg_123456",
    "fromId": "1",
    "receiveId": "2",
    "type": 1,
    "content": "你好",
    "readFlag": false,
    "revokeFlag": false,
    "createTime": "2026-02-09T10:00:00"
  }
}
```

**消息类型说明**：
- `1`: 文本消息

---

### 2. 撤回消息

**接口地址**: `POST /im/message/revoke`

**是否需要登录**: 是

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| uId | String | 是 | 消息ID |

**请求示例**:

```json
{
  "uId": "msg_123456"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 3. 删除消息

**接口地址**: `GET /im/message/delete/{uId}`

**是否需要登录**: 是

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| uId | String | 是 | 消息ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 4. 分页查询消息

**接口地址**: `POST /im/message/query`

**是否需要登录**: 是

**请求参数**:

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| pageNum | Integer | 否 | 1 | 页码 |
| pageSize | Integer | 否 | 20 | 每页数量 |
| otherUserId | String | 否 | - | 其他用户ID |
| groupId | Long | 否 | - | 群组ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "pageNum": 1,
    "pageSize": 20,
    "total": 1,
    "pages": 1,
    "list": [
      {
        "uId": "msg_123456",
        "fromId": "1",
        "receiveId": "2",
        "type": 1,
        "content": "你好",
        "readFlag": true,
        "revokeFlag": false,
        "createTime": "2026-02-09T10:00:00"
      }
    ]
  }
}
```

---

### 5. 查询对话消息列表

**接口地址**: `GET /im/message/chat/{otherUserId}`

**是否需要登录**: 是

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| otherUserId | String | 是 | 对方用户ID |

**请求参数**:

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| pageNum | Integer | 否 | 1 | 页码 |
| pageSize | Integer | 否 | 20 | 每页数量 |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "pageNum": 1,
    "pageSize": 20,
    "total": 1,
    "pages": 1,
    "list": [
      {
        "uId": "msg_123456",
        "fromId": "1",
        "receiveId": "2",
        "type": 1,
        "content": "你好",
        "readFlag": true,
        "revokeFlag": false,
        "createTime": "2026-02-09T10:00:00"
      }
    ]
  }
}
```

---

### 6. 标记消息为已读

**接口地址**: `POST /im/message/markAsRead/{otherUserId}`

**是否需要登录**: 是

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| otherUserId | String | 是 | 对方用户ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 7. 获取未读消息数

**接口地址**: `GET /im/message/unreadCount`

**是否需要登录**: 是

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": 5
}
```

---

## WebSocket 实时通讯

### WebSocket 连接

**连接地址**: `ws://localhost:1025/ws/im`

### 消息订阅

- **个人消息队列**: `/queue/chat/{userId}` - 订阅自己的消息
- **公共聊天室**: `/topic/chat` - 订阅公共广播

### 消息发送

- **发送消息**: `/app/chat/send`
- **撤回消息**: `/app/chat/revoke`

### 前端集成示例

```javascript
// 连接 WebSocket
const socket = new SockJS('http://localhost:1025/ws/im');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    // 订阅个人消息
    stompClient.subscribe('/queue/chat/' + userId, function (message) {
        handleMessage(JSON.parse(message.body));
    });
});

// 发送消息
stompClient.send('/app/chat/send', {}, JSON.stringify({
    receiveId: 'user2',
    type: 1,
    content: 'Hello'
}));

// 撤回消息
stompClient.send('/app/chat/revoke', {}, JSON.stringify({
    uId: 'message-id'
}));
```

---

## 错误码说明

### 通用错误码 (SystemErrorCode)

| 错误码 | 说明 |
|--------|------|
| 500 | 系统错误 |

### 用户错误码 (UserErrorCode)

| 错误码 | 说明 |
|--------|------|
| 400 | 参数错误 |
| 401 | 登录状态无效 |
| 403 | 无权限 |
| 1001 | 验证码错误或已失效 |
| 1002 | 账号或密码错误 |
| 1003 | 账号已被禁用 |
| 1004 | 该邮箱已被注册 |
| 1005 | 用户不存在 |
| 1006 | 昵称已存在 |
| 1007 | 密钥不存在 |
| 1008 | 密钥已被禁用 |
| 1009 | 密钥已被使用 |
| 1010 | 密钥已过期 |
| 1011 | 密钥周期类型无效 |
| 1012 | 密钥与项目不匹配 |

---

## 注意事项

1. **认证方式**: 所有需要登录的接口都需要在请求头中携带 `Authorization: Bearer {token}`
2. **时间格式**: 统一使用 ISO 8601 格式（如：`2026-02-09T10:00:00`）
3. **分页参数**: `pageNum` 从 1 开始
4. **防重复提交**: 部分接口标注了 `@RepeatSubmit`，防止重复提交
5. **密码复杂度**: 密码必须符合复杂度要求
6. **验证码有效期**: 邮箱验证码有效期为 5 分钟
7. **验证码冷却时间**: 同一邮箱发送验证码间隔不少于 60 秒

---

## 更新日志

| 版本 | 日期 | 说明 |
|------|------|------|
| 1.0.0 | 2026-02-09 | 初始版本，包含用户认证、个人信息、密钥管理、IM 模块 |