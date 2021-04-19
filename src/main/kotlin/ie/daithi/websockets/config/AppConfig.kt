package ie.daithi.websockets.config

import ie.daithi.websockets.model.WsMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory




@Configuration
@ComponentScan(basePackages = ["ie.daithi.websockets"])
class AppConfig(
    @Value("\${redis.database}") private val database: Int,
    @Value("\${redis.host}") private val host: String,
    @Value("\${redis.port}") private val port: Int,
    @Value("\${redis.password}") private val password: String
    ) {

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory? {
        val config = RedisStandaloneConfiguration()
        config.database = database
        config.hostName = host
        config.port = port
        config.password = RedisPassword.of(password)
        return LettuceConnectionFactory(config)
    }

    @Bean
    fun redisTemplate(reactiveRedisConnectionFactory: ReactiveRedisConnectionFactory): ReactiveRedisOperations<String, WsMessage> {
        val valueSerializer: RedisSerializer<WsMessage> = Jackson2JsonRedisSerializer(WsMessage::class.java)
        val serializationContext: RedisSerializationContext<String, WsMessage> =
            RedisSerializationContext.newSerializationContext<String, WsMessage>(RedisSerializer.string())
                .value(valueSerializer)
                .build()
        return ReactiveRedisTemplate(reactiveRedisConnectionFactory, serializationContext)
    }
}