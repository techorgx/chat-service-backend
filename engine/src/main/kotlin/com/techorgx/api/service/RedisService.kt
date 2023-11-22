package com.techorgx.api.service

import io.lettuce.core.api.async.RedisAsyncCommands
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class RedisService(
    private val redisAsyncCommands: RedisAsyncCommands<String, String>,
    private val redisPubSubConnection: StatefulRedisPubSubConnection<String, String>
) {
    @PostConstruct
    fun initialize() {
        subscribeUser()
    }

    fun publishMessage(payloadString: String) {
        redisAsyncCommands.publish(USER_TOPIC, payloadString)
    }

    fun subscribeUser() {
        redisPubSubConnection.async().subscribe(USER_TOPIC)
    }

    private companion object {
        private const val USER_TOPIC = "user-to-user-chat-channel"
    }
}