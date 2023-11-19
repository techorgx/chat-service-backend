package com.techorgx.api.service

import com.techorgx.api.mapper.PayloadMapper
import io.lettuce.core.api.async.RedisAsyncCommands
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection
import org.springframework.stereotype.Service

@Service
class RedisService(
    private val redisAsyncCommands: RedisAsyncCommands<String, String>,
    private val payloadMapper: PayloadMapper,
    private val redisPubSubConnection: StatefulRedisPubSubConnection<String, String>
) {
    fun publishMessage(payloadString: String) {
        val payload = payloadMapper.mapPayload(payloadString)
        redisAsyncCommands.publish(payload.username, payload.message)
    }

    fun subscribeUser(username: String) {
        redisPubSubConnection.async().subscribe(username)
    }
}