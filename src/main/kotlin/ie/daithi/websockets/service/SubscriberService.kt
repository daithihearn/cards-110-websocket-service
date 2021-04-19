package ie.daithi.websockets.service

import ie.daithi.websockets.model.WsMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.connection.ReactiveSubscription
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Service
import org.springframework.data.redis.core.ReactiveStringRedisTemplate

import org.springframework.data.redis.listener.ChannelTopic

import javax.annotation.PostConstruct

@Service
class SubscriberService(
    private val redisTemplate: ReactiveRedisOperations<String, WsMessage>,
    private val websocketService: WebsocketService,
    @Value("\${redis.topic}") private val topic: String) {

    @PostConstruct
    private fun init() {
        redisTemplate
            .listenTo(ChannelTopic.of(topic))
            .map(ReactiveSubscription.Message<String, WsMessage>::getMessage)
            .subscribe(websocketService::send)
    }
}