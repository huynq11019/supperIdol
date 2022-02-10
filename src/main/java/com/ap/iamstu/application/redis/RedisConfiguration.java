/**
 * 
 */
package com.ap.iamstu.application.redis;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author LinhLH
 *
 */
//@Configuration
@Getter
public class RedisConfiguration {
	@Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;
    
    @Value("${spring.redis.timeout}")
    private Duration timeout;
    
    @Bean
    @Primary
	JedisConnectionFactory jedisConnectionFactory() throws Exception {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
		
		JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration
				.builder();
		jedisClientConfiguration.connectTimeout(timeout);
		jedisClientConfiguration.usePooling();
		
		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
	}

    @Bean
    RedisTemplate< String, Object> redisTemplate() throws Exception {
        final RedisTemplate< String, Object> template = new RedisTemplate< String, Object>();
        
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());

        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        
        return template;
    }
}
