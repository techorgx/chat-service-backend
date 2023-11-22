package com.techorgx.api.interceptor

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.util.MultiValueMap

class SessionChannelInterceptor: ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {

        val headers: MessageHeaders = message.headers;
        val accessor: StompHeaderAccessor = StompHeaderAccessor.wrap(message);

        val multiValueMap = headers[StompHeaderAccessor.NATIVE_HEADERS] as MultiValueMap<*, *>?
        logger.info(multiValueMap.toString())

        return message;
    }

    private companion object {
        val logger: Logger = LogManager.getLogger(SessionChannelInterceptor::class.qualifiedName)
    }
}