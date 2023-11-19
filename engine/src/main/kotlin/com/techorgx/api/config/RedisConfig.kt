package com.techorgx.api.config

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.async.RedisAsyncCommands
import io.lettuce.core.pubsub.RedisPubSubAdapter
import io.lettuce.core.pubsub.RedisPubSubListener
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import javax.annotation.PreDestroy


@Configuration
open class RedisConfig(
    @Value("\${redis.host}")
    private val redisHost: String,
    @Value("\${redis.port}")
    private val redisPort: Int
) {

    private lateinit var pubSubConnection: StatefulRedisPubSubConnection<String, String>
    private lateinit var connection: StatefulRedisConnection<String, String>
    private lateinit var redisClient: RedisClient

    @Bean
    @Order(1)
    open fun redisAsyncCommands(): RedisAsyncCommands<String, String> {
        redisClient = RedisClient.create("redis://$redisHost:$redisPort")
        connection = redisClient.connect()
        return connection.async()
    }

    @Bean
    @Order(2)
    open fun redisPubSubConnection(): StatefulRedisPubSubConnection<String, String> {
        pubSubConnection = redisClient.connectPubSub()
        pubSubConnection.addListener(listener)
        return pubSubConnection
    }

    private var listener: RedisPubSubListener<String, String> = object : RedisPubSubAdapter<String, String>() {
        override fun message(channel: String, message: String) {
            println(String.format("Channel: %s, Message: %s", channel, message))
        }
    }

    @PreDestroy
    fun onDestroy() {
        pubSubConnection.close()
        redisClient.shutdown()
    }
}