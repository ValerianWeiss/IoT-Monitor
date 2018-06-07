package com.vuebackend.communication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${allowedOrigins}")
    private String[] allowedOrigins;

    @Value("${socketEndpoints}")
    private String[] socketEndpoints;

    @Value("${applicationDestinationPrefixes}")
    private String[] applicationDestinationPrefixes;

    @Value("${simpleBrokerBasePaths}")
    private String[] simpleBrokerBasePaths;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(simpleBrokerBasePaths);
        config.setApplicationDestinationPrefixes(applicationDestinationPrefixes);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(socketEndpoints).setAllowedOrigins(allowedOrigins).withSockJS();
    }
}