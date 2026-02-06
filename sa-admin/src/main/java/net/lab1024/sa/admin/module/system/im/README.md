# IM 消息模块使用说明

## 功能特性

- ✅ 实时消息发送与接收（基于 WebSocket + STOMP）
- ✅ 消息撤回功能
- ✅ 消息删除功能
- ✅ 消息已读状态管理
- ✅ 对话历史查询
- ✅ 未读消息统计
- ✅ 支持多种消息类型（文本、图片、语音、视频、文件、系统消息）

## 数据库初始化

执行数据库脚本：
```bash
mysql -u root -p smart < mysql/t_im.sql
```

## API 接口

### 1. 发送消息
```
POST /im/message/send
Content-Type: application/json

{
  "receiveId": "user123",
  "type": 1,
  "content": "你好，这是一条消息"
}
```

### 2. 撤回消息
```
POST /im/message/revoke
Content-Type: application/json

{
  "uId": "消息ID"
}
```

### 3. 删除消息
```
GET /im/message/delete/{uId}
```

### 4. 分页查询消息
```
POST /im/message/query
Content-Type: application/json

{
  "fromId": "user123",
  "receiveId": "user456",
  "pageNum": 1,
  "pageSize": 20
}
```

### 5. 查询对话消息列表
```
GET /im/message/chat/{otherUserId}?pageNum=1&pageSize=20
```

### 6. 标记消息为已读
```
POST /im/message/markAsRead/{otherUserId}
```

### 7. 获取未读消息数
```
GET /im/message/unreadCount
```

## WebSocket 使用

### 连接 WebSocket

```javascript
// 使用 SockJS + STOMP
const socket = new SockJS('http://localhost:1024/ws/im');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    // 订阅个人消息队列
    stompClient.subscribe('/queue/chat/{userId}', function (message) {
        const data = JSON.parse(message.body);
        console.log('收到消息:', data);
    });
});
```

### 发送消息

```javascript
const message = {
    fromId: '当前用户ID',
    receiveId: '接收者ID',
    type: 1,  // 1-文本消息
    content: '消息内容'
};

stompClient.send('/app/chat/send', {}, JSON.stringify(message));
```

### 撤回消息

```javascript
const revokeData = {
    fromId: '当前用户ID',
    uId: '要撤回的消息ID'
};

stompClient.send('/app/chat/revoke', {}, JSON.stringify(revokeData));
```

## 消息类型枚举

| 值 | 类型 | 说明 |
|----|------|------|
| 1 | TEXT | 文本消息 |
| 2 | IMAGE | 图片消息 |
| 3 | VOICE | 语音消息 |
| 4 | VIDEO | 视频消息 |
| 5 | FILE | 文件消息 |
| 99 | SYSTEM | 系统消息 |

## 演示页面

启动应用后，访问：
```
http://localhost:1024/im-demo.html
```

## 目录结构

```
sa-base/src/main/java/net/lab1024/sa/base/module/support/im/
├── constant/
│   └── ImMessageTypeEnum.java        # 消息类型枚举
├── config/
│   └── WebSocketConfig.java          # WebSocket配置
├── dao/
│   └── ImMessageDao.java             # 数据访问层
├── domain/
│   ├── entity/
│   │   └── ImMessageEntity.java      # 消息实体
│   ├── form/
│   │   ├── ImMessageSendForm.java    # 发送表单
│   │   ├── ImMessageQueryForm.java   # 查询表单
│   │   └── ImMessageRevokeForm.java  # 撤回表单
│   └── vo/
│       └── ImMessageVO.java          # 消息VO
└── service/
    └── ImMessageService.java         # 消息服务

sa-admin/src/main/java/net/lab1024/sa/admin/module/system/im/
├── ImWebSocketController.java        # WebSocket控制器
└── controller/
    └── ImMessageController.java      # REST API控制器
```

## 注意事项

1. **用户ID**：当前使用 `StpUtil.getLoginIdAsString()` 获取当前登录用户ID
2. **消息ID**：使用 UUID 生成，确保唯一性
3. **外键约束**：`from_id` 和 `receive_id` 关联到 `t_employee.employee_uid`
4. **WebSocket端点**：`/ws/im`
5. **消息代理**：使用 `/topic` 和 `/queue` 作为消息代理前缀

## 依赖说明

项目已包含必要的依赖：
- Spring WebSocket
- STOMP Messaging
- SockJS Client（前端）

无需额外配置。