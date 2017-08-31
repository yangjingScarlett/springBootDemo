package com.example.demo.web;

import com.example.demo.domain.WiselyMessage;
import com.example.demo.domain.WiselyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author Yangjing
 */
@Controller
public class WsController {

    //点对点方式下，通过SimpMessagingTemplate向浏览器发送消息
    @Autowired
    private SimpMessagingTemplate MessagingTemplate;

    @MessageMapping("/welcome")//当浏览器向服务端发送请求时，映射到/welcome这个地址，类似于@RequestMapping
    @SendTo("/topic/getResponse")//当服务端有消息时，会对订阅了@SendTo中的路径的浏览器发送消息
    public WiselyResponse say(WiselyMessage message) throws InterruptedException {
        Thread.sleep(3000);
        return new WiselyResponse("Welcome, " + message.getName() + "!");
    }

    @MessageMapping("/chat")
    public void handleChat(Principal principal, String msg) {//在spring mvc中，可以直接在参数中获得Principal，它包含当前用户信息
        //下面是一段硬编码，如果发送人是wyf，则发送给wisely，反之也一样
        if (principal.getName().equals("wyf")) {
            //发送消息给用户，第一个参数是接收消息的用户，第二个是浏览器订阅的地址，第三个是消息本身
            MessagingTemplate.convertAndSendToUser("wisely", "/queue/notifications", principal.getName() + "-send:" + msg);
        } else {
            MessagingTemplate.convertAndSendToUser("wyf", "/queue/notifications", principal.getName() + "-send:" + msg);
        }

    }
}
