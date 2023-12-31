package com.techorgx.api.config

import com.techorgx.api.interceptor.SessionChannelInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@Configuration
@EnableWebSocketMessageBroker
open class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/chat-app")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(sessionChannelInterceptor())
    }

    @Bean
    open fun sessionChannelInterceptor(): SessionChannelInterceptor {
        return SessionChannelInterceptor()
    }
}
