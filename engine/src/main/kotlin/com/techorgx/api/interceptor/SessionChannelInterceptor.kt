package com.techorgx.api.interceptor

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.util.MultiValueMap

class SessionChannelInterceptor: ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {

        println("Channel Interceptor starts");

        val headers: MessageHeaders = message.headers;
        val accessor: StompHeaderAccessor = StompHeaderAccessor.wrap(message);

        val multiValueMap = headers[StompHeaderAccessor.NATIVE_HEADERS] as MultiValueMap<*, *>?
        multiValueMap?.forEach { (key, values) ->
            println("$key: ${values.joinToString()}")
        }

        println("Channel Interceptor ends");

        return message;
    }
}