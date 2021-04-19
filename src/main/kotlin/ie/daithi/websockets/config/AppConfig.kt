package ie.daithi.websockets.config

import ie.daithi.websockets.model.WsMessage
import io.lettuce.core.RedisURI
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
@ComponentScan(basePackages = ["ie.daithi.websockets"])
class AppConfig(
    @Value("\${REDIS_URL}")
    private val redisUriString: String,
    @Value("\${REDIS_TLS_URL:#{null}}")
    private val redisTlsUriString: String?
    ) {

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {

        val redisUri = if (redisTlsUriString != null)
            RedisURI.create(redisTlsUriString)
        else
            RedisURI.create(redisUriString)

        val config = RedisStandaloneConfiguration()
        config.database = redisUri.database
        config.hostName = redisUri.host
        config.port = redisUri.port
        config.password = RedisPassword.of(redisUri.password)

        val clientConfig = if (redisUri.isSsl)
            LettuceClientConfiguration.builder().useSsl().build()
        else
            LettuceClientConfiguration.builder().build()

        return LettuceConnectionFactory(config, clientConfig)
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

    companion object {
        private val logger = LogManager.getLogger(AppConfig::class.java)
    }
}