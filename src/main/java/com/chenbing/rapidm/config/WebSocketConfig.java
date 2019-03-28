package com.chenbing.rapidm.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        config.enableSimpleBroker("/receive").setHeartbeatValue(new long[]{10000L,10000L}).setTaskScheduler(threadPoolTaskScheduler);
//        config.enableSimpleBroker("/receive");
        config.setApplicationDestinationPrefixes("/send");
        config.setUserDestinationPrefix("/receive");
//        config.setPathMatcher(new AntPathMatcher("/"));
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/insurance/ai/web-chat-endpoint").setAllowedOrigins("*").withSockJS();
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ImmutableMessageChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                //1、判断是否首次连接
//                if (StompCommand.CONNECT.equals(accessor.getCommand())){
//                    //2、判断用户名和密码
//                    String openid = accessor.getNativeHeader("openid").get(0);
//                    Principal principal = () -> openid;
//                    accessor.setUser(principal);
//                    return message;
//                }
//                //不是首次连接，已经登陆成功
//                return message;
//            }
//        });
//    }

    @EventListener
    public void onSessionDisconnectEvent(SessionDisconnectEvent event) {
        logger.info(JSONObject.toJSONString(event));
    }

    @EventListener
    public void onSessionSubscribeEvent(SessionSubscribeEvent event) {
        logger.info(JSONObject.toJSONString(event));
    }


//    @OnClose
//    public void registerStompEndpoint1s(StompEndpointRegistry registry) {
//        registry.addEndpoint("/insurance/ai/web-chat-endpoint").setAllowedOrigins("*").withSockJS();
//    }

}