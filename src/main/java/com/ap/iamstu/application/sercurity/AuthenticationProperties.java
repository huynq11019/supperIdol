package com.ap.iamstu.application.sercurity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "security.authentication.jwt")
@Data
public class AuthenticationProperties {

    private String keyStore;

    private String keyStorePassword;

    private String keyAlias;

    private Duration accessTokenExpiresIn = Duration.ofHours(1);

    private Duration refreshTokenExpiresIn = Duration.ofDays(30);

    private Duration emailTokenExpiresIn = Duration.ofMinutes(10);

    @Value("${security.authentication.jwt.base64-secret}")
    private String base64Secret;

    @Value("${security.authentication.jwt.token-duration}")
    private int tokenDuration;

    @Value("${security.authentication.jwt.token-remember-me-duration}")
    private int tokenRememberMeDuration;

    @Value("${security.authentication.jwt.refesh-token-duration}")
    private int refeshTokenDuration;

    @Value("${security.authentication.jwt.data-duration}")
    private int dataDuration;

    @Value("${security.authentication.jwt.csrf-token-duration}")
    private int csrfTokenDuration;

    @Value("${security.authentication.cookie.domain-name}")
    private String domainName;

    @Value("${security.authentication.cookie.enable-ssl}")
    private boolean enableSsl;

    @Value("${security.authentication.cookie.path}")
    private String path;

    @Value("${security.authentication.cookie.http-only}")
    private boolean httpOnly;

    @Value("${security.authentication.cookie.sameSite}")
    private String sameSite;

    @Value("${security.cache.url-patterns}")
    private String[] urlPatterns;

    @Value("${security.cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${security.cors.allowed-methods}")
    private String allowedMethods;

    @Value("${security.cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${security.cors.exposed-headers}")
    private String exposedHeaders;

    @Value("${security.cors.allow-credentials}")
    private boolean allowCredentials;

    @Value("${security.cors.max-age}")
    private long maxAge;

//    @Value("${security.login.max-attempt-time}")
//    private int loginMaxAttemptTime;
//
//    @Value("${security.login.block-expired-after}")
//    private int loginBlockExpiredAfter;
}
