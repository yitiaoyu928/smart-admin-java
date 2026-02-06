package net.lab1024.sa.admin.module.system.message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class AdminWebSocketController {

    @MessageMapping("/send")
    @SendTo("/topic/im-demo")
    public String send(String payload) {
        return payload;
    }
}
