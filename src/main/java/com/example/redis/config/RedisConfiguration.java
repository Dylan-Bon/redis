package com.example.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisConfiguration {
    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.host}")
    private String redisHost;

    private RedisStandaloneConfiguration redisStandaloneConfiguration() {
        return new RedisStandaloneConfiguration(redisHost, redisPort);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory(redisStandaloneConfiguration());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>(); //used for automatic serialization/deserialization
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }


}
