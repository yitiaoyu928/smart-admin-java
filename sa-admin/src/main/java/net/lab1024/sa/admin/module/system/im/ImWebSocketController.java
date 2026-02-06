package net.lab1024.sa.admin.module.system.im;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import net.lab1024.sa.base.module.support.im.domain.form.ImMessageSendForm;
import net.lab1024.sa.base.module.support.im.domain.vo.ImMessageVO;
import net.lab1024.sa.base.module.support.im.service.ImMessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * IM WebSocket控制器
 *
 * @Author 1024创新实验室
 * @Date 2026-02-06
 */
@Slf4j
@Controller
@Tag(name = "IM消息-WebSocket")
public class ImWebSocketController {

    @Resource
    private ImMessageService imMessageService;

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 发送消息（通过WebSocket）
     * 客户端发送到: /app/chat/send
     * 服务器广播到: /topic/chat
     */
    @MessageMapping("/chat/send")
    @SendTo("/topic/chat")
    public String sendMessage(String payload) {
        try {
            // 解析消息
            com.alibaba.fastjson.JSONObject json = JSON.parseObject(payload);
            String fromId = json.getString("fromId");
            String receiveId = json.getString("receiveId");
            Integer type = json.getInteger("type");
            String content = json.getString("content");

            // 构建发送表单
            ImMessageSendForm sendForm = new ImMessageSendForm();
            sendForm.setReceiveId(receiveId);
            sendForm.setType(type);
            sendForm.setContent(content);

            // 保存消息到数据库
            ResponseDTO<ImMessageVO> response = imMessageService.send(fromId, sendForm);
            if (!response.getOk()) {
                return JSON.toJSONString(ResponseDTO.error(response.getCode(), response.getMsg()));
            }

            ImMessageVO messageVO = response.getData();

            // 发送给接收者（点对点）
            String userDestination = "/queue/chat/" + receiveId;
            messagingTemplate.convertAndSend(userDestination, ResponseDTO.ok(messageVO));

            // 返回给发送者
            return JSON.toJSONString(ResponseDTO.ok(messageVO));

        } catch (Exception e) {
            log.error("发送IM消息失败", e);
            return JSON.toJSONString(ResponseDTO.error("发送失败: " + e.getMessage()));
        }
    }

    /**
     * 撤回消息（通过WebSocket）
     * 客户端发送到: /app/chat/revoke
     */
    @MessageMapping("/chat/revoke")
    public String revokeMessage(String payload) {
        try {
            // 解析消息
            com.alibaba.fastjson.JSONObject json = JSON.parseObject(payload);
            String uId = json.getString("uId");
            String userId = json.getString("fromId");

            // 撤回消息
            ResponseDTO<String> response = imMessageService.revoke(uId, userId);
            if (!response.getOk()) {
                return JSON.toJSONString(response);
            }

            // 通知撤回
            ImMessageVO revokeVO = new ImMessageVO();
            revokeVO.setUId(uId);
            revokeVO.setRevokeFlag(true);

            return JSON.toJSONString(ResponseDTO.ok(revokeVO));

        } catch (Exception e) {
            log.error("撤回IM消息失败", e);
            return JSON.toJSONString(ResponseDTO.error("撤回失败: " + e.getMessage()));
        }
    }
}