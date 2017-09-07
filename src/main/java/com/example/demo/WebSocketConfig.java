package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author Yangjing
 * @explain 广播式
 * 广播式即在服务器端有消息时，将消息发送给所有连接了当前endpoint的浏览器
 */
@Configuration
@EnableWebSocketMessageBroker//开启使用STOMP协议来传输基于代理（message broker）的消息，这时控制器支持使用@MessageMapping就像使用@RequestMapping一样
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {//注册STOMP协议的节点（endpoint），并映射指定的url
        //SockJS是一个JavaScript库，提供跨浏览器JavaScript的API，创建了一个低延迟、全双工的浏览器和web服务器之间通信通道。
        registry.addEndpoint("/endpointWisely").withSockJS();//注册一个STOMP的endpoint，并指定使用SockJS协议
        registry.addEndpoint("/endpointChat").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {//MessageBroker：消息代理
        //广播式应配置一个/topic消息代理,表示在topic这个域上可以向客户端发消息
        //点对点式增加一个/queue消息代理
        registry.enableSimpleBroker("/topic","/queue");
    }
}
