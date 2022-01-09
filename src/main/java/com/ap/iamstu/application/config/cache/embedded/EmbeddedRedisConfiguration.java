package com.ap.iamstu.application.config.cache.embedded;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import redis.embedded.RedisExecProvider;
import redis.embedded.RedisServer;
import redis.embedded.util.OS;
import redis.embedded.util.OsArchitecture;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
@ConditionalOnExpression("${spring.redis.embedded:false}")
public class EmbeddedRedisConfiguration {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws IOException {
        String REDIS_BINARY_WINDOWS_PATH = "redis-server-5.0.10.exe";
        RedisExecProvider customProvider = RedisExecProvider.defaultProvider()
                .override(OS.WINDOWS, REDIS_BINARY_WINDOWS_PATH);
        OsArchitecture osArch = OsArchitecture.detect();
        if (OS.WINDOWS.equals(osArch.os())) {
            redisServer = RedisServer.builder().redisExecProvider(customProvider)
                    .port(redisPort)
                    .setting("bind 127.0.0.1")
                    .build();
        } else {
            redisServer = new RedisServer(customProvider, redisPort);
        }
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }
}
